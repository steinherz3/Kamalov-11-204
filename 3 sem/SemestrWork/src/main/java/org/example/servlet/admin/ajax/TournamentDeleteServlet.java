package org.example.servlet.admin.ajax;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.TournamentService;

import java.io.IOException;

@WebServlet("/admin/tournament/delete")
public class TournamentDeleteServlet extends HttpServlet {

    private final TournamentService service = TournamentService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service.delete(req);
    }
}
