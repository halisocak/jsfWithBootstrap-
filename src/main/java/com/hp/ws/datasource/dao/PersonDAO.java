package com.hp.ws.datasource.dao;

import java.util.List;

import com.hp.ws.datasource.entity.Person;
 
public interface PersonDAO {
 
    public void addPerson(Person p);
    public List<Person> listPersons();
}