package br.net.serviceapp.mylibrary.api.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.serviceapp.mylibrary.api.entity.Book;
import br.net.serviceapp.mylibrary.api.repository.BookRepository;

@Service
public class BookService {
    
    @Autowired
    private BookRepository repository;

    public Book save(Book book){
        return repository.save(book);
    }

    public void update(Book book){
        if(book.getId() > 0)
            repository.save(book);
    }

    public void delete(Long id){
       repository.deleteById(id);
    }

    public List<Book> listAll(){
        return repository.findAll();
    }

    public Book getById(Long id){
        Optional<Book> book = repository.findById(id);
        if(book.isPresent())
        return book.get();
        else return null;
    }

    
    public Book getByPtoviderId(String id){
        Optional<Book> book = repository.findByProviderId(id);
        if(book.isPresent())
        return book.get();
        else return null;
    }

    public List<Book> getByUser(Long id){
        return repository.findByUser(id);
    }

}