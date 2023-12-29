package org.example.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.BookingService;
import org.example.service.TournamentService;

import java.io.IOException;

@WebServlet("/admin/tournament")
public class TournamentCreateServlet extends HttpServlet {

    private final TournamentService service = TournamentService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/admin/tournament.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service.create(req);
        resp.sendRedirect("/admin/tournament");
    }
}
