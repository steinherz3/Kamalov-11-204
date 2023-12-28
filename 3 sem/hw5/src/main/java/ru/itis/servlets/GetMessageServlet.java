package ru.itis.servlets;

import ru.itis.dao.DataBase;
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

@WebServlet("/getMessage")
public class GetMessageServlet extends HttpServlet {

    private final static String GET_MESSAGE_TABLE_HTML_PATH = "src/main/webapp/html/GetMessageTable.html";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        HttpSession session = request.getSession();

        String receiverName = (String) session.getAttribute("name");
        try(PrintWriter writer = response.getWriter()) {
            String resultHtml = HTMLReaderUtil.readHTMLAndAddMessagesTable(Path.of(GET_MESSAGE_TABLE_HTML_PATH),
                    DataBase.getAllMessagesByReceiver(receiverName));
            writer.println(resultHtml);
        }
        catch (IOException e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
