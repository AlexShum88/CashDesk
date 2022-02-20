package my.project.servlets;

import my.project.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static my.project.entity.Roles.*;

public class AuthorizationServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(AuthorizationServlet.class);
    private final ContextListener conL = new ContextListener();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("in auth servlet");
        LOG.debug("session id = {} ", req.getSession().getId());
        User user = (User) req.getAttribute("user");

        req.setCharacterEncoding("UTF-8"); //для нормальной работы с кирилицей в пост запросах;
        // этот функционал можно/лучше вынести в фильтр (колесников покаывает в конце лекции)

        req.getSession().setAttribute("user", user);

        LOG.debug(" user {} role {}",req.getSession().getAttribute("senior"), SENIOR_CASHIER.name.equals(user.getRole()));
        if (req.getSession().getAttribute("senior")==null && SENIOR_CASHIER.name.equals(user.getRole())){
            LOG.debug("authServ#doPost#in if seinor==null & user senior");
            req.getSession().setAttribute("senior", user);
            LOG.debug(req.getSession().getAttribute("senior"));
            req.getRequestDispatcher("check").forward(req,resp);
        }
        if (GUEST.name.equals(user.getRole())) req.getRequestDispatcher("views/guest.html").forward(req, resp);
//        resp.sendRedirect("views/guest.html"); PRG pattern (defence against sending form twice)
        if (MERCHANDISER.name.equals(user.getRole())) req.getRequestDispatcher("merchandiser").forward(req, resp);
        if (CASHIER.name.equals(user.getRole())) req.getRequestDispatcher("views/workPlace/cashier.jsp").forward(req, resp);
//        if (SENIOR_CASHIER.name.equals(user.getRole())) req.getRequestDispatcher("views/workPlace/senior_cashier.jsp").forward(req, resp);






    }
}
