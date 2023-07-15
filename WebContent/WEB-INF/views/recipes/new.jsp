<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>レシピ新規作成</h2>

        <form method="POST" class="form-button-container" enctype="multipart/form-data"
            action="${pageContext.request.contextPath}/create">
            <label for="title">タイトル</label><br /> <input type="text" name="title"
                class="title" id="title" value="${recipe.title }" /> <br />
            <br /> <label for="ingredient_recipe">材料</label><br />
            <textarea name="ingredient" class="ingredient" id="ingredient_recipe"
                rows="6" wrap="hard">${recipe.ingredient}</textarea>
            <br />
            <br /> <label for="content_recipe">作り方</label><br />
            <textarea name="content" class="content" id="content_recipe"
                rows="10" wrap="hard">${recipe.content}</textarea>
            <br />
            <br />
            <label for="file_recipe">画像</label><br />
            <input type="file" name="file" id="file_recipe" /><br />
            <input type="hidden" name="_token" value="${_token}" />

            <button type="submit">投稿</button>
        </form>

        <p><a href="${pageContext.request.contextPath}/index">戻る</a></p>

    </c:param>
</c:import>