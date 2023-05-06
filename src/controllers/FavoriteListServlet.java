package controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Recipe;
import utils.DBUtil;

/**
 * Servlet implementation class FavoriteListServlet
 */
@WebServlet("/favorite_list")
public class FavoriteListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FavoriteListServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        // データベースからfavoritesテーブルのrecipe_idを取得してListに格納
        TypedQuery<Integer> query = em.createQuery("SELECT f.recipe_id FROM Favorite f", Integer.class);
        List<Integer> recipeIds = query.getResultList();

        // RecipeデータベースからrecipeIdsに一致するRecipeを取得する(IN句を使う)
        TypedQuery<Recipe> recipesQuery = em.createQuery(
                "SELECT r FROM Recipe r WHERE id IN (:recipeIds)",
                Recipe.class)
                .setParameter("recipeIds", recipeIds);
        List<Recipe> favoriteRecipes = recipesQuery.getResultList();

        em.close();

        // お気に入りListをリクエストスコープに保存
        request.setAttribute("favorite_recipes", favoriteRecipes);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/recipes/favoriteList.jsp");
        rd.forward(request, response);
    }

}
