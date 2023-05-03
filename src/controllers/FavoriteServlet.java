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
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        em.getTransaction().begin();

        Favorite f = new Favorite();

        Integer id = Integer.parseInt(request.getParameter("id"));
        f.setRecipe_id(id);

        String action = request.getParameter("action");

        //すでにデータベースに登録されているIDを取得
        TypedQuery<Integer> query = em.createQuery("SELECT f.recipe_id FROM Favorite f", Integer.class);
        List<Integer> existingIds = query.getResultList();


        if (action.equals("add_favorite")) {
            if (existingIds.contains(id)) {
                //エラーメッセージ

            } else {
                em.persist(f);
            }
        } else if (action.equals("remove_favorite")) {
            em.remove(f);
        }

        em.getTransaction().commit();
        em.close();

        response.sendRedirect(request.getContextPath() + "/index");

    }

}
