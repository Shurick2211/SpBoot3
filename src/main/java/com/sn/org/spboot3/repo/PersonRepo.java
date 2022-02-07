package com.sn.org.spboot3.repo;

import com.sn.org.spboot3.model.Person;
import com.sn.org.spboot3.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepo extends JpaRepository<Person,Long> {

  Person getByLogin(String login);
  Person getByName(String name);
}
