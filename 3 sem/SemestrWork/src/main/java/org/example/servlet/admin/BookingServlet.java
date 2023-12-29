package org.example.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.BookingService;

import java.io.IOException;

@WebServlet("/admin/booking")
public class BookingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("booking_list", service.getWaitingBooking());
        req.getRequestDispatcher("/WEB-INF/admin/booking.jsp").forward(req,resp);
    }

    private final BookingService service = BookingService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service.updateStatus(req);
    }
}
