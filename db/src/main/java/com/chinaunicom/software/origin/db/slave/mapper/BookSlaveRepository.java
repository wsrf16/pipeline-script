package com.chinaunicom.software.origin.db.slave.mapper;

import com.aio.portable.swiss.suite.storage.rds.jpa.BaseJpaRepository;
import com.chinaunicom.software.origin.db.slave.model.Book;


public interface BookSlaveRepository extends BaseJpaRepository<Book, Long> {

}
