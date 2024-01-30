package com.maksimkaxxl.service;

import com.maksimkaxxl.entity.AuthorEntity;
import com.maksimkaxxl.dao.AuthorDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AuthorService {

    private static final AuthorService INSTANCE = new AuthorService();

    private final AuthorDao authorDao = AuthorDao.getInstance();

    private AuthorService() {
    }

    public List<AuthorEntity> findAllAuthors() {
        return authorDao.findAll();
    }

    public Optional<AuthorEntity> findAuthorById(Integer id) {
        return authorDao.findById(id);
    }

    public AuthorEntity save(AuthorEntity authorEntity) throws SQLException {
        return authorDao.save(authorEntity);
    }


    public static AuthorService getINSTANCE() {
        return INSTANCE;
    }
}
