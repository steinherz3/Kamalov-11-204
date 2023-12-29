package org.example.servlet.ajax;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.TournamentService;

import java.io.IOException;

@WebServlet("/tournament/delete")
public class TournamentAjaxDeleteServlet extends HttpServlet {

    TournamentService service = TournamentService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service.deleteFromTournament(req);
    }
}
