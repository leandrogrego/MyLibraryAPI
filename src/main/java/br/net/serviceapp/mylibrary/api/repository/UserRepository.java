package br.net.serviceapp.mylibrary.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.net.serviceapp.mylibrary.api.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value="SELECT * FROM `user` WHERE `token` = ? LIMIT 1", nativeQuery=true)
	public User findByToken(String token);
	
	@Query(value="SELECT * FROM `user` WHERE `social_id` = ? AND provider = ? LIMIT 1", nativeQuery=true)
	public User findBySocialIdAndProvider(String socialId, String provider);
	
	@Query(value="SELECT * FROM `user` WHERE `number` = ? LIMIT 1", nativeQuery=true)
	public User findByNumber(String number);

    @Query(value="SELECT * FROM `user` WHERE `email` = ? LIMIT 1", nativeQuery=true)
	public User findByEmailIgnoreCaseContaining(String email);

    @Query(value="SELECT * FROM `user` WHERE `name` = %?% LIMIT 1", nativeQuery=true)
	public List<User> findByNameIgnoreCaseContaining(String name);

	public List<User> findByType(String type);

}
