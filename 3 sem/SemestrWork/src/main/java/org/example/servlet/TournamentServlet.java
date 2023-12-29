package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.TournamentService;

import java.io.IOException;

@WebServlet(urlPatterns = "/tournament")
public class TournamentServlet extends HttpServlet {

    private static TournamentService service = TournamentService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("from")==null&&req.getParameter("to")==null) {
            req.setAttribute("tournament_list", service.getAll());
        } else {
            req.setAttribute("tournament_list", service.findOnTime(req));
        }
        req.getRequestDispatcher("/WEB-INF/tournament.jsp").forward(req, resp);
    }
}
