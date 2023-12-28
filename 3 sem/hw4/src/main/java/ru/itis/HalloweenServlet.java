package ru.itis;

import ru.itis.util.HTMLReaderUtil;
import ru.itis.util.JsonPresentUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

@WebServlet("/hello")
public class HalloweenServlet extends HttpServlet {

    private final static String HALLOWEEN_HTML = "src/main/webapp/html/HW2.html";
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        try(PrintWriter writer = resp.getWriter()) {
            String resultHtml = HTMLReaderUtil.readHTML(Path.of(HALLOWEEN_HTML));
            writer.println(resultHtml);
        }
        catch (IOException e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new IllegalArgumentException("File reading error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String reqBody;
        try(BufferedReader reader = req.getReader()){
             reqBody = reader.lines().findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
        catch (IOException e){
            throw new IllegalArgumentException(e.getMessage());
        }

        int result = JsonPresentUtil.getIntStream(reqBody)
                .filter(x -> x > 5)
                .mapToInt(Integer::intValue).sum();

        try(PrintWriter writer = resp.getWriter()){
            writer.println(result);
        }
        catch (IOException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}