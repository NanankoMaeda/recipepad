<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>id : ${recipe.id}の編集ページ</h2>

        <form method="POST" class="form-button-container" enctype="multipart/form-data"
            action="${pageContext.request.contextPath}/update">
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
            <label for="file_recipe">画像</label><br />
            <img id="uploaded_image" src="${pageContext.request.contextPath}/upload/${recipe.file}" width="300"><br />
            <input type="file" name="file" id="file_recipe" onchange="loadImage(event)"><br />
            <br /> <input type="hidden" name="_token" value="${_token}" />

            <button type="submit">更新</button>
        </form>

        <p><a href="#" onclick="confirmDestroy();">削除</a></p>
        <form method="POST" action="${pageContext.request.contextPath}/destroy">
            <input type="hidden" name="_token" value="${_token}" />
        </form>
        <script>
        function loadImage(event) {
            var output = document.getElementById('uploaded_image');
            output.src = URL.createObjectURL(event.target.files[0]);
            output.onload = function() {
                URL.revokeObjectURL(output.src)
            }
        };

        function confirmDestroy() {
            if(confirm("本当に削除してよろしいですか？")){
                document.forms[1].submit();
            }
        }
        </script>
        <p><a href="${pageContext.request.contextPath}/index">戻る</a></p>

    </c:param>
</c:import>