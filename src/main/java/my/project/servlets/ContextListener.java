package my.project.servlets;

import my.project.services.db.DbCheckManager;
import my.project.services.db.DbManager;
import javax.servlet.*;

public class ContextListener implements ServletContextListener, ServletContextAttributeListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DbManager dbManager = DbManager.getInstance();
        DbCheckManager dbCheckManager = DbCheckManager.getInstance();
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("dbManager", dbManager);
        servletContext.setAttribute("dbCheckManager", dbCheckManager);
    }

    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        System.out.println("attribute added");
    }
}
