package com.tlkm.broadcast5g.repository.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Set;

@Repository
public class CSVRepositoryImpl implements CSVCustomRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public Set<String> findByFileName(Date sentDate) {
       // entityManager.createQuery("")

       // entityManager.createNativeQuery("select file_name from csv where ")
        return null;
    }
}
