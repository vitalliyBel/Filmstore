<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Films Application</title>
</head>
<body>
    <center>
        <h1>Films </h1>
        <h2>
            <a href="/new">Add New Film</a>
            &nbsp;&nbsp;&nbsp;
            <a href="/list">List All Films</a>

        </h2>
    </center>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of Films</h2></caption>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Genre</th>
                <th>Year</th>
                <th>Price</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="film" items="${listFilm}">
                <tr>
                    <td><c:out value="${film.id}" /></td>
                    <td><c:out value="${film.title}" /></td>
                    <td><c:out value="${film.genre}" /></td>
                    <td><c:out value="${film.year}" /></td>
                    <td><c:out value="${film.price}" /></td>
                    <td>
                        <a href="/edit?id=<c:out value='${film.id}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="/delete?id=<c:out value='${film.id}' />">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>