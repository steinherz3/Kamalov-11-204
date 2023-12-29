package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.TournamentService;
import org.example.service.UserService;

import java.io.IOException;

@WebServlet("/tournament/detail")
public class TournamentPersonalServlet extends HttpServlet {

    private static TournamentService service = TournamentService.getInstance();

    private static UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getSession().setAttribute("tournament",service.getTournament(req));
        } catch (RuntimeException e){
            req.getSession().setAttribute("tournament",null);
            req.getSession().setAttribute("user_list", null);
        }
        if (req.getSession().getAttribute("user")!=null){
            req.getSession().setAttribute("user_list", userService.getUsersByTournamentId(Long.valueOf(req.getParameter("tournament_id"))));
        }
        req.getRequestDispatcher("/WEB-INF/tournamentPersonal.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service.registerOnTournament(req);
    }
}
