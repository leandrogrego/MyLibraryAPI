package br.net.serviceapp.mylibrary.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.net.serviceapp.mylibrary.api.Service.BookService;
import br.net.serviceapp.mylibrary.api.Service.UserService;
import br.net.serviceapp.mylibrary.api.entity.Book;
import br.net.serviceapp.mylibrary.api.entity.Response;
import br.net.serviceapp.mylibrary.api.entity.User;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("books")
public class BookResource {
   
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping
    public Response MyBooks(
        @RequestHeader(value="Authorization") String token
    ){
        User user = userService.getByToken(token);
        if(user != null){
            if(user.getBooks().size() == 0) return new Response(true, "Lista vazia!", user.getBooks());
            return new Response(true, "Lista com "+user.getBooks().size()+" livros", user.getBooks());
        }
        return new Response(false, "Login inválido!", null);
    }

    @PostMapping
    public Response add(
        @RequestHeader(value="Authorization") String token,
        @RequestBody Book book
    ){
        User user = userService.getByToken(token);
        if(user != null){
            if(!checkBookByProviderId(user, book)){
                user.addBook(book);
                user = userService.save(user);
                book = user.getBooks().get(user.getBooks().size() - 1);
                return new Response(true, "Livro Adicionado com sucesso!", book);
            }
            return new Response(true, "Livro já adicionado!", book);
        }
        return new Response(false, "Login inválido!", null);
    }

    @PutMapping
    public Response update(
        @RequestHeader(value="Authorization") String token,
        @RequestBody Book book
    ){
        User user = userService.getByToken(token);
        if(user != null ){
            Book b = bookService.getById(book.getId());
            int index = user.getBooks().indexOf(b);
            if(index != -1){
                user.getBooks().set(index, book);
                userService.save(user);
                return new Response(true, "Livro salvo com sucesso!", book);
            }
            return new Response(false, "Livro não localizado na sua lista!", false);
        } 
        return new Response(false, "Login inválido!", null);
    }

    @DeleteMapping
    public Response remove(
        @RequestHeader(value="Authorization") String token,
        @RequestParam Long bookId
    ){
        User user = userService.getByToken(token);
        if(user != null){
            Book book = bookService.getById(bookId);
            if(user != null && book != null){
                user.getBooks().remove(book);
                userService.save(user);
                return new Response(true, "Livro removido com sucesso!", book);
            }
            return new Response(false, "Livro não encontrado na sua lista!", null);
        }
        return new Response(false, "Login inválido!", null);
    }

    private boolean checkBookByProviderId(User user, Book book){
        for(Book b : user.getBooks()) {
            if (b.getProviderId() == book.getProviderId()) return true; 
        }
        return false;
    }

}
