package org.example.servlet.admin.ajax;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.PCService;

import java.io.IOException;

@WebServlet("/admin/pc/update")
public class PCUpdateServlet extends HttpServlet {

    private static PCService service = PCService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service.updatePC(req);
    }
}
