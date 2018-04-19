package com.kosmo.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.POIXMLException;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.icepdf.core.pobjects.Document;
//import org.icepdf.core.pobjects.Page;
//import org.icepdf.core.util.GraphicsRenderingHints;

public class Converter {

	static float SCALE = 1.0f;
	static float ROTATION = 0f;

	public static void pptx2png(InputStream in, OutputStream out) throws IOException {

		File tempFile = File.createTempFile("tempfile", ".ppt");
		FileUtils.copyInputStreamToFile(in, tempFile);
		FileInputStream fileInputStream = new FileInputStream(tempFile);
		XMLSlideShow ppt = null;
		try {
			ppt = new XMLSlideShow(fileInputStream);
		} catch (POIXMLException e) {
			// try ppt?
			ppt2png(tempFile, out);
			return;
		} finally {
			ppt.close();
			tempFile.delete();
			fileInputStream.close();
		}

		Dimension pgsize = ppt.getPageSize();
		int width = (int) (pgsize.width * SCALE);
		int height = (int) (pgsize.height * SCALE);

		if (ppt.getSlides().size() > 0) {
			XSLFSlide firstSlide = ppt.getSlides().get(0);

			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			firstSlide.draw(prepareGraphics(img, width, height));
			ImageIO.write(img, "png", out);
		}
	}

	private static Graphics2D prepareGraphics(BufferedImage img, int width, int height) {
		Graphics2D graphics = img.createGraphics();
		// default rendering options
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

		graphics.setColor(Color.white);
		graphics.clearRect(0, 0, width, height);

		graphics.scale(SCALE, SCALE);
		return graphics;
	}

	public static void ppt2png(File file, OutputStream out) throws IOException {
		try (FileInputStream in = new FileInputStream(file.getAbsolutePath())) {
			ppt2png(in, out);
		}
	}

	public static void ppt2png(InputStream in, OutputStream out) throws IOException {
		SlideShow ppt = new SlideShow(new HSLFSlideShow(in));
		Dimension pgsize = ppt.getPageSize();
		int width = (int) (pgsize.width * SCALE);
		int height = (int) (pgsize.height * SCALE);
		if (ppt.getSlides().length > 0) {
			Slide firstSlide = ppt.getSlides()[0];
			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			firstSlide.draw(prepareGraphics(img, width, height));
			ImageIO.write(img, "png", out);
		}
	}

	public static void docx2png(InputStream in, OutputStream out) throws IOException {
		XWPFDocument document = new XWPFDocument(in);
		PdfOptions options = PdfOptions.create();

		ByteArrayOutputStream outPdf = new ByteArrayOutputStream();
		PdfConverter.getInstance().convert(document, outPdf, options);

		ByteArrayInputStream pdfIn = new ByteArrayInputStream(outPdf.toByteArray());
		pdf2pngUsingPdfBox(pdfIn, out);
	}

//	public static void pdf2png(InputStream in, OutputStream out) throws IOException {
//		Document document = new Document();
//		try {
//			document.setInputStream(in, null);
//			if (document.getNumberOfPages() > 0) {
//				BufferedImage img = (BufferedImage) document.getPageImage(0, GraphicsRenderingHints.PRINT,
//						Page.BOUNDARY_CROPBOX, ROTATION, SCALE);
//				ImageIO.write(img, "png", out);
//			}
//			document.dispose();
//		} catch (Exception e) {
//			throw new IOException(e);
//		}
//	}

	public static void pdf2pngUsingPdfBox(InputStream in, OutputStream out) throws IOException {
		PDDocument document = PDDocument.load(in);
		PDFRenderer pdfRenderer = new PDFRenderer( document );
		System.out.println( out + "전체페이지 수 : " + document.getNumberOfPages() );
		BufferedImage img = pdfRenderer.renderImageWithDPI( 0, 100, ImageType.RGB );
		ImageIO.write(img, "png", out);
		document.close();

//		PDDocument pdf = PDDocument.load(in);
//		List<PDPage> pages = pdf.getDocumentCatalog().getAllPages();
//		if (pages.size() > 0) {
//			PDPage firstPage = pages.get(0);
//			BufferedImage img = firstPage.convertToImage(BufferedImage.TYPE_INT_RGB, 96);
//			ImageIO.write(img, "png", out);
//		}
	}

}