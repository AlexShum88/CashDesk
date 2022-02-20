package my.project.servlets;

import my.project.entity.Product;
import my.project.services.db.DbProductManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MerchandiserServlet extends HttpServlet {
    Logger LOG = LogManager.getLogger(MerchandiserServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DbProductManager productManager = DbProductManager.getInstance();
        req.getSession().setAttribute("dbm", productManager);



        req.setAttribute("listOfProduct", viewProduct(req));
        req.getRequestDispatcher("views/workPlace/merch.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("insert")!=null) insertProduct(req);
        if (req.getParameter("delete")!=null) deleteProduct(req);
        if (req.getParameter("setPrice")!=null) changePrice(req);
        if (req.getParameter("setNumber")!=null) changeNumber(req);



        req.setAttribute("listOfProduct", viewProduct(req));
        req.getRequestDispatcher("views/workPlace/merch.jsp").forward(req, resp);
    }

    private void changeNumber(HttpServletRequest req) {
        LOG.debug("in change price");
        var dbm = (DbProductManager) req.getSession().getAttribute("dbm");
        Integer id = Integer.parseInt(req.getParameter("prodID"));
        Double number = Double.valueOf(req.getParameter("number"));
        dbm.changeProductNumber(id, number);

    }

    private List<Product> viewProduct (HttpServletRequest req){
        LOG.debug("in view product");
        var dbm = (DbProductManager) req.getSession().getAttribute("dbm");
         return dbm.getAllProducts();
    }

    private void changePrice(HttpServletRequest req) {
        LOG.debug("in change price");
        var dbm = (DbProductManager) req.getSession().getAttribute("dbm");
        Integer id = Integer.parseInt(req.getParameter("prodID"));
        Double price = Double.valueOf(req.getParameter("price"));
        dbm.changeProductPrice(id, price);

    }


    private void insertProduct(HttpServletRequest req){
        LOG.debug("in insert product");
        System.out.println("in insert product");
        System.out.println(req.getParameter("newName"));
        var dbm = (DbProductManager) req.getSession().getAttribute("dbm");
        String prName = req.getParameter("newName");
        Double price =  0.0;
        Double number =  0.0;
        Product product = new Product(prName, price, number);
        dbm.insertProduct(product);

    }

    private void deleteProduct (HttpServletRequest req) {
        LOG.debug("in delete product");
        var dbm = (DbProductManager) req.getSession().getAttribute("dbm");
        Integer id = Integer.parseInt(req.getParameter("prodID"));
        dbm.deleteProduct(id);



    }




}
