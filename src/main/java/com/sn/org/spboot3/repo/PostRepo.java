package com.sn.org.spboot3.repo;

import com.sn.org.spboot3.model.Person;
import com.sn.org.spboot3.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {

    List<Post> findAllByAuthor(Person person);
}

