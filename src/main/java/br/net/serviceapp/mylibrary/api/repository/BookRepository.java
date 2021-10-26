package br.net.serviceapp.mylibrary.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.net.serviceapp.mylibrary.api.entity.Livro;

public interface BookRepository extends JpaRepository<Livro, Long> {

	@Query(value="SELECT * FROM `livro` WHERE `user_id` = ?", nativeQuery=true)
	public List<Livro> findByUser(Long id);

}
