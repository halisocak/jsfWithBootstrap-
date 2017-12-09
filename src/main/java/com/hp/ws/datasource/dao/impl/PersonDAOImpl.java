package com.hp.ws.datasource.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hp.ws.datasource.dao.PersonDAO;
import com.hp.ws.datasource.entity.Person;

@Repository
public class PersonDAOImpl extends BaseDaompl implements PersonDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(PersonDAOImpl.class);

 
    @Override
    public void addPerson(Person p) {
        entityManager.persist(p);
        logger.info("Person saved successfully, Person Details="+p);
    }
 
    @Override
    public List<Person> listPersons() {
        
        return  findAll(Person.class);        
    } 
}
