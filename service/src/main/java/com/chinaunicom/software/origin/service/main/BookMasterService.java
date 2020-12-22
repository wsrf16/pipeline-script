package com.chinaunicom.software.origin.service.main;

import com.chinaunicom.software.origin.db.master.mapper.BookMasterMapper;
import com.chinaunicom.software.origin.db.master.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookMasterService {

    @Autowired(required = false)
    BookMasterMapper bookMasterMapper;

    public Book get(int id) {
        return bookMasterMapper.get(id);
    }
}
