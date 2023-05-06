package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Favorite;
import models.Recipe;
import utils.DBUtil;

/**
 * Servlet implementation class ShowServlet
 */
@WebServlet("/show")
public class ShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //該当のIDレシピ1件のみをデータベースから取得
        Recipe r = em.find(Recipe.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        //お気に入り状態をチェック
        Integer recipeId = Integer.parseInt(request.getParameter("id"));
        boolean isFavorited = isRecipeFavorited(recipeId);

        //データをリクエストスコープにセットしてshow.jspを呼び出す
        request.setAttribute("recipe", r);
        request.setAttribute("isFavorited", isFavorited);


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/recipes/show.jsp");
        rd.forward(request, response);
    }

    //お気に入り状態をチェックするメソッド
    public boolean isRecipeFavorited(Integer recipeId) {
        boolean isFavorited = false;
        EntityManager em = DBUtil.createEntityManager();

        try {
            // JPQLクエリを準備
            TypedQuery<Favorite> query = em.createQuery(
                    "SELECT f FROM Favorite f WHERE recipe_id = :recipeId",
                    Favorite.class)
                    .setParameter("recipeId", recipeId);
            Favorite result = query.getSingleResult();

            //結果があればお気に入り登録済み
            if (result != null) {
                isFavorited = true;
            }
        } catch (NoResultException e) {
            //結果がない場合はお気に入り登録されていない
            isFavorited = false;
        } finally {
            em.close();
        }

        return isFavorited;
    }

}
