package my.project.services.servlets;

import my.project.services.db.DbManager;
import javax.servlet.*;

public class ContextListener implements ServletContextListener, ServletContextAttributeListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("in context listener");
        DbManager dbManager = DbManager.getInstance();
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("dbManager", dbManager);
    }

    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        System.out.println("attribute added");
    }
}
