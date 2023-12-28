package ru.itis.servlets;

import ru.itis.dao.DataBase;
import ru.itis.models.Account;
import ru.itis.util.HTMLReaderUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;

@WebServlet("/name")
public class NameServlet extends HttpServlet {
    private final static String NAME_FORM_HTML_PATH = "src/main/webapp/html/NameForm.html";

    @Override
    public void init() throws ServletException {
        super.init();
        DataBase.addAccount("John");
        log("Added test account - John");

        DataBase.addAccount("Fred");
        log("Added test account - Fred");

        DataBase.addAccount("Tony");
        log("Added test account - Tony");

        Account fred = DataBase.getAccount("Fred").orElseThrow(IllegalArgumentException::new);
        Account john = DataBase.getAccount("John").orElseThrow(IllegalArgumentException::new);
        Account tony = DataBase.getAccount("Tony").orElseThrow(IllegalArgumentException::new);
        DataBase.addMessage("Bla Bla", fred, john);
        DataBase.addMessage("Say hello", fred, john);
        DataBase.addMessage("take five", fred, john);
        DataBase.addMessage("Give me likes", fred, john);
        DataBase.addMessage("Abatai tara", fred, john);
        DataBase.addMessage("Give me likes", fred, tony);
        DataBase.addMessage("I'm late, sorry", fred, tony);
        DataBase.addMessage("Go to StarBacks", fred, tony);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        try(PrintWriter writer = response.getWriter()) {
            String resultHtml = HTMLReaderUtil.readHTML(Path.of(NAME_FORM_HTML_PATH));
            writer.println(resultHtml);
        }
        catch (IOException e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60);
        String name = request.getParameter("name");


        session.setAttribute("name", name);
        log(name + " signIn");

        if(DataBase.hasAccount(name)){
            log(name + " is already in database");
        }
        else {
            DataBase.addAccount(name);
            log("Accounts: \n" + DataBase.getAllAccounts());
            log(name + " saved into database");
        }


        try {
            log("redirecting to /sendMessage");
            response.sendRedirect("/sendMessage");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
