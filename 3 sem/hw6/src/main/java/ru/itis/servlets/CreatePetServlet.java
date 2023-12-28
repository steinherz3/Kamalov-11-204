package ru.itis.servlets;

import ru.itis.config.PrepareConfig;
import ru.itis.models.Pet;
import ru.itis.service.PetService;
import ru.itis.service.impl.PetServiceSingleTonImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/createPet")
public class CreatePetServlet extends HttpServlet {

    private PetService petService;

    @Override
    public void init() throws ServletException {
        super.init();
        PrepareConfig.prepareDataSource();
        petService = PetServiceSingleTonImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher("jsp/createPet.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String accountName = (String) session.getAttribute("name");

        String petName = request.getParameter("name");
        Integer petAge = Integer.parseInt(request.getParameter("age"));
        Double petHeight = Double.parseDouble(request.getParameter("height"));
        String petColor = request.getParameter("color");
        String petSex = request.getParameter("sex");


        Pet pet = petService.createPet(petName, petAge, petHeight, petColor, petSex, accountName)
                .orElseThrow(IllegalArgumentException::new);

        log("Create Pet:\n" + "Id: " + pet.getId() +
                "\nName: " + pet.getName() +
                "\nAge: " + pet.getAge() +
                "\nHeight: " + pet.getHeight() +
                "\nColor: " + pet.getColor() +
                "\nSex: " + pet.getSex());
    }
}
