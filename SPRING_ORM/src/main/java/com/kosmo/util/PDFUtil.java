package com.kosmo.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class PDFUtil {
	public void ConvertDocToPDF(String docPath, String pdfPath) {
        try {
            InputStream is = new FileInputStream(new File(docPath));
            OutputStream out = new FileOutputStream(new File(pdfPath));

            XWPFDocument document = new XWPFDocument(is);
            PdfOptions options = PdfOptions.create();
            PdfConverter.getInstance().convert(document, out, options);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


	public void ConvertPptToPDF(String docPath, String pdfPath) {
        try {
            InputStream is = new FileInputStream(new File(docPath));
            OutputStream out = new FileOutputStream(new File(pdfPath));

            XMLSlideShow ppt = new XMLSlideShow(is);
            XSLFSlide slide1 = ppt.createSlide();
            XSLFSlide firstSlide = ppt.getSlides().get(0);
            ppt.write(out);

            slide1.clear();
            firstSlide.clear();
            ppt.close();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {

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
