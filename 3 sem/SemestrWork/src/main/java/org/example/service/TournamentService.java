package org.example.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.model.Tournament;
import org.example.model.User;
import org.example.repository.TournamentRepository;
import org.example.util.MailService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TournamentService {

    private static TournamentRepository repository;

    private static UserService userService;

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public void create(HttpServletRequest request){
        Tournament tournament = Tournament.builder()
                .name(request.getParameter("name"))
                .description(request.getParameter("description"))
                .startTime(LocalDateTime.parse(request.getParameter("start_time"), formatter))
                .build();
        repository.create(tournament);
    }

    public void delete(HttpServletRequest request){
        repository.delete(Long.valueOf(request.getParameter("tournament_id")));
    }

    public void deleteFromTournament(HttpServletRequest request){
        repository.deleteFromTournament(Long.valueOf(request.getParameter("user_id")),Long.valueOf(request.getParameter("tournament_id")));
    }

    public void update(HttpServletRequest request){
        Tournament tournament = Tournament.builder()
                .id(Long.valueOf(request.getParameter("tournament_id")))
                .name(request.getParameter("name"))
                .description(request.getParameter("description"))
                .startTime(LocalDateTime.parse(request.getParameter("start_time"), formatter))
                .build();
        repository.create(tournament);
    }

    public Tournament getTournament(HttpServletRequest request){
        return repository.findById(Long.valueOf(request.getParameter("tournament_id"))).orElseThrow(RuntimeException::new);
    }

    public List<Tournament> getAll(){
        return repository.findAll();
    }

    public List<Tournament> findOnTime(HttpServletRequest request){
        return repository.findOnTime(LocalDateTime.parse(request.getParameter("from"), formatter),
                LocalDateTime.parse(request.getParameter("to"),formatter));
    }

    public void registerOnTournament(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        repository.registerOnTournament(Long.valueOf(request.getParameter("tournament_id")), user.getId());
        List<User> list = userService.getUsersByTournamentId(Long.valueOf(request.getParameter("tournament_id")));
        for (User u: list
             ) {
            MailService.sendMessage(u.getEmail(), "New player registered for a tournament!" , "Net player with nickname "
            + user.getUsername() +" registered for a tournament.");
        }
    }

    private TournamentService(){
        repository = TournamentRepository.getInstance();
        userService = UserService.getInstance();
    }

    private static class SingletonHolder{
        public static final TournamentService INSTANCE = new TournamentService();
    }

    public static TournamentService getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public static void outMap(Map<Integer, String> map){
        for (int i : map.keySet()
             ) {
            System.out.println("key: "+i+", value: "+map.get(i));
        }
    }
}
