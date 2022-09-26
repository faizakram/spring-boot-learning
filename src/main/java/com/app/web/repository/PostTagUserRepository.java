package com.app.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.web.model.PostTagUser;

@Repository
public interface PostTagUserRepository extends JpaRepository<PostTagUser, Long> {

}
