/**
 * @author Mélissa MAZROU
 * @author Redouane OTMANI
 * 
 */

package pfe_32_aid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class SC_App_02_Reentrancy {


	public static List <String> AttackerContract=new ArrayList<String>();
	
	
	public static HashMap<String, HashMap<Integer, Double>> hm_hm_SucceededReentrancyTransactionsPerBlock = 
	new HashMap<String, HashMap<Integer, Double>>();
	
	public static HashMap<Integer, Double> hm_SucceededReentrancyTransactionsPerBlock = new HashMap<Integer, Double>();

	
	public static HashMap<String, HashMap<Integer, String>> hm_hm_FailedReentrancyTransactionsPerBlock =
	new HashMap<String, HashMap<Integer, String>>();

	public static HashMap<Integer, String> hm_FailedReentrancyTransactionsPerBlock = new HashMap<Integer, String>();

/*
	public static long lTempsDebut;
	public static long lTempsFin;
*/
	
	public static BigInteger BlockStartAnalysis=BigInteger.valueOf(0);
	public static BigInteger BlockEndAnalysis=BigInteger.valueOf(0);
	
	
	
	
	public static void initializeAllVariables() {
	
		AttackerContract=new ArrayList<String>();
		
		hm_hm_SucceededReentrancyTransactionsPerBlock = new HashMap<String, HashMap<Integer, Double>>();
		
		hm_SucceededReentrancyTransactionsPerBlock = new HashMap<Integer, Double>();
		
		
		hm_hm_FailedReentrancyTransactionsPerBlock = new HashMap<String, HashMap<Integer, String>>();

		hm_FailedReentrancyTransactionsPerBlock = new HashMap<Integer, String>();

/*		lTempsDebut = 0;
		lTempsFin = 0;
*/
		BlockStartAnalysis=BigInteger.valueOf(0);
		BlockEndAnalysis=BigInteger.valueOf(0);

	}
	
	
	public static void initApplicationReentrancy(String contractAddressConstructor, 
			BigInteger blockStartAnalysisConstructor, 
			BigInteger blockEndAnalysisConstructor) {
		
		initializeAllVariables();
		
		
		String contractAddress = contractAddressConstructor;
		contractAddress = contractAddress.toLowerCase();

		BlockStartAnalysis = blockStartAnalysisConstructor;
		BlockEndAnalysis = blockEndAnalysisConstructor;
		
		DetectReentrancyAttack(contractAddress);
		
	}

	
	
	@SuppressWarnings("deprecation")
	public static void DetectReentrancyAttack(String contractAddress) {

//		lTempsDebut = System.currentTimeMillis();
		
		
try {
	List<String> linesNumberOfCallsFromContractPerBlock = FileUtils.readLines(
			new File("Smart Contracts/"+contractAddress+"/Info_Internal_Transactions/numberOfCallsFromContractPerBlock.md"));


	creationRepertoire("Smart Contracts/"+contractAddress+"/Detection");
	creationRepertoire("Smart Contracts/"+contractAddress+"/Detection/Reentrancy");
	creationRepertoire("Smart Contracts/"+contractAddress+"/Detection/Reentrancy/AttackDetection");
	//creationRepertoire("Smart Contracts/"+adresseContrat+"/Detection/Reentrance/DetectionVulnerableAUneAttaque");

	
/*****We generate all the traces in a single file in order to see if there is a pattern***********/
	
	PrintWriter file_TraceId= new PrintWriter(
			"Smart Contracts/"+contractAddress+"/Detection/Reentrancy/AttackDetection/"+
			"TraceId.md", "UTF-8");

	
	String lines_Info_Internal_Transactions = "";
	
try (BufferedReader br = new BufferedReader(new FileReader(new File("Smart Contracts/"+contractAddress+"/Info_Internal_Transactions/Info_Internal_Transactions.md")))) {
    String line;
    while ((line = br.readLine()) != null) {
       lines_Info_Internal_Transactions +=line;
       lines_Info_Internal_Transactions +="\n";
    }
}

	Pattern pattern=null;
	Matcher matcher=null;
	
	for (int i = 0; i < linesNumberOfCallsFromContractPerBlock.size(); i++) {

		pattern = Pattern.compile("blockNumber "+linesNumberOfCallsFromContractPerBlock.get(i).split(" ")[0]+"\n"
	  			+ "\tfrom "+contractAddress+"\n"
				+ "\tto (.*)\n"
			    + "\tvalue (.*)\n"
				+ "\tcontractAddress (.*)\n"
				+ "\tinput (.*)\n"
				+ "\ttype (.*)\n"
				+ "\ttraceId (.*)\n"
				+ "\tisError (.*)\n"
				+ "\terrCode(.*)");
		
		matcher = pattern.matcher(lines_Info_Internal_Transactions);
		file_TraceId.println(linesNumberOfCallsFromContractPerBlock.get(i).split(" ")[0]);
		
		
		while (matcher.find()) {
			file_TraceId.println(matcher.group(6));	
		}//fin while()
	
	}//fin for()
	
	file_TraceId.close();

	//System.out.println("Fin g�n�ration traces");

	
	
	List<String> linesTraceId = FileUtils.readLines(
			new File("Smart Contracts/"+contractAddress+"/Detection/Reentrancy/AttackDetection/"+
					"TraceId.md"));

	
		boolean ifSmartContractReentrant=false;
		
if(linesTraceId.size()>linesNumberOfCallsFromContractPerBlock.size()) {//To check if traces have been generated, otherwise, it is not necessary to to go any further.
		//

		int i=0,k=0;
		int numberReentrantTransactionsPerBloc=0;
		boolean ifBlockReentrant = false;
	
		
		

		//A loop to go through the blocks
for (i = 0; i < linesTraceId.size() && k <linesNumberOfCallsFromContractPerBlock.size() ; i++) {
			numberReentrantTransactionsPerBloc=0;
			ifBlockReentrant = false;
			
			for (int j = i+1; j < i + Integer.valueOf(linesNumberOfCallsFromContractPerBlock.get(k).split(" ")[1]); j++) {

				if(
					(linesTraceId.get(j+1).contains(linesTraceId.get(j)) 
					||	
					linesTraceId.get(j+1).contains(linesTraceId.get(j).substring(0, linesTraceId.get(j).length()-1)))
						
				   && 
				   (linesTraceId.get(j).length() - linesTraceId.get(j).replace("_", "").length())>=1) 
				{

					if(verifyReentrancyEther_And_Error(
							linesNumberOfCallsFromContractPerBlock.get(k).split(" ")[0], 
							linesTraceId.get(j), 
							lines_Info_Internal_Transactions)) {
						ifBlockReentrant =true;
						ifSmartContractReentrant = true;
						numberReentrantTransactionsPerBloc++;
					}
					
					if((j+1) == (i + Integer.valueOf(linesNumberOfCallsFromContractPerBlock.get(k).split(" ")[1]))) {
						if(verifyReentrancyEther_And_Error(
								linesNumberOfCallsFromContractPerBlock.get(k).split(" ")[0], 
								linesTraceId.get(j+1), 
								lines_Info_Internal_Transactions)) {
							ifBlockReentrant =true;
							ifSmartContractReentrant = true;
							numberReentrantTransactionsPerBloc++;
						}
					}
					
					
					}//end if()
			
								
}//end for() loop

			if(ifBlockReentrant) {
				
					/*System.out.println("Nombre Transactions R�eentrantes dans le bloc "
							+linesnombreAppelDepuisNotreContratParBloc.get(k).split(" ")[0]+
							 " est: "+nombreTransactionsReentrantesParBloc);
					*/
					
					hm_hm_SucceededReentrancyTransactionsPerBlock.put(linesNumberOfCallsFromContractPerBlock.get(k).split(" ")[0], 
							hm_SucceededReentrancyTransactionsPerBlock
							);
					
					
					
			}
			
			i=i+Integer.valueOf(linesNumberOfCallsFromContractPerBlock.get(k).split(" ")[1]);//�a c'est pour avoir le saut vers la ligne
			//de l'autre bloc
			k++;
			
			
			hm_SucceededReentrancyTransactionsPerBlock = new HashMap<Integer, Double>();
			hm_FailedReentrancyTransactionsPerBlock = new HashMap<Integer, String>();
			
			
}//end for loop
	
	if(!ifSmartContractReentrant) {
	
	//lTempsFin = System.currentTimeMillis();	
	//On appelle la fonction qui g�n�re le rapport
	generateReportPDF(contractAddress, ifSmartContractReentrant, AttackerContract, hm_hm_SucceededReentrancyTransactionsPerBlock,
			hm_hm_FailedReentrancyTransactionsPerBlock);

	
	}else {
		//lTempsFin = System.currentTimeMillis();
		
		generateReportPDF(contractAddress, ifSmartContractReentrant, AttackerContract, hm_hm_SucceededReentrancyTransactionsPerBlock,
				hm_hm_FailedReentrancyTransactionsPerBlock);
		
		
	}
	
}//end if()
else{
	//lTempsFin = System.currentTimeMillis();
	
	generateReportPDF(contractAddress, ifSmartContractReentrant, AttackerContract, hm_hm_SucceededReentrancyTransactionsPerBlock,
			hm_hm_FailedReentrancyTransactionsPerBlock);
}


	} catch (IOException e) {
		JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
		//System.out.println("Exception "+e.getMessage());
		}

}

	
	
	public static boolean verifyReentrancyEther_And_Error(String blockNumber,String TraceId,String lines_Info_Internal_Transactions) throws FileNotFoundException, IOException {
	
	Pattern pattern = Pattern.compile("blockNumber "+blockNumber+"\n"
  			+ "\tfrom (.*)\n"
			+ "\tto (.*)\n"
		    + "\tvalue (.*)\n"
			+ "\tcontractAddress (.*)\n"
			+ "\tinput (.*)\n"
			+ "\ttype (.*)\n"
			+ "\ttraceId "+TraceId+"\n"
			+ "\tisError (.*)\n"
			+ "\terrCode(.*)");
	
	Matcher matcher = pattern.matcher(lines_Info_Internal_Transactions.toString());
	Integer i=0;
	Double d=0d;
	
	
	while (matcher.find()) { 
		d=0d;i=0;
	   if(Double.valueOf(matcher.group(3))>0 ) {//If there is an ether transfer of course
		   if(matcher.group(7).equals("0") /*|| matcher.group(8).isBlank()*/ ) {//if there is no error while executing the transaction
			   
			   if(hm_SucceededReentrancyTransactionsPerBlock.isEmpty()) {

				   hm_SucceededReentrancyTransactionsPerBlock.put(1, Double.valueOf(matcher.group(3))/Math.pow(10, 18));
				   
			   }
			   else{
				   i=(Integer) hm_SucceededReentrancyTransactionsPerBlock.keySet().toArray()[0];
					
					d=hm_SucceededReentrancyTransactionsPerBlock.get(
							   hm_SucceededReentrancyTransactionsPerBlock.keySet().toArray()[0]);
					
					hm_SucceededReentrancyTransactionsPerBlock.remove(i);
					hm_SucceededReentrancyTransactionsPerBlock.put(i+1, Double.valueOf(matcher.group(3))/Math.pow(10, 18) + d);

			   }
			   
			   if(!AttackerContract.contains(matcher.group(2))) {AttackerContract.add(matcher.group(2));}
			   
			   return true;
		   } else {

			  		hm_FailedReentrancyTransactionsPerBlock= new HashMap<Integer, String>();
					
					
					if(hm_hm_FailedReentrancyTransactionsPerBlock.containsKey(blockNumber)) {
		
											
					if(!hm_hm_FailedReentrancyTransactionsPerBlock.get(blockNumber).get(
							hm_hm_FailedReentrancyTransactionsPerBlock.get(blockNumber).keySet().toArray()[0]
							).contains(matcher.group(8))) {
							

						hm_FailedReentrancyTransactionsPerBlock.put(
									(Integer.valueOf(hm_hm_FailedReentrancyTransactionsPerBlock.get(blockNumber).keySet().toArray()[0].toString()) +1)
									,
									hm_hm_FailedReentrancyTransactionsPerBlock.get(blockNumber).get(
											hm_hm_FailedReentrancyTransactionsPerBlock.get(blockNumber).keySet().toArray()[0]
											)+", "+matcher.group(8)
									
									);	

						
						}
						else {
							hm_FailedReentrancyTransactionsPerBlock.put(
									(Integer.valueOf(hm_hm_FailedReentrancyTransactionsPerBlock.get(blockNumber).keySet().toArray()[0].toString()) +1)
									,matcher.group(8));
						}

						
						
					}else {
						hm_FailedReentrancyTransactionsPerBlock.put(1,matcher.group(8));
					}

				   
				   
				   
					hm_hm_FailedReentrancyTransactionsPerBlock.put(blockNumber,hm_FailedReentrancyTransactionsPerBlock);				   
				   
				   
				   
				   
				   if(!AttackerContract.contains(matcher.group(2))) {AttackerContract.add(matcher.group(2));}
				      
				   return false;
				   
		   
		   }
	   }
	   
	}//end while()

	return false;
}

		
	
	public static void creationRepertoire(String nom) {
	    //Creating a File object
	    File file = new File(nom);
	    //Creating the directory
	    boolean boolCreationRepertoire = file.mkdir();

	    //Si le r�pertoire existe, alors on le supprime et on le recr�e
	    if(!boolCreationRepertoire) {
	  	  try {
				FileUtils.deleteDirectory(file);
				Thread.sleep(1000);
				file.mkdir();

	  	  } catch (IOException e) {
	  		JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
	  	  }catch (InterruptedException e) {
	  		JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
	  	  }
	    }
	}

	
	
	public static void generateReportPDF(
			String contractAddress, 
			boolean ifSmartContractReentrant, 
			List <String>attackerContract, 
			HashMap<String, HashMap<Integer, Double>> hm_hm_SucceededReentrancyTransactionsPerBlock,
			HashMap<String, HashMap<Integer, String>> hm_hm_FailedReentrancyTransactionsPerBlock
			
			) {
		
		
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

		
		html +=  "<body style=\"font-family:calibri;font-size:14px;line-height:0.3;\">\r\n";
		

		html += "<h1 style=\"text-align: center;\"><span style=\"color: #003366;\"><strong>Detection Report of App2&nbsp;</strong></span></h1>\r\n" + 
				"<p><span style=\"color: #003366;\"><strong>&nbsp;</strong></span></p>\r\n" + 
				"<p>&nbsp;</p>\r\n" + 
				"<p>&nbsp;</p>\r\n" + 
				"<h2><span style=\"color: #003366;\"><strong>1. Detection Result</strong></span></h2>\r\n"
				+ "<br>";
		

		html +="<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Contract address : <strong>"+contractAddress+"</strong></span></li>\r\n" + 
				"</ul>\r\n";

		/*
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li>Le temps d'ex&eacute;cution de l'application 2: <strong>"+(((lTempsFin - lTempsDebut)/1000)+1)+" seconde(s)</strong></li>\r\n" + 
				"</ul>\r\n";
		*/
		//System.out.println("Le contrat "+adresseContrat);

		//On r�cup�re les blocs d�but analyse et fin analyse, les blocs r�els!
		
		BufferedReader br;
		String everything = null;
		try {
			br = new BufferedReader(new FileReader("Smart Contracts/"+contractAddress+"/Info_Internal_Transactions/numberOfCallsFromContractPerBlock.md"));
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);sb.append("\n");
		        line = br.readLine();
		    }
		    everything = sb.toString();
		    br.close();
		    
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			}
		
		
	if(ifSmartContractReentrant) {
		
		//
			
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">The contract <strong>suffered a &lt;Reentrancy&gt; attack in the analysis interval "
				+ "[ "+BlockStartAnalysis;
		
		if(everything.split("\n").length==1) {
			html +=	" - "+BlockEndAnalysis
					+ " ]"		
					+ "</strong></span></li>\r\n" + 
					"</ul>\r\n";
			
		}else {
			html +=  " - "+everything.split("\n")[everything.split("\n").length-1].split(" ")[0]
					+ " ]"		
					+ "</strong></span></li>\r\n" + 
					"</ul>\r\n";
		}
		
		
		
		
		//System.out.println("Le(s) contrat(s) attaquant(s)");
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li>The address(es) of the attacking contract(s) :&nbsp;</li>\r\n" + 
				"</ul>";
		
			for (String attacker : attackerContract) {
				//System.out.println(attaquant);
				html += "<p><strong>"+attacker+"</strong></p>";
			}
		
		
		html += "<h3>&nbsp;</h3>\r\n" + 
				"<p><span style=\"color: #000000;\">&nbsp;</span></p>\r\n" + 
				"<h2><span style=\"color: #003366;\">2. Reentrant transactions per block&nbsp;</span></h2>\r\n";

		
		
		html +="<table style=\"width: 738px;\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
				"<tbody>\r\n" + 
				"<tr style=\"height: 13.625px;\">\r\n" + 
				"<td style=\"width: 133px; text-align: center; height: 13.625px;\">\r\n" + 
				"<p><strong>Block Number</strong></p>\r\n" + 
				"</td>\r\n" + 
				"<td style=\"width: 305px; height: 13.625px; text-align: center;\">\r\n" + 
				"<p><strong>Number of reentrant transaction</strong></p>\r\n" + 
				"</td>\r\n" + 
				"<td style=\"width: 308px; height: 13.625px; text-align: center;\">\r\n" + 
				"<p><strong>Total amount of</strong> <strong>Ether</strong>&nbsp;</p>\r\n" + 
				"</td>\r\n" + 
				"</tr>\r\n";
		
		
		
		List sortedKeys=new ArrayList(hm_hm_SucceededReentrancyTransactionsPerBlock.keySet());
		Collections.sort(sortedKeys);
		
		for (Object object : sortedKeys) {
		
		html += "<tr style=\"height: 13px;\">\r\n" + 
				"<td style=\"width: 133px; height: 13px; text-align: center;\">\r\n" + 
				"<p>"+object+"</p>\r\n" + 
				"</td>\r\n" + 
				"<td style=\"width: 305px; height: 13px; text-align: center;\">\r\n" + 
				"<p>"+hm_hm_SucceededReentrancyTransactionsPerBlock.get(object).keySet().toArray()[0]+"</p>\r\n" + 
				"</td>\r\n" + 
				"<td style=\"width: 308px; height: 13px; text-align: center;\">\r\n" + 
				"<p>"+hm_hm_SucceededReentrancyTransactionsPerBlock.get(object).get(
						hm_hm_SucceededReentrancyTransactionsPerBlock.get(object).keySet().toArray()[0])+"</p>\r\n" + 
				"</td>\r\n" + 
				"</tr>\r\n";
		
		}
		

		html +="</tbody>\r\n"+ 
				"</table>\r\n";
		

		html +="<h3>&nbsp;</h3>\r\n" + 
				"<h2><span style=\"color: #003366;\">3. Failed Reentrant Transactions per Block</span></h2>\r\n" + 
				"<table style=\"width: 594px;\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
				"<tbody>\r\n" + 
				"<tr style=\"height: 13px;\">\r\n" + 
				"<td style=\"width: 123px; text-align: center; height: 13px;\">\r\n" + 
				"<p><strong>Block Number</strong></p>\r\n" + 
				"</td>\r\n" + 
				"<td style=\"width: 325px; height: 13px; text-align: center;\">\r\n" + 
				"<p><strong>Number of failed reentrant transactions</strong></p>\r\n" + 
				"</td>\r\n" + 
				"<td style=\"width: 154px; height: 13px; text-align: center;\">\r\n" + 
				"<p><strong>Error type</strong></p>\r\n" + 
				"</td>\r\n" + 
				"</tr>\r\n";
		
		
		List sortedKeys2=new ArrayList(hm_hm_FailedReentrancyTransactionsPerBlock.keySet());
		Collections.sort(sortedKeys2);
		
		for (Object object2 : sortedKeys2) {

			if(hm_hm_FailedReentrancyTransactionsPerBlock.get(object2).get(
					hm_hm_FailedReentrancyTransactionsPerBlock.get(object2).keySet().toArray()[0]).contains(",")) {
				
				html += "<tr style=\"height: 13px;\">\r\n" + 
						"<td style=\"width: 123px; height: 13px; text-align: center;\">\r\n" + 
						"<p>"+object2+"</p>\r\n" + 
						"</td>\r\n" + 
						"<td style=\"width: 325px; height: 13px; text-align: center;\">\r\n" + 
						"<p>"+hm_hm_FailedReentrancyTransactionsPerBlock.get(object2).keySet().toArray()[0]+"&nbsp;&nbsp;</p>\r\n" + 
						"</td>\r\n" + 
						"<td style=\"width: 154px; height: 13px; text-align: center;\">\r\n" + 
						"<p>"+hm_hm_FailedReentrancyTransactionsPerBlock.get(object2).get(
								hm_hm_FailedReentrancyTransactionsPerBlock.get(object2).keySet().toArray()[0]).split(",")[0]+",<br><br><br>"
						+hm_hm_FailedReentrancyTransactionsPerBlock.get(object2).get(
								hm_hm_FailedReentrancyTransactionsPerBlock.get(object2).keySet().toArray()[0]).split(",")[1]+ "</p>\r\n" + 
						"</td>\r\n" + 
						"</tr>\r\n";
				
			}else {
				html += "<tr style=\"height: 13px;\">\r\n" + 
						"<td style=\"width: 123px; height: 13px; text-align: center;\">\r\n" + 
						"<p>"+object2+"</p>\r\n" + 
						"</td>\r\n" + 
						"<td style=\"width: 325px; height: 13px; text-align: center;\">\r\n" + 
						"<p>"+hm_hm_FailedReentrancyTransactionsPerBlock.get(object2).keySet().toArray()[0]+"&nbsp;&nbsp;</p>\r\n" + 
						"</td>\r\n" + 
						"<td style=\"width: 154px; height: 13px; text-align: center;\">\r\n" + 
						"<p>"+hm_hm_FailedReentrancyTransactionsPerBlock.get(object2).get(
								hm_hm_FailedReentrancyTransactionsPerBlock.get(object2).keySet().toArray()[0])+"</p>\r\n" + 
						"</td>\r\n" + 
						"</tr>\r\n";
			}
		
		
		}
		
		html += "</tbody>\r\n" + 
				"</table>\r\n";
		

		
		}
	
	else {
			
	html += "<ul style=\"list-style-type: circle;\">\r\n" + 
			"<li><span style=\"color: #000000;\"> The contract "
			+ "<strong>did not suffer a &lt;Reentrancy&gt; attack in the analysis interval "
			+ "[ "+BlockStartAnalysis
			+" - ";
	
	if(everything.split("\n").length==1) {
		html +=  BlockEndAnalysis
				+" ]"		
				+ "</strong></span></li>\r\n" + 
				"</ul>\r\n";
		
	}else {
		html += everything.split("\n")[everything.split("\n").length-1].split(" ")[0]
				+" ]"		
				+ "</strong></span></li>\r\n" + 
				"</ul>\r\n";
	}
	
	}
	
	
	
	html += "</body>\r\n" + 
			"</html>\r\n" ;
	
	try {
		createPdf(html, "App_02_Report.pdf");
				
	} catch (Exception e1) {
		JOptionPane.showMessageDialog(null,e1.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
		e1.printStackTrace();
		}

	
		
		
	}

	
	
	public static void createPdf(String html, String dest) throws IOException {
		ConverterProperties properties = new ConverterProperties();
		    properties.setFontProvider(new DefaultFontProvider(true, true, true));
		    
		    HtmlConverter.convertToPdf(html, new FileOutputStream(dest),properties);
	}

	

	
	
}
