/**
 * @author Mélissa MAZROU
 * @author Redouane OTMANI
 * 
 */

package pfe_32_aid;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

import org.apache.commons.io.FileUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;


public class _Main {

	public static JFrame frmDetectVulnerabilyAttackStep1=null;
	
	public static JLabel Step1_Label =null;
	public static JLabel Step2_SubLabel=null;
	public static JTextField Step1_textField_StartBlock =null;
	public static JTextField Step1_textField_EndBlock=null;
	public static JTextField Step1_textField_InseretAddress=null;

	public static HashMap<String, BigInteger> Step1_hm_AddressesSmartContracts = new HashMap<String, BigInteger>();
	
	public static JComboBox Step1_comboBox=null;
	public static JLabel Step1_subLabel_StartBlock=null; 
	public static JLabel Step1_subLabel_EndBlock=null;
	public static JButton Step1_btnConfirm=null;
	
	public static BigInteger Step1_NumStartBloc=null;
	public static BigInteger Step1_NumEndBlock=null;
	
	static Web3j web3j=null;
	static BigInteger Step1_NumberOfLastMinedBlock=BigInteger.valueOf(0);
	
	public static JFrame Step1_WindowProgressBar=null;
	public static JPanel Step1_contentPaneWindow_ProgressBar=null;
	public static JProgressBar Step1_ProgressBar_Window_ProgressBar=null;
	public static JLabel Step1_LabelBlockNumberWindowProgressBar=null;
	
	public static JFrame Step1_WindowProgressBarContractAddress=null;
	public static JPanel Step1_contentPaneWindowProgressBarContractAddress=null;
	public static JProgressBar Step1_ProgressBarWindowProgressBarContractAddress=null;
	public static JLabel Step1_LabelBlocNumberWindowProgressBarContractAddress=null;
	
	public static JButton btnInsertContractAddressOrRange=null;	
	
	public static String YesNoArray[] = {"Yes", "No"};
	
	
	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {

		_Main frame2_Application_DetectionVulnerabilyAttack_Step1 =
				new _Main();

		frame2_Application_DetectionVulnerabilyAttack_Step1.frmDetectVulnerabilyAttackStep1.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	
	public _Main() {
	
		initialiseAllVariables();
		initialize();

	}


	
	/**
	 * Initialize the contents of the frame.
	 */

	
	public static void initialiseAllVariables() {
		Step1_Label =null;
		Step2_SubLabel=null;
		Step1_textField_StartBlock =null;
		Step1_textField_EndBlock=null;
		Step1_textField_InseretAddress=null;

		Step1_hm_AddressesSmartContracts = new HashMap<String, BigInteger>();
		
		Step1_comboBox=null;
		Step1_subLabel_StartBlock=null; 
		Step1_subLabel_EndBlock=null;
		Step1_btnConfirm=null;
		
		Step1_NumStartBloc=null;
		Step1_NumEndBlock=null;
		
		web3j=null;
		Step1_NumberOfLastMinedBlock=BigInteger.valueOf(0);
		
		Step1_WindowProgressBar=null;
		Step1_contentPaneWindow_ProgressBar=null;
		Step1_ProgressBar_Window_ProgressBar=null;
		Step1_LabelBlockNumberWindowProgressBar=null;
		
		Step1_WindowProgressBarContractAddress=null;
		Step1_contentPaneWindowProgressBarContractAddress=null;
		Step1_ProgressBarWindowProgressBarContractAddress=null;
		Step1_LabelBlocNumberWindowProgressBarContractAddress=null;
		
		btnInsertContractAddressOrRange=null;	
		
	}

	
	private void initialize() {
		frmDetectVulnerabilyAttackStep1 = new JFrame();
		frmDetectVulnerabilyAttackStep1.getContentPane().setBackground(new Color(0, 51, 102));
		frmDetectVulnerabilyAttackStep1.setTitle("Inserting inputs");
		frmDetectVulnerabilyAttackStep1.setBounds(0, 0, 1020, 600);
		frmDetectVulnerabilyAttackStep1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDetectVulnerabilyAttackStep1.setResizable(false);
		frmDetectVulnerabilyAttackStep1.setLocationRelativeTo(null);
		frmDetectVulnerabilyAttackStep1.getContentPane().setLayout(null);

		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("merriweather", Font.BOLD, 12)));
		deleteDirectory("Smart Contracts");
		
		
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBounds(10, 629, 440, 2);
		frmDetectVulnerabilyAttackStep1.getContentPane().add(separator_1);
		
		
		
/************************************************************************************************************************/
/**-----------------------------------------Step 1-------------------------------------------------------------------**/

		
		Step1_Label = new JLabel("<html>\r\n<body>\r\nChoose the type of input data to insert\r\n</body>\r\n</html>\r\n");
		Step1_Label.setForeground(new Color(46, 139, 87));
		Step1_Label.setHorizontalAlignment(SwingConstants.CENTER);
		Step1_Label.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 35));
		Step1_Label.setBounds(0, 45, 1016, 57);
		frmDetectVulnerabilyAttackStep1.getContentPane().add(Step1_Label);

		Step2_SubLabel = new JLabel("<html>\r\n<body>\r\nPlease choose between inserting a smart contract address or a block range \r\n</body>\r\n</html>\r\n");
		Step2_SubLabel.setHorizontalAlignment(SwingConstants.CENTER);
		Step2_SubLabel.setForeground(new Color(192, 192, 192));
		Step2_SubLabel.setFont(new Font("Merriweather Light", Font.PLAIN, 20));
		Step2_SubLabel.setBounds(0, 122, 1016, 57);
		frmDetectVulnerabilyAttackStep1.getContentPane().add(Step2_SubLabel);
		

		
		Step1_comboBox = new JComboBox();
		Step1_comboBox.setBackground(new Color(245, 245, 245));
		Step1_comboBox.setFont(new Font("Merriweather Light", Font.PLAIN, 20));
		Step1_comboBox.setBorder(null);
		Step1_comboBox.setModel(new DefaultComboBoxModel(new String[] {"...", "Insert an address of a Smart Contract", "Insert a range of blocks"}));
		
		Step1_comboBox.setBounds(289, 208, 438, 35);
		
		
		frmDetectVulnerabilyAttackStep1.getContentPane().add(Step1_comboBox);
		
		/**Selection des blocs***/
		int defaultDismissTimeout = ToolTipManager.sharedInstance().getDismissDelay();
		
		//
		Step1_subLabel_StartBlock = new JLabel("Start Block");
		Step1_subLabel_StartBlock.setForeground(new Color(192, 192, 192));
		Step1_subLabel_StartBlock.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		Step1_subLabel_StartBlock.setBounds(289, 273, 128, 35);
		Step1_subLabel_StartBlock.setVisible(false);
		frmDetectVulnerabilyAttackStep1.getContentPane().add(Step1_subLabel_StartBlock);
		
		//
		Step1_textField_StartBlock = new JTextField();
		Step1_textField_StartBlock.setFont(new Font("Merriweather Light", Font.PLAIN, 20));
		Step1_textField_StartBlock.setBackground(new Color(245, 245, 245));
		Step1_textField_StartBlock.setBounds(458, 273, 269, 35);
		Step1_textField_StartBlock.setToolTipText(
				"<html>\r\n" + 
				"<body style=\"font-family:merriweather;\">\r\n" + 
				"The very first contract was created in the block number 48643"+
				"</body>\r\n" + 
				"</html>"
				);
		Step1_textField_StartBlock.addMouseListener(new MouseAdapter() {
			  public void mouseEntered(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(8000);}
			  public void mouseExited(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);}
			});
		
		Step1_textField_StartBlock.setVisible(false);
		frmDetectVulnerabilyAttackStep1.getContentPane().add(Step1_textField_StartBlock);
		Step1_textField_StartBlock.setColumns(10);
		
		//
		Step1_subLabel_EndBlock = new JLabel("End Block");
		Step1_subLabel_EndBlock.setForeground(new Color(192, 192, 192));
		Step1_subLabel_EndBlock.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		Step1_subLabel_EndBlock.setBounds(289, 343, 128, 35);
		Step1_subLabel_EndBlock.setVisible(false);
		frmDetectVulnerabilyAttackStep1.getContentPane().add(Step1_subLabel_EndBlock);
		
		//
		Step1_textField_EndBlock = new JTextField();
		Step1_textField_EndBlock.setFont(new Font("Merriweather Light", Font.PLAIN, 20));
		Step1_textField_EndBlock.setBackground(new Color(245, 245, 245));
		Step1_textField_EndBlock.setColumns(10);
		Step1_textField_EndBlock.addMouseListener(new MouseAdapter() {
			  public void mouseEntered(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(8000);}
			  public void mouseExited(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);}
			});
		Step1_textField_EndBlock.setBounds(458, 343, 269, 35);
		Step1_textField_EndBlock.setVisible(false);
		frmDetectVulnerabilyAttackStep1.getContentPane().add(Step1_textField_EndBlock);
		
	
		
		
		/**Insert of a smart contract address***/
		Step1_textField_InseretAddress = new JTextField();
		Step1_textField_InseretAddress.setBackground(new Color(245, 245, 245));
		Step1_textField_InseretAddress.setFont(new Font("Merriweather Light", Font.PLAIN, 20));
		Step1_textField_InseretAddress.setBounds(289, 273, 438, 35);
		Step1_textField_InseretAddress.setVisible(false);
		frmDetectVulnerabilyAttackStep1.getContentPane().add(Step1_textField_InseretAddress);
		Step1_textField_InseretAddress.setColumns(10);

		
		
		
/***************************************Confirm button**********************************************************************/
		Step1_btnConfirm = new JButton("Continue");
		Step1_btnConfirm.setBackground(new Color(245, 245, 245));
		Step1_btnConfirm.setForeground(new Color(0, 0, 0));


		
		Step1_btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					Step1_btnConfirm.setEnabled(false);
				
				if(Step1_comboBox.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(null, "Please select one of the options from the drop-down list", "Attention", 
							JOptionPane.WARNING_MESSAGE);
					
				}

				
				if(Step1_comboBox.getSelectedIndex()==1) {
					
					if(Step1_textField_InseretAddress.getText().isBlank()) {
						JOptionPane.showMessageDialog(null, "Please insert a smart contract address", "Attention", 
								JOptionPane.WARNING_MESSAGE);
					}
					else {
						//We launch a thread 
						Thread_Step1_Insert_Address thread_Etape1_Insertion_Adresse = new Thread_Step1_Insert_Address();
						thread_Etape1_Insertion_Adresse.start();
					}
				}

				
				if(Step1_comboBox.getSelectedIndex()==2) {

					try {
						Step1_NumStartBloc = BigInteger.valueOf(Integer.parseInt(Step1_textField_StartBlock.getText()));
						Step1_NumEndBlock	 = BigInteger.valueOf(Integer.parseInt(Step1_textField_EndBlock.getText()));

						if(Step1_NumStartBloc.compareTo(BigInteger.valueOf(0))<0) {throw new Exception();}
						if(Step1_NumEndBlock.compareTo(BigInteger.valueOf(0))<0) {throw new Exception();}
						if((Step1_NumStartBloc.compareTo(Step1_NumEndBlock)>0)) {throw new Exception();}

						
						if((Step1_NumStartBloc.compareTo(BigInteger.valueOf(48643))<0) 
								|| 
						   (Step1_NumEndBlock.compareTo(BigInteger.valueOf(48643))<0))
						{
							JOptionPane.showMessageDialog(null, "The very first contract was created on 07 August 2015 in block 48643.\n"
															  + "Thus, the blocks preceding this one do not contain any contracts\n "
															  + "Thus, the blocks preceding this one do not contain any contracts",
									"Attention",JOptionPane.WARNING_MESSAGE);
						}
						
					else {
					
						if((Step1_NumStartBloc.compareTo(Step1_NumberOfLastMinedBlock)>0) 
								|| 
								   (Step1_NumEndBlock.compareTo(Step1_NumberOfLastMinedBlock)>0)) {

							JOptionPane.showMessageDialog(null, "The last block mined a few moments ago\n "
									+ "is the block number "+ Step1_NumberOfLastMinedBlock
									,"Information",JOptionPane.INFORMATION_MESSAGE);
						}
						
						else {
																				
							Thread_Step1_Blocs_Range thread_Etape1_Intervalle_Blocs = new Thread_Step1_Blocs_Range();
							thread_Etape1_Intervalle_Blocs.start();
							}

						}
						
						
						
					}catch(Exception excpetion) {
						JOptionPane.showMessageDialog(null, 
								"Please insert a block range or check your internet connection."
								, "Attention", 
								JOptionPane.WARNING_MESSAGE);
					}
					
				}//end if(Step1_comboBox.getSelectedIndex==2)
				
				
				
				Step1_btnConfirm.setEnabled(true);	
			
			}//end actionPerformed of Confirm Button
		});
		
		
		Step1_btnConfirm.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		Step1_btnConfirm.setBounds(778, 455, 180, 35);
		frmDetectVulnerabilyAttackStep1.getContentPane().add(Step1_btnConfirm);

		
		
		
		Step1_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(Step1_comboBox.getSelectedIndex()==0) {
					Step1_subLabel_StartBlock.setVisible(false);Step1_subLabel_EndBlock.setVisible(false);
					Step1_textField_StartBlock.setVisible(false);Step1_textField_EndBlock.setVisible(false);
					//-----------------
					Step1_textField_InseretAddress.setVisible(false);
					//-----------------
				}

				if(Step1_comboBox.getSelectedIndex()==1) {
					Step1_subLabel_StartBlock.setVisible(false);
					Step1_subLabel_EndBlock.setVisible(false);
					Step1_textField_StartBlock.setVisible(false);
					Step1_textField_EndBlock.setVisible(false);
					//-----------------
					Step1_textField_InseretAddress.setVisible(true);
					//-----------------
					//
					
				}
				
				if(Step1_comboBox.getSelectedIndex()==2) {
					Step1_subLabel_StartBlock.setVisible(true);
					Step1_subLabel_EndBlock.setVisible(true);
					Step1_textField_StartBlock.setVisible(true);
					Step1_textField_EndBlock.setVisible(true);
					//-----------------
					Step1_textField_InseretAddress.setVisible(false);
					//-----------------
					connectionToEthereum();
					
				}
				
			}
		});

		
		JButton btnQuitter = new JButton("Quit");
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(JOptionPane.showOptionDialog(null, "Are you sure you want to leave SCVADetection?", 
						"Disconnect",
						0, JOptionPane.QUESTION_MESSAGE, null, YesNoArray, null) == JOptionPane.OK_OPTION) {
					
					//frmDetectVulnerabilyAttackStep1.dispose();
					System.exit(0);
				}
			}
		});
		btnQuitter.setForeground(new Color(0, 0, 0));
		btnQuitter.setBackground(new Color(245, 245, 245));

		btnQuitter.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		btnQuitter.setBounds(59, 455, 180, 35);
		frmDetectVulnerabilyAttackStep1.getContentPane().add(btnQuitter);
		
		
		
	}//end void initialize()

	
	
/********************************************************************************************************************************/	
/********************************************************************************************************************************/	
/********************************************************************************************************************************/
/********************************************************************************************************************************/	
/**---------------------------------------------Step 01------------------------------------------------------------------------*/
/**--------------------------------------Block range--------------------------------------------------------------------*/

	public void connectionToEthereum() {
		
		JOptionPane.showMessageDialog(null, 
				"Connecting to Ethereum... Please wait a few seconds"
				,"Connecting to Ethereum",
				JOptionPane.INFORMATION_MESSAGE);

		web3j = Web3j.build(new HttpService("https://mainnet.infura.io/v3/a9d86db249d148cfab980c5fe68810d7"));
		
		
		  //We return the number of the very last block
		  EthBlockNumber blockNumber;
		try {
				
				blockNumber = web3j.ethBlockNumber().send();
				Step1_NumberOfLastMinedBlock = blockNumber.getBlockNumber();
				
			
			Step1_textField_EndBlock.setToolTipText(
					"<html>\r\n" + 
					"<body style=\"font-family:merriweather;\">\r\n" + 
					"The last mined block is the block number "+Step1_NumberOfLastMinedBlock+
					"</body>\r\n" + 
					"</html>");
	
			JOptionPane.showMessageDialog(null, 
					"Successful connection to Ethereum"
					,"Connecting to Ethereum",JOptionPane.INFORMATION_MESSAGE);

		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, 
					"Failed to connect to Ethereum"
					,"Connection error",JOptionPane.ERROR_MESSAGE);
		}
		
			
	}

	
	public class Thread_Step1_Blocs_Range extends Thread{
		public void run() {
			Step1_btnConfirm.setEnabled(false);
			InitGettingSmartContractAddress();
		}
	}

	
	public static void InitGettingSmartContractAddress() {

		Boolean boolBlockContainsContract =false; 
		Boolean boolBlockRangeContainsContract=false;

		
		try {
		
		  EthBlock ethBlock = null;
		  List<TransactionResult> TransactionsList = null;
		  TransactionResult tr ;
		  String opCode=null;
		  
		  //We create the "Smart Contract" directory the one that will contain our smart contracts

		  //It is the user who will choose the range of the blocks
		  BigInteger BlocDebut = Step1_NumStartBloc;
		  BigInteger BlocFin = Step1_NumEndBlock;

		  CreationProgressBar();
		  
		  EthGetTransactionReceipt ethGetTransactionReceipt=null;
		  Optional<TransactionReceipt> trR=null;
		  //A loop to go through the range of blocks
		    for (BigInteger i = BlocDebut; i.compareTo(BlocFin) <=0 ;i=i.add(BigInteger.ONE)) 
		    {

		    	//We initialize our boolean
		    	boolBlockContainsContract = false;

		    	
		    	//We launch our progress bar
		    	Step1_LabelBlockNumberWindowProgressBar.setText("Block N°"+i);
		    	

		    	//Return information about the block using its number
					ethBlock= web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(i), false).send();
		          
		          //Return all transactions of this block
		          TransactionsList = ethBlock.getBlock().getTransactions();   
		            
		          //Now we go through the transactions of each block to see if they are contract creation transactions.
		          for (int j = 0; j < TransactionsList.size(); j++) {
		        	  tr= TransactionsList.get(j);
		 		    ethGetTransactionReceipt = web3j.ethGetTransactionReceipt((String) tr.get()).send();
				    trR = ethGetTransactionReceipt.getTransactionReceipt();
				     
				    if (trR.get().getContractAddress() != null) {

							Step1_hm_AddressesSmartContracts.put(trR.get().getContractAddress(), i);
							
				        boolBlockRangeContainsContract = true;
				        boolBlockContainsContract = true;
				    }
				    //System.out.print(j+", ");
				}

		if(!boolBlockContainsContract) {
			JOptionPane.showMessageDialog(null, "No contracts have been created in the block "+i,
					"Information", JOptionPane.INFORMATION_MESSAGE);
		}
		
		          
		        }//End loop that goes through the blocks

		    Step1_WindowProgressBar.dispose();
		    
		    if(!boolBlockRangeContainsContract) {
		    JOptionPane.showMessageDialog(null, "No contracts have been created in the selected block range."
		    		+ "\nPlease enter another block range",
		    		"Attention",JOptionPane.WARNING_MESSAGE);
		    
		   
		    Step1_btnConfirm.setEnabled(true);
		    	}
		    else {
		    
			JOptionPane.showMessageDialog(null, 
					"End of the verification of the existence of smart contracts in the chosen block range"
					,"Information",JOptionPane.INFORMATION_MESSAGE);
			

			JOptionPane.showMessageDialog(null, "You can continue"
					,"Next",JOptionPane.INFORMATION_MESSAGE);

			frmDetectVulnerabilyAttackStep1.dispose();
			initStep2_Part1();
			
		    }
		    
		} catch(IOException ex) {
			JOptionPane.showMessageDialog(null, "Error while sending json-rpc requests"
					,"Error",JOptionPane.ERROR_MESSAGE);
			frmDetectVulnerabilyAttackStep1.dispose();
			throw new RuntimeException("Error while sending json-rpc requests", ex);
			
		}

	}//end InitiGettingSmartContractAddress
	
	
	public static void CreationProgressBar() {
		
		Step1_WindowProgressBar = new JFrame();
		
		Step1_WindowProgressBar.setTitle("Verification..");
		Step1_WindowProgressBar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Step1_WindowProgressBar.setBounds(100, 100, 400, 100);
		Step1_WindowProgressBar.setResizable(false);
		Step1_WindowProgressBar.setLocationRelativeTo(null);

		Step1_contentPaneWindow_ProgressBar = new JPanel();
		Step1_contentPaneWindow_ProgressBar.setBorder(new EmptyBorder(5, 5, 5, 5));
		Step1_WindowProgressBar.setContentPane(Step1_contentPaneWindow_ProgressBar);
		Step1_contentPaneWindow_ProgressBar.setLayout(null);

		
		Step1_ProgressBar_Window_ProgressBar = new JProgressBar();
		Step1_ProgressBar_Window_ProgressBar.setForeground(Color.GREEN);
		Step1_ProgressBar_Window_ProgressBar.setBounds(29, 21, 171, 27);
		Step1_contentPaneWindow_ProgressBar.add(Step1_ProgressBar_Window_ProgressBar);

		Step1_ProgressBar_Window_ProgressBar.setIndeterminate(true);

		Step1_LabelBlockNumberWindowProgressBar = new JLabel("Block N\u00B0 ...");//
		Step1_LabelBlockNumberWindowProgressBar.setFont(new Font("merriweather", Font.BOLD, 12));
		Step1_LabelBlockNumberWindowProgressBar.setBounds(210, 21, 116, 27);
		Step1_contentPaneWindow_ProgressBar.add(Step1_LabelBlockNumberWindowProgressBar);
	
		Step1_WindowProgressBar.setVisible(true);
	}
	

	public static void deleteDirectory(String nom) {
		try {
				FileUtils.deleteDirectory(new File(nom));
	  	  } catch (IOException e) {
	  		JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
	  	  }catch (Exception e) {
		  	JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
	  	  }
	}
	
/**---------------------------------------------Step 01------------------------------------------------------------------------*/
/**--------------------------------------Insert a smart contract address-----------------------------------------------*/

	
	
	public class Thread_Step1_Insert_Address extends Thread{
		public void run() {
			Step1_btnConfirm.setEnabled(false);
		      try {
		    	  verifyAddressValidity(Step1_textField_InseretAddress.getText().toLowerCase());
		      } catch (Exception e) {
		    	  JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
		      }
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static void verifyAddressValidity(String contractAddress) throws IOException {

		BufferedReader br = null;
	    String content;
	    
	    creatingProgressBarAddressInsertion();

	    try {
	           URL url = new URL("https://etherscan.io/api?module=opcode&action=getopcode&address="+contractAddress);
	           br = new BufferedReader(new InputStreamReader(url.openStream()));
	           String line;
	           StringBuilder sb = new StringBuilder();
	           while ((line = br.readLine()) != null) {sb.append(line);sb.append(System.lineSeparator());}
	           
	           content = sb.toString();
	           //System.out.println(content);
	           //System.out.println("First one");
	       
	       } finally {if (br != null) {br.close();}}
		
		
		if(content.contains("Unable to decode")) {
			
			Step1_WindowProgressBarContractAddress.dispose();
			
			JOptionPane.showMessageDialog(null, "The address entered is not valid", "Error", 
					JOptionPane.ERROR_MESSAGE);
			
			Step1_btnConfirm.setEnabled(true);
		
		}else {
			
			Step1_btnConfirm.setEnabled(false);

			//Now we retrieve the block in which the contract was created
			//Then, we store it on our HashMap
			Step1_hm_AddressesSmartContracts.put(contractAddress, getBlockNumberWhereContractWasCreated_Step1(contractAddress));
			
			//On retrieve the last mined bloc 
			Step1_NumberOfLastMinedBlock = getLastMinedBlock_Step1();
			
			Step1_WindowProgressBarContractAddress.dispose();
			
			JOptionPane.showMessageDialog(null, "End of the inserted address contract verification"
					,"Information",JOptionPane.INFORMATION_MESSAGE);

			
			JOptionPane.showMessageDialog(null, "You can continue"
					,"Next",JOptionPane.INFORMATION_MESSAGE);

			
			//We start the Step2 
			frmDetectVulnerabilyAttackStep1.dispose();
			initStep2_Part1();
			
		}//end else
	
	}
	
	
	public static void creatingProgressBarAddressInsertion() {
		
		Step1_WindowProgressBarContractAddress = new JFrame();
		
		Step1_WindowProgressBarContractAddress.setTitle("Verification..");
		Step1_WindowProgressBarContractAddress.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Step1_WindowProgressBarContractAddress.setBounds(100, 100, 250, 100);
		Step1_WindowProgressBarContractAddress.setResizable(false);
		Step1_WindowProgressBarContractAddress.setLocationRelativeTo(null);

		Step1_contentPaneWindowProgressBarContractAddress = new JPanel();
		Step1_contentPaneWindowProgressBarContractAddress.setBorder(new EmptyBorder(5, 5, 5, 5));
		Step1_WindowProgressBarContractAddress.setContentPane(Step1_contentPaneWindowProgressBarContractAddress);
		Step1_contentPaneWindowProgressBarContractAddress.setLayout(null);

		
		Step1_ProgressBarWindowProgressBarContractAddress = new JProgressBar();
		Step1_ProgressBarWindowProgressBarContractAddress.setForeground(Color.GREEN);
		Step1_ProgressBarWindowProgressBarContractAddress.setBounds(20, 10, 170, 27);
		Step1_contentPaneWindowProgressBarContractAddress.add(Step1_ProgressBarWindowProgressBarContractAddress);

		Step1_ProgressBarWindowProgressBarContractAddress.setIndeterminate(true);

		
		Step1_WindowProgressBarContractAddress.setVisible(true);

	}

	
	
	public static BigInteger getBlockNumberWhereContractWasCreated_Step1(String contractAddress) {
		

		try {
			
			BufferedReader brGetTransactionHash = null;
		    String contentGetTransactionHash=null;
		    
	        URL urlGetTransactionHash= new URL("https://etherscan.io/address/"+
						contractAddress);
		
	           brGetTransactionHash = new BufferedReader(new InputStreamReader(urlGetTransactionHash.openStream()));
	           
	           String lineGetTransactionHash;
	           StringBuilder sbGetTransactionHash = new StringBuilder();
	           
	           while ((lineGetTransactionHash = brGetTransactionHash.readLine()) != null) {sbGetTransactionHash.append(lineGetTransactionHash);sbGetTransactionHash.append(System.lineSeparator());}
	           
	           contentGetTransactionHash = sbGetTransactionHash.toString();

	           brGetTransactionHash.close();
	          
	          
	           //We get the hash of the transaction that created our contract with a regular expression
	   //		Pattern patternGetTransactionHash = Pattern.compile("at txn <a href='/tx/"
		//			+ "(.*)' data-toggle='tooltip' title='Creator Txn Hash' class='hash-tag text-truncate'>(.*)</a>");
			
	   		Pattern patternGetTransactionHash = Pattern.compile("title='Creator Txn Hash'>(.*)</a>");
	   		
			//System.out.println(contentGetTransactionHash);
	   		   		
			Matcher matcherGetTransactionHash = patternGetTransactionHash.matcher(contentGetTransactionHash);
			
			
			String transactionHash=null;
			
			while(matcherGetTransactionHash.find()) {
				transactionHash= matcherGetTransactionHash.group(1);
			}	           
			
			 //System.out.println(transactionHash);
			/************************************************************************************/			
			  
			BufferedReader brGetBlockNumber = null;
		    String contentGetBlockNumber=null;

	        
	        URL urlGetBlockNumber= new URL("https://api.etherscan.io/api?module=proxy"
	        		+ "&action=eth_getTransactionByHash"
	        		+ "&txhash="+transactionHash
	        		+ "&apikey=PUT_HERE_YOUR_API_KEY");
			
	        //System.out.println(urlGetBlockNumber);
	        
	           brGetBlockNumber = new BufferedReader(new InputStreamReader(urlGetBlockNumber.openStream()));
	           
	           String lineGetBlockNumber;
	           StringBuilder sbGetBlockNumber = new StringBuilder();
	           
	          
	           
	           while ((lineGetBlockNumber = brGetBlockNumber.readLine()) != null) {
	        	   sbGetBlockNumber.append(lineGetBlockNumber);
	        	   sbGetBlockNumber.append(System.lineSeparator());}
	           
	           
	           contentGetBlockNumber = sbGetBlockNumber.toString();
	           brGetBlockNumber.close();
			
	          // System.out.println(contentGetBlockNumber);
	           
		   		Pattern patternGetBlockNumber = Pattern.compile("\"blockNumber\":\"0x(.*)\",\"from\"");
				
				
				Matcher matcherGetBlockNumber = patternGetBlockNumber.matcher(contentGetBlockNumber);
				
				
				String blockNumberHexa=null;
			//	System.out.println("before while");
				
				while(matcherGetBlockNumber.find()) {
					blockNumberHexa= matcherGetBlockNumber.group(1);
				}	           

			//	System.out.println(blockNumberHexa);
				
				BigInteger blockNumberDecimal = new BigInteger(blockNumberHexa, 16);

				
				return blockNumberDecimal;	           
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			
		}
		
		return null;
		
	}


	public static BigInteger getLastMinedBlock_Step1() {
		try {
			
			BufferedReader brGetBlockNumber = null;
		    String contentGetBlockNumber=null;

	        
	        URL urlGetBlockNumber= new URL("https://api.etherscan.io/api?module=proxy"
	        		+ "&action=eth_blockNumber"
	        		+ "&apikey=PUT_HERE_YOUR_API_KEY");
			
	        
	           brGetBlockNumber = new BufferedReader(new InputStreamReader(urlGetBlockNumber.openStream()));
	           	           
	           
	           String lineGetBlockNumber;
	           StringBuilder sbGetBlockNumber = new StringBuilder();
	           
	           
	           while ((lineGetBlockNumber = brGetBlockNumber.readLine()) != null) {
	        	   sbGetBlockNumber.append(lineGetBlockNumber);
	        	   sbGetBlockNumber.append(System.lineSeparator());}
	           
	           contentGetBlockNumber = sbGetBlockNumber.toString();
	           brGetBlockNumber.close();
			

		   		Pattern patternGetBlockNumber = Pattern.compile("\"result\":\"0x(.*)\"}");
				
				
				Matcher matcherGetBlockNumber = patternGetBlockNumber.matcher(contentGetBlockNumber);
				
				
				String blockNumberHexa=null;
				
				while(matcherGetBlockNumber.find()) {
					blockNumberHexa= matcherGetBlockNumber.group(1);
				}	           
	           
				BigInteger blockNumberDecimal = new BigInteger(blockNumberHexa, 16);

	           
				return blockNumberDecimal;	           
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
		}
		
		return null;
		
	}
		


	
/********************************************************************************************************************************/	
/********************************************************************************************************************************/	
/********************************************************************************************************************************/
/********************************************************************************************************************************/	
/***********************************************Step 02*************************************************************************/

	public static void initStep2_Part1() {
		Frame2_Application_DetectionVulnerabilityAttack_Step2 frame2_Application_DetectionVulnerabilityAttack_Step2 =
				new Frame2_Application_DetectionVulnerabilityAttack_Step2(
						Step1_hm_AddressesSmartContracts,
						Step1_NumberOfLastMinedBlock
						);
		frame2_Application_DetectionVulnerabilityAttack_Step2.frmDetectVulnerabilityAttackStep2.setVisible(true);
		
	}

	
}
