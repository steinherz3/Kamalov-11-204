package org.example.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.model.User;
import org.example.model.enums.UserRole;
import org.example.repository.UserRepository;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class UserService {


    public User create(HttpServletRequest request){
        User user = User.builder()
                .username(request.getParameter("username"))
                .email(request.getParameter("email"))
                .hashPassword(String.valueOf(request.getParameter("password").hashCode()))
                .build();
        repository.create(user);
        return login(request);
    }

    public void update(HttpServletRequest request){
        User oldUser = loginOldPass(request);
            User user = User.builder()
                    .id(oldUser.getId())
                    .username(request.getParameter("username"))
                    .email(request.getParameter("email"))
                    .hashPassword(String.valueOf(request.getParameter("new_password").hashCode()))
                    .build();
            repository.update(user);
    }


    public User login(HttpServletRequest request){
        User user = repository.findByUsername(request.getParameter("username")).orElseThrow(RuntimeException::new);
        if (user.getHashPassword().equals(String.valueOf(request.getParameter("password").hashCode()))){
            return user;
        }
        throw new RuntimeException();
    }

    private User loginOldPass(HttpServletRequest request){
        User user = repository.findByUsername(request.getParameter("username")).orElseThrow(RuntimeException::new);
        if (user.getHashPassword().equals(String.valueOf(request.getParameter("old_password").hashCode()))){
            return user;
        }
        throw new RuntimeException();
    }

    public User getUserByBookingId(Long bookingId) {
        return repository.findUsersByBookingId(bookingId).orElseThrow(RuntimeException::new);
    }

    public List<User> getAdminList() {
        return repository.findAll();
    }

    public List<User> getUsersByTournamentId(Long tournamentId) {
        return repository.findUsersByTournamentId(tournamentId);
    }

    private static UserRepository repository;

    private UserService(){
        repository = UserRepository.getInstance();
    }

    private static class SingletonHolder{
        public static final UserService INSTANCE = new UserService();
    }

    public static UserService getInstance(){
        return SingletonHolder.INSTANCE;
    }
}
