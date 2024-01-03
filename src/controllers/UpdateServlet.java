package controllers;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import models.Recipe;
import utils.DBUtil;

@WebServlet("/update")
@MultipartConfig
public class UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Recipe r = em.find(Recipe.class, (Integer)(request.getSession().getAttribute("recipe_id")));

            String title = request.getParameter("title");
            r.setTitle(title);

            String ingredient = request.getParameter("ingredient");
            r.setIngredient(ingredient);

            String content = request.getParameter("content");
            r.setContent(content);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            r.setUpdated_at(currentTime);       // 更新日時のみ上書き

            if (ingredient.contains(",") || content.contains(",")) {
                request.getSession().setAttribute("errorMessage", "材料や作り方に「,(カンマ)」は入力できません。");
                response.sendRedirect(request.getContextPath() + "/error");
                return;
            }

            Part part = request.getPart("file");
            if (part != null && part.getSize() > 0) {
                String file = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                part.write("C:\\pleiades\\workspace\\recipepad\\WebContent\\upload\\"+file);
                r.setFile(file);
            }


            em.getTransaction().begin();
            em.getTransaction().commit();
            request.getSession().setAttribute("flush", "更新が完了しました。");
            em.close();

            request.getSession().removeAttribute("recipe_id");

            response.sendRedirect(request.getContextPath() + "/index");
        }
    }
}
