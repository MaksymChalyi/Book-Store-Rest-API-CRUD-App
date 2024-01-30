package com.maksimkaxxl.dao;

import com.maksimkaxxl.entity.BookEntity;
import com.maksimkaxxl.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDao implements Dao<Integer, BookEntity> {

    private static final String FIND_ALL_BOOKS = """
            SELECT * FROM books;
            """;

    private static final String FIND_BOOK_BY_ID = """
            SELECT * FROM books WHERE id = ?;
            """;

    private static final String FIND_BOOK_BY_TITLE = """
            SELECT * FROM books WHERE title = ?;
            """;

    private static final String SAVE_BOOK = """
            INSERT INTO books(id, title, publication_year, genre, author_id) 
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String DELETE_BOOK_BY_ID = """
            DELETE FROM books
            WHERE id = ?
            """;

    public static final String UPDATE_BOOK_BY_ID = """
            UPDATE books
            SET title            = ?,
                publication_year = ?,
                genre            = ?,
                author_id        = ?
            WHERE id = ?;
                        """;


    private static final BookDao INSTANCE = new BookDao();

    public static BookDao getInstance() {
        return INSTANCE;
    }

    private BookDao() {
    }


    @Override
    public List<BookEntity> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BOOKS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BookEntity> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(buildBooks(resultSet));
            }

            return books;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<BookEntity> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BOOK_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return Optional.of(buildBooks(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public Optional<BookEntity> findByTitle(String title) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BOOK_BY_TITLE)) {
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return Optional.of(buildBooks(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK_BY_ID)) {
            preparedStatement.setInt(1, id);
            var result = preparedStatement.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(BookEntity entity) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK_BY_ID)) {
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setInt(2, entity.getPublication_year());
            preparedStatement.setString(3, entity.getGenre());
            preparedStatement.setInt(4, entity.getAuthor_id());
            preparedStatement.setInt(5, entity.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public BookEntity save(BookEntity entity) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_BOOK)) {
            preparedStatement.setObject(1, entity.getId());
            preparedStatement.setObject(2, entity.getTitle());
            preparedStatement.setObject(3, entity.getPublication_year());
            preparedStatement.setObject(4, entity.getGenre());
            preparedStatement.setObject(5, entity.getAuthor_id());

            preparedStatement.executeUpdate();
            return entity;
        }
    }

    private BookEntity buildBooks(ResultSet resultSet) throws SQLException {
        Integer authorId = resultSet.getInt("author_id");
        return new BookEntity(
                resultSet.getObject("id", Integer.class),
                resultSet.getObject("title", String.class),
                resultSet.getObject("publication_year", Integer.class),
                resultSet.getObject("genre", String.class),
                authorId);
    }

}
