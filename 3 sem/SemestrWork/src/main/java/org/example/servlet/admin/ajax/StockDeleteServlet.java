package org.example.servlet.admin.ajax;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.StockService;

import java.io.IOException;

@WebServlet("/admin/stock/delete")
public class StockDeleteServlet extends HttpServlet {

    private final StockService service = StockService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service.deleteStock(req);
    }
}
