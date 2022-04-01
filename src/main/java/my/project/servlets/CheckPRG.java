package my.project.servlets;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import my.project.db.DbCheckManager;
import my.project.db.DbProductManager;
import my.project.model.Product;
import my.project.model.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * servlet to execute PRG pattern
 */
@WebServlet(name = "CheckPRG", value = "/CheckPRG")
public class CheckPRG extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String toCheck = "/views/workPlace/check.jsp";
        setAttributeForJsp(req);
        if (req.getSession().getAttribute("print") != null) {
            try {
                createPdf(req, resp);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        req.getRequestDispatcher(toCheck).forward(req, resp);
    }

    /**
     * prepare attributes to create view
     */
    private void setAttributeForJsp(HttpServletRequest req) {
        DbCheckManager dbm = DbCheckManager.getInstance();
        Transaction check = (Transaction) req.getSession().getAttribute("check");
        Map<Product, Double> productsAndCurrentPrise = new LinkedHashMap<>();
        List<Product> products = dbm.getAllProdOfCheck(check.getId());
        for (int i = 0; i < products.size(); i++) {
            products.get(i).setNumber(dbm.getNumber(check.getId(), products.get(i).getId()));
            productsAndCurrentPrise.put(products.get(i), dbm.getCurrentPrice(check.getId(), products.get(i).getId()));
        }
        //for prod list on jsp
        req.setAttribute("products", productsAndCurrentPrise);
        //for choose product on jsp
        List<Product> allProducts = DbProductManager.getInstance().getAllProducts();
        req.setAttribute("allProducts", allProducts);
        //for total sum
        req.setAttribute("totalSum", dbm.getTotalSum(check.getId()));
    }

    private void createPdf(HttpServletRequest req, HttpServletResponse resp) throws DocumentException, IOException {
        System.out.println("create pdf");
        System.out.println("in check#doGet");
        //log param
        System.out.println("start log param:");
        req.getParameterMap().keySet().forEach(System.out::println);
        System.out.println("end log param;");
        String text = "aloha!";
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=aga.pdf");
        Document doc = new Document();
        PdfWriter.getInstance(doc, resp.getOutputStream());
        doc.open();
        doc.add(new Paragraph(text));
        doc.close();

    }
}
