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


public class SC_App_03_Reentrancy {


	/**Variables if source code is available**/
	public static String contractAddressAvailableSourceCodeReport;
	public static List <String> listContractsInternalAvailableSourceCodeReport=new ArrayList<String>();
	public static List <String> listFunctionsOfContractAvailableSourceCodeReport = new ArrayList<String>();
	
	public static List<String> identifiersFunctionsLevel1CallValueAvailableSourceCodeReport = new ArrayList<String>();
	public static List<String> identifiersFunctionsLevel1SendAvailableSourceCodeReport = new ArrayList<String>();
	public static List<String> identifiersFunctionsLevel1TransferAvailableSourceCodeReport = new ArrayList<String>();
	
	public static List<String> ListTraceNestedCallsCallValueSourceCodeAvailableReport = new ArrayList<String>();
	public static List<String> listTraceNestedCallsSendCodeSourceCodeAvailableReport = new ArrayList<String>();
	public static List<String> listTraceNestedCallsTransferSourceCodeAvailableReport = new ArrayList<String>();

	public static List<String> listVerifyPatternUpdateCallValueSourceCodeAvailableReport = new ArrayList<String>();
	public static List<String> listVerifyPatternUpdateSendSourceCodeAvailableReport = new ArrayList<String>();
	public static List<String> listVerifyPatternUpdateTransferSourceCodeAvailableReport = new ArrayList<String>();

	
	/**Variables if source code is reversed*/
	public static String contractAddressReversedSourceCodeReport;
	public static List <String> listContractsInternalsReversedSourceCodeReport=new ArrayList<String>();
	public static List <String> listFunctionsHeadersOfContractReversedSourceCodeReport = new ArrayList<String>();
	public static List<String> IdentifiersFunctionLevel1CallGasReversedSourceCodeReport = new ArrayList<String>();
	public static List<String> ListTraceNestedCallsCallGasReversedSourceCodeReport = new ArrayList<String>();
	
	public static List<String> listVerifyPatternUpdateCallGasReversedSourceCodeReport = new ArrayList<String>();



	public static void initializeAllVariables() {
		
		contractAddressAvailableSourceCodeReport=null;
		listContractsInternalAvailableSourceCodeReport=new ArrayList<String>();
		listFunctionsOfContractAvailableSourceCodeReport = new ArrayList<String>();
		
		identifiersFunctionsLevel1CallValueAvailableSourceCodeReport = new ArrayList<String>();
		identifiersFunctionsLevel1SendAvailableSourceCodeReport = new ArrayList<String>();
		identifiersFunctionsLevel1TransferAvailableSourceCodeReport = new ArrayList<String>();
		
		ListTraceNestedCallsCallValueSourceCodeAvailableReport = new ArrayList<String>();
		listTraceNestedCallsSendCodeSourceCodeAvailableReport = new ArrayList<String>();
		listTraceNestedCallsTransferSourceCodeAvailableReport = new ArrayList<String>();

		listVerifyPatternUpdateCallValueSourceCodeAvailableReport = new ArrayList<String>();
		listVerifyPatternUpdateSendSourceCodeAvailableReport = new ArrayList<String>();
		listVerifyPatternUpdateTransferSourceCodeAvailableReport = new ArrayList<String>();
		
	
		contractAddressReversedSourceCodeReport=null;
		listContractsInternalsReversedSourceCodeReport=new ArrayList<String>();
		listFunctionsHeadersOfContractReversedSourceCodeReport = new ArrayList<String>();
		IdentifiersFunctionLevel1CallGasReversedSourceCodeReport = new ArrayList<String>();
		ListTraceNestedCallsCallGasReversedSourceCodeReport = new ArrayList<String>();
		listVerifyPatternUpdateCallGasReversedSourceCodeReport = new ArrayList<String>();
	}
	
	
	public static void initReentrancy_App3(String contractAddress) {
		initializeAllVariables();
		//lTempsDebut = System.currentTimeMillis();
		Boolean bSourceCodeVerified=false;
		
		File file =new File("Smart Contracts/"+contractAddress);
		String [] directories = file.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		
		for (int j = 0; j < directories.length; j++) {
			if(directories[j].contains("Contract_Source_Code")) {bSourceCodeVerified=true;break;}
		}

		
		if(bSourceCodeVerified) {
					contractAddressAvailableSourceCodeReport = contractAddress;
					
					detectReentrancyVulnerabilityAvailableSourceCode(contractAddress);
					
							
					generateReportPDFAvailableSourceCode();
		}
		else {
					contractAddressReversedSourceCodeReport = contractAddress;
					
					detectReentrancyVulnerabilitySourceCodeReversed(contractAddress);
					
				
					generateReportPDFReversedSourceCode();
		}
	}
	
	


	public static void detectReentrancyVulnerabilityAvailableSourceCode(String contractAddress) {

		
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
			e.printStackTrace();
			} 

		/****Deleting all comments *******/
		fileContract = deletingAllCommentsFromContractSourceCode(fileContract);


		//We replace the "function" word by "fonction" (the french word) just to list'em without being confused.
		
		for (int i = 0; i < fileContract.split("\n").length; i++) {
			
			
			if(fileContract.split("\n")[i].contains("\'")&&fileContract.split("\n")[i].contains("function")) {

			
				listContractSourceCode.add(fileContract.split("\n")[i].replace("function", "fonction"));
			}else {
				listContractSourceCode.add(fileContract.split("\n")[i]);
				
			}
			
		}
				
		
		int i=0;
		String lineTemp="";
		List <String> listInternalsContracts = new ArrayList<String>();//Because there can be multiple contracts in the contract.
		List <String> listFunctions = new ArrayList<String>();
		List <String> listFunctionsOfContract = new ArrayList<String>();

		
/*********************************For contract generating***********************************************/
		while (i < listContractSourceCode.size()) {
			
			if(    listContractSourceCode.get(i).startsWith("contract") &&  
					( 
						(listContractSourceCode.get(i).contains("{"))||
						(listContractSourceCode.get(i).contains("is"))||
						(listContractSourceCode.get(i+1).contains("{")) //
					)
					
					)
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
			
				listInternalsContracts.add(lineTemp);
				lineTemp="";
			
			}
			i++;
		}


		/**for the report**/
		for (String s : listInternalsContracts) {
			listContractsInternalAvailableSourceCodeReport.add(s);
		}

		
		
/*********************************For functions generating*********************************************/
		for (int j = 0; j < listInternalsContracts.size(); j++) {

			for (int k = 1; k < listInternalsContracts.get(j).split("\n").length -1 ; k++) { 
				
				if(listInternalsContracts.get(j).split("\n")[k].replaceFirst("    ", "").replaceFirst("\t", "").replaceFirst("  ", "").startsWith("function")) {

					lineTemp += listInternalsContracts.get(j).split("\n")[k].replaceFirst("    ", "").replaceFirst("\t", "").replaceFirst("  ", "")+"\n";

					k++;

					while(!listInternalsContracts.get(j).split("\n")[k].replaceFirst("    ", "").replaceFirst("\t", "").replaceFirst("  ", "").startsWith("}")) {
						lineTemp += listInternalsContracts.get(j).split("\n")[k].replaceFirst("    ", "").replaceFirst("\t", "").replaceFirst("  ", "")+"\n";
						k++;
					}
					
					if(listInternalsContracts.get(j).split("\n")[k].replaceFirst("    ", "").replaceFirst("\t", "").replaceFirst("  ", "").startsWith("}")) {
						lineTemp += listInternalsContracts.get(j).split("\n")[k].replaceFirst("    ", "").replaceFirst("\t", "").replaceFirst("  ", "")+"\n";
					}
					
					listFunctions.add(lineTemp);
					lineTemp="";
				
				}
				}//
			
		}//

		//Now we'll need to retrieve the body of the function
		for (String s : listFunctions) {
			if(s.contains("{")) {
				listFunctionsOfContract.add(s);

			}
		}

	
		
		/**For the report*/
		for (String s : listFunctionsOfContract) {
			listFunctionsOfContractAvailableSourceCodeReport.add(s);
		}
		
		
		/********For different calls***********/
		
		generateFunctionsThatUseTrasnfertEtherSourceCodeAvailable(listFunctionsOfContract, ".call.value(");

		generateFunctionsThatUseTrasnfertEtherSourceCodeAvailable(listFunctionsOfContract, ".send(");
		
		generateFunctionsThatUseTrasnfertEtherSourceCodeAvailable(listFunctionsOfContract, ".transfer(");
		

	}

	
	public static String deletingAllCommentsFromContractSourceCode(String contratString) {

		

		contratString = contratString.replaceAll( "//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/", "$1 " );
		
		
		String []strTemp;

		strTemp = contratString.split("\n");
		contratString = "";
		
		for (int i = 0; i < strTemp.length; i++) {
			if(!strTemp[i].isBlank()) {
				contratString +=strTemp[i]+"\n";
			}
		}
		return contratString; 
	}

	
	public static void generateFunctionsThatUseTrasnfertEtherSourceCodeAvailable(List <String> listFunctions, 
			String functionSendEther) {
		
		
/****************************************************************************************************/
/**First Step : we look for functions that use one of the ether transfer functions 
 * such as call.value, transfer() and send()
 **/
		
		Pattern pattern = Pattern.compile("function (.*)");
		Matcher matcher=null;
		
		List<String> identifiersFunctionsLevel1 = new ArrayList<String>();
		List<String> identifiersFunctionsLevel2 = new ArrayList<String>();
		List <String> listTraceNestedCalls = new ArrayList<String>();
		
		
		for (String s : listFunctions) {
			if(s.contains(functionSendEther)) {
				matcher = pattern.matcher(s);
				if(matcher.find()) {

					if(matcher.group(1).split("\\(")[0].isBlank()) {
						identifiersFunctionsLevel1.add("fallback");
					}else {
						identifiersFunctionsLevel1.add(matcher.group(1).split("\\(")[0]);
					}
				}
			}
		}
				
				/**For the report**********************************/
					if(functionSendEther.contains("call.value")){
						for (String s : identifiersFunctionsLevel1) {
							identifiersFunctionsLevel1CallValueAvailableSourceCodeReport.add(s);
						}
					}
					
					if(functionSendEther.contains(".send(")) {
						for (String s : identifiersFunctionsLevel1) {
							identifiersFunctionsLevel1SendAvailableSourceCodeReport.add(s);
						}
						
					}
					
					if(functionSendEther.contains(".transfer(")) {
						for (String s : identifiersFunctionsLevel1) {
							identifiersFunctionsLevel1TransferAvailableSourceCodeReport.add(s);
						}
					}
				
				/**************************************************/

	
/***2nd step : we look for the functions that call the functions of the 1st step******************/
	
	for (String idfFunctionLevel1 : identifiersFunctionsLevel1) {
		
		listTraceNestedCalls.add(functionSendEther+" <- "+idfFunctionLevel1);
		
		if(!idfFunctionLevel1.contains("fallback")){
				for (String s : listFunctions) {
						
						if(s.matches(idfFunctionLevel1) && !s.contains("function "+idfFunctionLevel1)) {
							matcher = pattern.matcher(s);
							while(matcher.find()) {
		
								if(matcher.group(1).split("\\(")[0].isBlank()) {
									identifiersFunctionsLevel2.add("fallback");
									
									listTraceNestedCalls.add(functionSendEther+" <- "+idfFunctionLevel1+" <- fallback");
									
								}else {
									identifiersFunctionsLevel2.add(matcher.group(1).split("\\(")[0]);
																
									listTraceNestedCalls.add(functionSendEther+
											" <- "+idfFunctionLevel1+
											" <- "+matcher.group(1).split("\\(")[0]);
									
									 }
								
								}
						}
				}
				
		}
		
		
/********3rd step : we look for the functions that call for the functions of the 2nd step*************/	
		
			for (String idfFunctionLevel2 : identifiersFunctionsLevel2) {
				
				if(!idfFunctionLevel2.contains("fallback")) {
					for (String s : listFunctions) {
						if(s.matches(idfFunctionLevel2) && !s.contains("function "+idfFunctionLevel2) ) {
							matcher = pattern.matcher(s);
							while(matcher.find()) {

								if(matcher.group(1).split("\\(")[0].isBlank()) {
									listTraceNestedCalls.add(functionSendEther+
									" <- "+idfFunctionLevel1+
									" <- "+idfFunctionLevel2+
									" <- fallback");
								}
								else {
								
									listTraceNestedCalls.add(functionSendEther+
									" <- "+idfFunctionLevel1+
									" <- "+idfFunctionLevel2+
									" <- "+matcher.group(1).split("\\(")[0]);
								}
								
								
							}
						}
					}
				}
			}

		identifiersFunctionsLevel2 = new ArrayList<String>();

		} 

		/**For the report*/
		if(functionSendEther.contains(".call.value")) {
			for (String s : listTraceNestedCalls) {
				ListTraceNestedCallsCallValueSourceCodeAvailableReport.add(s);
			}
		}
		if(functionSendEther.contains(".send(")) {
			for (String s : listTraceNestedCalls) {
				listTraceNestedCallsSendCodeSourceCodeAvailableReport.add(s);
			}
		}
		if(functionSendEther.contains(".transfer(")) {
			for (String s : listTraceNestedCalls) {
				listTraceNestedCallsTransferSourceCodeAvailableReport.add(s);
			}
		}
		/*******************************/

				
		List<String> listVerifyIfAlreadyTested= new ArrayList<String>();

		for (String s : listTraceNestedCalls) {
			
			for (int j = 0; j < s.split("<- ").length; j++) {

				if(j==1) {
					if(!listVerifyIfAlreadyTested.contains(s.split("<- ")[j].replace(" ", "")+";"+functionSendEther.replace(" ",""))
							) {

						pattern = Pattern.compile("function "+s.split("<- ")[j]);
						
						listVerifyIfAlreadyTested.add(s.split("<- ")[j].replace(" ","") +";"+functionSendEther.replace(" ", ""));
					
					}
					
				}
				if(j>1) {
				
					if(!listVerifyIfAlreadyTested.contains(
							s.split("<- ")[j].replace(" ", "") 
							+";"+s.split("<- ")[j-1].replace(" ",""))) {
					
						pattern = Pattern.compile("function "+s.split("<- ")[j]);
						
						listVerifyIfAlreadyTested.add(s.split("<- ")[j].replace(" ", "") +";"+s.split("<- ")[j-1].replace(" ",""));
					}
				}
			}
		}
				
		
	
		if(functionSendEther.contains(".call.value(")) {
			
			for (String stringVerifyIfAlreadyTested : listVerifyIfAlreadyTested) {
				
				listVerifyPatternUpdateCallValueSourceCodeAvailableReport.add(stringVerifyIfAlreadyTested.split(";")[0]+" compared to "+stringVerifyIfAlreadyTested.split(";")[1]);
				
				pattern = Pattern.compile("function "+stringVerifyIfAlreadyTested.split(";")[0]);
				
				for (String stringListFunction : listFunctions) {
					matcher = pattern.matcher(stringListFunction);
					if(matcher.find()) {
							verifyPatternUpdateAvailableSourceCode(stringVerifyIfAlreadyTested.split(";")[1], stringListFunction, functionSendEther);
					}
				}
			}
		}
		
		if(functionSendEther.contains(".send(")) {
			
			for (String stringVerifyIfAlreadyTested : listVerifyIfAlreadyTested) {
				listVerifyPatternUpdateSendSourceCodeAvailableReport.add(stringVerifyIfAlreadyTested.split(";")[0]+" compared to "+stringVerifyIfAlreadyTested.split(";")[1]);
				
				pattern = Pattern.compile("function "+stringVerifyIfAlreadyTested.split(";")[0]);
			
				for (String stringListFunction : listFunctions) {
					matcher = pattern.matcher(stringListFunction);
					if(matcher.find()) {
							verifyPatternUpdateAvailableSourceCode(stringVerifyIfAlreadyTested.split(";")[1], stringListFunction, functionSendEther);
					}
				}
			}
		}
		
		if(functionSendEther.contains(".transfer(")) {
			for (String stringVerifyIfAlreadyTested : listVerifyIfAlreadyTested) {
			
				listVerifyPatternUpdateTransferSourceCodeAvailableReport.add(stringVerifyIfAlreadyTested.split(";")[0]+" compared to "+stringVerifyIfAlreadyTested.split(";")[1]);
				
				pattern = Pattern.compile("function "+stringVerifyIfAlreadyTested.split(";")[0]);
			
				for (String stringListFunction : listFunctions) {
					matcher = pattern.matcher(stringListFunction);
					if(matcher.find()) {
							verifyPatternUpdateAvailableSourceCode(stringVerifyIfAlreadyTested.split(";")[1], stringListFunction, functionSendEther);
					}
				}
			}
		}
	}

	
	public static void verifyPatternUpdateAvailableSourceCode(String functionCallValue, String contract_function, String sendingEtherFunction) {
		List<String> listLinesFunction = new ArrayList<String>();
		int numberLineFunctionCallValue=0;	
		
			//we split the function line by line
			for (int i = 0; i < contract_function.split("\n").length; i++) {
				listLinesFunction.add(contract_function.split("\n")[i]);
			 }

			//We get the line number that contains Call.value function
			for (String ligne : listLinesFunction) {
				if(ligne.contains(functionCallValue)) {break;}
				numberLineFunctionCallValue++;
			}
				
			if(sendingEtherFunction.contains(".call.value(")) {
				
				if((numberLineFunctionCallValue+1) < listLinesFunction.size() &&
						!listLinesFunction.get((numberLineFunctionCallValue+1)).contains("return")) {
					for (int i = numberLineFunctionCallValue; i < listLinesFunction.size(); i++) {
									
						 	if(containsUpdateAvailableSourceCode(listLinesFunction.get(i))) {
							
						 		if(listLinesFunction.get(i).matches("(.*)/$")) {//it's just to get the whole line back in the case of the division
																	
									listVerifyPatternUpdateCallValueSourceCodeAvailableReport.add(listLinesFunction.get(i)
									+""+listLinesFunction.get(i+1).replace(" ", ""));
									
									
								}else {
									//System.out.println("Un update Ligne "+listLignesFonction.get(i));
									listVerifyPatternUpdateCallValueSourceCodeAvailableReport.add(listLinesFunction.get(i));
								}
						 		
							}
					}
				}
			}
			
			if(sendingEtherFunction.contains(".send(")) {
			
				if((numberLineFunctionCallValue+1) < listLinesFunction.size() &&
					!listLinesFunction.get((numberLineFunctionCallValue+1)).contains("return")
					
					) {
				for (int i = numberLineFunctionCallValue; i < listLinesFunction.size(); i++) {
	
					 	if(listLinesFunction.get(i).contains("return")) {break;}
						
					 	if(containsUpdateAvailableSourceCode(listLinesFunction.get(i))) {
						
					 		if(listLinesFunction.get(i).matches("(.*)/$")) {//to get the whole line back in the case of the division
															
								listVerifyPatternUpdateSendSourceCodeAvailableReport.add(listLinesFunction.get(i)
								+""+listLinesFunction.get(i+1).replace(" ", ""));
								
								
							}else {
								listVerifyPatternUpdateSendSourceCodeAvailableReport.add(listLinesFunction.get(i));
							}
					 		
						}
				}
			}
			}
			
			
			
			if(sendingEtherFunction.contains(".transfer(")) {
				if((numberLineFunctionCallValue+1) < listLinesFunction.size() &&
						!listLinesFunction.get((numberLineFunctionCallValue+1)).contains("return")) {
					for (int i = numberLineFunctionCallValue; i < listLinesFunction.size(); i++) {
		
						 	if(listLinesFunction.get(i).contains("return")) {break;}
							
						 	if(containsUpdateAvailableSourceCode(listLinesFunction.get(i))) {
							
						 		if(listLinesFunction.get(i).matches("(.*)/$")) {//to get the whole line back in the case of the division
																	
									listVerifyPatternUpdateTransferSourceCodeAvailableReport.add(listLinesFunction.get(i)
									+""+listLinesFunction.get(i+1).replace(" ", ""));
									
									
								}else {
									listVerifyPatternUpdateTransferSourceCodeAvailableReport.add(listLinesFunction.get(i));
								}
							}
					}
				}
			}
	}

	
	
	public static boolean containsUpdateAvailableSourceCode(String line) {

			Pattern pattern = Pattern.compile(
					//Arithmetic operations
					  "\\+"
					+ "|-"
					+ "|\\*"
					+ "|/"
					+ "|%"
					+ "|\\+\\+"
					+ "|--"
					
					//Assignment operations
					+ "| = "
					+ "|\\+="
					+ "|-="
					+ "|\\*="
					+ "|/="
					+ "|%="
					
					//update operations of Solidity
				/*	+ "|emit"
					+ "|delete"
				*/	
					);
			
			Matcher matcher = pattern.matcher(line);
			
				if(matcher.find()) {
					return true;
				}
		
		return false;
	}

	
	
	public static void generateReportPDFAvailableSourceCode() {
		
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

		
		html += "<h1 style=\"text-align: center;\"><span style=\"color: #003366;\">Detection Report Of App3</span></h1>\r\n" + 
				"<p>&nbsp;</p>\r\n" + 
				"<h2><span style=\"color: #003366;\">1. Detection Result&nbsp;</span></h2><br>\r\n";
		
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li>Contract address :&nbsp;<strong><span style=\"color: #000000;\">"+contractAddressAvailableSourceCodeReport+"</span></strong></li>\r\n" + 
				"</ul>\r\n";

		
		if(		(listVerifyPatternUpdateCallValueSourceCodeAvailableReport.size()-ListTraceNestedCallsCallValueSourceCodeAvailableReport.size())>0
				||
				(listVerifyPatternUpdateSendSourceCodeAvailableReport.size()-listTraceNestedCallsSendCodeSourceCodeAvailableReport.size())>0
				||
				(listVerifyPatternUpdateTransferSourceCodeAvailableReport.size()-listTraceNestedCallsTransferSourceCodeAvailableReport.size())>0
				
				) 
		{
				
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">The contract <strong>is vulnerable to the &lt;Reentrancy&gt; attack </strong></span></li>\r\n" + 
				"</ul>\r\n" + 
				"<p>&nbsp;</p>\r\n";
			
		
		}
		else {
	
			html += "<ul style=\"list-style-type: circle;\">\r\n" + 
					"<li><span style=\"color: #000000;\">The contract <strong>is not vulnerable to the &lt;Reentrancy&gt;</strong></span></li>\r\n" + 
					"</ul>\r\n" + 
					"<p>&nbsp;</p>\r\n";
		
		}	
		
		
		html += "<h2><span style=\"color: #003366;\"><strong>2.&nbsp;Additional information</strong></span></h2><br>\r\n";	
		
				
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Number of internal contracts :&nbsp;<strong>"+listContractsInternalAvailableSourceCodeReport.size()+"</strong></span></li>\r\n" + 
				"</ul>\r\n";
		
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Headers (ie. names) of internal contracts</span></li>\r\n" + 
				"</ul>\r\n";
		
		
		html += "<p style=\"padding-left: 60px;\">";
		
		for (String s : listContractsInternalAvailableSourceCodeReport) {
			
			html += "<span style=\"color: #008080;\"><em>"+s.split("\n")[0].replace("{", "").replace("is","")+"</em></span></br></br></br>\r\n";
			
		}
		
		
		html += "</p>\r\n";
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Number of functions in the contract : <strong>"+listFunctionsOfContractAvailableSourceCodeReport.size()+"</strong></span></li>\r\n" + 
				"</ul>\r\n";
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">The headers of these functions&nbsp;</span></li>\r\n" + 
				"</ul>\r\n";
	
		
		html +="<p style=\"padding-left: 60px;\">";
		
		
Pattern pattern = Pattern.compile("function(.*)");//
Matcher matcher;

		for (String s : listFunctionsOfContractAvailableSourceCodeReport) {
				matcher = pattern.matcher(s);
				if(matcher.find()) {

					if(matcher.group(1).split("\\(")[0].isBlank()) {
						html += "<span style=\"color: #008080;\"><em>fallback</em></span></br></br></br>";
					}else {
						html += "<span style=\"color: #008080;\"><em> "+matcher.group(1).split("\\(")[0]+"</em></span></br></br></br>";
						
					}
				}
		}

		
		html += "</p>";
		
		
		/******************************************************************************************************************/
		
		html += "<h3><span style=\"color: #003366;\"><strong>2.1 CALL.VALUE()</strong></span></h3>\r\n";	
		
		
		html +="<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">The number of functions using <em>.call.value() :&nbsp;</em>"
				+ "<strong>"+identifiersFunctionsLevel1CallValueAvailableSourceCodeReport.size()+"</strong></span></li>\r\n" + 
				"</ul>\r\n";
				
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Headers of these functions</span></li>\r\n" + 
				"</ul>\r\n";
		
		html += "<p style=\"padding-left: 60px;\">";
		
		for (String s : identifiersFunctionsLevel1CallValueAvailableSourceCodeReport) {
			html += "<span style=\"color: #008080;\"><em>"+s+"</em></span></br></br></br>";
		}
	
		html += "</p>\r\n";
		
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Nested calls related to <em>call.value()</em></span></li>\r\n" + 
				"</ul>\r\n";
		
		html += "<p style=\"padding-left: 60px;\">";
		
		html += "<span style=\"color: #008080;\"><em>";

		for (String s: ListTraceNestedCallsCallValueSourceCodeAvailableReport) {	
			html += s.replace(".call.value(", ".call.value()")+"<br><br><br>";
		}

		
		html += "</em></span></p>";
		

		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">By exploiting all the paths</span></li>\r\n" + 
				"</ul>\r\n";
		
		
		html += "<table style=\"width: 662px;\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
				"<tbody>\r\n" + 
				"<tr style=\"height: 26px;\">\r\n" + 
				"<td style=\"width: 207px; height: 26px;\">\r\n" + 
				"<p style=\"padding-left: 150px;\"><span style=\"color: #000000;\">&nbsp;</span></p>\r\n" + 
				"</td>\r\n" + 
				"<td style=\"width: 459px; height: 26px; text-align: center;\">\r\n" + 
				"<p><strong><span style=\"color: #000000;\">Line of code executing an update after an interaction with an</br></br></br> external contract</span></strong></p>\r\n" + 
				"</td>\r\n" + 
				"</tr>\r\n";
		
	
		for (int i = 0; i < listVerifyPatternUpdateCallValueSourceCodeAvailableReport.size(); i++) {
			
			if(listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").split(" compared to ")[0].length()>30){
				html += "<tr style=\"height: 26px;\">\r\n" + 
						"<td style=\"width: 207px; height: 26px; text-align: center;\">\r\n" + 
						"<p><span style=\"color: #000000;\"><em style=\"color: #008080;\">"
						+listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").split(" compared to ")[0].substring(0, 30)
						+"<br><br><br> "
						+ listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").split(" compared to ")[0].substring(30, 
								listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").split(" compared to ")[0].length())
						+"<br><br><br>";
				
			}else {
				html += "<tr style=\"height: 26px;\">\r\n" + 
						"<td style=\"width: 207px; height: 26px; text-align: center;\">\r\n" + 
						"<p><span style=\"color: #000000;\"><em style=\"color: #008080;\">"
						+listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").split(" compared to ")[0]
						+"<br><br><br>";
				
			}
			html += "</em> compared to <br><br><br>";
			if(listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").split(" compared to ")[1].length()>30) {
				
				html +=	"<em style=\"color: #008080;\">"
						+listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").split(" compared to ")[1].substring(0, 30)
						+"<br><br><br>"
						+listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").split(" compared to ")[1].substring(30, 
								listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").split(" compared to ")[1].length())
						+"</em></span></p>\r\n" + 
						"</td>\r\n";
			}else {
				
				html += "<em style=\"color: #008080;\">"
						+listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").split(" compared to ")[1]
						+"</em></span></p>\r\n" + 
						"</td>\r\n";
			}
			
			
			
			
				html += "<td style=\"width: 459px; height: 13px; padding-left: 30px;\">";
				
				while(i+1 < listVerifyPatternUpdateCallValueSourceCodeAvailableReport.size()
						&& !listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i+1).contains("compared to")) 
				{
					i++;
					if(listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").length()>180) {
						html += "<p><span style=\"color: #000000;\">"
								+listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").substring(0,60)
								+"<br><br><br>"
								+listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").substring(60,120)
								+"<br><br><br>"
								+listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").substring(120,180)
								+"<br><br><br>"
								+listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").substring(180,
										listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").length())
								+"</span></p>";
					}else {
						if(listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").length()>120) {
							html += "<p><span style=\"color: #000000;\">"
									+listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").substring(0,60)
									+"<br><br><br>"
									+listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").substring(60,120)
									+"<br><br><br>"
									+listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").substring(120,
											listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").length())
									+"</span></p>";
						}
						else {
							if(listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").length()>60) {
								html += "<p><span style=\"color: #000000;\">"
										+listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").substring(0,60)
										+"<br><br><br>"
										+listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").substring(60,
												listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").length())
										+"</span></p>";
							}
							
							if(listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()").length()<=60) {
								html += "<p><span style=\"color: #000000;\">"
										+listVerifyPatternUpdateCallValueSourceCodeAvailableReport.get(i).replace(".call.value(", ".call.value()")
										+"</span></p>";
							}							
						}
					}
				}
				html += "</td>\r\n" + 
						"</tr>\r\n";
		}
		
		
		html += "</tbody>\r\n" + 
				"</table><br><br><br>";
		

		
		/******************************************************************************************************************************************/
		
		
		html += "<h3><span style=\"color: #003366;\"><strong>2.2 SEND()</strong></span></h3>\r\n";	
			
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Number of functions using \r\n" + 
				"<em>.send() :&nbsp;</em><strong>"+identifiersFunctionsLevel1SendAvailableSourceCodeReport.size()+"</strong>\r\n" + 
				"</span></li>\r\n" + 
				"</ul>";
		
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Headers of these functions&nbsp;</span></li>\r\n" + 
				"</ul>\r\n";

		html += "<p style=\"padding-left: 60px;\">";
		
		for (String s : identifiersFunctionsLevel1SendAvailableSourceCodeReport) {
			html += "<span style=\"color: #008080;\"><em>"+s+"</em></span></br></br></br>";
		}
	
		html += "</p>\r\n";
		
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Nested calls related to <em>.send()</em></span></li>\r\n" + 
				"</ul>\r\n";

		html +="<p style=\"padding-left: 60px;\">";

		html += "<span style=\"color: #008080;\"><em>";
		
		for (String s: listTraceNestedCallsSendCodeSourceCodeAvailableReport) {
		
			html += s.replace(".send(", ".send()")+"<br><br><br>";
			
		}
		
		html += "</em></span></p>";
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">By exploiting all the paths</span></li>\r\n" + 
				"</ul>\r\n";
		
		
		html += "<table style=\"width: 662px;\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
				"<tbody>\r\n" + 
				"<tr style=\"height: 26px;\">\r\n" + 
				"<td style=\"width: 207px; height: 26px;\">\r\n" + 
				"<p style=\"padding-left: 150px;\"><span style=\"color: #000000;\">&nbsp;</span></p>\r\n" + 
				"</td>\r\n" + 
				"<td style=\"width: 459px; height: 26px; text-align: center;\">\r\n" + 
				"<p><strong><span style=\"color: #000000;\">Line of code executing an update after an interaction with an</br></br></br> external contract</span></strong></p>\r\n" + 
				"</td>\r\n" + 
				"</tr>\r\n";
		
		
		for (int i = 0; i < listVerifyPatternUpdateSendSourceCodeAvailableReport.size(); i++) {
				
			if(listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").split(" compared to ")[0].length()>30){
				html += "<tr style=\"height: 26px;\">\r\n" + 
						"<td style=\"width: 207px; height: 26px; text-align: center;\">\r\n" + 
						"<p><span style=\"color: #000000;\"><em style=\"color: #008080;\">"
						+listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").split(" compared to ")[0].substring(0, 30)
						+"<br><br><br> "
						+ listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").split(" compared to ")[0].substring(30, 
								listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").split(" compared to ")[0].length())
						+"<br><br><br>";
				
			}else {
				html += "<tr style=\"height: 26px;\">\r\n" + 
						"<td style=\"width: 207px; height: 26px; text-align: center;\">\r\n" + 
						"<p><span style=\"color: #000000;\"><em style=\"color: #008080;\">"
						+listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").split(" compared to ")[0]
						+"<br><br><br>";
				
			}
			html += "</em> compared to <br><br><br>";
			if(listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").split(" compared to ")[1].length()>30) {
				
				html +=	"<em style=\"color: #008080;\">"
						+listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").split(" compared to ")[1].substring(0, 30)
						+"<br><br><br>"
						+listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").split(" compared to ")[1].substring(30, 
								listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").split(" compared to ")[1].length())
						+"</em></span></p>\r\n" + 
						"</td>\r\n";
			}else {
				
				html += "<em style=\"color: #008080;\">"
						+listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").split(" compared to ")[1]
						+"</em></span></p>\r\n" + 
						"</td>\r\n";
			}

			
			
				html += "<td style=\"width: 459px; height: 13px; padding-left: 30px;\">";
				
				while(i+1 < listVerifyPatternUpdateSendSourceCodeAvailableReport.size()
						&& !listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i+1).contains("compared to")) 
				{
					i++;

					if(listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").length()>180) {
						
						html += "<p><span style=\"color: #000000;\">"
								+listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").substring(0,60)
								+"<br><br><br>"
								+listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").substring(60,120)
								+"<br><br><br>"
								+listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").substring(120,180)
								+"<br><br><br>"
								+listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").substring(180,
										listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").length())
								+"</span></p>";
					}else {
						if(listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").length()>120) {
							html += "<p><span style=\"color: #000000;\">"
									+listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").substring(0,60)
									+"<br><br><br>"
									+listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").substring(60,120)
									+"<br><br><br>"
									+listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").substring(120,
											listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").length())
									+"</span></p>";							
						}else {
							if(listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").length()>60) {
								html += "<p><span style=\"color: #000000;\">"
										+listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").substring(0,60)
										+"<br><br><br>"
										+listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").substring(60,
												listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").length())
										+"</span></p>";
							}
							if(listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()").length()<=60) {
								html += "<p><span style=\"color: #000000;\">"
										+listVerifyPatternUpdateSendSourceCodeAvailableReport.get(i).replace(".send(", ".send()")
										+"</span></p>";
							}
						}
						
					}
				}
				
				html += "</td>\r\n" + 
						"</tr>\r\n";
		
		}
		
		
		
		html += "</tbody>\r\n" + 
				"</table><br><br><br><br><br><br>";


		
		/********************************************************************************************************************************/
		
		
		html += "<h3><span style=\"color: #003366;\"><strong>2.3 TRANSFER()</strong></span></h3>\r\n";	
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Number of functions using <em>.transfer() :&nbsp;&nbsp;</em>"
				+ "<strong>"+identifiersFunctionsLevel1TransferAvailableSourceCodeReport.size()+"</strong></span></li>\r\n" + 
				"</ul>\r\n";


		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Headers of these functions&nbsp;</span></li>\r\n" + 
				"</ul>\r\n";

		html += "<p style=\"padding-left: 60px;\">";
		
		for (String s : identifiersFunctionsLevel1TransferAvailableSourceCodeReport) {
			html += "<span style=\"color: #008080;\"><em>"+s+"</em></span></br></br></br>";
		}
		
		html += "</p>\r\n";

		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Nested calls related to <em>.transfer()</em></span></li>\r\n" + 
				"</ul>\r\n";

		html +="<p style=\"padding-left: 60px;\">";
		html += "<span style=\"color: #008080;\"><em>";

		for (String s: listTraceNestedCallsTransferSourceCodeAvailableReport) {
			html += s.replace(".transfer(", ".transfer()")+"<br><br><br>";
			
		}

		
		html += "</em></span></p>";

		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">By exploiting all the paths</span></li>\r\n" + 
				"</ul>\r\n";

		html += "<table style=\"width: 662px;\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
				"<tbody>\r\n" + 
				"<tr style=\"height: 26px;\">\r\n" + 
				"<td style=\"width: 207px; height: 26px;\">\r\n" + 
				"<p style=\"padding-left: 150px;\"><span style=\"color: #000000;\">&nbsp;</span></p>\r\n" + 
				"</td>\r\n" + 
				"<td style=\"width: 459px; height: 26px; text-align: center;\">\r\n" + 
				"<p><strong><span style=\"color: #000000;\">Line of code executing an update after an interaction with an</br></br></br> external contract</span></strong></p>\r\n" + 
				"</td>\r\n" + 
				"</tr>\r\n";

		
		for (int i = 0; i < listVerifyPatternUpdateTransferSourceCodeAvailableReport.size(); i++) {
			
			
			if(listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").split(" compared to ")[0].length()>30){
				html += "<tr style=\"height: 26px;\">\r\n" + 
						"<td style=\"width: 207px; height: 26px; text-align: center;\">\r\n" + 
						"<p><span style=\"color: #000000;\"><em style=\"color: #008080;\">"
						+listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").split(" compared to ")[0].substring(0, 30)
						+"<br><br><br> "
						+ listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").split(" compared to ")[0].substring(30, 
								listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").split(" compared to ")[0].length())
						+"<br><br><br>";
				
			}else {
				html += "<tr style=\"height: 26px;\">\r\n" + 
						"<td style=\"width: 207px; height: 26px; text-align: center;\">\r\n" + 
						"<p><span style=\"color: #000000;\"><em style=\"color: #008080;\">"
						+listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").split(" compared to ")[0]
						+"<br><br><br>";
				
			}
			html += "</em>compared to <br><br><br>";
			if(listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").split(" compared to ")[1].length()>30) {
				
				html +=	"<em style=\"color: #008080;\">"
						+listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").split(" compared to ")[1].substring(0, 30)
						+"<br><br><br>"
						+listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").split(" compared to ")[1].substring(30, 
								listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").split(" compared to ")[1].length())
						+"</em></span></p>\r\n" + 
						"</td>\r\n";
			}else {
				
				html += "<em style=\"color: #008080;\">"
						+listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").split(" compared to ")[1]
						+"</em></span></p>\r\n" + 
						"</td>\r\n";
			}
			
			
			
				html += "<td style=\"width: 459px; height: 13px; padding-left: 30px;\">";
				
				while(i+1 < listVerifyPatternUpdateTransferSourceCodeAvailableReport.size()
						&& !listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i+1).contains("compared to")) 
				{
					i++;

					if(listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").length()>180) {
						html += "<p><span style=\"color: #000000;\">"
								+listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").substring(0,60)
								+"<br><br><br>"
								+listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").substring(60,120)
								+"<br><br><br>"
								+listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").substring(120,180)
								+"<br><br><br>"
								+listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").substring(180,
										listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").length())
								+"</span></p>";
					}
					else {
						if(listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").length()>120) {
							html += "<p><span style=\"color: #000000;\">"
									+listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").substring(0,60)
									+"<br><br><br>"
									+listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").substring(60,120)
									+"<br><br><br>"
									+listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").substring(120,
											listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").length())
									+"</span></p>";
						}	
						else {
							if(listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").length()>60) {
								html += "<p><span style=\"color: #000000;\">"
										+listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").substring(0,60)
										+"<br><br><br>"
										+listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").substring(60,
												listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").length())
										+"</span></p>";
							}
							if(listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()").length()<60) {
								html += "<p><span style=\"color: #000000;\">"
										+listVerifyPatternUpdateTransferSourceCodeAvailableReport.get(i).replace(".transfer(", ".transfer()")
										+"</span></p>";
							}		
							
						}
					}
				}
				
				html += "</td>\r\n" + 
						"</tr>\r\n";
		
		}
			
			
			html += "</body>\r\n" + 
					"</html>\r\n" ;

			
			try {
				createPdf(html, "App_03_Report.pdf");
				
			
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
				}

			
	}	
		
	
	
	public static void detectReentrancyVulnerabilitySourceCodeReversed(String contractAddress) {

		List<String> listContractSourceCode = new ArrayList<String>();
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
			e.printStackTrace();
			} 

		/****Deleting all comments*******/
		fileContract = deleteCommentsContractReversedSourceCode(fileContract);
		/**********************************************/
		
		
		for (int i = 0; i < fileContract.split("\n").length; i++) {
			listContractSourceCode.add(fileContract.split("\n")[i]);
		}
				
		
		int i=0;
		
		String TheContract = "";
		List <String> listFunctionsHeader = new ArrayList<String>();
		List <String> listContractFunctionsTemp = new ArrayList<String>();

		
/*********************************Retrieving internal contracts***********************************************/

		for (String s : listContractSourceCode) {
			TheContract+= s;
		}

		
		/**For the report**/
			listContractsInternalsReversedSourceCodeReport.add(TheContract);
		
		
		
/*********************************Generating functions*****************************************************/

		try (BufferedReader br = 
				new BufferedReader(
						new FileReader(
								new File("Smart Contracts/"+contractAddress+"/Contract_Reverted_Source_Code/InternalMethods.sol")))) {
			String line;
		    while ((line = br.readLine()) != null) {
		       listFunctionsHeader.add(line);
		    }
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			} 


		/***********************Retrieving each function body*******/
		
			Pattern pattern = Pattern.compile("function main(.*)function "+listFunctionsHeader.get(0).split("\\(")[0]);
			Matcher matcher = pattern.matcher(TheContract);
					if(matcher.find()) {
						//System.out.println("function main"+matcher.group(1));
						listContractFunctionsTemp.add("function main"+matcher.group(1));
					}
			
					
					for (int j = 0; j < listFunctionsHeader.size(); j++) {
						
						if(j+1==listFunctionsHeader.size()) {
							pattern = Pattern.compile("function "
									+ listFunctionsHeader.get(j).split("\\(")[0]
									+ "(.*)");
							
							matcher = pattern.matcher(TheContract);

							if(matcher.find()) {
								listContractFunctionsTemp.add("function "+ listFunctionsHeader.get(j).split("\\(")[0]+matcher.group(1));
							}
							
						}else {

							pattern = Pattern.compile("function "
									+ listFunctionsHeader.get(j).split("\\(")[0]
									+ "(.*)"
									+ "function "
									+ listFunctionsHeader.get(j+1).split("\\(")[0]);
							
							matcher = pattern.matcher(TheContract);

							if(matcher.find()) {
								listContractFunctionsTemp.add("function "+ listFunctionsHeader.get(j).split("\\(")[0]+matcher.group(1));
							}

						}
					}
					
		/******************************************************************/		

		/**For the report*/
		for (String s : listFunctionsHeader) {
			listFunctionsHeadersOfContractReversedSourceCodeReport.add(s);
		}
		
	
		List<String> listContractFunctions=new ArrayList<String>();
		
		for (String s : listContractFunctionsTemp) {
			listContractFunctions.add(s.replace("{", "{\n").replace(";", ";\n"));
		}
		
		/********For the different calls***********/
		generateFunctionsThatUseTrasnfertEtherReversedSourceCode(listContractFunctions, ".call.gas(");

	}

	
	public static String deleteCommentsContractReversedSourceCode(String stringContract) {

		stringContract = stringContract.replaceAll("(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|(//.*)", "");
		
		String []strTemp;

		strTemp = stringContract.split("\n");
		stringContract = "";
		
		for (int i = 0; i < strTemp.length; i++) {
			if(!strTemp[i].isBlank()) {
				stringContract +=strTemp[i]+"\n";
			}
		}
		return stringContract; 

	}

		


	public static void generateFunctionsThatUseTrasnfertEtherReversedSourceCode(List <String> listFunctions, 
			String functionSendingEther) {
		
/****************************************************************************************************/
/** First step : we look for functions that use one of the ether transfer functions
 *  such as call.value, transfer() and send()
 **/
		
		Pattern pattern = Pattern.compile("function (.*)");
		Matcher matcher=null;
		
		List<String> identifierFunctionsLevel1 = new ArrayList<String>();
		List<String> identifiantsFunctionsLevel2 = new ArrayList<String>();
		List <String> listTraceNestedCalls = new ArrayList<String>();
		
		
		for (String s : listFunctions) {
			if(s.contains(functionSendingEther)) {
				matcher = pattern.matcher(s);
				if(matcher.find()) {
						identifierFunctionsLevel1.add(matcher.group(1).split("\\(")[0]);
				}
			}
		}
				
				/**For the report**********************************/
				for (String s : identifierFunctionsLevel1) {
					IdentifiersFunctionLevel1CallGasReversedSourceCodeReport.add(s);
				}
					
				/**************************************************/

	
				
/***Second step: We look for functions that call the first step functions******************/
	
	for (String idfFunctionLevel1 : identifierFunctionsLevel1) {
		
		listTraceNestedCalls.add(functionSendingEther+" <- "+idfFunctionLevel1);
		
				for (String s : listFunctions) {
						
						if(s.matches(idfFunctionLevel1) && !s.contains("function "+idfFunctionLevel1)) {
							matcher = pattern.matcher(s);
							while(matcher.find()) {

								identifiantsFunctionsLevel2.add(matcher.group(1).split("\\(")[0]);
									
									listTraceNestedCalls.add(functionSendingEther+
											" <- "+idfFunctionLevel1+
											" <- "+matcher.group(1).split("\\(")[0]);
								
								}
						}
				}
				
				
/***Third step : We look for functions that call the second step function******************/	
		
			for (String idfFunctionLevel2 : identifiantsFunctionsLevel2) {
				
					for (String s : listFunctions) {
						if(s.matches(idfFunctionLevel2) && !s.contains("function "+idfFunctionLevel2) ) {
							matcher = pattern.matcher(s);
							while(matcher.find()) {

									listTraceNestedCalls.add(functionSendingEther+
									" <- "+idfFunctionLevel1+
									" <- "+idfFunctionLevel2+
									" <- "+matcher.group(1).split("\\(")[0]);
								
							}
						}
					}
		
			}

		identifiantsFunctionsLevel2 = new ArrayList<String>();

		} 


		/**For the report */
		for (String s : listTraceNestedCalls) {
			ListTraceNestedCallsCallGasReversedSourceCodeReport.add(s);
		}
		
		
		/*******************************/
		List<String> listVerifyIfAlreadyTested= new ArrayList<String>();

		for (String s : listTraceNestedCalls) {
			
			for (int j = 0; j < s.split("<- ").length; j++) {

				if(j==1) {
					if(!listVerifyIfAlreadyTested.contains(s.split("<- ")[j].replace(" ", "")+";"+functionSendingEther.replace(" ",""))
							) {

						pattern = Pattern.compile("function "+s.split("<- ")[j]);
						
						listVerifyIfAlreadyTested.add(s.split("<- ")[j].replace(" ","") +";"+functionSendingEther.replace(" ", ""));
					
					}
					
				}
				if(j>1) {
				
					if(!listVerifyIfAlreadyTested.contains(
							s.split("<- ")[j].replace(" ", "") 
							+";"+s.split("<- ")[j-1].replace(" ",""))) {
					
						pattern = Pattern.compile("function "+s.split("<- ")[j]);
						
						listVerifyIfAlreadyTested.add(s.split("<- ")[j].replace(" ", "") +";"+s.split("<- ")[j-1].replace(" ",""));
					}
				}
			}
		}
				
		
		for (String stringVerifyIfAlreadyTested : listVerifyIfAlreadyTested) {
			
			listVerifyPatternUpdateCallGasReversedSourceCodeReport.add(stringVerifyIfAlreadyTested.split(";")[0]+" compared to "+stringVerifyIfAlreadyTested.split(";")[1]);
			
			pattern = Pattern.compile("function "+stringVerifyIfAlreadyTested.split(";")[0]);
			
			for (String stringListFunction : listFunctions) {
				matcher = pattern.matcher(stringListFunction);
				if(matcher.find()) {
						verifyPatternUpdateReversedSourceCode(stringVerifyIfAlreadyTested.split(";")[1], 
								stringListFunction);
				}
			}
		}
		
	}

	
	

	public static void verifyPatternUpdateReversedSourceCode(String FunctionCallValue, String contractFunction) {
		List<String> listLinesFunction = new ArrayList<String>();
		int numberLineFunctionCallValue=0;	
		
			for (int i = 0; i < contractFunction.split("\n").length; i++) {
				listLinesFunction.add(contractFunction.split("\n")[i]);
			 }

			//Getting line number that contains CallValue
			for (String ligne : listLinesFunction) {
				if(ligne.contains(FunctionCallValue)) {break;}
				numberLineFunctionCallValue++;
			}
			
				if((numberLineFunctionCallValue+1) < listLinesFunction.size()) {
					
					for (int i = numberLineFunctionCallValue; i < listLinesFunction.size(); i++) {
		
						 	if(containsUpdateReversedSourceCode(listLinesFunction.get(i))) {
							
						 		if(listLinesFunction.get(i).matches("(.*)/$")) {
								
									listVerifyPatternUpdateCallGasReversedSourceCodeReport.add(listLinesFunction.get(i)
									+""+listLinesFunction.get(i+1).replace(" ", ""));
									
									
								}else {
									listVerifyPatternUpdateCallGasReversedSourceCodeReport.add(listLinesFunction.get(i));
								}
							}
						 	
					}
				}
	}

	
	

	public static boolean containsUpdateReversedSourceCode(String line) {

			Pattern pattern = Pattern.compile(
					  "\\+"
					+ "|-"
					+ "|\\*"
					+ "|/"
					+ "|%"
					+ "|\\+\\+"
					+ "|--"
					
					+ "| = "
					+ "|\\+="
					+ "|-="
					+ "|\\*="
					+ "|/="
					+ "|%="
					);
			
			Matcher matcher = pattern.matcher(line);
			
				if(matcher.find()) {
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

		
		html += "<h1 style=\"text-align: center;\"><span style=\"color: #003366;\">Detection report of app3</span></h1>\r\n" + 
				"<p>&nbsp;</p>\r\n" + 
				"<h2><span style=\"color: #003366;\">1. Detection results&nbsp;</span></h2><br>\r\n";
		
		
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li>Contract address :&nbsp;<strong><span style=\"color: #000000;\">"+contractAddressReversedSourceCodeReport+"</span></strong></li>\r\n" + 
				"</ul>\r\n";

		
		if(	(listVerifyPatternUpdateCallGasReversedSourceCodeReport.size()- ListTraceNestedCallsCallGasReversedSourceCodeReport.size()) >0 ) {
			
			html += "<ul style=\"list-style-type: circle;\">\r\n" + 
					"<li><span style=\"color: #000000;\">This contract is <strong> vulnerable to "
					+ "&lt; Reentrancy &gt; attack &nbsp;</strong></span></li>\r\n" + 
					"</ul>\r\n" + 
					"<p>&nbsp;</p>\r\n";
		
		}
		else {
			html += "<ul style=\"list-style-type: circle;\">\r\n" + 
					"<li><span style=\"color: #000000;\">This contract<strong> is not vulnerable to"
					+ "&lt; Reentrancy &gt;&nbsp;</strong></span></li>\r\n" + 
					"</ul>\r\n" + 
					"<p>&nbsp;</p>\r\n";
		}	
		
		html += "<h2><span style=\"color: #003366;\"><strong>2. Additional information</strong></span></h2>\r\n";
		

		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Number of functions in the contract : <strong>"
				+ ""+(listFunctionsHeadersOfContractReversedSourceCodeReport.size()+1)+"</strong></span></li>\r\n" + 
				"</ul>\r\n";
		
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Headers of these functions</span></li>\r\n" + 
				"</ul>\r\n";
		
		html +="<p style=\"padding-left: 60px;\">";
		
		html += "<span style=\"color: #008080;\"><em>main()</em></span><br><br><br>";
		for (String s : listFunctionsHeadersOfContractReversedSourceCodeReport) {
			html +="<span style=\"color: #008080;\"><em>"+s+"</em></span><br><br><br>";
		}

		html +="</p>";
		
		
		
		html += "<h3><span style=\"color: #003366;\"><strong>CALL.GAS().VALUE()</strong></span></h3>\r\n";			
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">The number of functions using <em>.call.gas().value() :&nbsp;</em>"
				+ "<strong>"+IdentifiersFunctionLevel1CallGasReversedSourceCodeReport.size()+"</strong>"
				+ "</span></li>\r\n" + 
				"</ul>\r\n";
		
		
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Headers of these functions</span></li>\r\n" + 
				"</ul>\r\n";
		
		
		html +="<p style=\"padding-left: 60px;\">";
		
		for (String s : IdentifiersFunctionLevel1CallGasReversedSourceCodeReport) {
			html +="<span style=\"color: #008080;\"><em>"+s+"</em></span><br><br><br>";
		}
	
		html +="</p>";
		
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">"
				+ "Nested calls related to <em>call.gas().value()</em></span></li>\r\n" + 
				"</ul>\r\n";
		

		html += "<p style=\"padding-left: 60px;\"><span style=\"color: #000000;\"><em>";
		
		for (String s: ListTraceNestedCallsCallGasReversedSourceCodeReport) {
			html += "<span style=\"color: #008080;\">"+s.replace(".call.gas(", ".call.gas().value()")+"</span><br><br><br>";
		}
		
		html +="</em></span></p>";


		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">By exploiting all the paths</span></li>\r\n" + 
				"</ul>\r\n";
		
		html += "<table style=\"width: 662px;\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
				"<tbody>\r\n" + 
				"<tr style=\"height: 26px;\">\r\n" + 
				"<td style=\"width: 207px; height: 26px;\">\r\n" + 
				"<p style=\"padding-left: 150px;\"><span style=\"color: #000000;\">&nbsp;</span></p>\r\n" + 
				"</td>\r\n" + 
				"<td style=\"width: 459px; height: 26px; text-align: center;\">\r\n" + 
				"<p><strong><span style=\"color: #000000;\">Line of code executing an update after an interaction with an</br></br></br> external contract</span></strong></p>\r\n" + 
				"</td>\r\n" + 
				"</tr>\r\n";

		
		for (int i = 0; i < listVerifyPatternUpdateCallGasReversedSourceCodeReport.size(); i++) {
			
			html +="<tr style=\"height: 26px;\">\r\n" + 
					"<td style=\"width: 207px; height: 26px; text-align: center;\">\r\n" + 
					"<p><span style=\"color: #000000;\"><em style=\"color: #008080;\">"
					+listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i).replace(".call.gas(", ".call.gas().value()").split(" compared to ")[0]
					+"<br><br><br> </em>compared to  <br><br><br>"
					+ "<em style=\"color: #008080;\">"
					+listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i).replace(".call.gas(", ".call.gas().value()").split(" compared to ")[1]
					+"</em></span></p>\r\n" + 
					"</td>\r\n";
			
				html += "<td style=\"width: 459px; height: 13px; padding-left: 30px;\">";
				
				while(i+1 < listVerifyPatternUpdateCallGasReversedSourceCodeReport.size()
						&& !listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i+1).contains("compared to")) 
				{
					i++;
					
					
					if(listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i).replace(".call.gas(", ".call.gas().value()").length()>180) {
						
						html += "<p><span style=\"color: #000000;\">"
								+listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i).replace(".call.gas(", ".call.gas().value()").substring(0,60)
								+"<br><br><br>"
								+listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i).replace(".call.gas(", ".call.gas().value()").substring(60,120)
								+"<br><br><br>"								
								+listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i).replace(".call.gas(", ".call.gas().value()").substring(120,180)
								+"<br><br><br>"								
								+listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i).replace(".call.gas(", ".call.gas().value()").substring(180,
										listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i).replace(".call.gas(", ".call.gas().value()").length())
								+"</span></p>";	
					}
					else {
						if(listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i).replace(".call.gas(", ".call.gas().value()").length()>120) {

							html += "<p><span style=\"color: #000000;\">"
									+listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i).replace(".call.gas(", ".call.gas().value()").substring(0,60)
									+"<br><br><br>"
									+listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i).replace(".call.gas(", ".call.gas().value()").substring(60,120)
									+"<br><br><br>"								
									+listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i).replace(".call.gas(", ".call.gas().value()").substring(120,
											listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i).replace(".call.gas(", ".call.gas().value()").length())
									+"</span></p>";									
						}
						else {
							if(listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i).replace(".call.gas(", ".call.gas().value()").length()>60) {
								html += "<p><span style=\"color: #000000;\">"
										+listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i).replace(".call.gas(", ".call.gas().value()").substring(0,60)
										+"<br><br><br>"
										+listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i).replace(".call.gas(", ".call.gas().value()").substring(60,
												listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i).replace(".call.gas(", ".call.gas().value()").length())
										+"</span></p>";									
							}

							if(listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i).replace(".call.gas(", ".call.gas().value()").length()<=60) {
								html += "<p><span style=\"color: #000000;\">"
										+listVerifyPatternUpdateCallGasReversedSourceCodeReport.get(i).replace(".call.gas(", ".call.gas().value()")
										+"</span></p>";
							}

							
						}
					
					}
					
					


				}
				html += "</td>\r\n" + 
						"</tr>\r\n";
		}//

		
		
		html += "</tbody>\r\n" + 
				"</table><br><br><br>";

		
		html += "</body>\r\n" + 
				"</html>\r\n" ;

		
		try {
			createPdf(html, "App_03_Report.pdf");
	 
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,e1.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			e1.printStackTrace();
			}
		
	}


	
	/*******************************/
	

	public static void createPdf(String html, String dest) throws IOException {
		
		ConverterProperties properties = new ConverterProperties();
		    properties.setFontProvider(new DefaultFontProvider(true, true, true));
		    
		    HtmlConverter.convertToPdf(html, new FileOutputStream(dest),properties);
	}
	


}
