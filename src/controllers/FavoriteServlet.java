package controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Favorite;
import utils.DBUtil;

/**
 * Servlet implementation class FavoriteServlet
 */
@WebServlet("/favorite")
public class FavoriteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FavoriteServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action.equals("add_favorite")) {
            addFavorite();
        } else if (action.equals("remove_favorite")) {
            removeFavorite();
        }
    }

    private void addFavorite() {
        EntityManager em = DBUtil.createEntityManager();
        em.getTransaction().begin();

        Favorite f = new Favorite();
        Integer recipeId = Integer.parseInt(request.getParameter("id"));
        f.setRecipe_id(recipeId);

        // 重複したレシピをお気に入りしないようにする
        TypedQuery<Integer> query = em.createQuery("SELECT f.recipe_id FROM Favorite f", Integer.class);
        List<Integer> existingIds = query.getResultList();
        if (existingIds.contains(recipeId)) {
            // エラーを返却する
            response.sendRedirect();
        }

        em.persist(f);
        em.getTransaction().commit();
        em.close();

        response.sendRedirect(request.getContextPath() + "/index");
    }

    private void removeFavorite() {
        EntityManager em = DBUtil.createEntityManager();
        em.getTransaction().begin();

        // recipe_idからfavoriteを取得する
        Integer recipeId = Integer.parseInt(request.getParameter("id"));
        TypedQuery<Favorite> query = em.createQuery(
                "SELECT * FROM Favorite WHERE recipe_id = :recipeId",
                Favorite.class)
                .setParameter("recipeId", recipeId);
        Favorite f = query.getSingleResult();

        em.remove(f);
        em.getTransaction().commit();
        em.close();

        response.sendRedirect(request.getContextPath() + "/index");
    }

}
