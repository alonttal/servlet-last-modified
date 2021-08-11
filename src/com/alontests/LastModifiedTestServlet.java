package com.alontests;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
 You can override getLastModified() to specify the last time (since UTC 1/1/1970) the content
 returned by GET request has changed.
 The servlet will automatically add a "Last-Modified" header to the response.
 On the next request by the client, an "If-Modified-Since" header can be added with the last
 modified value (browsers usually add this header (if cache is not disabled)).
 If the value returned by getLastModified() was not changed the doGet method would not run and
 a response with status 304 (Not-Modified) would be sent back.
*/
@WebServlet(name = "Last-Modified",
        urlPatterns = {"/lastModified"},
        loadOnStartup = 1)
public class LastModifiedTestServlet extends HttpServlet {
    private static long LAST_MODIFIED = 0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter writer = resp.getWriter()) {
            writer.println(LAST_MODIFIED);
        }
    }

    @Override
    protected long getLastModified(HttpServletRequest req) {
        // Simulate a modification every 10 seconds
        long currentTimeMillis = System.currentTimeMillis();
        if ((currentTimeMillis - LAST_MODIFIED) >= 10_000) {
            LAST_MODIFIED = currentTimeMillis;
        }
        return LAST_MODIFIED;
    }
}
