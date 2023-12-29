package org.example.servlet.ajax;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.BookingService;

import java.io.IOException;

@WebServlet("/booking/delete")
public class BookingAjaxDeleteServlet extends HttpServlet {

    private static final BookingService service = BookingService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service.deleteBooking(req);
    }
}
