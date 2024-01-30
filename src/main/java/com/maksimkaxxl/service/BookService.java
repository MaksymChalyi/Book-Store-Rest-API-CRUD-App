package com.maksimkaxxl.service;

import com.maksimkaxxl.entity.BookEntity;
import com.maksimkaxxl.dao.BookDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BookService {


    private static final BookService INSTANCE = new BookService();

    private final BookDao bookDao = BookDao.getInstance();

    public static BookService getInstance() {
        return INSTANCE;
    }

    private BookService() {
    }

    public List<BookEntity> findAllBooks() {
        return bookDao.findAll();
    }


    public Optional<BookEntity> findBookById(Integer id) {
        return bookDao.findById(id);
    }

    public Optional<BookEntity> findByTitle(String title) {
        return bookDao.findByTitle(title);
    }

    public BookEntity save(BookEntity bookEntity) throws SQLException {
        return bookDao.save(bookEntity);
    }

    public boolean delete(Integer id) {
        return bookDao.delete(id);
    }

    public void update(BookEntity entity) {
        bookDao.update(entity);
    }


}
