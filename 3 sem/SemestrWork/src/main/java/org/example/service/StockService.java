package org.example.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.model.Stock;
import org.example.model.User;
import org.example.repository.StockRepository;
import org.example.util.MailService;

import java.util.List;

public class StockService {

    private static StockRepository repository;

    private static UserService userService;

    public void createStock(HttpServletRequest request) {
        Stock stock = Stock.builder()
                .name(request.getParameter("name"))
                .count(Long.valueOf(request.getParameter("count")))
                .price(Long.valueOf(request.getParameter("price")))
                .build();
        repository.create(stock);
    }

    public void updateStock(HttpServletRequest request) {
        Stock stock = Stock.builder()
                .id(Long.valueOf(request.getParameter("stock_id")))
                .name(request.getParameter("name"))
                .count(Long.valueOf(request.getParameter("count")))
                .price(Long.valueOf(request.getParameter("price")))
                .build();
        repository.update(stock);
    }

    public boolean orderStock(HttpServletRequest request) {
        Stock stock = findById(request);
        if (stock.getCount()>0) {
            stock.setCount(stock.getCount() - 1);
            List<User> list = userService.getAdminList();
            if (stock.getCount() <= 10 && stock.getCount() > 0) {
                for (User u : list
                ) {
                    MailService.sendMessage(u.getEmail(), "Stock is running out", "Stock is running out. Currently we have " +
                            "only " + stock.getCount() + " number of " + stock.getName());
                }
            } else if (stock.getCount() == 0) {
                for (User u : list
                ) {
                    MailService.sendMessage(u.getEmail(), "Stock is over", "Stock is over. Currently we haven't " +
                            "any number of " + stock.getName());
                }
            }
            return true;
        }
        return false;
    }

    public void deleteStock(HttpServletRequest request) {
        repository.delete(Long.valueOf(request.getParameter("stock_id")));
    }

    public List<Stock> getAll() {
        return repository.findAll();
    }

    public Stock findById(HttpServletRequest request) {
        return repository.findById(Long.valueOf(request.getParameter("stock_id"))).orElseThrow(RuntimeException::new);
    }

    private StockService() {
        repository = StockRepository.getInstance();
        userService = UserService.getInstance();
    }

    public List<Stock> getAllExistence() {
        return repository.getAllExistence();
    }

    private static class SingletonHolder {
        public static final StockService INSTANCE = new StockService();
    }

    public static StockService getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
