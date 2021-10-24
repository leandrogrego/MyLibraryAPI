package br.net.serviceapp.mylibrary.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.net.serviceapp.mylibrary.api.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	public Book findByToken(Long id);

}
