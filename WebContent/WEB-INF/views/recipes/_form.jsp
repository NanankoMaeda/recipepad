<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<label for="title">タイトル</label><br/>
<input type="text" name="title" id="title" value="${recipe.title }" />
<br/><br/>
<label for="ingredient_recipe">材料</label><br/>
<input type="text" name="ingredient" id="ingredient_recipe" value="${recipe.ingredient}" />
<br/><br/>
<label for="content_recipe">作り方</label><br/>
<input type="text" name="content" id="content_recipe" value="${recipe.content}" />
<br/><br/>

<input type="hidden" name="_token" value="${_token}" />

<button type="submit">投稿</button>