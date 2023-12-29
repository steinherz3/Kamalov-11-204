package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.BookingService;
import org.example.service.PCService;

import java.io.IOException;

@WebServlet("/pc/detail")
public class PCServlet extends HttpServlet {

    private static final PCService service = PCService.getInstance();

    private static final BookingService bookingService = BookingService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getSession().setAttribute("pc",service.getPC(req));
            if (req.getSession().getAttribute("user")!=null){
                req.getSession().setAttribute("booking_list", bookingService.getBookingByUserIdAndPCId(req));
            }
        } catch (RuntimeException e){
            req.getSession().setAttribute("pc",null);
        }

        req.getRequestDispatcher("/WEB-INF/pcPersonal.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        bookingService.createBooking(req);
        resp.sendRedirect("/pc/detail?pc_id="+req.getParameter("pc_id"));
    }
}
