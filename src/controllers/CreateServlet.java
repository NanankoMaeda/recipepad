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

@WebServlet("/create")
@MultipartConfig
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();
            em.getTransaction().begin();

            Recipe r = new Recipe();

            String title = request.getParameter("title");
            r.setTitle(title);

            String ingredient = request.getParameter("ingredient");
            r.setIngredient(ingredient);

            String content = request.getParameter("content");
            r.setContent(content);

            //","を入力できないようにする
            if (ingredient.contains(",") || content.contains(",")) {
                // エラーを返却する
                request.getSession().setAttribute("errorMessage", "材料や作り方に「,(カンマ)」は入力できません。");
                response.sendRedirect(request.getContextPath() + "/error");
                return;
            }

            Part part = request.getPart("file");
            String file = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            part.write("C:\\pleiades\\workspace\\recipepad\\WebContent\\upload\\"+file);
            r.setFile(file);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            r.setCreated_at(currentTime);
            r.setUpdated_at(currentTime);

            em.persist(r);
            em.getTransaction().commit();
            request.getSession().setAttribute("flush", "登録が完了しました。");
            em.close();

            response.sendRedirect(request.getContextPath() + "/index");
        }
    }

}