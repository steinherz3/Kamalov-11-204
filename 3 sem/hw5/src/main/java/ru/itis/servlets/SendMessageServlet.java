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
import java.nio.file.Path;

@WebServlet("/sendMessage")
public class SendMessageServlet extends HttpServlet {


    private final String SEND_MESSAGE_FORM_HTML_PATH = "src/main/webapp/html/SendMessageForm.html";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        try(PrintWriter writer = response.getWriter()) {
            String resultHtml = HTMLReaderUtil.readHTML(Path.of(SEND_MESSAGE_FORM_HTML_PATH));
            writer.println(resultHtml);
        }
        catch (IOException e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String senderName = (String) session.getAttribute("name");

        String messageText = request.getParameter("message");
        String receiverName = request.getParameter("receiverName");

        if(!DataBase.hasAccount(receiverName)){
            response.sendRedirect("/sendMessage?error=receiverIsNotExist");
            log("error - receiverIsNotExist");
        }
        else{
            Account receiver = DataBase.getAccount(receiverName).orElseThrow(IllegalArgumentException::new);
            Account sender = DataBase.getAccount(senderName).orElseThrow(IllegalArgumentException::new);
            DataBase.addMessage(messageText, sender, receiver);
            log("Messages: \n" + DataBase.getAllMessages());
            log("Message is saved\n sender name - " + senderName + "\n receiver name - " + receiverName);

            response.sendRedirect("/sendMessage?success");
            log("Success");
        }

    }
}
