package com.maksimkaxxl.dao;

import com.maksimkaxxl.Entity.AuthorEntity;
import com.maksimkaxxl.util.ConnectionManager;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorDao implements Dao<Integer, AuthorEntity> {

    private static final String SAVE_AUTHOR = """
            INSERT INTO authour(id, first_name, last_name, phone, email) 
            VALUES (?, ?, ?, ?, ?)
            """;
    private static final String FIND_ALL = """
            SELECT * FROM authour ;
            """;

    private static final String FIND_BY_ID = """
            SELECT * FROM authour WHERE id = ?;
            """;



    private static final AuthorDao INSTANCE = new AuthorDao();

    public static AuthorDao getInstance() {
        return INSTANCE;
    }

    private AuthorDao() {
    }

    @Override
    public List<AuthorEntity> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<AuthorEntity> authors = new ArrayList<>();
            while (resultSet.next()) {
                authors.add(buildAuthors(resultSet));
            }
            return authors;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private AuthorEntity buildAuthors(ResultSet resultSet) throws SQLException {
        return new AuthorEntity(
                resultSet.getObject("id", Integer.class),
                resultSet.getObject("first_name", String.class),
                resultSet.getObject("last_name", String.class),
                resultSet.getObject("phone", String.class),
                resultSet.getObject("email", String.class));
    }

    @Override
    public Optional<AuthorEntity> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildAuthors(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public void update(AuthorEntity entity) {

    }

    @SneakyThrows
    @Override
    public AuthorEntity save(AuthorEntity entity) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_AUTHOR)) {
            preparedStatement.setObject(1, entity.getId());
            preparedStatement.setObject(2, entity.getFirstName());
            preparedStatement.setObject(3, entity.getLastName());
            preparedStatement.setObject(4, entity.getPhone());
            preparedStatement.setObject(5, entity.getEmail());

            preparedStatement.executeUpdate();
            return entity;
        }
    }
}
