import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class PDFUtil {
	public void ConvertToPDF(String docPath, String pdfPath) {
        try {
            InputStream doc = new FileInputStream(new File(docPath));
            XWPFDocument document = new XWPFDocument(doc);
            PdfOptions options = PdfOptions.create();
            OutputStream out = new FileOutputStream(new File(pdfPath));
            PdfConverter.getInstance().convert(document, out, options);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

//	public static void main(String[] args)  {
//		String input = "D:\\git_repository\\SPRING_ORM\\src\\main\\webapp\\uploads\\sample-2pp.docx"; // .gif and .jpg are ok too!
//		String output = "D:\\git_repository\\SPRING_ORM\\src\\main\\webapp\\uploads\\444.pdf";
//
//
//		PDFUtil cwoWord = new PDFUtil();
//	    cwoWord.ConvertToPDF(input, output);
//
//		System.out.println("done");
//
//	}


}
