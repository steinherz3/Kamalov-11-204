package ru.itis.servlets;


import ru.itis.config.PrepareConfig;
import ru.itis.models.Account;
import ru.itis.service.AccountService;
import ru.itis.service.impl.AccountServiceSingleTonImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/name")
public class NameServlet extends HttpServlet {

    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        super.init();
        PrepareConfig.prepareDataSource();

        accountService = AccountServiceSingleTonImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher("/jsp/name.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(6000);
        String accountName = request.getParameter("name");

        session.setAttribute("name", accountName);

        Account account =  accountService.signIn(accountName)
                .orElseThrow(IllegalArgumentException::new);
        log("SignIn Account:\n" + "Id: " + account.getId() + "\nName: " + account.getName());

        response.sendRedirect("/createPet");
    }
}
