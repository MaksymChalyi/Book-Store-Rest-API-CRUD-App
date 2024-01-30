# Book Management REST Service

## Project Overview

The "Book Management REST Service" project provides functionality to perform operations on authors and books through a REST interface. The server-side is implemented using Java Servlets, JDBC, and PostgreSQL for the database, while the client-side uses POSTMAN for interaction with the server.

## Data Structure

### Author:

- id (Unique identifier for the author)
- First Name
- Last Name
- Phone
- Email

### Book:

- id (Unique identifier for the book)
- Title
- Publication Year
- Genre
- Author's id (Foreign key)

## Functionality

### Author:

1. Retrieve all authors
2. Retrieve author by id
3. Add a new author

### Book:

1. Retrieve book by id
2. Retrieve book by title
3. Add a new book
4. Update book information
5. Delete a book

## Implementation

### Server:

1. Create PostgreSQL database with "books" and "authors" tables.
2. Utilize the `AuthorEntity` and `BookEntity` classes to represent authors and books.
3. Develop the `BookServlet` servlet to handle REST requests.

### Client:

1. Use POSTMAN for sending REST requests to the server.

## Implementation Details

### Server:

1. Set up the PostgreSQL database and tables.
2. Use JDBC for database interaction.
3. Employ the Jackson library for working with JSON format.
4. Use Tomcat v8.5.89 as the server.

### Client:

1. Utilize POSTMAN for sending HTTP requests.

## Request Examples

### Author:

1. Retrieve all authors:
   ```
   GET http://localhost:8082/BookStoreRestAPICRUD/authors
   ```
   ![authors_GET_ALL.png](https://github.com/MaksymChalyi/Book-Store-Rest-API-CRUD-App/blob/master/images/authors_GET_ALL.png)

2. Retrieve author by id:
   ```
   GET http://localhost:8082/BookStoreRestAPICRUD/authors?id=1
   ```
   ![authors_GET_BY_ID.png](https://github.com/MaksymChalyi/Book-Store-Rest-API-CRUD-App/blob/master/images/authors_GET_BY_ID.png)

3. Add a new author:
   ```
   POST http://localhost:8082/BookStoreRestAPICRUD/authors?id=8&firstName=Sergiy&lastName=Yastrubovich&phone=380950687656&email=sergiyyastr@gmail.com
   ```
   ![authors_POST_NEW_AUTHOR.png](https://github.com/MaksymChalyi/Book-Store-Rest-API-CRUD-App/blob/master/images/authors_POST_NEW_AUTHOR.png)

### Book:

1. Retrieve all books:
   ```
   GET http://localhost:8082/BookStoreRestAPICRUD/books
   ```
   ![books_GET_ALL.png](https://github.com/MaksymChalyi/Book-Store-Rest-API-CRUD-App/blob/master/images/books_GET_ALL.png)

2. Retrieve book by id:
   ```
   GET http://localhost:8082/BookStoreRestAPICRUD/books?id=2
   ```
   ![books_GET_BY_ID.png](https://github.com/MaksymChalyi/Book-Store-Rest-API-CRUD-App/blob/master/images/books_GET_BY_ID.png)

3. Retrieve book by title:
   ```
   GET http://localhost:8082/BookStoreRestAPICRUD/books?title=I was updated this book title twice
   ```
   ![books_GET_BY_TITLE.png](https://github.com/MaksymChalyi/Book-Store-Rest-API-CRUD-App/blob/master/images/books_GET_BY_TITLE.png)

4. Add a new book:
   ```
   POST http://localhost:8082/BookStoreRestAPICRUD/books?id=4&title=This is something&publication_year=2016&genre=Romance&author_id=7
   ```
   ![books_POST_NEW_BOOK.png](https://github.com/MaksymChalyi/Book-Store-Rest-API-CRUD-App/blob/master/images/books_POST_NEW_BOOK.png)

5. Update book information by id:
   ```
   PUT http://localhost:8082/BookStoreRestAPICRUD/books?id=4&title=This is something updated&publication_year=2018&genre=Science fiction&author_id=6
   ```
   ![books_PUT_UPDATE_BOOK_BY_ID.png](https://github.com/MaksymChalyi/Book-Store-Rest-API-CRUD-App/blob/master/images/books_PUT_UPDATE_BOOK_BY_ID.png)

6. Delete a book by id:
   ```
   DELETE http://localhost:8082/BookStoreRestAPICRUD/books?id=4
   ```
   ![books_DELETE_DELETE_BOOK_BY_ID.png](https://github.com/MaksymChalyi/Book-Store-Rest-API-CRUD-App/blob/master/images/books_DELETE_DELETE_BOOK_BY_ID.png)

## Running the Project

1. Clone the repository using `git clone`.
2. In the `resource` folder, find the `application.properties` file and specify parameters for connecting to the database (driver, url, user, password).
3. Start the server and use POSTMAN to send requests.
