package my.project.services.commands.senior;

import my.project.services.commands.Command;
import my.project.db.DbCheckManager;

import javax.servlet.http.HttpServletRequest;

public interface SeniorCommand extends Command {
    default DbCheckManager getDbm(HttpServletRequest req){
        return (DbCheckManager)  req.getServletContext().getAttribute("dbCheckManager");
    }
}
