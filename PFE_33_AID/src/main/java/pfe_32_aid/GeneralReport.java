package pfe_32_aid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
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

public class GeneralReport {

	public static String contractAddress =null;
	
	public static boolean bSourceCodeVerified=false;
	
	public static String contractNameOrKnownAs=null;
	public static String compilerVersion=null;
	public static String contractStatusDeployedOrDestructed=null;
	public static String dateAndTimeOfContractCreation=null;
	
	//
	public static String blockContractCreationNumber=null;
	public static String gasLimitOfBlockOfContractCreation=null;
	public static String gasUsedOfBlockOfContractCreation=null;
	public static String minerAddressOfBlockOfContractCreation=null;
	public static String TotalNumberOfTransactionInBlockOfContractCreation=null;

	//
	public static ArrayList<String> ListExternalTransactionsNumberOfCallsPerBlock=new ArrayList<String>();
	public static ArrayList<String> ListExternalTransactionsSourceTransactionByBlockTemp=new ArrayList<String>();
	public static HashMap<String, String> hm_ExternalTransactionsSourceTransactionPerBlock = new HashMap<String, String>();
	
	
	public static int numberOfExternalTransactionsBlocks=0;
	public static String arrayInfoBlock_ExternalTransactions[][]=null;
	
	//
	public static ArrayList<String> ListInternalTransactionsNumberOfCallsPerBlock=new ArrayList<String>();
	public static ArrayList<String> ListInternalTransactionsDestinationTransactionPerBlockTemp=new ArrayList<String>();
	public static HashMap<String, String> hm_InternalTransactionsDestinationTransactionPerBlock = new HashMap<String, String>();
	
	public static int numberOfInternalTransactionBlocks=0;
	public static String arrayInfoBlock_InternalTransactions[][]=null;

	
	//
	public static boolean boolFileNumberCallToContractByEmptyBlock;
	public static boolean boolDirectory_InfoBlock_Contract_ExternalTransactions_Empty;
	
	
	public static boolean boolFileNumberCallFromContractByEmptyBlock;
	public static boolean boolDirectory_InfoBlock_Contract_InternalTransactions_Empty;	
	//



	public static void initializeAllVariables() {
		contractAddress =null;
		
		bSourceCodeVerified=false;
		
		contractNameOrKnownAs=null;
		compilerVersion=null;
		contractStatusDeployedOrDestructed=null;
		dateAndTimeOfContractCreation=null;
		
		//
		blockContractCreationNumber=null;
		gasLimitOfBlockOfContractCreation=null;
		gasUsedOfBlockOfContractCreation=null;
		minerAddressOfBlockOfContractCreation=null;
		TotalNumberOfTransactionInBlockOfContractCreation=null;

		//
		ListExternalTransactionsNumberOfCallsPerBlock=new ArrayList<String>();
		ListExternalTransactionsSourceTransactionByBlockTemp=new ArrayList<String>();
		hm_ExternalTransactionsSourceTransactionPerBlock = new HashMap<String, String>();
		
		
		numberOfExternalTransactionsBlocks=0;
		arrayInfoBlock_ExternalTransactions=null;
		
		//
		ListInternalTransactionsNumberOfCallsPerBlock=new ArrayList<String>();
		ListInternalTransactionsDestinationTransactionPerBlockTemp=new ArrayList<String>();
		hm_InternalTransactionsDestinationTransactionPerBlock = new HashMap<String, String>();
		
		numberOfInternalTransactionBlocks=0;
		arrayInfoBlock_InternalTransactions=null;

		
		//
		boolFileNumberCallToContractByEmptyBlock=false;
		boolDirectory_InfoBlock_Contract_ExternalTransactions_Empty=false;
		
		
		boolFileNumberCallFromContractByEmptyBlock=false;
		boolDirectory_InfoBlock_Contract_InternalTransactions_Empty=false;	
		//

		
	}
	
	public static void initGenerateReport() {
		initializeAllVariables();

		Pattern pattern=null;
		Matcher matcher=null;

		
		
		// Get directory name
		
		File file = new File("Smart Contracts");
		String[] directories = file.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		contractAddress = directories[0];
		
		
		/**************If contract source code was verified on Etherscan**********************/
		
		
		file =new File("Smart Contracts/"+contractAddress);
		directories = file.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		
		for (int j = 0; j < directories.length; j++) {
			if(directories[j].contains("Contract_Source_Code")) {bSourceCodeVerified=true;break;}
		}
		
		
		if(bSourceCodeVerified) {

			try (BufferedReader br = new BufferedReader(new FileReader(new File("Smart Contracts/"+contractAddress+"/"
					+ "Contract_Source_Code/additionalInfo.md")))) {
			    String line;
			    String contentFile="";
			    while ((line = br.readLine()) != null) {
			    	contentFile +=line;
			    	contentFile +="\n";
			    }

			pattern = Pattern.compile("ContractName: \"(.*)\"");
			
			for (int i = 0; i < contentFile.split("\n").length; i++) {
				
				matcher = pattern.matcher(contentFile.split("\n")[i]);

				while (matcher.find()) {
					contractNameOrKnownAs=matcher.group(1);
					
					break;
				}
			}
			
			pattern = Pattern.compile("CompilerVersion: \"(.*)\"");
			for (int i = 0; i < contentFile.split("\n").length; i++) {
				
				matcher = pattern.matcher(contentFile.split("\n")[i]);

				while (matcher.find()) {
					compilerVersion=matcher.group(1);
					break;
				}
			}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
				//e.printStackTrace();
				}
			
		}
	
		
		//Contract status
		
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("Smart Contracts/"+contractAddress+"/"
					+ "Bytecode-OPCode/ContractStatus.txt"), "UTF-8"));
		
			String line;
		    String contenuFichier="";
		    while ((line = br.readLine()) != null) {
		    	contenuFichier +=line;
		    	contenuFichier +="\n";
		    }		
			    contractStatusDeployedOrDestructed=contenuFichier;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			//e.printStackTrace();
			}
		
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("Smart Contracts/"+contractAddress+"/"
					+ "Info_Creation_Block/Info_Creation_Block.md"), "UTF-8"));
		
			String line;
		    String contentFile="";

		    while ((line = br.readLine()) != null) {
		    	contentFile +=line;
		    	contentFile +="\n";
		    }		
			    
			    for (int i = 0; i < contentFile.split("\n").length; i++) {
			    	
			    	if(contentFile.split("\n")[i].startsWith("blockNumber ")) {
			    		blockContractCreationNumber=contentFile.split("\n")[i].split("blockNumber ")[1];
			    	}

			    	if(contentFile.split("\n")[i].startsWith("gasLimit ")) {
			    		gasLimitOfBlockOfContractCreation=contentFile.split("\n")[i].split("gasLimit ")[1];
			    	}

			    	if(contentFile.split("\n")[i].startsWith("gasUsed ")) {
			    		gasUsedOfBlockOfContractCreation=contentFile.split("\n")[i].split("gasUsed ")[1];
			    	}
			    	
			    	if(contentFile.split("\n")[i].startsWith("miner ")) {
			    		minerAddressOfBlockOfContractCreation=contentFile.split("\n")[i].split("miner ")[1];
			    	}

			    	
			    	if(contentFile.split("\n")[i].startsWith("time ")) {
			    		dateAndTimeOfContractCreation=contentFile.split("\n")[i].split("time ")[1];
			    	}

			    	if(contentFile.split("\n")[i].startsWith("transactionsSize ")) {
			    		TotalNumberOfTransactionInBlockOfContractCreation=contentFile.split("\n")[i].split("transactionsSize ")[1];
			    	}
			    	
				}
			    
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			//e.printStackTrace();
			}
		

		/****************************************External Transactions**************************************************************************************/

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("Smart Contracts/"+contractAddress+"/"
					+ "Info_External_Transactions/NumberOfCallsToContractPerBlock.md"), "UTF-8"));
		
			String line;
		    String contentFile="";
		    while ((line = br.readLine()) != null) {
		    	contentFile +=line.replace(" ","=");
		    	contentFile +="\n";
		    }		
			    
			    for (int j = 0; j < contentFile.split("\n").length; j++) {
			    	
			    	ListExternalTransactionsNumberOfCallsPerBlock.add(contentFile.split("\n")[j]);
				}
			    numberOfExternalTransactionsBlocks = contentFile.split("\n").length;
			    
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			//e.printStackTrace();
			}

	
		
		// The source of the external block transaction

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("Smart Contracts/"+contractAddress+"/"
					+ "Info_External_Transactions/Info_External_Transactions.md"), "UTF-8"));
		
			String line;
		    String contentFile="";
		    String blockNumberExternalTemp="";
		    
		    while ((line = br.readLine()) != null) {
		    	contentFile +=line;
		    	contentFile +="\n";
		    }		
			    
			    for (int j = 0; j < contentFile.split("\n").length; j++) {
					if(contentFile.split("\n")[j].startsWith("blockNumber ")) {
								   blockNumberExternalTemp = contentFile.split("\n")[j].split("blockNumber ")[1]+" ";
						j++;
						while(j<contentFile.split("\n").length
								&&
								!contentFile.split("\n")[j].startsWith("blockNumber ")
								) 
						{
							if(contentFile.split("\n")[j].contains("to "+contractAddress)){
								
								ListExternalTransactionsSourceTransactionByBlockTemp.add(blockNumberExternalTemp+" "+contentFile.split("\n")[j-1].split("from ")[1]);
								
								break;
							}else {
								j++;
							}
						}//while
					}
					
				}
			    
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			}
		

		String strTempNumberExternal_Block="";
		String strTempSourceAddressExternal="";
		for (int i = 0; i < ListExternalTransactionsSourceTransactionByBlockTemp.size(); i++) {
		
			if(!hm_ExternalTransactionsSourceTransactionPerBlock.containsKey(ListExternalTransactionsSourceTransactionByBlockTemp.get(i).split("  ")[0])) {
				strTempNumberExternal_Block=ListExternalTransactionsSourceTransactionByBlockTemp.get(i).split("  ")[0];
				strTempSourceAddressExternal=ListExternalTransactionsSourceTransactionByBlockTemp.get(i).split("  ")[1];
				//
				i++;
				while(i < ListExternalTransactionsSourceTransactionByBlockTemp.size()
						&&
						ListExternalTransactionsSourceTransactionByBlockTemp.get(i).split("  ")[0].contains(strTempNumberExternal_Block)) 
				{
					strTempSourceAddressExternal+="\n"+ListExternalTransactionsSourceTransactionByBlockTemp.get(i).split("  ")[1];
					i++;
				}
				
				hm_ExternalTransactionsSourceTransactionPerBlock.put(strTempNumberExternal_Block, strTempSourceAddressExternal);
				strTempNumberExternal_Block="";strTempSourceAddressExternal="";
			}
		}

		
	
		//Info about each block

		File folder = new File("Smart Contracts/"+contractAddress+"/Info_Blocks_Contract_External_Transactions");
		File[] listOfFiles = folder.listFiles();
		arrayInfoBlock_ExternalTransactions = new String[numberOfExternalTransactionsBlocks][2];
		
		for (int i = 0; i < listOfFiles.length; i++) {
			arrayInfoBlock_ExternalTransactions[i][0]=listOfFiles[i].getName().split("Bloc_N°")[1].split(".md")[0];
			
			try {
				
				br = new BufferedReader(new InputStreamReader(new FileInputStream(listOfFiles[i]), "UTF-8"));
				String line;
			    String contentFile="";
			    
			    while ((line = br.readLine()) != null) {
			    	contentFile +=line;
			    	contentFile +="\n";
			    }		

			    	for (int j = 0; j < contentFile.split("\n").length; j++) {
						if(contentFile.split("\n")[j].startsWith("gasLimit ")) {
							arrayInfoBlock_ExternalTransactions[i][1]=
									contentFile.split("\n")[j].split("gasLimit ")[1];
						}

						if(contentFile.split("\n")[j].startsWith("gasUsed ")) {
							arrayInfoBlock_ExternalTransactions[i][1]=
									arrayInfoBlock_ExternalTransactions[i][1]+"\n"+
									contentFile.split("\n")[j].split("gasUsed ")[1];							
						}

						if(contentFile.split("\n")[j].startsWith("miner ")) {
							arrayInfoBlock_ExternalTransactions[i][1]=
									arrayInfoBlock_ExternalTransactions[i][1]+"\n"+
									contentFile.split("\n")[j].split("miner ")[1];							
						}

						if(contentFile.split("\n")[j].startsWith("time ")) {
							arrayInfoBlock_ExternalTransactions[i][1]=
									arrayInfoBlock_ExternalTransactions[i][1]+"\n"+
									contentFile.split("\n")[j].split("time ")[1];					
						}

						if(contentFile.split("\n")[j].startsWith("transactionsSize ")) {
							arrayInfoBlock_ExternalTransactions[i][1]=
									arrayInfoBlock_ExternalTransactions[i][1]+"\n"+
									contentFile.split("\n")[j].split("transactionsSize ")[1];					
						}
					}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
				//e.printStackTrace();
				}
		}
		

		/****************************************Internal Transactions**************************************************************************************/
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("Smart Contracts/"+contractAddress+"/"
					+ "Info_Internal_Transactions/numberOfCallsFromContractPerBlock.md"), "UTF-8"));
		
			String line;
		    String contentFile="";
		    while ((line = br.readLine()) != null) {
		    	contentFile +=line.replace(" ","=");
		    	contentFile +="\n";
		    }		
			    
			    for (int j = 0; j < contentFile.split("\n").length; j++) {
			    	ListInternalTransactionsNumberOfCallsPerBlock.add(contentFile.split("\n")[j]);
				}
			    
			    numberOfInternalTransactionBlocks = contentFile.split("\n").length;

			    
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			//e.printStackTrace();
			}

		
		// The destination of the internal transaction per block
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("Smart Contracts/"+contractAddress+"/"
					+ "Info_Internal_Transactions/Info_Internal_Transactions.md"), "UTF-8"));
		
			String line;
		    String contentFile="";
		    String blockNumberInternalTemp="";
		    
		    
		    while ((line = br.readLine()) != null) {
		    	contentFile +=line;
		    	contentFile +="\n";
		    }		
			    
			    for (int j = 0; j < contentFile.split("\n").length; j++) {
					if(contentFile.split("\n")[j].startsWith("blockNumber ")) {
						
						blockNumberInternalTemp =contentFile.split("\n")[j].split("blockNumber ")[1]+" ";
						
						j++;
						while(j<contentFile.split("\n").length
								&&
								!contentFile.split("\n")[j].startsWith("blockNumber ")
								) {
							if(contentFile.split("\n")[j].contains("from "+contractAddress)){
								
								if(contentFile.split("\n")[j+1].split("to ").length>=2) {
									ListInternalTransactionsDestinationTransactionPerBlockTemp.add(
											blockNumberInternalTemp+" "+
											contentFile.split("\n")[j+1].split("to ")[1]);
								}
								
								
								break;
								
							} else {
								j++;
							}
						}
					}
				}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			//e.printStackTrace();
			}


		String strTempNumBlockInternal="";
		String strTempDestinationAddressInternal="";

		for (int i = 0; i < ListInternalTransactionsDestinationTransactionPerBlockTemp.size(); i++) {
		
			if(!hm_InternalTransactionsDestinationTransactionPerBlock.containsKey(ListInternalTransactionsDestinationTransactionPerBlockTemp.get(i).split("  ")[0])) {
				strTempNumBlockInternal=ListInternalTransactionsDestinationTransactionPerBlockTemp.get(i).split("  ")[0];
				strTempDestinationAddressInternal=ListInternalTransactionsDestinationTransactionPerBlockTemp.get(i).split("  ")[1];
				//
				i++;
				while(i < ListInternalTransactionsDestinationTransactionPerBlockTemp.size()
						&&
						ListInternalTransactionsDestinationTransactionPerBlockTemp.get(i).split("  ")[0].contains(strTempNumBlockInternal)) 
				{
					strTempDestinationAddressInternal+="\n"+ListInternalTransactionsDestinationTransactionPerBlockTemp.get(i).split("  ")[1];
					i++;
				}
				
				hm_InternalTransactionsDestinationTransactionPerBlock.put(strTempNumBlockInternal, strTempDestinationAddressInternal);
				strTempNumBlockInternal="";strTempDestinationAddressInternal="";
			}
		}


		// Info on each block 

		folder = new File("Smart Contracts/"+contractAddress+"/Info_Blocks_Contract_Internal_Transactions");
		listOfFiles = folder.listFiles();
		
		
		arrayInfoBlock_InternalTransactions = new String [numberOfInternalTransactionBlocks][2];

		for (int i = 0; i < listOfFiles.length; i++) {
			arrayInfoBlock_InternalTransactions[i][0]=listOfFiles[i].getName().split("Bloc_N°")[1].split(".md")[0];
			try {
				
				br = new BufferedReader(new InputStreamReader(new FileInputStream(listOfFiles[i]), "UTF-8"));
			
				String line;
			    String contentFile="";
			    
			    while ((line = br.readLine()) != null) {
			    	contentFile +=line;
			    	contentFile +="\n";
			    }
				    
			    	for (int j = 0; j < contentFile.split("\n").length; j++) {
						if(contentFile.split("\n")[j].startsWith("gasLimit ")) {
							arrayInfoBlock_InternalTransactions[i][1]=
									contentFile.split("\n")[j].split("gasLimit ")[1];
						}

						if(contentFile.split("\n")[j].startsWith("gasUsed ")) {
							arrayInfoBlock_InternalTransactions[i][1]=
									arrayInfoBlock_InternalTransactions[i][1]+"\n"+
									contentFile.split("\n")[j].split("gasUsed ")[1];							
						}

						if(contentFile.split("\n")[j].startsWith("miner ")) {
							arrayInfoBlock_InternalTransactions[i][1]=
									arrayInfoBlock_InternalTransactions[i][1]+"\n"+
									contentFile.split("\n")[j].split("miner ")[1];							
						}

						if(contentFile.split("\n")[j].startsWith("time ")) {
							arrayInfoBlock_InternalTransactions[i][1]=
									arrayInfoBlock_InternalTransactions[i][1]+"\n"+
									contentFile.split("\n")[j].split("time ")[1];							
						}

						if(contentFile.split("\n")[j].startsWith("transactionsSize ")) {
							arrayInfoBlock_InternalTransactions[i][1]=
									arrayInfoBlock_InternalTransactions[i][1]+"\n"+
									contentFile.split("\n")[j].split("transactionsSize ")[1];							
						}
					}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
				//e.printStackTrace();
				}
		}


		GenerateReportPDF();
		
		
		
		
	}

	
	public static void GenerateReportPDF() {
			
		try {
			createPdf(PutVariablesIntoHTMLFile(), "GeneralReport.pdf");
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,e1.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			//e1.printStackTrace();
			}

	}
	
	
	public static String PutVariablesIntoHTMLFile() {

		String html="<!DOCTYPE html>\r\n" + 
				"<html>\r\n";
		
		html += "<head>" + 
				"<style>" + 
				"table {table-layout: fixed;width: 100%;}" + 
	//			"tr {"
	//			+ "word-wrap: break-word;"
	//			+ "overflow-wrap: break-word;}" + 
				"</style>" + 
				"</head>";
		
		html +=  "<body style=\"font-family:calibri;font-size:14px;line-height:0.3;\">\r\n" + 
				"<p>&nbsp;</p>\r\n" + 
				"<div>\r\n" + 
				"<p>&nbsp;</p>\r\n" + 
				"</div>\r\n" + 
				"<div>\r\n" + 
				"<h1 style=\"text-align: center;\"><span style=\"background-color: #ffffff; color: #003366;\">SCVADetection General Report</span></h1>\r\n" + 
				"<p>&nbsp;</p>\r\n" + 
				"<p>&nbsp;</p>\r\n" + 
				"<h2><span style=\"color: #003366;\">1. General information&nbsp;&nbsp;</span></h2>\r\n" + 
				"</div>\r\n" + 
				"<p>&nbsp;</p>\r\n" + 
				"<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Contract Address&nbsp;: <strong>"+contractAddress+"</strong></span></li>\r\n" + 
				"</ul>\r\n"; 
			
		if (bSourceCodeVerified) {
			html += "<ul style=\"list-style-type: circle;\">\r\n" + 
					"<li><span style=\"color: #000000;\">The source code of the contract has been verified on Etherscan</span></li>\r\n" + 
					"</ul>\r\n" + 
					"<ul style=\"list-style-type: circle;\">\r\n" + 
					"<li><span style=\"color: #000000;\">The contract is known as : <strong>"+contractNameOrKnownAs+"</strong></span></li>\r\n" + 
					"</ul>\r\n" + 
					"<ul style=\"list-style-type: circle;\">\r\n" + 
					"<li><span style=\"color: #000000;\">Compiler Version&nbsp;: <strong>"+compilerVersion+"</strong></span></li>\r\n" + 
					"</ul>\r\n" + 
					"";
		}else {
			html += "<ul style=\"list-style-type: circle;\">\r\n" + 
					"<li><span style=\"color: #000000;\">The source code has not yet been verified on Etherscan</span></li>\r\n" + 
					"</ul>\r\n";
		}
		
		if(contractStatusDeployedOrDestructed.contains("deployed")) {
			html += "<ul style=\"list-style-type: circle;\">\r\n" + 
					"<li><span style=\"color: #000000;\">Contract status&nbsp;: <strong>Deployed</strong> (in other words it has not been destroyed)</span></li>\r\n" + 
					"</ul>\r\n" + 
					"";
		}else {
			html += "<ul style=\"list-style-type: circle;\">\r\n" + 
					"<li><span style=\"color: #000000;\">Contract status&nbsp;: <strong>Destroyed</strong></span></li>\r\n" + 
					"</ul>\r\n";
		}
		
		
		html += "<div>\r\n" + 
				"<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">The time and date of creation of the contract : "
				+ "<strong>"+dateAndTimeOfContractCreation+"</strong></span></li>\r\n" + 
				"</ul>\r\n" + 
				"</div>\r\n";
		
		html += "<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">The block number of the contract creation : <strong>"+blockContractCreationNumber+"</strong></span></li>\r\n" + 
				"</ul>\r\n" + 
				"<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">Gas limit of the contract creation block : <strong>"+gasLimitOfBlockOfContractCreation+"</strong></span></li>\r\n" + 
				"</ul>\r\n" + 
				"<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">The amount of gas used by the contract creation block :&nbsp;&nbsp;<strong>"+gasUsedOfBlockOfContractCreation+"</strong></span></li>\r\n" + 
				"</ul>\r\n" + 
				"<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">The address of the mineur of the block of creation of the contract : <strong>"+minerAddressOfBlockOfContractCreation+"</strong></span></li>\r\n" + 
				"</ul>\r\n" + 
				"<ul style=\"list-style-type: circle;\">\r\n" + 
				"<li><span style=\"color: #000000;\">The total number of transactions in the contract creation block : <strong>"+TotalNumberOfTransactionInBlockOfContractCreation+"</strong></span></li>\r\n" + 
				"</ul>\r\n";
		
		
		html += "<div><strong>&nbsp;</strong></div>\r\n" + 
				"<h2><span style=\"color: #003366;\"><strong>2. External Transactions</strong></span></h2>\r\n" + 
				"<h3><strong>&nbsp;<span style=\"color: #003366;\">2.1 External transactions (towards the contract) per block in the selected analysis block interval &nbsp;</span></strong></h3>\r\n" + 
				"";
		
		html += "<table style=\"width: 273px; height: 67px;\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
				"<tbody>\r\n";
		
		html += "<tr style=\"height: 14.0938px;\">\r\n" + 
				"<td style=\"width: 122.667px; height: 14.0938px; text-align: center;\">\r\n" + 
				"<p><strong>Block Number&nbsp;</strong></p>\r\n" + 
				"</td>\r\n" + 
				"<td style=\"width: 144.667px; height: 14.0938px; text-align: center;\">\r\n" + 
				"<p><strong>Number of calls</strong></p>\r\n" + 
				"</td>\r\n" + 
				"</tr>\r\n";

		File file = new File("Smart Contracts/"+contractAddress+"/Info_External_Transactions/NumberOfCallsToContractPerBlock.md");

		boolFileNumberCallToContractByEmptyBlock = !file.exists() || file.length() == 0;
		
		if(!boolFileNumberCallToContractByEmptyBlock) {
			for (int i = 0; i < ListExternalTransactionsNumberOfCallsPerBlock.size(); i++) {
							
							html += "<tr style=\"height: 16px;\">\r\n" + 
									"<td style=\"width: 122.667px; height: 16px; text-align: center;\">\r\n" + 
									"<p>"+ListExternalTransactionsNumberOfCallsPerBlock.get(i).split("=")[0]+"&nbsp;&nbsp;</p>\r\n" + 
									"</td>\r\n" + 
									"<td style=\"width: 144.667px; height: 16px; text-align: center;\">\r\n" + 
									"<p>"+ListExternalTransactionsNumberOfCallsPerBlock.get(i).split("=")[1]+"</p>\r\n" + 
									"</td>\r\n" + 
									"</tr>\r\n" + 
									"";
			}
		}

		
		html += "</tbody>\r\n" + 
				"</table>\r\n";
		
		
		html += "<div>\r\n" + 
				"<h3>&nbsp;<span style=\"color: #003366;\">2.2&nbsp;<strong>Source of external transaction per block</strong></span></h3>\r\n" + 
				"</div>\r\n";
		
		
		
		html +="<div>\r\n" + 
				"<table style=\"width: 492px; border-color: black; height: 114px;\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
				"<tbody>\r\n" + 
				"<tr style=\"height: 25.1563px;\">\r\n" + 
				"<td style=\"width: 133.333px; height: 25.1563px; text-align: center;\">\r\n" + 
				"<p><strong>Block Number</strong></p>\r\n" + 
				"</td>\r\n" + 
				"<td style=\"width: 353.333px; height: 25.1563px; text-align: center;\">\r\n" + 
				"<p><strong>Address</strong></p>\r\n" + 
				"</td>\r\n" + 
				"</tr>\r\n";
		
		
		
		SortedSet<String> keys = new TreeSet<>(hm_ExternalTransactionsSourceTransactionPerBlock.keySet());
		for (String key : keys) { 
		   String value = hm_ExternalTransactionsSourceTransactionPerBlock.get(key);
		   //System.out.println("1. "+key+" "+value);

		   html += "<tr style=\"height: 13px;\">\r\n" + 
					"<td style=\"width: 133.333px; height: 13px; text-align: center;\">\r\n" + 
					"<p>"+key+"</p>\r\n" + 
					"</td>\r\n" + 
					"<td style=\"width: 353.333px; height: 13px;\">\r\n" + 
					"<p style=\"text-align: center;\">"+value.replace("\n","<br><br><br><br>")+"</p>\r\n" + 
					"</td>\r\n" + 
					"</tr>\r\n";
		}
		
		html +="</tbody>\r\n" + 
				"</table>\r\n" + 
				"</div>\r\n";

		
		html += "<h3><span style=\"color: #003366;\"><strong>2.3 Information about blocks calling the contract</strong></span></h3>\r\n" + 
				"";
		
		html += "<table style=\"width: 600px;\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
				"<tbody>\r\n" + 
				"<tr style=\"height: 35.0833px;\">\r\n" + 
				"<td style=\"width: 134px; height: 35.0833px; text-align: center;\">\r\n" + 
				"<p><strong>Block Number</strong></p>\r\n" + 
				"</td>\r\n" + 
				"<td style=\"width: 403px; height: 35.0833px; text-align: center;\">\r\n" + 
				"<p><strong>Informations&nbsp;</strong></p>\r\n" + 
				"</td>\r\n" + 
				"</tr>\r\n" + 
				"";


		
		try {
			boolDirectory_InfoBlock_Contract_ExternalTransactions_Empty=isDirEmpty(
				Paths.get("Smart Contracts/"+contractAddress+"/Info_Blocks_Contract_External_Transactions"));

			if(!boolDirectory_InfoBlock_Contract_ExternalTransactions_Empty) {
				
				for (int i = 0; i < arrayInfoBlock_ExternalTransactions.length; i++) {
					html += "<tr style=\"height: 49px;\">\r\n" + 
							"<td style=\"width: 134px; height: 49px; text-align: center;\">\r\n" + 
							"<p>"+arrayInfoBlock_ExternalTransactions[i][0]+"</p>\r\n" + 
							"</td>\r\n" + 
							"<td style=\"width: 403px; height: 49px;\">\r\n" + 
							"<div style=\"text-align: center;\">\r\n" + 
							"<p style=\"text-align: left; padding-left: 30px;\">Gas Limit : "+arrayInfoBlock_ExternalTransactions[i][1].split("\n")[0]+"</p>\r\n" + 
							"<p style=\"text-align: left; padding-left: 30px;\">Gaz Used : "+arrayInfoBlock_ExternalTransactions[i][1].split("\n")[1]+"</p>\r\n" + 
							"<p style=\"text-align: left; padding-left: 30px;\">Miner : "+arrayInfoBlock_ExternalTransactions[i][1].split("\n")[2]+"</p>\r\n" + 
							"<p style=\"text-align: left; padding-left: 30px;\">Date and time of creation : "+arrayInfoBlock_ExternalTransactions[i][1].split("\n")[3]+"</p>\r\n" + 
							"<p style=\"text-align: left; padding-left: 30px;\">Total number of transactions in this block : "+arrayInfoBlock_ExternalTransactions[i][1].split("\n")[4]+"</p>\r\n" + 
							"</div>\r\n" + 
							"</td>\r\n" + 
							"</tr>\r\n" + 
							"";
				}
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			//e.printStackTrace();
			}
		
			
		html += "</tbody>\r\n" + 
				"</table>\r\n" + 
				"<p>&nbsp;</p>\r\n" + 
				"";
		
		
		/*****************************************************************************************************************************/
		
		html += "<h2><span style=\"color: #003366;\">3. Internal Transactions</span></h2>\r\n" + 
				"<h3><span style=\"color: #003366;\"><strong>3.1 Internal transactions (from the contract) per block in the selected analysis block interval</strong></span></h3>\r\n" + 
				"";
		
		html += "<div>\r\n" + 
				"<table style=\"width: 332px; height: 106px;\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
				"<tbody>\r\n" + 
				"<tr style=\"height: 13.1875px;\">\r\n" + 
				"<td style=\"width: 134px; text-align: center; height: 13.1875px;\">\r\n" + 
				"<p><strong>Block Number&nbsp;</strong></p>\r\n" + 
				"</td>\r\n" + 
				"<td style=\"width: 192.667px; text-align: center; height: 13.1875px;\">\r\n" + 
				"<p><strong>&nbsp;Number of calls</strong></p>\r\n" + 
				"</td>\r\n" + 
				"</tr>\r\n" + 
				"";
		
		 file = new File("Smart Contracts/"+contractAddress+"/Info_Internal_Transactions/numberOfCallsFromContractPerBlock.md");
		 boolFileNumberCallFromContractByEmptyBlock = !file.exists() || file.length() == 0;
		
		 if(!boolFileNumberCallFromContractByEmptyBlock) {
			 
				for (int i = 0; i < ListInternalTransactionsNumberOfCallsPerBlock.size(); i++) {
					html += "<tr style=\"height: 13px;\">\r\n" + 
							"<td style=\"width: 134px; height: 13px; text-align: center;\">\r\n" + 
							"<p>"+ListInternalTransactionsNumberOfCallsPerBlock.get(i).split("=")[0]+"</p>\r\n" + 
							"</td>\r\n" + 
							"<td style=\"width: 192.667px; height: 13px; text-align: center;\">\r\n" + 
							"<p>"+ListInternalTransactionsNumberOfCallsPerBlock.get(i).split("=")[1]+"</p>\r\n" + 
							"</td>\r\n" + 
							"</tr>\r\n" + 
							"";
				}	
			 
		 }
		
		
		
		html += "</tbody>\r\n" + 
				"</table>\r\n" + 
				"</div>\r\n" + 
				"<div>\r\n" + 
				"";

		
		
		html += "<div>\r\n" + 
				"<h3><span style=\"color: #003366;\"><strong>3.2 The destination of the internal transaction per block</strong></span></h3>\r\n" + 
				"</div>\r\n" + 
				"";
		
		
		
		html += "<div>\r\n" + 
				"<table style=\"width: 495px; border-color: black; height: 196px;\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
				"<tbody>\r\n" + 
				"<tr style=\"height: 21.9167px;\">\r\n" + 
				"<td style=\"width: 143.333px; height: 21.9167px; text-align: center;\">\r\n" + 
				"<p><strong>Block Number</strong></p>\r\n" + 
				"</td>\r\n" + 
				"<td style=\"width: 346px; height: 21.9167px; text-align: center;\">\r\n" + 
				"<p><strong>Address</strong></p>\r\n" + 
				"</td>\r\n" + 
				"</tr>\r\n" + 
				"";
		
		
		SortedSet<String> keys2 = new TreeSet<>(hm_InternalTransactionsDestinationTransactionPerBlock.keySet());
		for (String key2 : keys2) { 
		   String value2 = hm_InternalTransactionsDestinationTransactionPerBlock.get(key2);
		   //System.out.println("1. "+key+" "+value);

		   html += "<tr style=\"height: 13px;\">\r\n" + 
					"<td style=\"width: 133.333px; height: 13px; text-align: center;\">\r\n" + 
					"<p>"+key2+"</p>\r\n" + 
					"</td>\r\n" + 
					"<td style=\"width: 353.333px; height: 13px;\">\r\n" + 
					"<p style=\"text-align: center;\">"+value2.replace("\n","<br><br><br><br>")+"</p>\r\n" + 
					"</td>\r\n" + 
					"</tr>\r\n";
		
		}

		
		html += "</tbody>\r\n" + 
				"</table>\r\n" + 
				"</div>\r\n" + 
				"";
		
		
		
		html += "<div>\r\n" + 
				"<h3><span style=\"color: #003366;\"><strong>3.3 Information about the blocks&nbsp;called by the contract</strong></span></h3>\r\n" + 
				"</div>\r\n" + 
				"";
		
		html += "<table style=\"width: 600px;\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
				"<tbody>\r\n" + 
				"<tr style=\"height: 36.5834px;\">\r\n" + 
				"<td style=\"width: 148px; height: 36.5834px; text-align: center;\">\r\n" + 
				"<p><strong>Block Number</strong></p>\r\n" + 
				"</td>\r\n" + 
				"<td style=\"width: 446.667px; height: 36.5834px; text-align: center;\">\r\n" + 
				"<p><strong>Informations</strong>&nbsp;</p>\r\n" + 
				"</td>\r\n" + 
				"</tr>\r\n" + 
				"";
		
		
		
		
		try {
			boolDirectory_InfoBlock_Contract_InternalTransactions_Empty=isDirEmpty(
					Paths.get("Smart Contracts/"+contractAddress+"/Info_Blocks_Contract_Internal_Transactions"));

			
			if(!boolDirectory_InfoBlock_Contract_InternalTransactions_Empty) {
				
				for (int i = 0; i < arrayInfoBlock_InternalTransactions.length; i++) {
					
					html += "<tr style=\"height: 49px;\">\r\n" + 
							"<td style=\"width: 134px; height: 49px; text-align: center;\">\r\n" + 
							"<p>"+arrayInfoBlock_InternalTransactions[i][0]+"</p>\r\n" + 
							"</td>\r\n" + 
							"<td style=\"width: 403px; height: 49px;\">\r\n" + 
							"<div style=\"text-align: center;\">\r\n" + 
							"<p style=\"text-align: left; padding-left: 30px;\">Gas Limit : "+arrayInfoBlock_InternalTransactions[i][1].split("\n")[0]+"</p>\r\n" + 
							"<p style=\"text-align: left; padding-left: 30px;\">Gaz Used : "+arrayInfoBlock_InternalTransactions[i][1].split("\n")[1]+"</p>\r\n" + 
							"<p style=\"text-align: left; padding-left: 30px;\">Miner : "+arrayInfoBlock_InternalTransactions[i][1].split("\n")[2]+"</p>\r\n" + 
							"<p style=\"text-align: left; padding-left: 30px;\">Date and time of creation : "+arrayInfoBlock_InternalTransactions[i][1].split("\n")[3]+"</p>\r\n" + 
							"<p style=\"text-align: left; padding-left: 30px;\">Total number of transactions in this block : "+arrayInfoBlock_InternalTransactions[i][1].split("\n")[4]+"</p>\r\n" + 
							"</div>\r\n" + 
							"</td>\r\n" + 
							"</tr>\r\n" + 
							"";
				}
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			//e.printStackTrace();
			}

		
		html +="</tbody>\r\n" + 
				"</table>\r\n" + 
				"";
		
		html += "</body>\r\n" + 
				"</html>\r\n" + 
				"";
		
		return html;
	}

	
	public static void createPdf(String html, String dest) throws IOException {
			    
		ConverterProperties properties = new ConverterProperties();
		    properties.setFontProvider(new DefaultFontProvider(true, true, true));
		    
		    HtmlConverter.convertToPdf(html, new FileOutputStream(dest),properties);
	}

		
	public static boolean isDirEmpty(final Path directory) throws IOException {
	    try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
	        return !dirStream.iterator().hasNext();
	    }
	}
}
