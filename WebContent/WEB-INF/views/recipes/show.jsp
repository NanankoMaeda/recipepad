<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">

        <h2>
            <c:out value="${recipe.title}" />
            のレシピ詳細
        </h2>

        <p>材料</p>
        <%
        String ingredients = (String) request.getAttribute("ingredients");
        %>
        <ul class="ingredient">
            <c:forEach var="ingredient" items="${fn:split(ingredients,',')}">
                <li><c:out value="${ingredient}" /></li>
            </c:forEach>
        </ul>
        <p>作り方</p>
        <%
        String contents = (String) request.getAttribute("contents");
        %>
        <div class="content">
            <ol>
            <c:forEach var="content" items="${fn:split(contents,',')}">
                <li><c:out value="${content}" /></li>
            </c:forEach>
            </ol>
        </div>
        <br />
        <p>作成日時：<fmt:formatDate value="${recipe.created_at}" pattern="yyyy/MM/dd HH:mm" /></p>
        <p>更新日時：<fmt:formatDate value="${recipe.updated_at}" pattern="yyyy/MM/dd HH:mm" /></p>

        <%
            //お気に入り状態をリクエスト属性から取得
            boolean isFavorited = (Boolean) request.getAttribute("isFavorited");
        %>

        <% if (isFavorited) { %>
            <!-- お気に入り解除ボタン -->
            <form class="button-container" action="favorite" method="post">
                <input type="hidden" name="id" id="id_recipe" value="${recipe.id}" />
                <button type="submit" name="action" value="remove_favorite" style="width:150px;height:40px">お気に入り解除</button>
            </form>
        <% } else { %>
            <!-- お気に入り登録ボタン -->
            <form class="button-container" action="favorite" method="post">
                <input type="hidden" name="id" id="id_recipe" value="${recipe.id}" />
                <button type="submit" name="action" value="add_favorite" style="width:150px;height:40px">お気に入り登録</button>
            </form>
        <% } %>

        <p><a href="${pageContext.request.contextPath}/edit?id=${recipe.id}">編集</a></p>
        <p><a href="${pageContext.request.contextPath}/index">戻る</a></p>

    </c:param>
</c:import>