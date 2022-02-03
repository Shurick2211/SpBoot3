package com.sn.org.spboot3.repo;

import com.sn.org.spboot3.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepo extends JpaRepository<Person,Long> {

  Person getByLogin(String login);
}
