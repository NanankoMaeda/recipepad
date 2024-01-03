<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
    <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>レシピ一覧</h2>
        <ul class="index">
            <c:forEach var="recipe" items="${recipes}">
                <li>
                    <a href="${pageContext.request.contextPath}/show?id=${recipe.id}">
                        <c:out value="${recipe.id}" />
                    </a>
                    <c:out value="${recipe.title}"></c:out>
                </li>
            </c:forEach>
        </ul>


        <c:forEach var="i" begin="1" end="${totalPages}">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <c:out value="${i}" />
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/index?page=${i}" class="pagination-item"><c:out value="${i}" /></a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <p><a href="${pageContext.request.contextPath}/new">投稿する</a></p>

        <p><a href="${pageContext.request.contextPath}/favorite_list">お気に入りレシピ</a></p>

        <button class="darkmode-button" onclick="toggleDarkMode()">ダークモード切り替え</button>
        <script>
            // ダークモード切り替え関数
            function toggleDarkMode() {
                var body = document.body;
                body.classList.toggle("dark-mode");

                // ダークモードの状態をローカルストレージに保存
                var isDarkMode = body.classList.contains("dark-mode");
                localStorage.setItem("darkMode", isDarkMode);
            }

            // ローカルストレージからダークモードの状態を読み込む
            window.onload = function() {
                var isDarkMode = localStorage.getItem("darkMode") === "true";
                if (isDarkMode) {
                    document.body.classList.add("dark-mode");
                }
            };
        </script>

    </c:param>
</c:import>
