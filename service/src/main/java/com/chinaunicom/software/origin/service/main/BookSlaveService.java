package com.chinaunicom.software.origin.service.main;

import com.aio.portable.swiss.suite.storage.db.jpa.JPASugar;
import com.chinaunicom.software.origin.db.slave.mapper.BookSlaveRepository;
import com.chinaunicom.software.origin.db.slave.model.Book;
import com.chinaunicom.software.origin.db.slave.vo.BookCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookSlaveService {
    @Autowired(required = false)
    BookSlaveRepository bookSlaveRepository;

    public List<Book> findBy(BookCondition condition) {
        Specification<Book> bookSpecification = JPASugar.<Book>buildSpecification(condition);
        Sort sort = JPASugar.buildSort(BookCondition.class);

        List<Book> all = bookSlaveRepository.findAll(bookSpecification, sort);
        return all;
    }
}
