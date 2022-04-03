package my.project.services.commands.check;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
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
    public static final String FONT = "/fonts/arial.ttf";
    public static final int FONTSIZE = 12;

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
        float margin = 5;

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
            PdfPTable table = new PdfPTable(1);
            table.addCell(new Phrase(String.format("Check id: %s", check.getId()), new Font(Font.FontFamily.HELVETICA, FONTSIZE, Font.BOLD)));
            table.addCell(new Phrase(String.format("Cashier id: %s", user.getId()), new Font(Font.FontFamily.HELVETICA, FONTSIZE, Font.ITALIC)));
            table.addCell(new Phrase(String.format("Print date: %s", new Date())));
            for (Map.Entry<Product, Double> prod : prodWithPrice.entrySet()) {
                String text = String.format("%s\n\t%s x %s = %s",prod.getKey().getName(), prod.getKey().getPrice(), prod.getKey().getNumber(), prod.getValue());
                table.addCell(new Phrase(text));
            }
            table.addCell(new Phrase(String.format("Total sum: %s", req.getAttribute("totalSum"))));
            BaseFont baseFont = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            String toSplit = (String) req.getSession().getAttribute("print");
            String[] wishAndThanks = toSplit.split("///");
            String thanks = wishAndThanks[0];
            String wish = wishAndThanks[1];
            Font font = new Font(baseFont, FONTSIZE, Font.NORMAL);
            table.addCell(new Phrase(thanks, font));
            table.addCell(new Phrase(wish, font));
            doc.add(table);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            req.getSession().setAttribute("print", null);
            doc.close();
        }


    }
}
