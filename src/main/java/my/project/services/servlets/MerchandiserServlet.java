package my.project.services.servlets;

import my.project.entity.Product;
import my.project.services.db.DbProductManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MerchandiserServlet extends HttpServlet {

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


        req.setAttribute("listOfProduct", viewProduct(req));
        req.getRequestDispatcher("views/workPlace/merch.jsp").forward(req, resp);
    }

    private List<Product> viewProduct (HttpServletRequest req){
        var dbm = (DbProductManager) req.getSession().getAttribute("dbm");
         return dbm.getAllProducts();
    }

    private void changePrice(HttpServletRequest req) {
        var dbm = (DbProductManager) req.getSession().getAttribute("dbm");
        String name = req.getParameter("name");
        Double price = Double.valueOf(req.getParameter("price"));
        dbm.changeProductPrice(name, price);

    }


    private void insertProduct(HttpServletRequest req){
        var dbm = (DbProductManager) req.getSession().getAttribute("dbm");
        String prName = req.getParameter("name");
        Double price =  Double.valueOf(req.getParameter("price"));
        Integer count = Integer.valueOf(req.getParameter("count"));
        boolean div =   Boolean.parseBoolean(req.getParameter("div"));
        Product product = new Product(prName, price, count, div);
        dbm.insertProduct(product);

    }

    private void deleteProduct (HttpServletRequest req) {
        var dbm = (DbProductManager) req.getSession().getAttribute("dbm");
        String name = req.getParameter("prodName");
        dbm.deleteProduct(name);
    }


}
