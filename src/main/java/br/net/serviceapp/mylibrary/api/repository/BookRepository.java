package br.net.serviceapp.mylibrary.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.net.serviceapp.mylibrary.api.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	@Query(value="SELECT * FROM `livro` WHERE `user_id` = ?", nativeQuery=true)
	public List<Book> findByUser(Long id);

	public Optional<Book> findByProviderId(String id);

}
