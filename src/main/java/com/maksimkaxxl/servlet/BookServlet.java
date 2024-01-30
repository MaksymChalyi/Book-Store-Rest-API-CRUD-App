package com.maksimkaxxl.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maksimkaxxl.entity.BookEntity;
import com.maksimkaxxl.service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/books/*")
public class BookServlet extends HttpServlet {

    private final BookService bookService = BookService.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String idParameter = req.getParameter("id");
        String titleParam = req.getParameter("title");

        if (idParameter != null && !idParameter.isEmpty()) {
            try {
                int bookId = Integer.parseInt(idParameter);
                Optional<BookEntity> bookById = bookService.findBookById(bookId);
                if (bookById.isPresent()) {
                    objectMapper.writeValue(resp.getWriter(), bookById.get());
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().print("Book not found!");
                }

            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Invalid id format!");
            }
        } else if (titleParam != null && !titleParam.isEmpty()) {
            try {
                Optional<BookEntity> bookByTitle = bookService.findByTitle(titleParam);
                if (bookByTitle.isPresent()) {
                    objectMapper.writeValue(resp.getWriter(), bookByTitle.get());
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().print("Book not found!");
                }

            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Invalid title format! Use text(String)");
            }
        } else {
            List<BookEntity> books = bookService.findAllBooks();
            objectMapper.writeValue(resp.getWriter(), books);
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String idParam = req.getParameter("id");
        String titleParam = req.getParameter("title");
        String publication_yearParam = req.getParameter("publication_year");
        String genreParam = req.getParameter("genre");
        String author_idParam = req.getParameter("author_id");

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int bookId = Integer.parseInt(idParam);
                int publication_year = Integer.parseInt(publication_yearParam);
                int author_id = Integer.parseInt(author_idParam);

                    BookEntity newBook = new BookEntity();
                    newBook.setId(bookId);
                    newBook.setTitle(titleParam);
                    newBook.setPublication_year(publication_year);
                    newBook.setGenre(genreParam);
                    newBook.setAuthor_id(author_id);

                    bookService.save(newBook);
                    objectMapper.writeValue(resp.getWriter(), newBook);

            } catch (NumberFormatException | SQLException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Invalid id or year format!");
            }
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String idParam = req.getParameter("id");
        try {
            if (idParam != null && !idParam.isEmpty()) {

                int bookId = Integer.parseInt(idParam);
                boolean IsDeleted = bookService.delete(bookId);

                if (IsDeleted) {
                    resp.getWriter().write("{\"status:\":\"success\"}");
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("{\"error:\":\"Something went wrong! Maybe missing book id\"}");
                }

            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error:\":\"Invalid book id\"}");

        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String idParam = req.getParameter("id");
        String title = req.getParameter("title");
        String publication_yearParam = req.getParameter("publication_year");
        String genre = req.getParameter("genre");
        String author_idParam = req.getParameter("author_id");

        try {
            if (idParam != null && !idParam.isEmpty()) {

                int bookId = Integer.parseInt(idParam);
                int author_id = Integer.parseInt(author_idParam);
                int publication_year = Integer.parseInt(publication_yearParam);
                Optional<BookEntity> existingBook = bookService.findBookById(bookId);
                if (existingBook.isPresent()) {
                    BookEntity newBook = existingBook.get();
                    newBook.setId(bookId);
                    newBook.setTitle(title);
                    newBook.setPublication_year(publication_year);
                    newBook.setGenre(genre);
                    newBook.setAuthor_id(author_id);

                    bookService.update(newBook);
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("{\"error:\":\"Something went wrong! Maybe missing book id\"}");
                }

            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error:\":\"Invalid book id\"}");


        }
    }
}

