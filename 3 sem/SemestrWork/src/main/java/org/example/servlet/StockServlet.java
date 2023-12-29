package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.StockService;

import java.io.IOException;

@WebServlet("/stock")
public class StockServlet extends HttpServlet {

    private static StockService service = StockService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("stock_list", service.getAllExistence());
        req.getRequestDispatcher("/WEB-INF/stock.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (service.orderStock(req)){
            req.setAttribute("order","You order stock with id: "+ req.getParameter("stock_id"));
        }
        else {
            req.setAttribute("order", "Something goes wrong. Try another stock");
        }
        resp.sendRedirect("/stock");
    }
}
