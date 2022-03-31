package my.project.services.commands.merch;

import my.project.db.DbProductManager;
import my.project.services.commands.Command;

import javax.servlet.http.HttpServletRequest;

public interface CommandMerch extends Command {
    default DbProductManager getDbm(HttpServletRequest req) {
        return (DbProductManager) req.getServletContext().getAttribute("dbProductManager");
    }


}
