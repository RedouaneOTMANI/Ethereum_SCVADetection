/**
 * @author Mélissa MAZROU
 * @author Redouane OTMANI
 * 
 */
package pfe_32_aid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.pdf.EncryptionConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;

public class SC_App_04_Bad_Randomness {

	/**Variables if source code is available**/
	public static String contractAddressReportAvailableSourceCode;
	public static List <String> listInternalContractsReportAvailableSourceCode=new ArrayList<String>();
	public static List <String> listFunctionsOfContractReportAvailableSourceCode = new ArrayList<String>();
	public static List <String> listVerifyBadRandomnessReportAvailableSourceCode= new ArrayList<String>();
	
	public static List <String> listVulnerabilitesFoundAvailableSourceCode= new ArrayList<String>();

	
	/**Variables if source code is reversed**/
	public static String contractAddressReportReversedSourceCode;
	public static List <String> listInternalContractsReportReversedSourceCode=new ArrayList<String>();
	public static List <String> listHeaderFunctionsOfContractReportReversedSourceCode = new ArrayList<String>();
	public static List <String> listVerifyBadRandomnessReportReversedSourceCode= new ArrayList<String>();
	public static List <String> listVulnerabilitesFoundReversedSourceCode= new ArrayList<String>();
	


	public static void initializeAllVariables() {
		contractAddressReportAvailableSourceCode="";
		listInternalContractsReportAvailableSourceCode=new ArrayList<String>();
		listFunctionsOfContractReportAvailableSourceCode = new ArrayList<String>();
		listVerifyBadRandomnessReportAvailableSourceCode= new ArrayList<String>();
		
		listVulnerabilitesFoundAvailableSourceCode= new ArrayList<String>();

		
		contractAddressReportReversedSourceCode="";
		listInternalContractsReportReversedSourceCode=new ArrayList<String>();
		listHeaderFunctionsOfContractReportReversedSourceCode = new ArrayList<String>();
		listVerifyBadRandomnessReportReversedSourceCode= new ArrayList<String>();
		listVulnerabilitesFoundReversedSourceCode= new ArrayList<String>();

	}
	
	
	public static void initBad_Randomness_App4(String contractAddress) {
		initializeAllVariables();
		
		Boolean bSourceCodeVerified=false;
		File file =new File("Smart Contracts/"+contractAddress);

		String [] directories = file.list(new FilenameFilter() {
			@Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		
		for (int j = 0; 
				j < directories.length; 
				j++) {
			if(directories[j].contains("Contract_Source_Code")) {bSourceCodeVerified=true;break;}
		}

		
		if(bSourceCodeVerified) {

			initApplicationAvailableSourceCodeCodeDispo(contractAddress);
			//for the report
			contractAddressReportAvailableSourceCode = contractAddress;
			generateReportPDFAvailableSourceCode();

			
		}
		else {
			
			initApplicationReversedSourceCode(contractAddress);
			
			//for the report
			contractAddressReportReversedSourceCode = contractAddress;
		
			generateReportPDFReversedSourceCode();
		}
		
		
	}
	
	
	
	public static void initApplicationAvailableSourceCodeCodeDispo(String contractAddress) {
		
		List<String> listContractSourceCode = new ArrayList<String>();
		String fileContract = "";
		
		try (BufferedReader br = 
				new BufferedReader(
						new FileReader(
								new File("Smart Contracts/"+contractAddress+"/Contract_Source_Code/ContractSourceCode.sol")))) {
			String line;
		    while ((line = br.readLine()) != null) {
		       fileContract += line+"\n";
		    }
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			//e.printStackTrace();
			} 


		/****Deleting all comments*******/
		fileContract = deleteCommentsOfContractAvailableSourceCode(fileContract);
		/**********************************************/
		for (int i = 0; i < fileContract.split("\n").length; i++) {
			
			if(fileContract.split("\n")[i].contains("\'")&&fileContract.split("\n")[i].contains("function")) {
		
				listContractSourceCode.add(fileContract.split("\n")[i].replace("function", "fonction"));
				
			}else {
				listContractSourceCode.add(fileContract.split("\n")[i]);
				
			}
			
		}
				
		
		int i=0;
		String lineTemp="";
		List <String> listInternalContracts = new ArrayList<String>();//
		List <String> listFunctionsTemp = new ArrayList<String>();
		List <String> listFunctionsOfContract = new ArrayList<String>();

		
		while (i < listContractSourceCode.size()) {
			
			if(
				  (listContractSourceCode.get(i).startsWith("contract")
						  ||
						  listContractSourceCode.get(i).startsWith("library") ) 
					
					&&  
					( 
							(listContractSourceCode.get(i).contains("{"))||
							(listContractSourceCode.get(i).contains("is")) ||
							(listContractSourceCode.get(i+1).contains("{"))
							
					))
			{

			lineTemp += listContractSourceCode.get(i)+"\n";
			i++;

				while(!listContractSourceCode.get(i).startsWith("}")) {
					lineTemp += listContractSourceCode.get(i)+"\n";
					i++;
				}
				
				if(listContractSourceCode.get(i).startsWith("}")) {
					lineTemp += listContractSourceCode.get(i)+"\n";
					}
			
				listInternalContracts.add(lineTemp);
				lineTemp="";
			
			}
			i++;
		}

		
		/**For the report**/
		for (String s : listInternalContracts) {
			listInternalContractsReportAvailableSourceCode.add(s);
		}
		
		
		for (int j = 0; j < listInternalContracts.size(); j++) {

			for (int k = 1; k < listInternalContracts.get(j).split("\n").length -1 ; k++) {
				
				if(listInternalContracts.get(j).split("\n")[k].replaceFirst("    ", "").replaceFirst("\t", "").replaceFirst("  ", "").startsWith("function")) {
					lineTemp += listInternalContracts.get(j).split("\n")[k].replaceFirst("    ", "").replaceFirst("\t", "").replaceFirst("  ", "")+"\n";
					k++;

					while(!listInternalContracts.get(j).split("\n")[k].replaceFirst("    ", "").replaceFirst("\t", "").replaceFirst("  ", "").startsWith("}")) {
						lineTemp += listInternalContracts.get(j).split("\n")[k].replaceFirst("    ", "").replaceFirst("\t", "").replaceFirst("  ", "")+"\n";
						k++;
					}
					
					if(listInternalContracts.get(j).split("\n")[k].replaceFirst("    ", "").replaceFirst("\t", "").replaceFirst("  ", "").startsWith("}")) {
						lineTemp += listInternalContracts.get(j).split("\n")[k].replaceFirst("    ", "").replaceFirst("\t", "").replaceFirst("  ", "")+"\n";
					}
					
					listFunctionsTemp.add(lineTemp);
					lineTemp="";
				
				}
			}
			
		}

		for (String s : listFunctionsTemp) {
			if(s.contains("{")) {
				listFunctionsOfContract.add(s);
			}
		}

		/***For the report*******/
		for (String s : listFunctionsOfContract) {
			listFunctionsOfContractReportAvailableSourceCode.add(s);
		}
		
		
		/****************We start processing...*************************/
		
		Pattern pattern = Pattern.compile("function (.*)");
		Matcher matcher=null;
		
		
		for (String s : listFunctionsOfContract) {
		
				matcher = pattern.matcher(s);
				if(matcher.find()) {
					if(matcher.group(1).split("\\(")[0].isBlank()) {
						
						verifyPatternBadRandomnessAvailableSourceCode(s,"fallback");

					}else {
						verifyPatternBadRandomnessAvailableSourceCode(s,matcher.group(1).split("\\(")[0]);
					}
				}
		}
	
	}
	
	
	public static String deleteCommentsOfContractAvailableSourceCode(String contractString) {

		contractString = contractString.replaceAll( "//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/", "$1 " );

		String []strTemp;

		strTemp = contractString.split("\n");
		contractString = "";
		
		for (int i = 0; i < strTemp.length; i++) {
			if(!strTemp[i].isBlank()) {
				contractString +=strTemp[i]+"\n";
			}
		}
		return contractString; 
	}
		
	
	public static void verifyPatternBadRandomnessAvailableSourceCode(String function, String functionHeader) {

		List<String> listLinesFunction = new ArrayList<String>();
		boolean b= false;
		
			//We split function line by line
			for (int i = 0; i < function.split("\n").length; i++) {
				listLinesFunction.add(function.split("\n")[i]);
			}

			for (String s : listLinesFunction) {
				if(Contains_Bad_Randomness_PatternAvailableSourceCode(s)) {
					
					if(!b) {
						listVerifyBadRandomnessReportAvailableSourceCode.add("Fonction "+functionHeader);
						
						b=true;
						}
					
					listVerifyBadRandomnessReportAvailableSourceCode.add(s);
				}
			}
	}

	
	public static boolean Contains_Bad_Randomness_PatternAvailableSourceCode(String line) {

		Pattern pattern = Pattern.compile(
				  "block.coinbase"
				+ "|block.difficulty"
				+ "|block.gaslimit"
				+ "|block.number"
				+ "|block.timestamp"
				+ "|now"
				+ "|block.blockhash"
				+ "|blockhash"
				);
		
		Matcher matcher = pattern.matcher(line);
		
			if(matcher.find()) {
				if( (line.contains("block.timestamp")||line.contains("now"))&& !listVulnerabilitesFoundAvailableSourceCode.contains("Timestamp dependency")) {
					listVulnerabilitesFoundAvailableSourceCode.add("Timestamp dependency");
					}
				
				if(line.contains("block.number") && !listVulnerabilitesFoundAvailableSourceCode.contains("Block number dependency") ) {
					listVulnerabilitesFoundAvailableSourceCode.add("Block number dependency");
					}
				
				if( (line.contains("block.blockhash")||line.contains("blockhash")) && !listVulnerabilitesFoundAvailableSourceCode.contains("solidity incorrect blockhash")) {
					listVulnerabilitesFoundAvailableSourceCode.add("solidity incorrect blockhash");
					}
				
				
				return true;
			}
	
	return false;
	}

	
	public static void generateReportPDFAvailableSourceCode() {
		
		String html = "<!DOCTYPE html>\r\n" + "<html>";
		
		html += "<head>" + 
				"<style>" + 
				"table {table-layout: fixed;width: 100%;}" + 
				"</style>" + 
				"</head>";

				
		html +=	 "<body style=\"font-family:calibri;font-size:14px;line-height:0.3;\">\r\n";

		html += "<h1 style=\"text-align: center;\"><span style=\"color: #003366;\">Detection report of App4</span></h1>\r\n" + 
				"<p>&nbsp;</p>\r\n" + 
				"<p>&nbsp;</p>\r\n" + 
				"<h2><span style=\"color: #003366;\">1. Detection results&nbsp;</span></h2>\r\n";
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Contract address: <strong>"+contractAddressReportAvailableSourceCode+"</strong></span></li>\r\n" + 
				"</ul>\r\n";
		

		boolean bVulnerable=false;
		for (String s : listVerifyBadRandomnessReportAvailableSourceCode) {
			if(!s.startsWith("Fonction")) {
				bVulnerable=true;
			}
		}

		if(bVulnerable) {
			html += "<ul style=\"list-style-type: circle;\">\r\n" + 
					"<li>This contract <strong>is vulnerable to &lt;Bad Randomness&gt;&nbsp;</strong></li>\r\n" + 
					"</ul>\r\n";
			
			for (String s: listVulnerabilitesFoundAvailableSourceCode) {
				html += "<ul style=\"list-style-type: circle;\">\r\n" + 
						"<li><span style=\"color: #000000;\">This contract <strong>is vulnerable to &lt;"
						+s
						+ "&gt;</strong></span></li>\r\n" + 
						"</ul>\r\n";
			}
		}
		else {
			html += "<ul style=\"list-style-type: circle;\">\r\n" + 
					"<li>This contract <strong>is not vulenrable to &lt;Bad Randomness&gt;&nbsp;</strong></li>\r\n" + 
					"</ul>\r\n";
		}
		

		html += "<p>&nbsp;</p>\r\n" + 
				"<h2><span style=\"color: #003366;\">2. Additional information&nbsp;</span></h2>\r\n";

		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Number of internal contracts : <strong>"+listInternalContractsReportAvailableSourceCode.size()+"</strong></span></li>\r\n" + 
				"</ul>\r\n";
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Headers of internal contracts</span></li>\r\n" + 
				"</ul>\r\n";
		
		html += "<p style=\"padding-left: 60px;\"><span style=\"color: #008080;\"><em>";
		
		for (String s : listInternalContractsReportAvailableSourceCode) {
			html += s.split("\n")[0].replace("{", "").replace("is","")+"<br><br><br>";
			
		}
		
		html += "</em></span></p>";
		
	html += "<ul style=\"list-style-type: circle;\">\r\n" + 
			"<li><span style=\"color: #000000;\">Number of functions in the contract : <strong>"+listFunctionsOfContractReportAvailableSourceCode.size()+"</strong></span></li>\r\n" + 
			"</ul>";
		
		
	html += "<ul style=\"list-style-type: circle;\">\r\n" + 
			"<li><span style=\"color: #000000;\">Headers of these functions</span></li>\r\n" + 
			"</ul>";
		
	html += "<p style=\"padding-left: 60px;\"><span style=\"color: #000000;\"><em>";
		
Pattern pattern = Pattern.compile("function(.*)");
Matcher matcher;
		for (String s : listFunctionsOfContractReportAvailableSourceCode) {
				matcher = pattern.matcher(s);
				if(matcher.find()) {

					if(matcher.group(1).split("\\(")[0].isBlank()) {
						//System.out.print("fallback, ");
						html += "<span style=\"color: #008080;\">fallback</span><br><br><br>";
						
					}else {
						//System.out.print(matcher.group(1).split("\\(")[0]+", ");
						html += "<span style=\"color: #008080;\">"+matcher.group(1).split("\\(")[0]+"</span><br><br><br>";						
					}
				}
		}
		
		
	html += "</em></span></p>";	

	html += "<p style=\"padding-left: 60px;\">&nbsp;</p>\r\n" + 
			"<p style=\"padding-left: 60px;\">&nbsp;</p>\r\n" + 
			"<p style=\"padding-left: 60px;\">&nbsp;</p>\r\n" + 
			"<ul style=\"list-style-type: circle;\">\r\n" + 
			"<li><span style=\"color: #000000;\">Analysis of &lt;Bad Randomness&gt;</span></li>\r\n" + 
			"</ul>\r\n";
	
	
	html += "<table style=\"width: 662px;\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
			"<tbody>\r\n";
	
	
	
	html += "<tr style=\"height: 13px;\">\r\n" + 
			"<td style=\"width: 207px; height: 13px; text-align: center;\">\r\n" + 
			"<p><span style=\"color: #000000;\"><strong>Functions</strong></span></p>\r\n" + 
			"</td>\r\n" + 
			"<td style=\"width: 459px; height: 13px; text-align: center;\">\r\n" + 
			"<p><span style=\"color: #000000;\"><strong>Lines of code involved</strong></span></p>\r\n" + 
			"</td>\r\n" + 
			"</tr>\r\n";
	
	
	if(bVulnerable) {
	
	
		for (int i = 0; i < listVerifyBadRandomnessReportAvailableSourceCode.size(); i++) {

			
			if(listVerifyBadRandomnessReportAvailableSourceCode.get(i).split("Fonction ")[1].length()>30) {
				html +="<tr style=\"height: 13px;\">\r\n" + 
						"<td style=\"width: 207px; height: 13px; text-align: center;\">\r\n" + 
						"<p><span style=\"color: #008080;\"><em>"
						+listVerifyBadRandomnessReportAvailableSourceCode.get(i).split("Fonction ")[1].substring(0, 30)+
						"<br><br><br>"
						+listVerifyBadRandomnessReportAvailableSourceCode.get(i).split("Fonction ")[1].substring(30, 
								listVerifyBadRandomnessReportAvailableSourceCode.get(i).split("Fonction ")[1].length())
						+"</em></span></p>\r\n" + 
						"</td>\r\n";
				
			}else {

				html +="<tr style=\"height: 13px;\">\r\n" + 
					"<td style=\"width: 207px; height: 13px; text-align: center;\">\r\n" + 
					"<p><span style=\"color: #008080;\"><em>"
					+listVerifyBadRandomnessReportAvailableSourceCode.get(i).split("Fonction ")[1]
					+"</em></span></p>\r\n" + 
					"</td>\r\n";

			}
			
			html +="<td style=\"width: 459px; height: 13px;\">\r\n" + 
					"<p style=\"padding-left: 30px;\">\r\n";
			
			while(i+1 < listVerifyBadRandomnessReportAvailableSourceCode.size() && 
					!listVerifyBadRandomnessReportAvailableSourceCode.get(i+1).startsWith("Fonction ")) {
					i++;
					
					if(listVerifyBadRandomnessReportAvailableSourceCode.get(i).length()>180) {
						
					}else {
						if(listVerifyBadRandomnessReportAvailableSourceCode.get(i).length()>180) {

							html += "<span style=\"color: #000000;\">"
							+listVerifyBadRandomnessReportAvailableSourceCode.get(i).substring(0, 60)
							+"<br><br><br>"
							+listVerifyBadRandomnessReportAvailableSourceCode.get(i).substring(60, 120)
							+"<br><br><br>"
							+listVerifyBadRandomnessReportAvailableSourceCode.get(i).substring(120, 180)
							+"<br><br><br>"							
							+listVerifyBadRandomnessReportAvailableSourceCode.get(i).substring(180, 
									listVerifyBadRandomnessReportAvailableSourceCode.get(i).length())
							+"</span><br><br><br>";
							
						}else {
							if(listVerifyBadRandomnessReportAvailableSourceCode.get(i).length()>120) {
								
								html += "<span style=\"color: #000000;\">"
								+listVerifyBadRandomnessReportAvailableSourceCode.get(i).substring(0, 60)
								+"<br><br><br>"
								+listVerifyBadRandomnessReportAvailableSourceCode.get(i).substring(60, 120)
								+"<br><br><br>"
								+listVerifyBadRandomnessReportAvailableSourceCode.get(i).substring(120, 
										listVerifyBadRandomnessReportAvailableSourceCode.get(i).length())
								+"</span><br><br><br>";
							}else {
								if(listVerifyBadRandomnessReportAvailableSourceCode.get(i).length()>60) {
									html += "<span style=\"color: #000000;\">"
									+listVerifyBadRandomnessReportAvailableSourceCode.get(i).substring(0, 60)
									+"<br><br><br>"
									+listVerifyBadRandomnessReportAvailableSourceCode.get(i).substring(60, 
											listVerifyBadRandomnessReportAvailableSourceCode.get(i).length())
									+"</span><br><br><br>";
								}	

								if(listVerifyBadRandomnessReportAvailableSourceCode.get(i).length()<=60) {
									html += "<span style=\"color: #000000;\">"+listVerifyBadRandomnessReportAvailableSourceCode.get(i)+"</span><br><br><br>";
								}
							}
						}
					}
			}
			html += "</p>\r\n" + 
					"</td>\r\n" + 
					"</tr>\r\n";
	
		}
	}	
		
		html += "</tbody>\r\n" + 
				"</table>";
		
		html += "</body>\r\n" + 
				"</html>\r\n" ;

		
		try {
			createPdf(html, "App_04_Report.pdf");
	
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,e1.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			e1.printStackTrace();
			}

		
		
	}
	
	

	public static void initApplicationReversedSourceCode(String contractAddress) {
		
		List<String> listSourceCodeContract = new ArrayList<String>();
		String fileContract = "";
		
		try (BufferedReader br = 
				new BufferedReader(
						new FileReader(
								new File("Smart Contracts/"+contractAddress+"/Contract_Reverted_Source_Code/SourceCode.sol")))) {
			String line;												     
		    while ((line = br.readLine()) != null) {
		       fileContract += line+"\n";
		    }
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			//e.printStackTrace();
			} 

		/****Deleting comments*******/
		fileContract = deleteCommentsOfContractReversedSourceCode(fileContract);
		/**********************************************/

		for (int i = 0; i < fileContract.split("\n").length; i++) {
			listSourceCodeContract.add(fileContract.split("\n")[i]);
		}
				
		
		int i=0;
		
		String TheContract = "";
		List <String> listHeaderOfFunctions = new ArrayList<String>();
		List <String> listFunctionsOfContractTemp = new ArrayList<String>();

		
		for (String s : listSourceCodeContract) {
			TheContract+= s;
		}
		
		/**For the report**/
			listInternalContractsReportReversedSourceCode.add(TheContract);


			try (BufferedReader br = 
					new BufferedReader(
							new FileReader(
									new File("Smart Contracts/"+contractAddress+"/Contract_Reverted_Source_Code/InternalMethods.sol")))) {
				String line;
			    while ((line = br.readLine()) != null) {
			       listHeaderOfFunctions.add(line);
			    }
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
				//e.printStackTrace();
				} 


				Pattern pattern = Pattern.compile("function main(.*)function "+listHeaderOfFunctions.get(0).split("\\(")[0]);
				Matcher matcher = pattern.matcher(TheContract);

				if(matcher.find()) {
							listFunctionsOfContractTemp.add("function main"+matcher.group(1));
				}
				
						
						for (int j = 0; j < listHeaderOfFunctions.size(); j++) {
							
							if(j+1==listHeaderOfFunctions.size()) {
								pattern = Pattern.compile("function "
										+ listHeaderOfFunctions.get(j).split("\\(")[0]
										+ "(.*)");
								
								matcher = pattern.matcher(TheContract);

								if(matcher.find()) {
									listFunctionsOfContractTemp.add("function "+ listHeaderOfFunctions.get(j).split("\\(")[0]+matcher.group(1));
								}
								
							}else {

								pattern = Pattern.compile("function "
										+ listHeaderOfFunctions.get(j).split("\\(")[0]
										+ "(.*)"
										+ "function "
										+ listHeaderOfFunctions.get(j+1).split("\\(")[0]);
								
								matcher = pattern.matcher(TheContract);

								if(matcher.find()) {
									listFunctionsOfContractTemp.add("function "+ listHeaderOfFunctions.get(j).split("\\(")[0]+matcher.group(1));
								}
							}
						}
						
			/******************************************************************/		

			/**For the report*/
			for (String s : listHeaderOfFunctions) {
				listHeaderFunctionsOfContractReportReversedSourceCode.add(s);
			}
			
			
			List<String> listFunctionsOfContract=new ArrayList<String>();
			
			for (String s : listFunctionsOfContractTemp) {
				listFunctionsOfContract.add(s.replace("{", "{\n").replace(";", ";\n"));
			}

			
			
			/*****************Strat processing*******************/
			
			VerifyPatternBadRandomnessCodeReverse(listFunctionsOfContract.get(0), "main()");

			for (int k = 1; k < listFunctionsOfContract.size(); k++) {
				
				VerifyPatternBadRandomnessCodeReverse(
						listFunctionsOfContract.get(k), 
						listHeaderOfFunctions.get(k-1));
				
			}
		
	}
	

	public static String deleteCommentsOfContractReversedSourceCode(String contractString) {
		contractString = contractString.replaceAll("(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|(//.*)", "");

		
		String []strTemp;

		strTemp = contractString.split("\n");
		contractString = "";
		
		for (int i = 0; i < strTemp.length; i++) {
			if(!strTemp[i].isBlank()) {
				contractString +=strTemp[i]+"\n";
			}
		}
		return contractString; 
	}
	
	
	public static void VerifyPatternBadRandomnessCodeReverse(String contract_function, String headerFunction) {

		List<String> listLinesFunction = new ArrayList<String>();
		boolean b= false;
		
			for (int i = 0; i < contract_function.split("\n").length; i++) {
				listLinesFunction.add(contract_function.split("\n")[i]);
			}

			for (String s : listLinesFunction) {
				if(Contains_Bad_Randomness_PatternReversedSourceCode(s)) {
					
					if(!b) {
						listVerifyBadRandomnessReportReversedSourceCode.add("Fonction "+headerFunction);
						
						b=true;
						}
					
					listVerifyBadRandomnessReportReversedSourceCode.add(s);
				}
			}
	}


	public static boolean Contains_Bad_Randomness_PatternReversedSourceCode(String line) {

		Pattern pattern = Pattern.compile(
				  "block.coinbase"
				+ "|block.difficulty"
				+ "|block.gaslimit"
				+ "|block.number"
				+ "|block.timestamp"
				+ "|now"
				+ "|block.blockhash"
				+ "|blockhash"
				);
		
		Matcher matcher = pattern.matcher(line);
		
			if(matcher.find()) {
	
				if( (line.contains("block.timestamp")||line.contains("now"))&& !listVulnerabilitesFoundReversedSourceCode.contains("Timestamp dependency")) {
					listVulnerabilitesFoundReversedSourceCode.add("Timestamp dependency");
					}
				
				if(line.contains("block.number") && !listVulnerabilitesFoundReversedSourceCode.contains("Block number dependency") ) {
					listVulnerabilitesFoundReversedSourceCode.add("Block number dependency");
					}
				
				if( (line.contains("block.blockhash")||line.contains("blockhash")) && !listVulnerabilitesFoundReversedSourceCode.contains("solidity incorrect blockhash")) {
					listVulnerabilitesFoundReversedSourceCode.add("solidity incorrect blockhash");
					}
				
				return true;
			}
	
	return false;
	}
	
	
	
	public static void generateReportPDFReversedSourceCode() {

		String html = "<!DOCTYPE html>\r\n" + 
				"<html>\r\n";  
				

		html += "<head>" + 
				"<style>" + 
				"table {table-layout: fixed;width: 100%;}" + 
	//			"tr {"
	//			+ "word-wrap: break-word;"
	//			+ "overflow-wrap: break-word;}" + 
				"</style>" + 
				"</head>";

		html += "<body style=\"font-family:calibri;font-size:14px;line-height:0.3;\">\r\n";
		
		html += "<h1 style=\"text-align: center;\"><span style=\"color: #003366;\">Detection report of App4</span></h1>\r\n" + 
				"<p>&nbsp;</p>\r\n" + 
				"<p>&nbsp;</p>\r\n" + 
				"<h2><span style=\"color: #003366;\">1. Detection results&nbsp;</span></h2>\r\n";
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Contract address: <strong>"+contractAddressReportReversedSourceCode+"</strong></span></li>\r\n" + 
				"</ul>\r\n";

		
		boolean bVulnerable=false;
		for (String s : listVerifyBadRandomnessReportReversedSourceCode) {
			if(!s.startsWith("Fonction ")) {
				bVulnerable=true;
			}
		}
	
		
		if(bVulnerable) {
			html += "<ul style=\"list-style-type: circle;\">\r\n" + 
					"<li><span style=\"color: #000000;\">The contract <strong>is vulnerable to &lt;Bad Randomness&gt;&nbsp;</strong></span></li>\r\n" + 
					"</ul>\r\n";
			
			for (String s: listVulnerabilitesFoundReversedSourceCode) {
				
			 html += "<ul style=\"list-style-type: circle;\">\r\n" + 
			 		"<li><span style=\"color: #000000;\">The contract <strong>is vulnerable to "
			 		+ "&lt;"
			 		+ s
			 		+ "&gt;&nbsp; &nbsp;</strong></span></li>\r\n" + 
			 		"</ul>\r\n";
				
			}
			
			
		}
		else {
			html += "<ul style=\"list-style-type: circle;\">\r\n" + 
					"<li><span style=\"color: #000000;\">The contract <strong>is not vulnerable to &lt;Bad Randomness&gt;&nbsp;</strong></span></li>\r\n" + 
					"</ul>\r\n";
		}

		html += "<p>&nbsp;</p>\r\n" + 
				"<h2><span style=\"color: #003366;\">2. Additional information</span></h2>\r\n";
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Number of functions in the contract&nbsp;<strong>"
				+(listHeaderFunctionsOfContractReportReversedSourceCode.size()+1)
				+"</strong></span></li>\r\n" + 
				"</ul>\r\n";
		
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Headers of these functions</span></li>\r\n" + 
				"</ul>\r\n";
		
		html += "<p style=\"padding-left: 60px;\"><span style=\"color: #008080;\"><em>";
		
		html += "main()<br><br><br>";
		
		for (String s : listHeaderFunctionsOfContractReportReversedSourceCode) {
			html += s+"<br><br><br>";
		}
	
		html += "</em></span><span style=\"color: #008080;\">&nbsp;</span></p>";
		
		
		if(bVulnerable) {

			html += "<ul style=\"list-style-type: circle;\">\r\n" + 
					"<li><span style=\"color: #000000;\">Bad Randomness Analysis</span></li>\r\n" + 
					"</ul>\r\n" + 
					"<table style=\"width: 689px;\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
					"<tbody>\r\n" + 
					"<tr style=\"height: 13px;\">\r\n" + 
					"<td style=\"width: 163px; height: 13px; text-align: center;\">\r\n" + 
					"<p><span style=\"color: #000000;\"><strong>Functions</strong></span></p>\r\n" + 
					"</td>\r\n" + 
					"<td style=\"width: 530px; height: 13px; text-align: center;\">\r\n" + 
					"<p><span style=\"color: #000000;\"><strong>Lines of code involved </strong></span></p>\r\n" + 
					"</td>\r\n" + 
					"</tr>\r\n";
			
			for (int i = 0; i < listVerifyBadRandomnessReportReversedSourceCode.size(); i++) {
				
				html +="<tr style=\"height: 13px;\">\r\n" + 
						"<td style=\"width: 163px; height: 13px; text-align: center;\">\r\n" + 
						"<p><span style=\"color: #008080;\"><em>"
						+listVerifyBadRandomnessReportReversedSourceCode.get(i).split("Fonction ")[1]
						+"</em></span></p>\r\n" + 
						"</td>\r\n";
				
				html +="<td style=\"width: 530px; height: 13px;\">\r\n" + 
						"<p style=\"padding-left: 30px;\">\r\n";
				
				while(i+1 < listVerifyBadRandomnessReportReversedSourceCode.size() && 
						!listVerifyBadRandomnessReportReversedSourceCode.get(i+1).startsWith("Fonction ")) {
						i++;
						
							if(listVerifyBadRandomnessReportReversedSourceCode.get(i).length()>180) {

								html += "<span style=\"color: #000000;\">"
								+listVerifyBadRandomnessReportReversedSourceCode.get(i).substring(0, 60)
								+"<br><br><br>"
								+listVerifyBadRandomnessReportReversedSourceCode.get(i).substring(60, 120)
								+"<br><br><br>"
								+listVerifyBadRandomnessReportReversedSourceCode.get(i).substring(120, 180)
								+"<br><br><br>"							
								+listVerifyBadRandomnessReportReversedSourceCode.get(i).substring(180, 
										listVerifyBadRandomnessReportReversedSourceCode.get(i).length())
								+"</span><br><br><br>";
								
							}else {
								if(listVerifyBadRandomnessReportReversedSourceCode.get(i).length()>120) {
									
									html += "<span style=\"color: #000000;\">"
									+listVerifyBadRandomnessReportReversedSourceCode.get(i).substring(0, 60)
									+"<br><br><br>"
									+listVerifyBadRandomnessReportReversedSourceCode.get(i).substring(60, 120)
									+"<br><br><br>"
									+listVerifyBadRandomnessReportReversedSourceCode.get(i).substring(120, 
											listVerifyBadRandomnessReportReversedSourceCode.get(i).length())
									+"</span><br><br><br>";
								}else {
									if(listVerifyBadRandomnessReportReversedSourceCode.get(i).length()>60) {
										html += "<span style=\"color: #000000;\">"
										+listVerifyBadRandomnessReportReversedSourceCode.get(i).substring(0, 60)
										+"<br><br><br>"
										+listVerifyBadRandomnessReportReversedSourceCode.get(i).substring(60, 
												listVerifyBadRandomnessReportReversedSourceCode.get(i).length())
										+"</span><br><br><br>";
									}	

									if(listVerifyBadRandomnessReportReversedSourceCode.get(i).length()<=60) {
										html += "<span style=\"color: #000000;\">"+listVerifyBadRandomnessReportReversedSourceCode.get(i)+"</span><br><br><br>";
									}
								}
							}
						
				}
				html += "</p>\r\n" + 
						"</td>\r\n" + 
						"</tr>\r\n";
				
			}
			
			
			
			html += "</tbody>" + 
					"</table>";
		}
	
			
		html += "</body>\r\n" + 
				"</html>\r\n" ;
		
		
		try {
			createPdf(html, "App_04_Report.pdf");
		
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,e1.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			}
  
		
		
	}

	
	
	/**************************************************************************/
	
	
	public static void createPdf(String html, String dest) throws IOException {
			
		ConverterProperties properties = new ConverterProperties();
		    properties.setFontProvider(new DefaultFontProvider(true, true, true));
		    
		    HtmlConverter.convertToPdf(html, new FileOutputStream(dest),properties);
	}

	
	
	
	

}
