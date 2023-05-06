<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="../layout/app.jsp">
    <c:param name="content">

        <h2><c:out value="${recipe.title}" /> のレシピ詳細</h2>

        <p>材料</p>
        <p><c:out value="${recipe.ingredient}" /></p><br/>
        <p>作り方</p>
        <p><c:out value="${recipe.content}" /></p><br/>
        <p>作成日時：<fmt:formatDate value="${recipe.created_at}" pattern="yyyy/MM/dd HH:mm" /></p>
        <p>更新日時：<fmt:formatDate value="${recipe.updated_at}" pattern="yyyy/MM/dd HH:mm" /></p>

        <p><a href="${pageContext.request.contextPath}/index">戻る</a></p>
        <p><a href="${pageContext.request.contextPath}/edit?id=${recipe.id}">編集</a></p>

        <%
            //お気に入り状態をリクエスト属性から取得
            boolean isFavorited = (Boolean) request.getAttribute("isFavorited");
        %>

        <% if (isFavorited) { %>
            <!-- お気に入り解除ボタン -->
            <form action="favorite" method="post">
                <input type="hidden" name="id" id="id_recipe" value="${recipe.id}" />
                <button type="submit" name="action" value="remove_favorite">お気に入り解除</button>
            </form>
        <% } else { %>
            <!-- お気に入り登録ボタン -->
            <form action="favorite" method="post">
                <input type="hidden" name="id" id="id_recipe" value="${recipe.id}" />
                <button type="submit" name="action" value="add_favorite">お気に入り登録</button>
            </form>
        <% } %>

    </c:param>
</c:import>