package my.project.services.commands.check;

import my.project.db.DbCheckManager;
import my.project.services.commands.Command;

import javax.servlet.http.HttpServletRequest;

public interface CommandCheck extends Command {
    default DbCheckManager getDbm(HttpServletRequest req) {
        return (DbCheckManager) req.getServletContext().getAttribute("dbCheckManager");
    }
}
