package my.project.services.servlets;

import my.project.entity.Product;
import my.project.entity.Transaction;
import my.project.entity.User;
import my.project.services.db.DbCheckManager;
import my.project.services.db.DbProductManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


public class CheckServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(CheckServlet.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("checkServlet#doPost");
        LOG.debug("session id {}", req.getSession().getId());


        req.getSession().setAttribute("check", null);
        LOG.debug("in session get senior {}",req.getSession().getAttribute("senior"));
        if (req.getSession().getAttribute("senior")!=null){
            req.getRequestDispatcher("/views/workPlace/check_edit_with_senior.jsp").forward(req, resp);
        }else {
            req.getSession().setAttribute("senior", null);
        }

        System.out.println("redact param"+req.getParameter("redact"));
        if (req.getParameter("redact")!=null){
            req.getRequestDispatcher("/index.html").forward(req, resp);
        }
        req.getRequestDispatcher("/views/workPlace/check_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("in check#doGet");
        DbCheckManager dbm = DbCheckManager.getInstance();

        //log param
        req.getParameterMap().keySet().forEach(LOG::debug);
        //chose action
        if (req.getParameter("createCheck")!=null)createCheck(req, dbm);
        if (req.getParameter("closeCheck")!=null)closeCheck(req, dbm);
        if (req.getParameter("selectedProduct")!=null)addSelectedProduct(req, dbm);
        if (req.getParameter("setNumber")!=null)setPriceByNumber(req, dbm);

        //init
        Transaction check = (Transaction) req.getSession().getAttribute("check");
            //get product + current price
        Map<Product, Double> productsAndCurrentPrise = new LinkedHashMap<>();

        List<Product> products = dbm.getAllProdOfCheck(check.getId());
        for (int i = 0; i < products.size(); i++) {
            productsAndCurrentPrise.put(
                    products.get(i),
                    dbm.getCurrentPrice(
                            check.getId(),
                            products.get(i).getId()
                    )
            );
        }
        //set total sum to db
        setTotalSumToDB(setTotalSum(productsAndCurrentPrise), dbm, check.getId());
        //make attribute to translate to jsp
        //______________________________
        //for prod list on jsp
        req.setAttribute("products", productsAndCurrentPrise);
        //for choose product on jsp
        List<Product> allProducts = DbProductManager.getInstance().getAllProducts();
        req.setAttribute("allProducts", allProducts);

        //for total sum
        req.setAttribute("totalSum", dbm.getTotalSum(check.getId()));
        //send
        req.getRequestDispatcher("/views/workPlace/check.jsp").forward(req, resp);
    }

    private Double setTotalSum (Map<Product, Double> productDoubleMap){
        return productDoubleMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .reduce(Double::sum)
                .orElse(0d);
    }

    private void setTotalSumToDB(Double totalSum , DbCheckManager dbm, Integer checkId){
        dbm.setTotalSum(totalSum, checkId);
    }

    private void setPriceByNumber(HttpServletRequest req, DbCheckManager dbm) {
        //init
        LOG.debug("set price by number");
        Transaction transaction=(Transaction) req.getSession().getAttribute("check");
        LOG.debug(transaction);
        Integer productId = Integer.valueOf(req.getParameter("productId"));
        LOG.debug(req.getParameter("productId"));
        Double number = Double.parseDouble(req.getParameter("number"));
        LOG.debug("number = {}",req.getParameter("number"));
        //doing
        dbm.setProdNumber(number, transaction.getId(), productId);
        dbm.setCurrentPrice(transaction.getId(),productId);
//        req.setAttribute("currentPrice", dbm.getCurrentPrice(transaction.getId(),productId));
    }

    private void addSelectedProduct(HttpServletRequest req, DbCheckManager dbm) {
        LOG.debug("add selected product");
        Transaction transaction=(Transaction) req.getSession().getAttribute("check");
        LOG.debug(transaction);
        req.getParameterMap().keySet().forEach(System.out::println);
        var prodId =  req.getParameter("product").split(" ")[1];
        Integer id = Integer.valueOf(prodId);
        LOG.debug(req.getParameter("product"));
        dbm.addProd(transaction.getId(), id);
    }


    private void createCheck(HttpServletRequest req, DbCheckManager dbm){
        LOG.debug("create check");
        User user = (User) req.getSession().getAttribute("user");
        dbm.newCheck(user.getId());
        req.getSession().setAttribute("check", dbm.getCheck(user.getId()));
        LOG.debug("{} === {}", dbm.getCheckId(user.getId()),  req.getSession().getAttribute("check"));
    }

    private void closeCheck(HttpServletRequest req, DbCheckManager dbm){
        LOG.debug("close check");
        User user= (User) req.getSession().getAttribute("user");
        dbm.closedCheck(dbm.getCheckId(user.getId()));
    }


}
