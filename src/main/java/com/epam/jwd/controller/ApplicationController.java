package com.epam.jwd.controller;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.CommandFactory;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.controller.command.impl.CustomRequestContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Process all requests from users
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
@WebServlet(urlPatterns = {"/home", "/admin", "/user"})
public class ApplicationController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String commandParamName = req.getParameter("command");
        Command command = CommandFactory.command(commandParamName);
        final ResponseContext execute = command.execute(new CustomRequestContext(req));

        if (command != null) {
            if (execute.isRedirect()) {
                res.sendRedirect(execute.getPage());
            } else {
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(execute.getPage());
                requestDispatcher.forward(req, res);
            }
        }
    }
}
