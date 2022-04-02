package my.project.services.commands.check;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import my.project.model.Product;
import my.project.model.Transaction;
import my.project.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class PrintCheckCommand implements CommandCheck {
    HttpServletRequest req;
    HttpServletResponse resp;

    public PrintCheckCommand(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
    }

    @Override
    public void execute() {
        createPdf(req, resp);
    }

    private void createPdf(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("create pdf");

        System.out.println("in check#doGet");
        //log param
        System.out.println("start log param:");
        for (Iterator<String> it = req.getSession().getAttributeNames().asIterator(); it.hasNext(); ) {
            String name = it.next();
            System.out.println(name);
        }
        System.out.println("________");
        for (Iterator<String> it = req.getAttributeNames().asIterator(); it.hasNext(); ) {
            String name = it.next();
            System.out.println(name);
        }
        System.out.println("end log param;");
        Map<Product, Double> prodWithPrice = (Map<Product, Double>) req.getAttribute("products");
        float pageWidth = 150f;
        float pageHeight = 150f + (30 * prodWithPrice.size());
        float margin = 2;

        User user = (User) req.getSession().getAttribute("user");
        Transaction check = (Transaction) req.getSession().getAttribute("check");

        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=aga.pdf");
        Document doc = new Document();

        try {

            PdfWriter.getInstance(doc, resp.getOutputStream());
            doc.setPageSize(new Rectangle(pageWidth, pageHeight));
            doc.setMargins(margin, margin, margin, margin);
            doc.open();
            doc.add(new Paragraph(String.format("Check id: %s", check.getId()), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.ITALIC)));
            doc.add(new Paragraph(String.format("Cashier id: %s", user.getId()), new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            doc.add(new Paragraph(String.format("Print date: %s", new Date())));

            for (Map.Entry<Product, Double> prod : prodWithPrice.entrySet()) {
                doc.add(new Paragraph(prod.getKey().getName()));
                doc.add(new Paragraph(String.format("   %s x %s = %s", prod.getKey().getPrice(), prod.getKey().getNumber(), prod.getValue())));
            }
            doc.add(new Paragraph(String.format("Total sum: %s", req.getAttribute("totalSum"))));

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            req.getSession().setAttribute("print", null);
            doc.close();
        }


    }
}
