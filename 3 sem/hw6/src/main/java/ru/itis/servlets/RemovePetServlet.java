package ru.itis.servlets;

import ru.itis.config.PrepareConfig;
import ru.itis.models.Account;
import ru.itis.service.AccountService;
import ru.itis.service.PetService;
import ru.itis.service.impl.AccountServiceSingleTonImpl;
import ru.itis.service.impl.PetServiceSingleTonImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/removePet")
public class RemovePetServlet extends HttpServlet {
    private AccountService accountService;
    private PetService petService;


    @Override
    public void init() throws ServletException {
        super.init();
        PrepareConfig.prepareDataSource();
        accountService = AccountServiceSingleTonImpl.getInstance();
        petService = PetServiceSingleTonImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accountName = (String) request.getSession().getAttribute("name");
        Account account = accountService.getAccountByNameWithPets(accountName)
                .orElseThrow(() -> new IllegalArgumentException("Account is not exist"));

        request.setAttribute("pets", account.getPets());
        request.getRequestDispatcher("/jsp/removePet.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String petId = request.getParameter("petId");

        if(petId == null)
            response.sendRedirect("/removePet?error=no-one-pet-selected");

        boolean isRemoved = petService.removePet(Long.parseLong(petId));

        if(isRemoved) {
            log("Remove of Pet is successful!");
            response.sendRedirect("/removePet?success");
        }
        else {
            log("Remove of Pet is failed");
            response.sendRedirect("/removePet?error");
        }
    }
}
