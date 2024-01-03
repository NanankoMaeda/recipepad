<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>レシピ新規作成</h2>

        <form method="POST" class="form-button-container" enctype="multipart/form-data"
            action="${pageContext.request.contextPath}/create" onsubmit="return confirmUpdate();">
            <label for="title">タイトル</label><br /> <input type="text" name="title"
                class="title" id="title" value="${recipe.title }" /> <br />
            <br />
            <label for="file_recipe">画像</label><br />
            <input type="file" name="file" id="file_recipe" /><br />
            <input type="hidden" name="_token" value="${_token}" />
            <br />
            <label for="ingredient_recipe">材料</label><br />
            <textarea name="ingredient" class="ingredient_textbox" id="ingredient_recipe"
                rows="6" wrap="hard">${recipe.ingredient}</textarea>
            <br />
            <br /> <label for="content_recipe">作り方</label><br />
            <textarea name="content" class="content_textbox" id="content_recipe"
                rows="10" wrap="hard">${recipe.content}</textarea>
            <br />

            <button type="submit">投稿</button>
        </form>
        <script>
        function confirmUpdate() {
            var titleInput = document.getElementById('title');
            var fileInput = document.getElementById('file_recipe');

            if (titleInput.value === "") {
                alert("タイトルを入力してください");
                return false;
            }
            if (fileInput.value === "") {
                alert("画像を選択してください");
                return false;
            }
            return confirm("更新してもよろしいですか？");
        }
        </script>

        <p><a href="${pageContext.request.contextPath}/index">戻る</a></p>

    </c:param>
</c:import>