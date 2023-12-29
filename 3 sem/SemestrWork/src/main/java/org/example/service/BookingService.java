package org.example.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.model.Booking;
import org.example.model.User;
import org.example.model.enums.BookingStatus;
import org.example.repository.BookingRepository;
import org.example.util.MailService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class BookingService {

    private static BookingRepository repository;

    private static UserService userService;

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void createBooking(HttpServletRequest request){
        LocalDateTime from = LocalDateTime.parse(request.getParameter("date")+ " "+request.getParameter("from"), formatter);
        LocalDateTime to = LocalDateTime.parse(request.getParameter("date")+ " "+request.getParameter("to"), formatter);
        Booking booking = Booking.builder()
                .pcId(Long.valueOf(request.getParameter("pc_id")))
                .userId(((User)(request.getSession().getAttribute("user"))).getId())
                .timeFrom(from)
                .timeTo(to)
                .build();
        repository.create(booking);
    }

    public boolean updateBooking(HttpServletRequest request){
        Optional<Booking> booking = repository.findById(Long.valueOf(request.getParameter("booking_id")));
        if (booking.isPresent()){
            LocalDateTime from = LocalDateTime.parse(request.getParameter("date")+ " "+request.getParameter("from"), formatter);
            LocalDateTime to = LocalDateTime.parse(request.getParameter("date")+ " "+request.getParameter("to"), formatter);
            Booking newBooking = Booking.builder()
                    .pcId(Long.valueOf(request.getParameter("pc_id")))
                    .userId(Long.valueOf(request.getParameter("user_id")))
                    .timeFrom(from)
                    .timeTo(to)
                    .build();
            repository.update(newBooking);
            return true;
        }
        return false;
    }

    public void updateStatus(HttpServletRequest request){
        repository.setStatus(Long.valueOf(request.getParameter("booking_id")),
                BookingStatus.valueOf(request.getParameter("status").toUpperCase()));
        User user = userService.getUserByBookingId(Long.valueOf(request.getParameter("booking_id")));
        MailService.sendMessage(user.getEmail(),
                "Status of your booking changed",
                "Status of your booking with id:" + request.getParameter("booking_id")+" has been changed to: "+request
                        .getParameter("status").toLowerCase()+".");
    }

    public void deleteBooking(HttpServletRequest request){
        repository.delete(Long.valueOf(request.getParameter("booking_id")));
    }

    public Booking getBookingById(HttpServletRequest request){
        return repository.findById(Long.valueOf(request.getParameter("booking_id")))
                .orElseThrow(RuntimeException::new);
    }

    public List<Booking> getBookingByUserId(HttpServletRequest request){
        return repository.findByUserId(Long.valueOf(request.getParameter("user_id")));
    }

    public List<Booking> getBookingByPCId(HttpServletRequest request){
        return repository.findByPCId(Long.valueOf(request.getParameter("pc_id")));
    }

    public List<Booking> getBookingByUserIdAndPCId(HttpServletRequest request){
        return repository.findByUserIdAndPCId(((User)(request.getSession().getAttribute("user"))).getId(),
                Long.valueOf(request.getParameter("pc_id")));
    }

    private BookingService(){
        repository = BookingRepository.getInstance();
        userService = UserService.getInstance();
    }

    public List<Booking> getWaitingBooking() {
        return repository.getWaitingBooking();
    }

    private static class SingletonHolder{
        public static final BookingService INSTANCE = new BookingService();
    }

    public static BookingService getInstance(){
        return SingletonHolder.INSTANCE;
    }

}
