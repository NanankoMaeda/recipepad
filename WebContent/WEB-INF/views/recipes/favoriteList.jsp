<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>お気に入りレシピ</h2>
        <ul class="index">
            <c:forEach var="favorite_recipe" items="${favorite_recipes}">
                <li>
                    <a href="${pageContext.request.contextPath}/show?id=${favorite_recipe.id}">
                        <c:out value="${favorite_recipe.id}" />
                    </a>
                    <c:out value="${favorite_recipe.title}"></c:out>
                </li>
            </c:forEach>
        </ul>

        <script>
            // ローカルストレージからダークモードの状態を読み込む
            window.onload = function() {
                var isDarkMode = localStorage.getItem("darkMode") === "true";
                if (isDarkMode) {
                    document.body.classList.add("dark-mode");
                }
            };
        </script>

        <p><a href="${pageContext.request.contextPath}/index">一覧に戻る</a></p>
    </c:param>
</c:import>