package my.project.servlets;

import my.project.db.DbCheckManager;
import my.project.db.DbManager;
import my.project.db.DbProductManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
/**
 * add attributes to context listener
 * */
public class ContextListener implements ServletContextListener, ServletContextAttributeListener {
    private static final Logger LOG = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DbManager dbManager = DbManager.getInstance();
        DbCheckManager dbCheckManager = DbCheckManager.getInstance();
        DbProductManager dbProductManager = DbProductManager.getInstance();
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("dbManager", dbManager);
        servletContext.setAttribute("dbCheckManager", dbCheckManager);
        servletContext.setAttribute("dbProductManager", dbProductManager);

    }

    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        LOG.info("attribute added");
    }
}
