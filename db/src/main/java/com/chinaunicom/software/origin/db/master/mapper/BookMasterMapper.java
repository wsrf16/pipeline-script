package com.chinaunicom.software.origin.db.master.mapper;

import com.aio.portable.swiss.suite.storage.rds.freedatasource.TargetDataSource;
import com.chinaunicom.software.origin.db.master.model.Book;

import java.util.List;


@TargetDataSource("master")
public interface BookMasterMapper {
    List<Book> getAll();

    Book get(int id);

    int insert(Book book);
}
