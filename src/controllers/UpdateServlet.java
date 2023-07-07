package controllers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Recipe;
import utils.DBUtil;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            // セッションスコープからメッセージのIDを取得して
            // 該当のIDのメッセージ1件のみをデータベースから取得
            Recipe r = em.find(Recipe.class, (Integer)(request.getSession().getAttribute("recipe_id")));

            // フォームの内容を各フィールドに上書き
            String title = request.getParameter("title");
            r.setTitle(title);

            String ingredient = request.getParameter("ingredient");
            r.setIngredient(ingredient);

            String content = request.getParameter("content");
            r.setContent(content);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            r.setUpdated_at(currentTime);       // 更新日時のみ上書き

          //","を入力できないようにする
            if (ingredient.contains(",") || content.contains(",")) {
                // エラーを返却する
                request.getSession().setAttribute("errorMessage", "材料や作り方に「,(カンマ)」は入力できません。");
                response.sendRedirect(request.getContextPath() + "/error");
                return;
            }

            // データベースを更新
            em.getTransaction().begin();
            em.getTransaction().commit();
            request.getSession().setAttribute("flush", "更新が完了しました。");
            em.close();

            // セッションスコープ上の不要になったデータを削除
            request.getSession().removeAttribute("recipe_id");

            // indexページへリダイレクト
            response.sendRedirect(request.getContextPath() + "/index");
        }
    }

}