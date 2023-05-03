package controllers;

import java.io.IOException;
import java.util.ArrayList;
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

        //データベースから全てのレシピ情報を取得
        List<Recipe> recipes = em.createNamedQuery("getAllRecipes", Recipe.class).getResultList();

        //recipesテーブルのidをListに格納
        List<Integer> recipeId = new ArrayList<Integer>();
        for (Recipe recipe : recipes) {
            recipeId.add(recipe.getId());
        }

        //データベースからfavoritesテーブルのrecipe_idを取得してListに格納
        TypedQuery<Integer> query = em.createQuery("SELECT f.recipe_id FROM Favorite f", Integer.class);
        List<Integer> favoriteId = query.getResultList();

        //recipesテーブルのidとfavoritesテーブルのrecipe_idが一致した場合にお気に入りListに追加
        List<Recipe> favorites = new ArrayList<Recipe>();
        for(int i = 0; i < recipeId.size(); i++) {
            for(int j = 0; j < favoriteId.size(); j++) {
                if(recipeId.get(i) == favoriteId.get(j)) {
                    favorites.add(recipes.get(i));
                }
            }
        }

        em.close();

        //お気に入りListをリクエストスコープに保存
        request.setAttribute("favorites", favorites);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/recipes/favoriteList.jsp");
        rd.forward(request, response);

    }

}
