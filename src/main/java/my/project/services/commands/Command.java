package my.project.services.commands;

import my.project.services.db.DbCheckManager;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    default DbCheckManager getDbm(){
        return DbCheckManager.getInstance();
    }
    void execute();

}
