package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.PCService;

import java.io.IOException;

@WebServlet("/pc")
public class PCListServlet extends HttpServlet {

    private static PCService service = PCService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("from")==null&&req.getParameter("to")==null&&req.getParameter("date")==null) {
            req.setAttribute("pc_list", service.getAll());
        } else {
            req.setAttribute("pc_list", service.findOnTime(req));
        }
        req.getRequestDispatcher("/WEB-INF/pc.jsp").forward(req, resp);
    }
}
