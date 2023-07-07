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
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int MAX_RESULTS = 10; // max number of recipes per page

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int page = 1; // default to page 1
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        TypedQuery<Recipe> query = em.createNamedQuery("getAllRecipes", Recipe.class);
        query.setFirstResult((page - 1) * MAX_RESULTS);
        query.setMaxResults(MAX_RESULTS);
        List<Recipe> recipes = query.getResultList();

        long totalRecipes = em.createNamedQuery("countAllRecipes", Long.class).getSingleResult();
        long totalPages = (totalRecipes + MAX_RESULTS - 1) / MAX_RESULTS; // round up

        em.close();

        request.setAttribute("recipes", recipes);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);

        // Handle flash message if present
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/recipes/index.jsp");
        rd.forward(request, response);
    }
}
