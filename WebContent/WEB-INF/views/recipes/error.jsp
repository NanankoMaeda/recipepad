<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>エラー画面</title>
</head>
<body>
    <h1>エラーが発生しました</h1>
    <p><%= request.getSession().getAttribute("errorMessage") %></p>
    <a href="<%= request.getContextPath() %>/clear-error">トップページへ戻る</a>
</body>
</html>