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
import java.util.Map;

@WebServlet("/updatePet")
public class UpdatePetServlet extends HttpServlet {

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
        request.getRequestDispatcher("/jsp/updatePet.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");


        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            System.out.println(entry.getKey() + ":");
            for (String param : entry.getValue()) {
                System.out.println(param + ",");
            }
        }
        String petId = request.getParameter("petId");
        String petName = request.getParameter("name");
        String petAge = request.getParameter("age");
        String petHeight = request.getParameter("height");
        String petColor = request.getParameter("color");
        String petSex = request.getParameter("Sex");

        if(petId == null)
            response.sendRedirect("updatePet?error=no-one-pet-selected");

        boolean isUpdated = petService.updatePet(Long.parseLong(petId), petName, petAge, petHeight, petColor, petSex);

        if(isUpdated) {
            log("Update of Pet is successful!");
            response.sendRedirect("/updatePet?success");
        }
        else {
            log("Update of Pet is failed");
            response.sendRedirect("/updatePet?error");
        }
    }
}
