package com.crud.product.repositories;

import com.crud.product.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("SELECT u FROM User u where u.username = :username")
    public User getUserByUsername(@Param("username") String username);
}
