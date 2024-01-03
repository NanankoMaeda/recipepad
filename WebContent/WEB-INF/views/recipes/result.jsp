<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String filename=(String)request.getAttribute("filename");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>fileupload</title>
</head>
<body>
<p>画像:<br><img src="/recipepad/upload/<%=filename %>"></p>
</body>
</html>