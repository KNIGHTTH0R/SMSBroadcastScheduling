package com.tlkm.broadcast5g.repository.database;


import com.tlkm.broadcast5g.model.UploadHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Set;

@Repository
public interface UploadHistoryRepository extends CrudRepository<UploadHistory,Long> {

    Set<UploadHistory> findByUploadDate(Date date);
}
