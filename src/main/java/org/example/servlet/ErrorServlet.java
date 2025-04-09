package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/error")
public class ErrorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMessage = (String) req.getAttribute("jakarta.servlet.error.message");
        Integer statusCode = (Integer) req.getAttribute("jakarta.servlet.error.status_code");
        req.setAttribute("statusCode", statusCode);
        req.setAttribute("errorMessage", errorMessage);
        req.getRequestDispatcher("view/error.jsp").forward(req, resp);
    }
}


