package ru.itis.servlets;

import ru.itis.config.PrepareConfig;
import ru.itis.models.Account;
import ru.itis.service.AccountService;
import ru.itis.service.PetService;
import ru.itis.service.impl.AccountServiceSingleTonImpl;
import ru.itis.service.impl.PetServiceSingleTonImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/getPets")
public class GetPetsServlet extends HttpServlet {

    private  AccountService accountService;
    private PetService petService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        accountService = AccountServiceSingleTonImpl.getInstance();
        petService = PetServiceSingleTonImpl.getInstance();
        PrepareConfig.prepareDataSource();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accountName = request.getParameter("user");
        System.out.println(accountName);

        if(accountName != null){
            try {
                Account account = accountService.getAccountByNameWithPets(accountName)
                        .orElseThrow(() -> new IllegalArgumentException("Account is not exist"));
                request.setAttribute("accountName", account.getName());
                request.setAttribute("accountPetsList", account.getPets());
                request.getRequestDispatcher("/jsp/getPetsByAccount.jsp").forward(request, response);
            }
            catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                response.sendRedirect("/getPets?error");
            }
        }
        else{
            request.setAttribute("petList", petService.getAllPets());
            petService.getAllPets().forEach(System.out::println);
            request.getRequestDispatcher("/jsp/getPets.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");


    }
}
