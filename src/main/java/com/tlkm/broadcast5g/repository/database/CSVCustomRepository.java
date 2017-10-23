package com.tlkm.broadcast5g.repository.database;

import java.util.Set;

import java.util.Date;

public interface CSVCustomRepository {


    Set<String> findByFileName(Date sentDate);
}
