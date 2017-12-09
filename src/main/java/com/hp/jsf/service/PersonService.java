package com.hp.jsf.service;

import java.util.List;

import javax.faces.bean.ManagedBean;

import com.hp.ws.datasource.entity.Person;

public interface PersonService {
 
    public void addPerson(Person p);
    public List<Person> listPersons();
     
}