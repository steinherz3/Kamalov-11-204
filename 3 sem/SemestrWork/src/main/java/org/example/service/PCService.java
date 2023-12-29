package org.example.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.model.PC;
import org.example.repository.PCRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PCService {

    private final PCRepository repository;

    private final static DateTimeFormatter formatter_time = DateTimeFormatter.ofPattern("HH:mm");

    private final static DateTimeFormatter formatter_date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void createPC(HttpServletRequest request){
        LocalTime from = LocalTime.parse(request.getParameter("from"), formatter_time);
        LocalTime to = LocalTime.parse(request.getParameter("to"), formatter_time);
        PC pc = PC.builder()
                .startWorkingTime(from)
                .endWorkingTime(to)
                .build();
        repository.create(pc);
    }

    public void updatePC(HttpServletRequest request){
        LocalTime from = LocalTime.parse(request.getParameter("from"), formatter_time);
        LocalTime to = LocalTime.parse(request.getParameter("to"), formatter_time);
        PC pc = PC.builder()
                .id(Long.valueOf(request.getParameter("pc_id")))
                .startWorkingTime(from)
                .endWorkingTime(to)
                .build();
        repository.update(pc);
    }

    public void deletePC(HttpServletRequest request){
        repository.delete(Long.valueOf(request.getParameter("pc_id")));
    }

    public PC getPC(HttpServletRequest request){
        return repository.findById(Long.valueOf(request.getParameter("pc_id"))).orElseThrow(RuntimeException::new);
    }

    public List<PC> getAll(){
        return repository.findAll();
    }

    public List<PC> findOnTime(HttpServletRequest request){
        LocalDateTime from = LocalDateTime.parse(request.getParameter("date")+ " "+request.getParameter("from"), formatter_date);
        LocalDateTime to = LocalDateTime.parse(request.getParameter("date")+ " "+request.getParameter("to"), formatter_date);
        return repository.findOnTime(from, to);
    }

    private PCService(){
        repository = PCRepository.getInstance();
    }

    private static class SingletonHolder{
        public static final PCService INSTANCE = new PCService();
    }

    public static PCService getInstance(){
        return SingletonHolder.INSTANCE;
    }

}
