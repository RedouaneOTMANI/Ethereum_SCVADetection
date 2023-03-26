/**
 * @author Mélissa MAZROU
 * @author Redouane OTMANI
 * 
 */
package pfe_32_aid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.pdf.EncryptionConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;

public class SC_App_01_ML {

	public static String contractAddress="";
	public static boolean bVulnerableContract=false;
	
	public static String detectionResult;
	
/*	public static long lTempsDebut;
	public static long lTempsFin;
*/	
	



	public static void initializeAllVariables() {
		contractAddress="";
		bVulnerableContract=false;
		
		detectionResult="";
		
	/*	lTempsDebut=0;
		lTempsFin=0;
	*/	
		
	}
	
	static void initApplicationML(String contractAddressConstructor) {
		initializeAllVariables();
		
		//lTempsDebut = System.currentTimeMillis();
				
		contractAddress = contractAddressConstructor;
		
		try {
			
    		File file1 =new File("Application1Temp.py");
    		InputStream input1 = SC_App_01_ML.class.getResourceAsStream("Application1.py");
    		FileUtils.copyInputStreamToFile(input1, file1);
    		
    		File file2 = new File("model1-b-2.h5");
            InputStream input2 = SC_App_01_ML.class.getResourceAsStream("model1-b-2.h5");
            FileUtils.copyInputStreamToFile(input2, file2);
            
            
    		Process p = Runtime.getRuntime().exec("python "+ file1);
    		
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			
			// Getting output from the python script.
			
			String sOutput;
            
			
            while ((sOutput = stdInput.readLine()) != null) {
                //System.out.println(sOutput);
                detectionResult = sOutput;
        		//JOptionPane.showMessageDialog(null, sOutput,"Information",JOptionPane.INFORMATION_MESSAGE);
            }

            String sErrorsTemps="";
            String sErrors ="";

            // .

            //
            while ((sErrorsTemps = stdError.readLine()) != null) {
            	sErrors += sErrorsTemps+"\n";
            }
            //

    		//Deleting tmp files..
            file1.delete();file2.delete();

    		
			} catch (Exception e) {
				//JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
		          //  e.printStackTrace();
		            //JOptionPane.showMessageDialog(null, e.getMessage(),"Exception",JOptionPane.ERROR_MESSAGE);
		            System.exit(-1); 
			}

		
		if(detectionResult.startsWith("Vulnerable")) {
    		bVulnerableContract=true;
		}

		//lTempsFin = System.currentTimeMillis();
		
		GenerateReportPDF();
		
		//System.out.println("Fin");
	}

	
	public static void GenerateReportPDF() {
		String html = "<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<body style=\"font-family:calibri;font-size:14px;line-height:0.3;\">\r\n";

		html +="<h1 style=\"text-align: center;\"><span style=\"color: #003366;\">Detection Report of App1</span></h1>\r\n" + 
				"<p>&nbsp;</p>\r\n" + 
				"<p>&nbsp;</p>\r\n";
		
		html +="<h2><span style=\"color: #003366;\">1. Detection Result&nbsp;</span></h2>\r\n" + 
				"<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Contract Address : <strong>"+contractAddress+"</strong></span></li>\r\n" + 
				"</ul>\r\n";
		
		if(bVulnerableContract) {
			
			html += "<ul style=\"list-style-type: circle;\">\r\n" + 
					"<li>This contract <strong>is vulnerable to one or more of the following vulnerabilities/attacks :</strong><br>"
					+ "<br><br><br><br>" + 
					"<ol>\r\n" + 
					"<li><strong>Reentrancy&nbsp;</strong></li><br><br><br>" + 
					"<li><strong>Integer Overflow and Underflow</strong></li><br><br><br>" + 
					"<li><strong>Transaction order dependence</strong></li><br><br><br>" + 
					"<li><strong>Timestamp dependence</strong></li><br><br><br>" + 
					"</ol>" + 
					"</li>" + 
					"</ul>";
			
		}else {
			html += "<ul style=\"list-style-type: circle;\">\r\n" + 
					"<li>This contract <strong>is not vulnerable to the following vulnerabilities/attacks :</strong><br>"
					+ "<br><br><br><br>" + 
					"<ol>\r\n" + 
					"<li><strong>Reentrancy&nbsp;</strong></li><br><br><br>" + 
					"<li><strong>Integer Overflow and Underflow</strong></li><br><br><br>" + 
					"<li><strong>Transaction order dependence</strong></li><br><br><br>" + 
					"<li><strong>Timestamp dependence</strong></li><br><br><br>" + 
					"</ol>" + 
					"</li>" + 
					"</ul>";
			
		}
		
		/*
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li>&nbsp;Le temps d'ex&eacute;cution de l'application 1: <strong>"+(lTempsFin - lTempsDebut)/1000+" seconde(s)</strong></li>\r\n" + 
				"</ul>\r\n" + 
				"<p>&nbsp;</p>";
		*/
		
		
		html += "<h2><span style=\"color: #003366;\">2.&nbsp;Results of the evaluation of the machine learning model&nbsp;</span></h2>\r\n" + 
				"<p>&nbsp;</p>\r\n";
		
		html += "<table style=\"height: 275px; width: 420px; margin-left: auto; margin-right: auto;\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
				"<tbody>\r\n" + 
				"<tr style=\"height: 40px;\">\r\n" + 
				"<td style=\"width: 244px; height: 40px; padding-left: 30px;\"><strong>Classification performance<br><br><br>measures</strong></td>\r\n" + 
				"<td style=\"width: 158px; height: 40px; padding-left: 30px;\"><strong>Results of the LSTM model</strong></td>\r\n" + 
				"</tr>\r\n";
		
		html += "<tr style=\"height: 27px;\">\r\n" + 
				"<td style=\"width: 244px; height: 27px; padding-left: 30px;\"><strong>Accuracy</strong></td>\r\n" + 
				"<td style=\"width: 158px; height: 27px; padding-left: 90px;\"><strong>86%</strong></td>\r\n" + 
				"</tr>\r\n" + 
				"<tr style=\"height: 27px;\">\r\n" + 
				"<td style=\"width: 244px; height: 27px; padding-left: 30px;\"><strong>Loss</strong></td>\r\n" + 
				"<td style=\"width: 158px; height: 27px; padding-left: 90px;\"><strong>35%</strong></td>\r\n" + 
				"</tr>\r\n" + 
				"<tr style=\"height: 27px;\">\r\n" + 
				"<td style=\"width: 244px; height: 27px; padding-left: 30px;\"><strong>Recall</strong></td>\r\n" + 
				"<td style=\"width: 158px; height: 27px; padding-left: 90px;\"><strong>86%</strong></td>\r\n" + 
				"</tr>\r\n" + 
				"<tr style=\"height: 27px;\">\r\n" + 
				"<td style=\"width: 244px; height: 27px; padding-left: 30px;\"><strong>Precision</strong></td>\r\n" + 
				"<td style=\"width: 158px; height: 27px; padding-left: 90px;\"><strong>86%</strong></td>\r\n" + 
				"</tr>\r\n" + 
				"<tr style=\"height: 27.8958px;\">\r\n" + 
				"<td style=\"width: 244px; height: 27.8958px; padding-left: 30px;\"><strong>F1-score</strong></td>\r\n" + 
				"<td style=\"width: 158px; height: 27.8958px; padding-left: 90px;\"><strong>86%</strong></td>\r\n" + 
				"</tr>\r\n" + 
				"<tr style=\"height: 27.8958px;\">\r\n" + 
				"<td style=\"width: 244px; height: 27.8958px; padding-left: 30px;\"><strong>AUC (Area under the ROC<br><br><br>Curve)</strong></td>\r\n" + 
				"<td style=\"width: 158px; height: 27.8958px; padding-left: 90px;\"><strong>86%</strong></td>\r\n" + 
				"</tr>\r\n" + 
				"</tbody>\r\n" + 
				"</table>\r\n" + 
				"<p>&nbsp;</p>\r\n" + 
				"<p>&nbsp;</p>\r\n" + 
				"<p>&nbsp;</p>";
		
		
		
		
		
		html += "</body>" + 
				"</html>" ;

		
		try {
			createPdf(html, "App_01_Report.pdf");
			
				
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,e1.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			e1.printStackTrace();
			}

	}
	
	
	public static void createPdf(String html, String dest) throws IOException {
		
	    //
		
		ConverterProperties properties = new ConverterProperties();
		    properties.setFontProvider(new DefaultFontProvider(true, true, true));
		    
		    HtmlConverter.convertToPdf(html, new FileOutputStream(dest),properties);
	}

	

}
