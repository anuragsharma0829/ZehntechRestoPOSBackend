package com.restopos.repository;

import java.util.Optional;

import com.restopos.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("Select u from User u where u.username=:username")
    public User getUserByUserName(@Param("username") String username);

    Optional<User>findByToken(String token);
    Optional<User>findByEmail(String token);


    Boolean existsBytoken(String token);
    public Optional<User> findById(Long id);

    User findByCity(String city);

//    @Query(value="select u.username from user_roles ur join users u on u.id=ur.user_id join roles r on r.id=ur.role_id where r.id=6; ", nativeQuery = true)
//    String getWatters( );


}