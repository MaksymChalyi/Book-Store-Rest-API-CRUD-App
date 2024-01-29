package com.maksimkaxxl.servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.maksimkaxxl.Entity.AuthorEntity;
import com.maksimkaxxl.service.AuthorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/authors/*")
public class AuthorServlet extends HttpServlet {

    private final AuthorService authorService = AuthorService.getINSTANCE();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String idParameter = req.getParameter("id");

        if (idParameter != null && !idParameter.isEmpty()) {
            try {
                int authorId = Integer.parseInt(idParameter);
                Optional<AuthorEntity> authorById = authorService.findAuthorById(authorId);
                if (authorById.isPresent()) {
                    objectMapper.writeValue(resp.getWriter(), authorById.get());
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().print("Authors not found!");
                }

            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Invalid id format!");
            }
        } else {
            List<AuthorEntity> authors = authorService.findAllAuthors();
            objectMapper.writeValue(resp.getWriter(), authors);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String idParam = req.getParameter("id");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");

        if (idParam != null && !idParam.isEmpty()) {
            try {
                AuthorEntity newAuthorEntity = new AuthorEntity();
                newAuthorEntity.setId(Integer.parseInt(idParam));
                newAuthorEntity.setFirstName(firstName);
                newAuthorEntity.setLastName(lastName);
                newAuthorEntity.setPhone(phone);
                newAuthorEntity.setEmail(email);
                authorService.save(newAuthorEntity);

                objectMapper.writeValue(resp.getWriter(), newAuthorEntity);

            } catch (NumberFormatException | SQLException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error:\":\"Invalid author id\"}");
            }
        }


    }
}
