package com.chinaunicom.software.origin.db.slave.mapper;

import com.chinaunicom.software.origin.db.slave.model.Book;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;


public interface BookSlaveRepository extends JpaRepositoryImplementation<Book, Long> {

}
