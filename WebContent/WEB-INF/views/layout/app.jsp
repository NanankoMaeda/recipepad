<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>Recipe Pad</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/recipe-style.css' />">
</head>
<body>
    <div class="container">
    <div id="wrapper">
        <div id="header">
            <h1>Recipe Pad</h1>
        </div>
        <div id="content">
            ${param.content}
        </div>
        <div id="footer">
            (c) Nanako Maeda
        </div>
    </div>
    </div>
</body>
</html>