<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>レシピ一覧</h2>
        <ul>
            <c:forEach var="recipe" items="${recipes}">
                <li>
                    <a href="${pageContext.request.contextPath}/show?id=${recipe.id}">
                        <c:out value="${recipe.id}" />
                    </a>
                    :<c:out value="${recipe.title}"></c:out>
                </li>
             </c:forEach>
        </ul>

        <p><a href="${pageContext.request.contextPath}/new">投稿する</a></p>

        <p><a href="${pageContext.request.contextPath}/favoriteList">お気に入りレシピ</a></p>

     </c:param>
</c:import>
