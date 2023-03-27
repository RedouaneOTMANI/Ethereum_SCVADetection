/**
 * @author Mélissa MAZROU
 * @author Redouane OTMANI
 * 
 */
package pfe_32_aid;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

import org.apache.commons.io.FileUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.http.HttpService;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Frame;

public class Frame3_DetectionAidModules extends JFrame {

	
	public static Web3j web3j;
	
	//This information we have to get from the previous class
	public static String contractAddress=null;
	public static BigInteger contractCreationBlock=null;
	public static BigInteger startAnalysisBlock=null;
	public static BigInteger endAnalysisBlock=null;

	//
	public static List<BigInteger> list_NumBlocks_External_Transactions=null;
	public static List<BigInteger> list_NumBlocks_Internal_Transactions=null;

	public static HashMap<BigInteger, Integer> hm_numberCallsToOurContractPerBlock = new HashMap<BigInteger, Integer>();
	
	private static JPanel contentPane;

	public static JLabel lblDetectionAidModules=null;
	
	public static JButton btnGenerateInfo_Creation_Block=null;
	
	public static JButton btnInfo_Blocks_Contract_External_Transactions=null;
	
	public static JButton btnInfo_Blocks_Contract_Internal_Transactions=null;
	
	public static JButton btnInfo_External_Transactions=null;
	
	public static JButton btnInfo_Internal_Transactions=null;
	
	public static JButton btnBytecode_OPCode=null;
	
	public static JButton btnSourceCodeContract=null;
	
	public static JButton btnReverseContract=null;
	
	public static JButton btnConfirmStep3=null;
	
	
	public static JFrame Step3_WindowProgressBarModulesGeneration=null;
	public static JPanel Step3_contentPaneWindowProgressBarModulesGeneration=null;
	public static JProgressBar Step3_progressBarWindow_ProgressBarModulesGeneration=null;

	public static String YesNoArray[] = {"Yes", "No"};
	
	
	public static HashMap<String, BigInteger> Step3_hm_AddressesSmartContracts = new HashMap<String, BigInteger>();
	public static BigInteger Step3_NumberOfLastMinedBlock = BigInteger.valueOf(0);

	
	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	
	public static void initializeAllVariables() {
		web3j=null;
		
		//This information we have to get from the previous class
		contractAddress=null;
		contractCreationBlock=null;
		startAnalysisBlock=null;
		endAnalysisBlock=null;

		list_NumBlocks_External_Transactions=null;
		list_NumBlocks_Internal_Transactions=null;

		hm_numberCallsToOurContractPerBlock = new HashMap<BigInteger, Integer>();
		
		contentPane=null;

		lblDetectionAidModules=null;
		
		btnGenerateInfo_Creation_Block=null;
		
		btnInfo_Blocks_Contract_External_Transactions=null;
		
		btnInfo_Blocks_Contract_Internal_Transactions=null;
		
		btnInfo_External_Transactions=null;
		
		btnInfo_Internal_Transactions=null;
		
		btnBytecode_OPCode=null;
		
		btnSourceCodeContract=null;
		
		btnReverseContract=null;
		
		btnConfirmStep3=null;
		
		Step3_WindowProgressBarModulesGeneration=null;
		Step3_contentPaneWindowProgressBarModulesGeneration=null;
		Step3_progressBarWindow_ProgressBarModulesGeneration=null;

		Step3_hm_AddressesSmartContracts = new HashMap<String, BigInteger>();
		Step3_NumberOfLastMinedBlock = BigInteger.valueOf(0);
		
		
	}

	
	public Frame3_DetectionAidModules(String contractAddressConstructor, 
			BigInteger blockCreationContractConstructor, 
			BigInteger startAnalysisBlockConstructor, 
			BigInteger endAnalysisBlockConstructor,
			HashMap<String, BigInteger> hm_AdressesSmartContracts,
			BigInteger numberOfLastMinedBlock
			) {

		initializeAllVariables();
		
		this.contractAddress = contractAddressConstructor;
		this.contractCreationBlock = blockCreationContractConstructor;
		this.startAnalysisBlock = startAnalysisBlockConstructor;
		this.endAnalysisBlock = endAnalysisBlockConstructor;

		Step3_hm_AddressesSmartContracts = hm_AdressesSmartContracts;
		Step3_NumberOfLastMinedBlock= numberOfLastMinedBlock;

UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("merriweather", Font.BOLD, 12)));
		

		setTitle("DAM Generating");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1020, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 51, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblDetectionAidModules = new JLabel("Generate Detection Aid Modules");
		
		lblDetectionAidModules.setForeground(new Color(46, 139, 87));
		lblDetectionAidModules.setHorizontalAlignment(SwingConstants.CENTER);
		lblDetectionAidModules.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 35));
		lblDetectionAidModules.setBounds(0, 31, 1016, 39);

		contentPane.add(lblDetectionAidModules);
		

		//******************************************************
		int defaultDismissTimeout = ToolTipManager.sharedInstance().getDismissDelay();
		
		btnGenerateInfo_Creation_Block = new JButton("Generate Info_Creation_Block");

		btnGenerateInfo_Creation_Block.setToolTipText("<html>\r\n<body style=\"width:400;font-family:merriweather;\">\r\nThis module contains information on the fields of the block in which the contract was created\r\n</body>\r\n</html>");

		btnGenerateInfo_Creation_Block.addMouseListener(new MouseAdapter() {
			  public void mouseEntered(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(10000);}
			  public void mouseExited(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);}
			});
		

		
		btnGenerateInfo_Creation_Block.setBackground(new Color(245, 245, 245));
		btnGenerateInfo_Creation_Block.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				Thread_Step3_ActionPerformed_btnGenerateInfo_Creation_Block thread_Step3_ActionPerformed_btnGenerateInfo_Creation_Block=
						new Thread_Step3_ActionPerformed_btnGenerateInfo_Creation_Block();
				
				thread_Step3_ActionPerformed_btnGenerateInfo_Creation_Block.start();
				
			}
		});
		btnGenerateInfo_Creation_Block.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		btnGenerateInfo_Creation_Block.setBounds(289, 100, 423, 35);
		contentPane.add(btnGenerateInfo_Creation_Block);
		
		btnInfo_Blocks_Contract_External_Transactions = new JButton("Generate Info_Blocks_Contract_External_Transactions");
		btnInfo_Blocks_Contract_External_Transactions.setToolTipText("<html>\r\n<body style=\"width:400;font-family:merriweather;\">\r\nThis module contains information on the fields of the blocks in which the contract has been in communication (Callee in an external transaction) in the selected analysis block interval\r\n</body>\r\n</html>");
		btnInfo_Blocks_Contract_External_Transactions.addMouseListener(new MouseAdapter() {
			  public void mouseEntered(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(10000);}
			  public void mouseExited(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);}
			});

		
		btnInfo_Blocks_Contract_External_Transactions.setBackground(new Color(245, 245, 245));
		btnInfo_Blocks_Contract_External_Transactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Thread_Step3_ActionPerformed_btnInfo_Blocks_Contract_External_Transactions thread_Step3_ActionPerformed_btnInfo_Blocks_Contract_External_Transactions =
						new Thread_Step3_ActionPerformed_btnInfo_Blocks_Contract_External_Transactions();
				
				thread_Step3_ActionPerformed_btnInfo_Blocks_Contract_External_Transactions.start();				
				
				
			}
		});
		
		btnInfo_Blocks_Contract_External_Transactions.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		btnInfo_Blocks_Contract_External_Transactions.setBounds(289, 192, 423, 35);
		contentPane.add(btnInfo_Blocks_Contract_External_Transactions);

		disableField_info_Blocks_Contract_External_Transactions();
		
		btnInfo_Blocks_Contract_Internal_Transactions = new JButton("Generate Info_Blocks_Contract_Internal_Transactions");
		btnInfo_Blocks_Contract_Internal_Transactions.setToolTipText("<html>\r\n<body style=\"width:400;font-family:merriweather;\">\r\nThis module contains information on the fields of the blocks in which the contract has been in communication (Caller in an internal transaction) in the selected analysis block interval\r\n</body>\r\n</html>");
		btnInfo_Blocks_Contract_Internal_Transactions.addMouseListener(new MouseAdapter() {
			  public void mouseEntered(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(10000);}
			  public void mouseExited(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);}
			});
		
		
		
		btnInfo_Blocks_Contract_Internal_Transactions.setBackground(new Color(245, 245, 245));
		btnInfo_Blocks_Contract_Internal_Transactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		Thread_Step3_ActionPerformed_btnInfo_Blocks_Contrat_Internal_Transactions thread_Step3_ActionPerformed_btnInfo_Blocks_Contrat_Internal_Transactions = 
				new Thread_Step3_ActionPerformed_btnInfo_Blocks_Contrat_Internal_Transactions();
		
		thread_Step3_ActionPerformed_btnInfo_Blocks_Contrat_Internal_Transactions.start();		

			}
		});
		btnInfo_Blocks_Contract_Internal_Transactions.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		btnInfo_Blocks_Contract_Internal_Transactions.setBounds(289, 284, 423, 35);
		contentPane.add(btnInfo_Blocks_Contract_Internal_Transactions);
		
		disableInfo_Field_Blocks_Contract_Internal_Transactions();
		
		btnInfo_External_Transactions = new JButton("Generate Info_External_Transactions");
		btnInfo_External_Transactions.setToolTipText("<html>\r\n<body style=\"width:400;font-family:merriweather;\">\r\nThis module contains all the information related to the invocation of the contract\r\n</body>\r\n</html>");
		btnInfo_External_Transactions.addMouseListener(new MouseAdapter() {
			  public void mouseEntered(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(10000);}
			  public void mouseExited(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);}
			});
		
		btnInfo_External_Transactions.setBackground(new Color(245, 245, 245));
		btnInfo_External_Transactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Thread_Step3_ActionPerformed_btnInfo_External_Transactions thread_Step3_ActionPerformed_btnInfo_External_Transactions = 
						new Thread_Step3_ActionPerformed_btnInfo_External_Transactions();

				thread_Step3_ActionPerformed_btnInfo_External_Transactions.start();
			}
		});
		btnInfo_External_Transactions.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		btnInfo_External_Transactions.setBounds(289, 146, 423, 35);
		contentPane.add(btnInfo_External_Transactions);
		
		disableInfoField_External_Transactions();
		
		btnInfo_Internal_Transactions = new JButton("Generate Info_Internal_Transactions");
		btnInfo_Internal_Transactions.setToolTipText("<html>\r\n<body style=\"width:400;font-family:merriweather;\">\r\nThis module contains all the information related to the invocation of the contract as well as the type of EVM instructions that can produce internal transactions\r\n</body>\r\n</html>");
		btnInfo_Internal_Transactions.addMouseListener(new MouseAdapter() {
			  public void mouseEntered(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(10000);}
			  public void mouseExited(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);}
			});
		
		btnInfo_Internal_Transactions.setBackground(new Color(245, 245, 245));
		btnInfo_Internal_Transactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread_Step3_ActionPerformed_btnInfo_Internal_Transactions thread_Step3_ActionPerformed_btnInfo_Internal_Transactions=
						new Thread_Step3_ActionPerformed_btnInfo_Internal_Transactions();
				thread_Step3_ActionPerformed_btnInfo_Internal_Transactions.start();

			}
		});
		btnInfo_Internal_Transactions.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		btnInfo_Internal_Transactions.setBounds(289, 238, 423, 35);
		contentPane.add(btnInfo_Internal_Transactions);
		
		disableInfoField_Internal_Transactions(); 
		
		btnBytecode_OPCode = new JButton("Generate contract Bytecode and OPCode");
		btnBytecode_OPCode.setToolTipText("<html>\r\n<body style=\"width:350;font-family:merriweather;\">\r\nThis module contains the Bytecode and the OPCode of the contract\r\n</body>\r\n</html>");
		btnBytecode_OPCode.addMouseListener(new MouseAdapter() {
			  public void mouseEntered(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(10000);}
			  public void mouseExited(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);}
			});
		
		btnBytecode_OPCode.setBackground(new Color(245, 245, 245));
		btnBytecode_OPCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread_Step3_ActionPerformed_btnBytecode_OPCode thread_Step3_ActionPerformed_btnBytecode_OPCode = 
						new Thread_Step3_ActionPerformed_btnBytecode_OPCode();
				thread_Step3_ActionPerformed_btnBytecode_OPCode.start();
				
			}
		});
		btnBytecode_OPCode.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		btnBytecode_OPCode.setBounds(289, 330, 423, 35);
		contentPane.add(btnBytecode_OPCode);

		disableFieldBytecode_OPCode();
		
		btnSourceCodeContract = new JButton("Generate contract source code");
		btnSourceCodeContract.setToolTipText("<html>\r\n<body style=\"width:400;font-family:merriweather;\">\r\nThis module contains the source code of the contract, its name, the compiler version and the arguments passed to the constructor\r\n</body>\r\n</html>");
		btnSourceCodeContract.addMouseListener(new MouseAdapter() {
			  public void mouseEntered(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(10000);}
			  public void mouseExited(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);}
			});
		btnSourceCodeContract.setBackground(new Color(245, 245, 245));
		btnSourceCodeContract.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread_Step3_ActionPerformed_btnSourceCodeContract thread_Step3_ActionPerformed_btnSourceCodeContract = 
						new Thread_Step3_ActionPerformed_btnSourceCodeContract();
				thread_Step3_ActionPerformed_btnSourceCodeContract.start();
			}
		});
		
		
		btnSourceCodeContract.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		btnSourceCodeContract.setBounds(289, 376, 423, 35);
		contentPane.add(btnSourceCodeContract);

		
		disableFieldContractSourceCode();
		
		btnReverseContract = new JButton("Reverse engineering of the contract");
		btnReverseContract.setToolTipText("<html>\r\n<body style=\"width:400;font-family:merriweather;\">\r\nThis module allows to generate the source code of the contract from its bytecode based on reverse engineering\r\n</body>\r\n</html>");
		btnReverseContract.addMouseListener(new MouseAdapter() {
			  public void mouseEntered(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(10000);}
			  public void mouseExited(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);}
			});
		btnReverseContract.setBackground(new Color(245, 245, 245));
		btnReverseContract.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread_Step3_ActionPerformed_btnReverseContract thread_Step3_ActionPerformed_btnReverseContract = 
						new Thread_Step3_ActionPerformed_btnReverseContract();
				
				thread_Step3_ActionPerformed_btnReverseContract.start();
				
			}
		});
		btnReverseContract.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		btnReverseContract.setBounds(289, 422, 423, 35);
		contentPane.add(btnReverseContract);

		
		disableFieldReverseEngineerContract();
		
		
		//*****************************************************		
		

		
		
		
		
		//*****************************************************
		btnConfirmStep3 = new JButton("Continuer");
		btnConfirmStep3.setForeground(new Color(0, 0, 0));
		btnConfirmStep3.setBackground(new Color(245, 245, 245));
		btnConfirmStep3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();
				Frame6_SCVADetection_Apps frame6_Applications_SCVADetection=
						new Frame6_SCVADetection_Apps(
								Step3_hm_AddressesSmartContracts,
								Step3_NumberOfLastMinedBlock,
								contractAddress,
								startAnalysisBlock,
								endAnalysisBlock
								);
				frame6_Applications_SCVADetection.setVisible(true);
				
				
				
			}
		});
		
		btnConfirmStep3.setEnabled(false);

		btnConfirmStep3.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		btnConfirmStep3.setBounds(776, 501, 180, 35);
		contentPane.add(btnConfirmStep3);
		
		JButton btnBackToStep2 = new JButton("Go back to previous step");
		btnBackToStep2.setToolTipText("");
		btnBackToStep2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(JOptionPane.showOptionDialog(null, "Are you sure you want to go back to the previous step?", 
						"Back to previous step",
						0, JOptionPane.QUESTION_MESSAGE, null, YesNoArray, null) == JOptionPane.OK_OPTION) {

					dispose();
					
					Frame2_Application_DetectionVulnerabilityAttack_Step2 frame2_Application_DetectionVulnerabilityAttack_Step2 =
							new Frame2_Application_DetectionVulnerabilityAttack_Step2(
									Step3_hm_AddressesSmartContracts,
									Step3_NumberOfLastMinedBlock
									);
					frame2_Application_DetectionVulnerabilityAttack_Step2.frmDetectVulnerabilityAttackStep2.setVisible(true);
				
				}
				
				
				
				
			}
		});

		btnBackToStep2.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		btnBackToStep2.setBackground(new Color(245, 245, 245));
		btnBackToStep2.setForeground(new Color(0, 0, 0));
		btnBackToStep2.setBounds(586, 501, 180, 35);
		contentPane.add(btnBackToStep2);

		
		JButton btnBackToStep1 = new JButton("Go back to step 1");
		btnBackToStep1.setToolTipText("");
		btnBackToStep1.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		btnBackToStep1.setBackground(new Color(245, 245, 245));
		btnBackToStep1.setForeground(new Color(0, 0, 0));
		btnBackToStep1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showOptionDialog(null, "Are you sure you want to go back to step 1?", 
						"Back to step 1",
						0, JOptionPane.QUESTION_MESSAGE, null, YesNoArray, null) == JOptionPane.OK_OPTION) {

					dispose();

					_Main frame2_Application_DetectionVulnerabilityAttack_Step1 =
							new _Main();

					frame2_Application_DetectionVulnerabilityAttack_Step1.frmDetectVulnerabilyAttackStep1.setVisible(true);

				
				}
			
			
			}
		});
		btnBackToStep1.setBounds(396, 501, 180, 35);
		contentPane.add(btnBackToStep1);

		
		
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showOptionDialog(null, "Are you sure you want to leave SCVADetection?", 
						"Disconnect",
						0, JOptionPane.QUESTION_MESSAGE, null, YesNoArray, null) == JOptionPane.OK_OPTION) {
					//dispose();
					System.exit(0);
				}

			}
		});
		btnQuit.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		btnQuit.setBackground(new Color(245, 245, 245));
		btnQuit.setForeground(new Color(0, 0, 0));
		btnQuit.setBounds(59, 501, 180, 35);
		contentPane.add(btnQuit);
		
	
	
	}

	
	public static void initStep3_Part2() {
		web3j = Web3j.build(new HttpService("https://mainnet.infura.io/v3/a9d86db249d148cfab980c5fe68810d7"));

		
		 //adresseContrat = "0xf91546835f756DA0c10cFa0CDA95b15577b84aA7".toLowerCase();

		//Supposons que l'user a choisi cette intervalle pour son analyse
		 /*BlocDebutAnalyse = BigInteger.valueOf(3694969);//
		 BlocFinAnalyse = BigInteger.valueOf(3695510);//*/
		
	}
	
	
	
/***************************************************************************************************************/
/**---------------ACTIVATION - DEACTIVATION OF FIELDS ----------------------------------------------------------*/
	
/**-----------------------------------------------ACTIVATE-DEACTIVATE------------------------------------------*/
	public static void activateFieldInfo_Block_Creation(){
		btnGenerateInfo_Creation_Block.setEnabled(true);
	}
	
	public static void disableFieldInfo_Bloc_Creation(){
		btnGenerateInfo_Creation_Block.setEnabled(false);
	}
	
	
	public static void activateFieldInfo_Blocks_Contract_External_Transactions() {
		btnInfo_Blocks_Contract_External_Transactions.setEnabled(true);
	}
	
	public static void disableField_info_Blocks_Contract_External_Transactions() {
		btnInfo_Blocks_Contract_External_Transactions.setEnabled(false);
	}
	
	
	public static void activateFieldInfo_Blocks_Contract_Internal_Transactions() {
		btnInfo_Blocks_Contract_Internal_Transactions.setEnabled(true);
	}

	public static void disableInfo_Field_Blocks_Contract_Internal_Transactions() {
		btnInfo_Blocks_Contract_Internal_Transactions.setEnabled(false);
	}
	
	public static void activateFieldInfo_External_Transactions() {
		btnInfo_External_Transactions.setEnabled(true);
	}
	
	public static void disableInfoField_External_Transactions() {
		btnInfo_External_Transactions.setEnabled(false);
	}
	
	
	public static void activateFieldInfo_Internal_Transactions() {
		btnInfo_Internal_Transactions.setEnabled(true);
	}

	public static void disableInfoField_Internal_Transactions() {
		btnInfo_Internal_Transactions.setEnabled(false);
	}

	
	public static void activateFieldBytecode_OPCode() {
		btnBytecode_OPCode.setEnabled(true);
	}

	
	public static void disableFieldBytecode_OPCode() {
		btnBytecode_OPCode.setEnabled(false);
	}

	
	public static void activateFieldContractSourceCode() {
		btnSourceCodeContract.setEnabled(true);
	}
	
	public static void disableFieldContractSourceCode() {
		btnSourceCodeContract.setEnabled(false);		
	}
	
	
	public static void activateFieldReverseEngineerContract() {
		btnReverseContract.setEnabled(true);
	}
 
	public static void disableFieldReverseEngineerContract() {
		btnReverseContract.setEnabled(false);
	}
	
	
	public static void activateConfirmButton() {
		btnConfirmStep3.setEnabled(true);
	}
	
	
/******************************************************************************************************************************/

	
	
/**---------------------------------------------------------------------------------------------------------------*/	
/**---------------------------------------------------------------------------------------------------------------*/	
	
	
	public class Thread_Step3_ActionPerformed_btnGenerateInfo_Creation_Block extends Thread{
		public void run() {
			
			btnGenerateInfo_Creation_Block.setEnabled(false);

			
			if(fileExists("Smart Contracts/"+contractAddress+"/Info_Creation_Block/Info_Creation_Block.md")) {
				JOptionPane.showMessageDialog(null, "This module was already generated", 
						"Information", 
						JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				deleteDirectory("Smart Contracts/"+contractAddress+"/Info_Creation_Block");
				
				createProgressBarModulesGenerating();
				
				generating_Info_Creation_Block(contractCreationBlock);

				Step3_WindowProgressBarModulesGeneration.dispose();
				
				JOptionPane.showMessageDialog(null, "Successful module generation", 
						"Information", 
						JOptionPane.INFORMATION_MESSAGE);
			}

			
			
			
			btnGenerateInfo_Creation_Block.setText("End of module generation Info_Creation_Block");
			disableFieldInfo_Bloc_Creation();
			activateFieldInfo_External_Transactions();

		}
	}

	
	public class Thread_Step3_ActionPerformed_btnInfo_External_Transactions extends Thread{
		public void run () {
			
			btnInfo_External_Transactions.setEnabled(false);

			deleteDirectory("Smart Contracts/"+contractAddress+"/Info_External_Transactions");
			
			createProgressBarModulesGenerating();
			
			generating_Info_External_Transactions(contractAddress, startAnalysisBlock, endAnalysisBlock);

			Step3_WindowProgressBarModulesGeneration.dispose();

			JOptionPane.showMessageDialog(null, "Successful module generation", 
					"Information", 
					JOptionPane.INFORMATION_MESSAGE);
			btnInfo_External_Transactions.setText("End of module generation Info_External_Transactions");
			disableInfoField_External_Transactions();
			activateFieldInfo_Blocks_Contract_External_Transactions();
			
		}
	}
	
	
	public class Thread_Step3_ActionPerformed_btnInfo_Blocks_Contract_External_Transactions extends Thread{
		public void run () {
			btnInfo_Blocks_Contract_External_Transactions.setEnabled(false);

			deleteDirectory("Smart Contracts/"+contractAddress+"/Info_Blocks_Contract_External_Transactions");
			
			createProgressBarModulesGenerating();

			
			generating_Info_Blocks_Contract_External_Transactions(contractAddress, startAnalysisBlock, endAnalysisBlock);

			Step3_WindowProgressBarModulesGeneration.dispose();
			
			JOptionPane.showMessageDialog(null, "Successful module generation", 
					"Information", 
					JOptionPane.INFORMATION_MESSAGE);
			btnInfo_Blocks_Contract_External_Transactions.setText("End of module generation Info_Blocks_External_Transactions");
			disableField_info_Blocks_Contract_External_Transactions();
			activateFieldInfo_Internal_Transactions();
			
		}
	}

	
	public class Thread_Step3_ActionPerformed_btnInfo_Internal_Transactions extends Thread{
		public void run () {
			
			btnInfo_Internal_Transactions.setEnabled(false);

			deleteDirectory("Smart Contracts/"+contractAddress+"/Info_Internal_Transactions");
			
			createProgressBarModulesGenerating();
			
			generating_Info_Internal_Transactions(contractAddress, startAnalysisBlock, endAnalysisBlock);
			
			Step3_WindowProgressBarModulesGeneration.dispose();

			JOptionPane.showMessageDialog(null, "Successful module generation", 
					"Information", 
					JOptionPane.INFORMATION_MESSAGE);
			btnInfo_Internal_Transactions.setText("End of module generation Info_Internal_Transactions");
			disableInfoField_Internal_Transactions();
			activateFieldInfo_Blocks_Contract_Internal_Transactions();
		}
	}

	
	public class Thread_Step3_ActionPerformed_btnInfo_Blocks_Contrat_Internal_Transactions extends Thread{
		public void run () {
			btnInfo_Blocks_Contract_Internal_Transactions.setEnabled(false);

			deleteDirectory("Smart Contracts/"+contractAddress+"/Info_Blocks_Contract_Internal_Transactions");
			
			createProgressBarModulesGenerating();
			
			generating_Info_Blocks_Contract_Internal_Transactions(contractAddress, startAnalysisBlock, endAnalysisBlock);
			
			Step3_WindowProgressBarModulesGeneration.dispose();
			
			JOptionPane.showMessageDialog(null, "Successful module generation", 
					"Information", 
					JOptionPane.INFORMATION_MESSAGE);
			btnInfo_Blocks_Contract_Internal_Transactions.setText("End of module generation Info_Blocks_Internal_Transactions");
			disableInfo_Field_Blocks_Contract_Internal_Transactions();
			
			activateFieldBytecode_OPCode();
		}
	}


	public class Thread_Step3_ActionPerformed_btnBytecode_OPCode extends Thread{
		public void run () {
			btnBytecode_OPCode.setEnabled(false);

			if(fileExists("Smart Contracts/"+contractAddress+"/Bytecode-OPCode/bytecode.txt")) {
				JOptionPane.showMessageDialog(null, 
						"This module was already generated", 
						"Information", 
						JOptionPane.INFORMATION_MESSAGE);
			} 
			
			else {

				//We create a directory Bytecode-OPCode
	  	  		createDirectory("Smart Contracts/"+contractAddress+"/Bytecode-OPCode");
	  	  		
				createProgressBarModulesGenerating();
	  	  		
		          try {
					createFileOPCode("Smart Contracts/"+contractAddress+"/Bytecode-OPCode/",
							  contractAddress);
					
			          createFileByteCode("Smart Contracts/"+contractAddress+"/Bytecode-OPCode/",
			        		  contractAddress);
					
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
					//e.printStackTrace();
				}
	  	  		
	  	  		
			Step3_WindowProgressBarModulesGeneration.dispose();

				JOptionPane.showMessageDialog(null, 
						"Successful module generation", 
						"Information", 
						JOptionPane.INFORMATION_MESSAGE);

			}
			
			btnBytecode_OPCode.setText("End of module generation Bytecode-OPCode");
			disableFieldBytecode_OPCode();
			activateFieldContractSourceCode();

			
		}
	}	

	
	public class Thread_Step3_ActionPerformed_btnSourceCodeContract extends Thread{
		public void run() {
			
			btnSourceCodeContract.setEnabled(false);

			if(fileExists("Smart Contracts/"+contractAddress+"/Contract_Reverted_Source_Code/ContractSourceCode.sol") 
					||
			   fileExists("Smart Contracts/"+contractAddress+"/Contract_Source_Code/ContractSourceCode.sol")	
					) {
				JOptionPane.showMessageDialog(null, 
						"This module was already generated",
						"Information" ,
						JOptionPane.INFORMATION_MESSAGE);
				btnSourceCodeContract.setText("End of module generation Contract_Source_Code");
				disableFieldContractSourceCode();
				activateConfirmButton();
			}
				
			else {
				createProgressBarModulesGenerating();
				
				if(generating_Contract_Source_Code()) {

					Step3_WindowProgressBarModulesGeneration.dispose();
				
					JOptionPane.showMessageDialog(null, 
							"Successful module generation",
							"Information" ,JOptionPane.INFORMATION_MESSAGE);

					
					
					btnSourceCodeContract.setText("End of module generation Contract_Source_Code");
					disableFieldContractSourceCode();

					JOptionPane.showMessageDialog(null, 
							"You can continue",
							"Next" ,JOptionPane.INFORMATION_MESSAGE);

					//As this is the last block generated, we activate the confirm button
					activateConfirmButton();
				
				}else {
					Step3_WindowProgressBarModulesGeneration.dispose();
					JOptionPane.showMessageDialog(null, 
							"The source code of the contract has not yet been verified on Etherscan", 
							"Attention",
							JOptionPane.WARNING_MESSAGE);
					
					Step3_WindowProgressBarModulesGeneration.dispose();
					JOptionPane.showMessageDialog(null, 
							"Please proceed to the module for reverse engineering", 
							"Indication",
							JOptionPane.INFORMATION_MESSAGE);

					btnSourceCodeContract.setText("Reverse");
					disableFieldContractSourceCode();

					
					//In this case we activate the contract reverse-engineer field
					activateFieldReverseEngineerContract();
				}				
			}	
		}
	}

	
	public class Thread_Step3_ActionPerformed_btnReverseContract extends Thread {
		public void run() {

			btnReverseContract.setEnabled(false);
						
			
Frame4_DetectionAidModules_ReverseContract_Bytecode frame4_DetectionAidModules_ReverseContract_Bytecode = 
new Frame4_DetectionAidModules_ReverseContract_Bytecode(contractAddress);
			
			// At the very end we activate the validate button, in fact I have to do a wait() it's better..
			disableFieldReverseEngineerContract();
			activateConfirmButton();
		}
		
	}
	
	
	
	
/**---------------------------------------------------------------------------------------------------------------*/	
/**---------------------------------------------------------------------------------------------------------------*/		
/**------------------------------------Module Info_Creation_Block--------------------------------------------------*/
	
	
	public static void generating_Info_Creation_Block(BigInteger numBlocCreation) {
		//System.out.println("generation_Info_Bloc_Creation");
			try {
				createDirectory("Smart Contracts");
				createDirectory("Smart Contracts/"+contractAddress);
				createDirectory("Smart Contracts/"+contractAddress+"/Info_Creation_Block");
				
				PrintWriter infoCreationBlock = new PrintWriter("Smart Contracts/"
						+contractAddress+"/Info_Creation_Block/"
						+ "Info_Creation_Block.md", "UTF-8");
				
				
				EthBlock ethBlock = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(numBlocCreation), false).send();
		 
		  		BigInteger blockNumber = ethBlock.getBlock().getNumber();
		  		String blockNumberHexa = ethBlock.getBlock().getNumberRaw();
				
		  		String extraData = ethBlock.getBlock().getExtraData();

		  		BigInteger difficulty = ethBlock.getBlock().getDifficulty();
		  		String difficultyHexa = ethBlock.getBlock().getDifficultyRaw();

		  		BigInteger difficultyTotale = ethBlock.getBlock().getTotalDifficulty();
		  		String difficultyTotaleHexa = ethBlock.getBlock().getTotalDifficultyRaw();

		  		BigInteger gasLimit = ethBlock.getBlock().getGasLimit();
		  		String gasLimitHexa = ethBlock.getBlock().getGasLimitRaw();

		  		BigInteger gasUsed = ethBlock.getBlock().getGasUsed();
		  		String gasUsedHexa = ethBlock.getBlock().getGasUsedRaw(); 

		  		String blockHash = ethBlock.getBlock().getHash();
		  		String parentHash = ethBlock.getBlock().getParentHash();

		  		String sha3Uncles = ethBlock.getBlock().getSha3Uncles();
		  		
		  		List<String> uncles = ethBlock.getBlock().getUncles();//Orphan blocks if any
				
		  		BigInteger nonce = ethBlock.getBlock().getNonce();
		  		String nonceHexa = ethBlock.getBlock().getNonceRaw();

		  		String mixHash = ethBlock.getBlock().getMixHash();//is a 256 - bit hash which proves combined with the nonce that a sufficient amount of computation has been carried out on this block
				
		  		String logsBloom = ethBlock.getBlock().getLogsBloom();

		  		String miner = ethBlock.getBlock().getMiner();
				
		  		String receiptsRoot = ethBlock.getBlock().getReceiptsRoot();//the root of the receipts trie of the block
																		  //Every time a transaction is executed, Ethereum generates a transaction receipt that contains information about the transaction execution. This field is the hash of the root node of the transactions receipt trie.

		  		String stateRoot = ethBlock.getBlock().getStateRoot();//the root of the final state trie of the block

		  		BigInteger blockSize = ethBlock.getBlock().getSize();
				String blockSizeHexa = ethBlock.getBlock().getSizeRaw();

				long timeStamp = ethBlock.getBlock().getTimestamp().longValue();
				String timeStampHex = ethBlock.getBlock().getTimestampRaw();
				Date time = new Date(timeStamp*1000);

				
				/**We write on the file*/
				infoCreationBlock.println("blockNumber "+blockNumber);
				infoCreationBlock.println("blockNumberHexa "+blockNumberHexa);
				infoCreationBlock.println("extraData "+extraData);
				infoCreationBlock.println("difficulty "+difficulty);
				infoCreationBlock.println("difficultyHexa "+difficultyHexa);
				infoCreationBlock.println("difficultyTotale "+difficultyTotale);
				infoCreationBlock.println("difficultyTotaleHexa "+difficultyTotaleHexa);
				infoCreationBlock.println("gasLimit "+gasLimit);
				infoCreationBlock.println("gasLimitHexa "+gasLimitHexa);
				infoCreationBlock.println("gasUsed "+gasUsed);
				infoCreationBlock.println("gasUsedHexa "+gasUsedHexa);
				infoCreationBlock.println("blockHash "+blockHash);
				infoCreationBlock.println("parentHash "+parentHash);
				infoCreationBlock.println("sha3Uncles "+sha3Uncles);
				infoCreationBlock.println("uncles "+uncles);
				infoCreationBlock.println("nonce "+nonce);
				infoCreationBlock.println("nonceHexa "+nonceHexa);
				infoCreationBlock.println("mixHash "+mixHash);
				infoCreationBlock.println("logsBloom "+logsBloom);
				infoCreationBlock.println("miner "+miner);
				infoCreationBlock.println("receiptsRoot "+receiptsRoot);
				infoCreationBlock.println("stateRoot "+stateRoot);
				infoCreationBlock.println("blockSize "+blockSize);
				infoCreationBlock.println("blockSizeHexa "+blockSizeHexa);
				infoCreationBlock.println("timeStamp "+timeStamp);
				infoCreationBlock.println("timeStampHex "+timeStampHex);
				infoCreationBlock.println("time "+time);
				
				
				
				
				String transactionsRoot = ethBlock.getBlock().getTransactionsRoot();//the root of the transaction tree of the block

				List<TransactionResult> listDeTransactions = ethBlock.getBlock().getTransactions();
				TransactionResult tr ;
				int transactionsSize = listDeTransactions.size();
				
				String transactionHash;
				BigInteger transactionIndex;
				String transactionIndexHexa;
				
				BigInteger transactionGasUsed;
				String transactionGasUsedHexa;
				
				BigInteger transactionCumulativeGasUsed;
				String transactionCumulativeGasUsedHexa;
				
				String transactionFrom;
				String transactionTo;
				
				List<Log> transactionLogs;
				String transactionLogsBloom;
				
				/**We write on the file*/
				infoCreationBlock.println("transactionsRoot "+transactionsRoot);
				infoCreationBlock.println("transactionsSize "+transactionsSize);
				
				
		        for (int j = 0; j < listDeTransactions.size(); j++) {
		      	    tr= listDeTransactions.get(j);
				    EthGetTransactionReceipt ethGetTransactionReceipt = web3j.ethGetTransactionReceipt((String) tr.get()).send();
				   //For each transaction, we generate
				    transactionIndex = ethGetTransactionReceipt.getResult().getTransactionIndex();
				    transactionHash = ethGetTransactionReceipt.getResult().getTransactionHash();
				    transactionIndexHexa = ethGetTransactionReceipt.getResult().getTransactionIndexRaw();
				    
				    transactionGasUsed = ethGetTransactionReceipt.getResult().getGasUsed(); 
				    transactionGasUsedHexa = ethGetTransactionReceipt.getResult().getGasUsedRaw();
				    
				    transactionCumulativeGasUsed = ethGetTransactionReceipt.getResult().getCumulativeGasUsed();
				    transactionCumulativeGasUsedHexa = ethGetTransactionReceipt.getResult().getCumulativeGasUsedRaw();

				    transactionFrom = ethGetTransactionReceipt.getResult().getFrom();
				    transactionTo = ethGetTransactionReceipt.getResult().getTo();
				    
				    transactionLogs = ethGetTransactionReceipt.getResult().getLogs();
				    transactionLogsBloom = ethGetTransactionReceipt.getResult().getLogsBloom();
				
				    
				    
				    
				    /**We write on the file */
					infoCreationBlock.println("transactionIndex "+transactionIndex);
					infoCreationBlock.println("	transactionIndexHexa "+transactionIndexHexa);
					infoCreationBlock.println("	transactionHash "+transactionHash);
					infoCreationBlock.println("	transactionGasUsed "+transactionGasUsed);
					infoCreationBlock.println("	transactionGasUsedHexa "+transactionGasUsedHexa);
					infoCreationBlock.println("	transactionCumulativeGasUsed "+transactionCumulativeGasUsed);
					infoCreationBlock.println("	transactionCumulativeGasUsedHexa "+transactionCumulativeGasUsedHexa);
					infoCreationBlock.println("	transactionFrom "+transactionFrom);
					infoCreationBlock.println("	transactionTo "+transactionTo);
					infoCreationBlock.println("	transactionLogs "+transactionLogs);
					infoCreationBlock.println("	transactionLogsBloom "+transactionLogsBloom);

					//We reinitialize variables
					transactionHash=null;transactionIndex=null;transactionIndexHexa=null;transactionGasUsed=null;transactionGasUsedHexa=null;transactionCumulativeGasUsed=null;
					transactionCumulativeGasUsedHexa=null;transactionFrom=null;transactionTo=null;transactionLogs=null;transactionLogsBloom=null;
					
					
		        }//End for loop

		        infoCreationBlock.close();
		        
		        
	}catch (Exception e) {
		JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
	}
			
	}//End generating Info_Creation_Block
	
	
	public static  boolean fileExists(String filePath) {
		java.io.File f = new File(filePath);
		if(f.exists() && !f.isDirectory()) { 
		    return true;
		}
		
		return false;
	}

	
/*******************************************************************************************************************************/
/****Module Info_External_Transactions*/


	public static void generating_Info_External_Transactions(String contractAddress, BigInteger blockStartAnalysis, BigInteger blockEndAnalysis) {
			  //System.out.println("generation_Info_Transactions_Externes");
		try {
			
			createDirectory("Smart Contracts/"+contractAddress+"/Info_External_Transactions");		
			
			PrintWriter fileInfo_External_Transactions_ = new PrintWriter(
					"Smart Contracts/"+contractAddress+"/Info_External_Transactions/"+
					"Info_External_Transactions.md", "UTF-8");

			PrintWriter file_numberCallsToOurContractPerBlock = new PrintWriter(
					"Smart Contracts/"+contractAddress+"/Info_External_Transactions/"+
					"NumberOfCallsToContractPerBlock.md", "UTF-8"
					);
			
			
			BufferedReader br = null;
		    String content_Info_External_Transactions=null, line=null;
			int blockNumber_Info_External_Transactions=0;
			int transactionHash_Info_External_Transactions=2;
			int from_Info_External_Transactions=6;//6 : when I purge what I have recovered, the "from" address is on the 6th line
			int to_Info_External_Transactions=7;//idem()
			int value_Info_External_Transactions=8;//idem()
			int input_Info_External_Transactions=13;//idem()
			int contratAdresse_Info_External_Transactions=14;//idem()

			URL url;
			StringBuilder sb;
			
			      
				  		url = new URL(
				  			"https://api.etherscan.io/api?module=account&action=txlist"
				  			+ "&address="+contractAddress
				  			+ "&startblock="+blockStartAnalysis
				  			+ "&endblock="+blockEndAnalysis
				  			+ "&apikey=PUT_HERE_YOUR_API_KEY"
				  			);
			           br = new BufferedReader(new InputStreamReader(url.openStream()));
			           sb = new StringBuilder();
			           while ((line = br.readLine()) != null) {sb.append(line);}
			           
			            content_Info_External_Transactions = sb.toString();
			           
						content_Info_External_Transactions = content_Info_External_Transactions.replace("{\"status\":\"1\",\"message\":\"OK\",\"result\":", "");
						content_Info_External_Transactions = content_Info_External_Transactions.replace("[", "");
						content_Info_External_Transactions = content_Info_External_Transactions.replace("]", "");
						content_Info_External_Transactions = content_Info_External_Transactions.replace("{","");
						content_Info_External_Transactions = content_Info_External_Transactions.replace("}","");
						content_Info_External_Transactions = content_Info_External_Transactions.replace(",", "\n");
						content_Info_External_Transactions = content_Info_External_Transactions.replace("\"", "");
						
						content_Info_External_Transactions = content_Info_External_Transactions.replace(":"," ");
						
						String array_Info_External_Transactions[] = content_Info_External_Transactions.split("\n");


						
						//To check if there is an error during the execution of the contract
						
						URL url_CheckIfThereWasAnErrorDuringContractExecution;
						StringBuilder sb_CheckIfThereWasAnErrorDuringContractExecution;
				   		BufferedReader br_CheckIfThereWasAnErrorDuringContractExecution = null;
				   		String line_CheckIfThereWasAnErrorDuringContractExecution;
				   		String content_CheckIfThereWasAnErrorDuringContractExecution;
				   		String [] array_CheckIfThereWasAnErrorDuringContractExecution;
				   		String [] transactionHash;

list_NumBlocks_External_Transactions = new ArrayList();


					for (int j = 0; j < (array_Info_External_Transactions.length/18); j++) {

if(!list_NumBlocks_External_Transactions.contains(new BigInteger(array_Info_External_Transactions[blockNumber_Info_External_Transactions].split(" ")[1]))) {
	list_NumBlocks_External_Transactions.add(new BigInteger(array_Info_External_Transactions[blockNumber_Info_External_Transactions].split(" ")[1]));
	
hm_numberCallsToOurContractPerBlock.put(new BigInteger(array_Info_External_Transactions[blockNumber_Info_External_Transactions].split(" ")[1]),1);

}else {
		hm_numberCallsToOurContractPerBlock.put(
		new BigInteger(array_Info_External_Transactions[blockNumber_Info_External_Transactions].split(" ")[1]),
		
		hm_numberCallsToOurContractPerBlock.get(
				new BigInteger(array_Info_External_Transactions[blockNumber_Info_External_Transactions].split(" ")[1])
						
				)+1
		);
	}
							
							fileInfo_External_Transactions_.println(array_Info_External_Transactions[blockNumber_Info_External_Transactions]);
							fileInfo_External_Transactions_.println("\tTx"+array_Info_External_Transactions[transactionHash_Info_External_Transactions]);
							fileInfo_External_Transactions_.println("\t"+array_Info_External_Transactions[from_Info_External_Transactions]);
							fileInfo_External_Transactions_.println("\t"+array_Info_External_Transactions[to_Info_External_Transactions]);
							fileInfo_External_Transactions_.println("\t"+array_Info_External_Transactions[value_Info_External_Transactions]);
							fileInfo_External_Transactions_.println("\t"+array_Info_External_Transactions[input_Info_External_Transactions]);
							fileInfo_External_Transactions_.println("\t"+array_Info_External_Transactions[contratAdresse_Info_External_Transactions]);
							
							//To check if there is an error during the execution of the contract

							transactionHash = array_Info_External_Transactions[transactionHash_Info_External_Transactions].split(" ");
					   		url_CheckIfThereWasAnErrorDuringContractExecution = new URL("https://api.etherscan.io/api?"
									+ "module=transaction&action=getstatus"
									+ "&txhash="+transactionHash[1]
									+ "&apikey=PUT_HERE_YOUR_API_KEY");

							br_CheckIfThereWasAnErrorDuringContractExecution = new BufferedReader(new InputStreamReader(url_CheckIfThereWasAnErrorDuringContractExecution.openStream()));
							sb_CheckIfThereWasAnErrorDuringContractExecution = new StringBuilder();
					           
					           
					           while ((line_CheckIfThereWasAnErrorDuringContractExecution = br_CheckIfThereWasAnErrorDuringContractExecution.readLine()) != null) {
					        	   sb_CheckIfThereWasAnErrorDuringContractExecution.append(line_CheckIfThereWasAnErrorDuringContractExecution);
					        	   }//fin while()
					           
					           content_CheckIfThereWasAnErrorDuringContractExecution = sb_CheckIfThereWasAnErrorDuringContractExecution.toString();
					           
					           content_CheckIfThereWasAnErrorDuringContractExecution = content_CheckIfThereWasAnErrorDuringContractExecution.replace("{\"status\":\"1\",\"message\":\"OK\",\"result\":","");
					           content_CheckIfThereWasAnErrorDuringContractExecution = content_CheckIfThereWasAnErrorDuringContractExecution.replace("{", "");content_CheckIfThereWasAnErrorDuringContractExecution = content_CheckIfThereWasAnErrorDuringContractExecution.replace("}", "");
					           content_CheckIfThereWasAnErrorDuringContractExecution = content_CheckIfThereWasAnErrorDuringContractExecution.replace(",", "\n");content_CheckIfThereWasAnErrorDuringContractExecution = content_CheckIfThereWasAnErrorDuringContractExecution.replace(":", " ");
					           content_CheckIfThereWasAnErrorDuringContractExecution = content_CheckIfThereWasAnErrorDuringContractExecution.replace("\"", "");
					        
					           array_CheckIfThereWasAnErrorDuringContractExecution = content_CheckIfThereWasAnErrorDuringContractExecution.split("\n");
					           
					           //We add the two fields Error and ErrorDescription to our file
					           for (int i = 0; i < array_CheckIfThereWasAnErrorDuringContractExecution.length; i++) {
								fileInfo_External_Transactions_.println("\t"+array_CheckIfThereWasAnErrorDuringContractExecution[i]);
					           }
					           			           
					           
					           br_CheckIfThereWasAnErrorDuringContractExecution.close();
							
							
							
							blockNumber_Info_External_Transactions 		+= 18;
							transactionHash_Info_External_Transactions  += 18;
							from_Info_External_Transactions 			+= 18; 
							to_Info_External_Transactions 				+= 18;
							value_Info_External_Transactions 			+= 18;
							input_Info_External_Transactions 			+= 18;
							contratAdresse_Info_External_Transactions   += 18;
					
						}//end for()		           
			           
			         br.close();
			  
			  fileInfo_External_Transactions_.close();

					  
			  List sortedKeys=new ArrayList(hm_numberCallsToOurContractPerBlock.keySet());
			  Collections.sort(sortedKeys);
				for (Object object : sortedKeys) {
					
					//System.out.println(object.toString() +" "+hm_numberCallsToOurContractPerBlock.get(object));
				    file_numberCallsToOurContractPerBlock.println(object.toString() +" "+hm_numberCallsToOurContractPerBlock.get(object));				
				
				}

			  file_numberCallsToOurContractPerBlock.close();
			  
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
		}
		
	}

	
	
/*****************************************************************************************************************************/
/**---------------------------------------Module Info_Blocks_Contract_External_Transactions-----------------------------------**/

	public static void generating_Info_Blocks_Contract_External_Transactions(String contractAddress, BigInteger BlockStartAnalysis, BigInteger BlockEndAnalysis) {
		//System.out.println("generation_Info_Blocs_Contrat_Transactions_Externes");
	
	try {
		createDirectory("Smart Contracts/"+contractAddress+"/Info_Blocks_Contract_External_Transactions");
		
		for (int i = 0; i < list_NumBlocks_External_Transactions.size(); i++) {
			
	       	 generating_Info_Block(list_NumBlocks_External_Transactions.get(i), 
	    			 "Smart Contracts/"
	 				 +contractAddress
					 +"/Info_Blocks_Contract_External_Transactions/");
			}
		
		 // System.out.println("Fin Info_Blocs_Contrat_Transactions_Externes");
		  }catch (Exception e) {
			  JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
		  }
	}

	
	public static void generating_Info_Block(BigInteger numBlock, String path) {

		try {
			PrintWriter file_infoBlock = new PrintWriter(path+"Bloc_N°"+numBlock+".md", "UTF-8");
			
			
			EthBlock ethBlock = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(numBlock), false).send();
	 
	  		BigInteger blockNumber = ethBlock.getBlock().getNumber();
	  		String blockNumberHexa = ethBlock.getBlock().getNumberRaw();
			
	  		String extraData = ethBlock.getBlock().getExtraData();

	  		BigInteger difficulty = ethBlock.getBlock().getDifficulty();
	  		String difficultyHexa = ethBlock.getBlock().getDifficultyRaw();

	  		BigInteger difficultyTotale = ethBlock.getBlock().getTotalDifficulty();
	  		String difficultyTotaleHexa = ethBlock.getBlock().getTotalDifficultyRaw();

	  		BigInteger gasLimit = ethBlock.getBlock().getGasLimit();
	  		String gasLimitHexa = ethBlock.getBlock().getGasLimitRaw();

	  		BigInteger gasUsed = ethBlock.getBlock().getGasUsed();
	  		String gasUsedHexa = ethBlock.getBlock().getGasUsedRaw(); 

	  		String blockHash = ethBlock.getBlock().getHash();
	  		String parentHash = ethBlock.getBlock().getParentHash();

	  		String sha3Uncles = ethBlock.getBlock().getSha3Uncles();
	  		
	  		List<String> uncles = ethBlock.getBlock().getUncles();//
			
	  		BigInteger nonce = ethBlock.getBlock().getNonce();
	  		String nonceHexa = ethBlock.getBlock().getNonceRaw();

	  		String mixHash = ethBlock.getBlock().getMixHash();//is a 256 - bit hash which proves combined with the nonce that a sufficient amount of computation has been carried out on this block
			
	  		String logsBloom = ethBlock.getBlock().getLogsBloom();

	  		String miner = ethBlock.getBlock().getMiner();
			
	  		String receiptsRoot = ethBlock.getBlock().getReceiptsRoot();//the root of the receipts trie of the block
																	  //Every time a transaction is executed, Ethereum generates a transaction receipt that contains information about the transaction execution. This field is the hash of the root node of the transactions receipt trie.

	  		String stateRoot = ethBlock.getBlock().getStateRoot();//the root of the final state trie of the block

	  		BigInteger blockSize = ethBlock.getBlock().getSize();
			String blockSizeHexa = ethBlock.getBlock().getSizeRaw();

			long timeStamp = ethBlock.getBlock().getTimestamp().longValue();
			String timeStampHex = ethBlock.getBlock().getTimestampRaw();
			Date time = new Date(timeStamp*1000);

			
			/***/
			file_infoBlock.println("blockNumber "+blockNumber);
			file_infoBlock.println("blockNumberHexa "+blockNumberHexa);
			file_infoBlock.println("extraData "+extraData);
			file_infoBlock.println("difficulty "+difficulty);
			file_infoBlock.println("difficultyHexa "+difficultyHexa);
			file_infoBlock.println("difficultyTotale "+difficultyTotale);
			file_infoBlock.println("difficultyTotaleHexa "+difficultyTotaleHexa);
			file_infoBlock.println("gasLimit "+gasLimit);
			file_infoBlock.println("gasLimitHexa "+gasLimitHexa);
			file_infoBlock.println("gasUsed "+gasUsed);
			file_infoBlock.println("gasUsedHexa "+gasUsedHexa);
			file_infoBlock.println("blockHash "+blockHash);
			file_infoBlock.println("parentHash "+parentHash);
			file_infoBlock.println("sha3Uncles "+sha3Uncles);
			file_infoBlock.println("uncles "+uncles);
			file_infoBlock.println("nonce "+nonce);
			file_infoBlock.println("nonceHexa "+nonceHexa);
			file_infoBlock.println("mixHash "+mixHash);
			file_infoBlock.println("logsBloom "+logsBloom);
			file_infoBlock.println("miner "+miner);
			file_infoBlock.println("receiptsRoot "+receiptsRoot);
			file_infoBlock.println("stateRoot "+stateRoot);
			file_infoBlock.println("blockSize "+blockSize);
			file_infoBlock.println("blockSizeHexa "+blockSizeHexa);
			file_infoBlock.println("timeStamp "+timeStamp);
			file_infoBlock.println("timeStampHex "+timeStampHex);
			file_infoBlock.println("time "+time);
			
			
			
			
			String transactionsRoot = ethBlock.getBlock().getTransactionsRoot();//the root of the transaction trie of the block

			List<TransactionResult> listDeTransactions = ethBlock.getBlock().getTransactions();
			TransactionResult tr ;
			int transactionsSize = listDeTransactions.size();
			
			String transactionHash;
			BigInteger transactionIndex;
			String transactionIndexHexa;
			
			BigInteger transactionGasUsed;
			String transactionGasUsedHexa;
			
			BigInteger transactionCumulativeGasUsed;
			String transactionCumulativeGasUsedHexa;
			
			String transactionFrom;
			String transactionTo;
			
			List<Log> transactionLogs;
			String transactionLogsBloom;
			
			/***/
			file_infoBlock.println("transactionsRoot "+transactionsRoot);
			file_infoBlock.println("transactionsSize "+transactionsSize);
			
			
	        for (int j = 0; j < listDeTransactions.size(); j++) {
	      	    tr= listDeTransactions.get(j);
			    EthGetTransactionReceipt ethGetTransactionReceipt = web3j.ethGetTransactionReceipt((String) tr.get()).send();
			   //

			    transactionIndex = ethGetTransactionReceipt.getResult().getTransactionIndex();
			    transactionHash = ethGetTransactionReceipt.getResult().getTransactionHash();
			    transactionIndexHexa = ethGetTransactionReceipt.getResult().getTransactionIndexRaw();
			    
			    transactionGasUsed = ethGetTransactionReceipt.getResult().getGasUsed(); 
			    transactionGasUsedHexa = ethGetTransactionReceipt.getResult().getGasUsedRaw();
			    
			    transactionCumulativeGasUsed = ethGetTransactionReceipt.getResult().getCumulativeGasUsed();
			    transactionCumulativeGasUsedHexa = ethGetTransactionReceipt.getResult().getCumulativeGasUsedRaw();

			    transactionFrom = ethGetTransactionReceipt.getResult().getFrom();
			    transactionTo = ethGetTransactionReceipt.getResult().getTo();
			    
			    transactionLogs = ethGetTransactionReceipt.getResult().getLogs();
			    transactionLogsBloom = ethGetTransactionReceipt.getResult().getLogsBloom();
			
			    
			    
			    
			    /***/
				file_infoBlock.println("transactionIndex "+transactionIndex);
				file_infoBlock.println("	transactionIndexHexa "+transactionIndexHexa);
				file_infoBlock.println("	transactionHash "+transactionHash);
				file_infoBlock.println("	transactionGasUsed "+transactionGasUsed);
				file_infoBlock.println("	transactionGasUsedHexa "+transactionGasUsedHexa);
				file_infoBlock.println("	transactionCumulativeGasUsed "+transactionCumulativeGasUsed);
				file_infoBlock.println("	transactionCumulativeGasUsedHexa "+transactionCumulativeGasUsedHexa);
				file_infoBlock.println("	transactionFrom "+transactionFrom);
				file_infoBlock.println("	transactionTo "+transactionTo);
				file_infoBlock.println("	transactionLogs "+transactionLogs);
				file_infoBlock.println("	transactionLogsBloom "+transactionLogsBloom);

				//
				transactionHash=null;transactionIndex=null;transactionIndexHexa=null;transactionGasUsed=null;transactionGasUsedHexa=null;transactionCumulativeGasUsed=null;
				transactionCumulativeGasUsedHexa=null;transactionFrom=null;transactionTo=null;transactionLogs=null;transactionLogsBloom=null;
				
				
	        }// 

	        file_infoBlock.close();
	        
	}catch (Exception e) {
		JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
	}

		
		
	}



	
/*******************************************************************************************************************************/
/****Module Info_Internal_Transactions*/


    public static void generating_Info_Internal_Transactions(String contractAddress, BigInteger blockStartAnalysis, BigInteger blocEndAnalysis) {

	try {

		createDirectory("Smart Contracts/"+contractAddress+"/Info_Internal_Transactions");
		PrintWriter fileInfo_Internal_Transactions = new PrintWriter(
				"Smart Contracts/"+contractAddress+"/Info_Internal_Transactions/"+
				"Info_Internal_Transactions.md", "UTF-8");

		
		BufferedReader br = null;
	    String content_Info_Internal_Transactions=null, line=null;
		int blockNumber_Info_Internal_Transactions=0;
		int from_Info_Internal_Transactions=3;
		int to_Info_Internal_Transactions=4;
		int value_Info_Internal_Transactions=5;
		int contractAddress_Info_Internal_Transactions=6;
		int input_Info_Internal_Transactions=7;
		int type_Info_Internal_Transactions=8;
		int traceId_Info_Internal_Transactions=11;
		int isError_Info_Internal_Transactions=12;
		int errCode_Info_Internal_Transactions=13;

		URL url;
		StringBuilder sb;
		
			  		url = new URL(
			  				"https://api.etherscan.io/api?module=account"
			  			  + "&action=txlistinternal"
			  			  + "&address="+contractAddress
			  			  + "&startblock="+blockStartAnalysis
			  			  + "&endblock="+blocEndAnalysis
			  			  + "&apikey=PUT_HERE_YOUR_API_KEY"
			  					);
       	  		
		
       			   br = new BufferedReader(new InputStreamReader(url.openStream()));
		           sb = new StringBuilder();
		           while ((line = br.readLine()) != null) {sb.append(line);}

		           
		            content_Info_Internal_Transactions = sb.toString();
		           
					content_Info_Internal_Transactions = content_Info_Internal_Transactions.replace("{\"status\":\"1\",\"message\":\"OK\",\"result\":", "");
					content_Info_Internal_Transactions = content_Info_Internal_Transactions.replace("[", "");
					content_Info_Internal_Transactions = content_Info_Internal_Transactions.replace("]", "");
					content_Info_Internal_Transactions = content_Info_Internal_Transactions.replace("{","");
					content_Info_Internal_Transactions = content_Info_Internal_Transactions.replace("}","");
					content_Info_Internal_Transactions = content_Info_Internal_Transactions.replace(",", "\n");
					content_Info_Internal_Transactions = content_Info_Internal_Transactions.replace("\"", "");
					
					content_Info_Internal_Transactions = content_Info_Internal_Transactions.replace(":"," ");
					
					String array_Info_Internal_Transactions[] = content_Info_Internal_Transactions.split("\n");

					
					
					list_NumBlocks_Internal_Transactions = new ArrayList();
					
					//System.out.println("tab_Info_Transactions_Internes.length "+ tab_Info_Transactions_Internes.length);
					
					
					for (int j = 0; j < (array_Info_Internal_Transactions.length)/14; j++) {
						
						//System.out.println(j);
						
if(!list_NumBlocks_Internal_Transactions.contains(new BigInteger(array_Info_Internal_Transactions[blockNumber_Info_Internal_Transactions].split(" ")[1]))) {

list_NumBlocks_Internal_Transactions.add(
		new BigInteger(array_Info_Internal_Transactions[blockNumber_Info_Internal_Transactions].split(" ")[1]));
	
}
						fileInfo_Internal_Transactions.println(array_Info_Internal_Transactions[blockNumber_Info_Internal_Transactions]);
						
						fileInfo_Internal_Transactions.println("\t"+array_Info_Internal_Transactions[from_Info_Internal_Transactions]);
						fileInfo_Internal_Transactions.println("\t"+array_Info_Internal_Transactions[to_Info_Internal_Transactions]);
						fileInfo_Internal_Transactions.println("\t"+array_Info_Internal_Transactions[value_Info_Internal_Transactions]);
						fileInfo_Internal_Transactions.println("\t"+array_Info_Internal_Transactions[contractAddress_Info_Internal_Transactions]);
						fileInfo_Internal_Transactions.println("\t"+array_Info_Internal_Transactions[input_Info_Internal_Transactions]);
						fileInfo_Internal_Transactions.println("\t"+array_Info_Internal_Transactions[type_Info_Internal_Transactions]);
						fileInfo_Internal_Transactions.println("\t"+array_Info_Internal_Transactions[traceId_Info_Internal_Transactions]);
						fileInfo_Internal_Transactions.println("\t"+array_Info_Internal_Transactions[isError_Info_Internal_Transactions]);
						fileInfo_Internal_Transactions.println("\t"+array_Info_Internal_Transactions[errCode_Info_Internal_Transactions]);


						//
						if(j==9999) {

						  		url = new URL(
						  				"https://api.etherscan.io/api?module=account"
						  			  + "&action=txlistinternal"
						  			  + "&address="+contractAddress
						  			  + "&startblock="+array_Info_Internal_Transactions[blockNumber_Info_Internal_Transactions].split(" ")[1]
						  			  + "&endblock="+blocEndAnalysis
						  			  + "&apikey=PUT_HERE_YOUR_API_KEY");
						  		
					           br = new BufferedReader(new InputStreamReader(url.openStream()));
					           sb = new StringBuilder();
					           while ((line = br.readLine()) != null) {sb.append(line);}
				           
				           			content_Info_Internal_Transactions = sb.toString();
				           
									content_Info_Internal_Transactions = content_Info_Internal_Transactions.replace("{\"status\":\"1\",\"message\":\"OK\",\"result\":", "");
									content_Info_Internal_Transactions = content_Info_Internal_Transactions.replace("[", "");
									content_Info_Internal_Transactions = content_Info_Internal_Transactions.replace("]", "");
									content_Info_Internal_Transactions = content_Info_Internal_Transactions.replace("{","");
									content_Info_Internal_Transactions = content_Info_Internal_Transactions.replace("}","");
									content_Info_Internal_Transactions = content_Info_Internal_Transactions.replace(",", "\n");
									content_Info_Internal_Transactions = content_Info_Internal_Transactions.replace("\"", "");
									
									content_Info_Internal_Transactions = content_Info_Internal_Transactions.replace(":"," ");
									
									array_Info_Internal_Transactions = content_Info_Internal_Transactions.split("\n");

									j=0;
									blockNumber_Info_Internal_Transactions=0;
									from_Info_Internal_Transactions=3;
									to_Info_Internal_Transactions=4;
									value_Info_Internal_Transactions=5;
									contractAddress_Info_Internal_Transactions=6;
									input_Info_Internal_Transactions=7;
									type_Info_Internal_Transactions=8;
									traceId_Info_Internal_Transactions=11;
									isError_Info_Internal_Transactions=12;
									errCode_Info_Internal_Transactions=13;
						}
						else {
						blockNumber_Info_Internal_Transactions	 +=14;
						from_Info_Internal_Transactions		  	 +=14;
						to_Info_Internal_Transactions		  	 +=14;
						value_Info_Internal_Transactions	  	 +=14;
						contractAddress_Info_Internal_Transactions+=14;
						input_Info_Internal_Transactions		 +=14;
						type_Info_Internal_Transactions	 		 +=14;
						traceId_Info_Internal_Transactions		 +=14;
						isError_Info_Internal_Transactions		 +=14;
						errCode_Info_Internal_Transactions		 +=14;
						}
						
					}		           
		           
		         br.close();

		  fileInfo_Internal_Transactions.close();
		
		  
			String lines_Info_Internal_Transactions = "";
			
			try (BufferedReader brNumberOfInternalTransactions = new BufferedReader(new FileReader(new File("Smart Contracts/"+contractAddress+"/Info_Internal_Transactions/"+
					"Info_Internal_Transactions.md")))) {
			    String lineNumberOfInternalTransactions;
			    while ((lineNumberOfInternalTransactions = brNumberOfInternalTransactions.readLine()) != null) {
			       lines_Info_Internal_Transactions +=lineNumberOfInternalTransactions;
			       lines_Info_Internal_Transactions +="\n";
			    }
			}
			

			Pattern pattern = 
					Pattern.compile("blockNumber (.+)\n\tfrom "+contractAddress
					);
			Matcher matcher = pattern.matcher(lines_Info_Internal_Transactions.toString());
			List<String> list = new ArrayList<String>();
			
			
			HashMap<String , Integer> hm = new HashMap<String, Integer>();
			while (matcher.find()) {
				if(!list.contains(matcher.group(1))) {
					list.add(matcher.group(1));
					hm.put(matcher.group(1),1);
				}else {
					
					hm.put(matcher.group(1),hm.get(matcher.group(1)) +1);
				}
			}//end while()

			
			
			
		PrintWriter file_NumberOfCallsFromContractPerBlock = new PrintWriter(
				"Smart Contracts/"+contractAddress+"/Info_Internal_Transactions/"+
				"numberOfCallsFromContractPerBlock.md", "UTF-8"
				);
			  
			  
		  List sortedKeys=new ArrayList(hm.keySet());
		  Collections.sort(sortedKeys);
			for (Object object : sortedKeys) {
				file_NumberOfCallsFromContractPerBlock.println(object.toString() +" "+hm.get(object));
			}

		
			file_NumberOfCallsFromContractPerBlock.close();
		  
	} catch (Exception e) {
		//System.out.println("Exception "+e.getMessage());
		JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
	}
}

	
	
/******************************************************************************************************************************/
/****Module Info_Blocks_Contract_Internal_Transactions************************/

	public static void generating_Info_Blocks_Contract_Internal_Transactions(String contractAddress, BigInteger blockStartAnalysis, BigInteger blockEndAnalysis) {
		  
		  
		  try {
			  createDirectory("Smart Contracts/"+contractAddress+"/Info_Blocks_Contract_Internal_Transactions");
			  
			  	for (int i = 0; i < list_NumBlocks_Internal_Transactions.size(); i++) {
		        	 generating_Info_Block(list_NumBlocks_Internal_Transactions.get(i), 
		        			 "Smart Contracts/"
		        			 +contractAddress
		        			 +"/Info_Blocks_Contract_Internal_Transactions/"
		        			 );
			  		
			         //System.out.println("Bloc "+list_NumBlocs_Transactions_Internes.get(i));			  		
				}
			  
				      				   
			//System.out.println("Fin generation_Info_Blocs_Contrat_Transactions_Internes");
				  
		  }catch (Exception e) {
			  JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
		  }
	}


	public static void createProgressBarModulesGenerating() {
		
		Step3_WindowProgressBarModulesGeneration = new JFrame();
		
		Step3_WindowProgressBarModulesGeneration.setTitle("Please wait...");
		Step3_WindowProgressBarModulesGeneration.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Step3_WindowProgressBarModulesGeneration.setBounds(100, 100, 250, 100);
		Step3_WindowProgressBarModulesGeneration.setResizable(false);
		Step3_WindowProgressBarModulesGeneration.setLocationRelativeTo(null);

		Step3_contentPaneWindowProgressBarModulesGeneration = new JPanel();
		Step3_contentPaneWindowProgressBarModulesGeneration.setBorder(new EmptyBorder(5, 5, 5, 5));
		Step3_WindowProgressBarModulesGeneration.setContentPane(Step3_contentPaneWindowProgressBarModulesGeneration);
		Step3_contentPaneWindowProgressBarModulesGeneration.setLayout(null);

		
		Step3_progressBarWindow_ProgressBarModulesGeneration = new JProgressBar();
		Step3_progressBarWindow_ProgressBarModulesGeneration.setForeground(Color.GREEN);
		Step3_progressBarWindow_ProgressBarModulesGeneration.setBounds(20, 10, 170, 27);
		Step3_contentPaneWindowProgressBarModulesGeneration.add(Step3_progressBarWindow_ProgressBarModulesGeneration);

		Step3_progressBarWindow_ProgressBarModulesGeneration.setIndeterminate(true);

		
		Step3_WindowProgressBarModulesGeneration.setVisible(true);

	}

	
	public static void createDirectory(String dirName) {
	    //Creating a File object
	    File file = new File(dirName);
	    //Creating the directory
	    boolean boolDirectoryCreation = file.mkdir();

	    //If directory exists, we delete it then create a new one.
	    if(!boolDirectoryCreation) {
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


	public static void deleteDirectory(String dirName) {
		try {
				FileUtils.deleteDirectory(new File(dirName));
	  	  } catch (IOException e) {
	  		JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
	  	  }catch (Exception e) {
	  		JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
	  	  }
	}


/*********************************************************************************************************************************/
/****Module Bytecode-Opcode*******/
	public static void createFileOPCode(String path,String contractAddress) throws IOException {

		BufferedReader br = null;
	    String content;
		 try {
	           URL url = new URL("https://etherscan.io/api?module=opcode&action=getopcode&address="+contractAddress);
	           br = new BufferedReader(new InputStreamReader(url.openStream()));
	           String line;
	           StringBuilder sb = new StringBuilder();
	           while ((line = br.readLine()) != null) {sb.append(line);sb.append(System.lineSeparator());}
	           
	           content = sb.toString();
	       
	       } finally {if (br != null) {br.close();}}
		

		content = content.replaceAll("<br>","\n");content = content.replaceAll("\"","");
		content = content.replace("{status:1,message:OK,result:", "");content = content.replace("}","");
		content = content.replaceAll("'","");content = content.replace("(Unknown Opcode)","");

		
		PrintWriter writer;
		try {
			writer = new PrintWriter(path+"opcode.txt", "UTF-8");
			writer.println(content);	
			writer.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
		} catch (UnsupportedEncodingException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
		}
	}

	
	public static void createFileByteCode(String path, String contractAddress) throws IOException {
		BufferedReader br = null;
	    String content;
		 try {
	           URL url = new URL("https://api.etherscan.io/api?module=proxy&action=eth_getCode&address="+contractAddress+
	        		   "&tag=latest&apikey=PUT_HERE_YOUR_API_KEY");//
	           br = new BufferedReader(new InputStreamReader(url.openStream()));
	           String line;
	           StringBuilder sb = new StringBuilder();
	           while ((line = br.readLine()) != null) {sb.append(line);sb.append(System.lineSeparator());}
	           
	           content = sb.toString();
	       
	       } finally {if (br != null) {br.close();}}

		
		PrintWriter writer;
		try {
			writer = new PrintWriter(path+"bytecode.txt", "UTF-8");
			writer.println(content);	
			writer.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
		} catch (UnsupportedEncodingException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
		}

	
	
		/*****in case the contract has been destroyed
		 * Because once the contract is destroyed, the bytecode will no longer be available.
		 * So we have to convert from opcode to bytecode
		 * We still need to store the information that the contract has been destroyed.
		 * ******************************************************/
		
		File f = new File(path+"bytecode.txt");

		PrintWriter writer2 = new PrintWriter(path+"ContractStatus.txt", "UTF-8");
		if(f.length()<=45) {writer2.println("destroyed");}
		else {writer2.println("deployed");}
		writer2.close();

		if(f.length()<=45) {

			String fileOPCode = "";
			try (BufferedReader br2 = 
					new BufferedReader(
							new FileReader(
									new File("Smart Contracts/"+contractAddress+"/Bytecode-OPCode/opcode.txt")))) {
				String line;
				
			    while ((line = br2.readLine()) != null) {fileOPCode += line+"\n";}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
				//e.printStackTrace();
				} 
			
			PrintWriter writer3 = new PrintWriter("Smart Contracts/"+contractAddress+"/Bytecode-OPCode/bytecode.txt", "UTF-8");
			writer3.println("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"0x"+convertOPCodeToBytecode(fileOPCode)+"\"}");	
			writer3.close();
		}
	
	}


	public static String convertOPCodeToBytecode(String content) {
		content=content.replaceAll("\\bADD\\b","01");
		content=content.replaceAll("\\bMUL\\b","02");			
		content=content.replaceAll("\\bSTOP\\b","00");			
		content=content.replaceAll("\\bSUB\\b","03");
		content=content.replaceAll("\\bSDIV\\b","05");
		content=content.replaceAll("\\bDIV\\b","04");
		content=content.replaceAll("\\bMOD\\b","06");
		content=content.replaceAll("\\bSMOD\\b","07");
		content=content.replaceAll("\\bADDMOD\\b","08");
		content=content.replaceAll("\\bMULMOD\\b","09");			
		content=content.replaceAll("\\bEXP\\b","0a");
		content=content.replaceAll("\\bSIGNEXTEND\\b","0b");
		content=content.replaceAll("\\bLT\\b","10");
		content=content.replaceAll("\\bGT\\b","11");
		content=content.replaceAll("\\bSLT\\b","12");
		content=content.replaceAll("\\bSGT\\b","13");
		content=content.replaceAll("\\bEQ\\b","14");
		content=content.replaceAll("\\bISZERO\\b","15");
		content=content.replaceAll("\\bAND\\b","16");
		content=content.replaceAll("\\bOR\\b","17");
		content=content.replaceAll("\\bXOR\\b","18");
		content=content.replaceAll("\\bNOT\\b","19");
		content=content.replaceAll("\\bBYTE\\b","1a");
		content=content.replaceAll("\\bSHA3\\b","20");
		content=content.replaceAll("\\bADDRESS\\b","30");
		content=content.replaceAll("\\bBALANCE\\b","31");
		content=content.replaceAll("\\bORIGIN\\b","32");
		content=content.replaceAll("\\bCALLER\\b","33");
		content=content.replaceAll("\\bCALLVALUE\\b","34");
		content=content.replaceAll("\\bCALLDATALOAD\\b","35");
		content=content.replaceAll("\\bCALLDATASIZE\\b","36");
		content=content.replaceAll("\\bCALLDATACOPY\\b","37");
		content=content.replaceAll("\\bCODESIZE\\b","38");
		content=content.replaceAll("\\bCODECOPY\\b","39");
		content=content.replaceAll("\\bGASPRICE\\b","3a");
		content=content.replaceAll("\\bEXTCODESIZE\\b","3b");
		content=content.replaceAll("\\bEXTCODECOPY\\b","3c");
		content=content.replaceAll("\\bRETURNDATASIZE\\b","3d");
		content=content.replaceAll("\\bRETURNDATACOPY\\b","3e");
		content=content.replaceAll("\\bBLOCKHASH\\b","40");
		content=content.replaceAll("\\bCOINBASE\\b","41");
		content=content.replaceAll("\\bTIMESTAMP\\b","42");
		content=content.replaceAll("\\bNUMBER\\b","43");
		content=content.replaceAll("\\bDEFICULTY\\b","44");
		content=content.replaceAll("\\bGASLIMIT\\b","45");
		content=content.replaceAll("\\bPOP\\b","50");
		content=content.replaceAll("\\bMLOAD\\b","51");
		content=content.replaceAll("\\bMSTORE\\b","52");
		content=content.replaceAll("\\bMSTORE8\\b","53");
		content=content.replaceAll("\\bSLOAD\\b","54");
		content=content.replaceAll("\\bSSTORE\\b","55");
		content=content.replaceAll("\\bJUMP\\b","56");
		content=content.replaceAll("\\bJUMPI\\b","57");
		content=content.replaceAll("\\bPC\\b","58");
		content=content.replaceAll("\\bMSIZE\\b","59");
		content=content.replaceAll("\\bGAS\\b","5a");
		content=content.replaceAll("\\bJUMPDEST\\b","5b");
		content=content.replaceAll("\\bPUSH1\\b","60");
		content=content.replaceAll("\\bPUSH2\\b","61");
		content=content.replaceAll("\\bPUSH3\\b","62");
		content=content.replaceAll("\\bPUSH4\\b","63");
		content=content.replaceAll("\\bPUSH5\\b","64");
		content=content.replaceAll("\\bPUSH6\\b","65");
		content=content.replaceAll("\\bPUSH7\\b","66");
		content=content.replaceAll("\\bPUSH8\\b","67");
		content=content.replaceAll("\\bPUSH9\\b","68");
		content=content.replaceAll("\\bPUSH10\\b","69");
		content=content.replaceAll("\\bPUSH11\\b","6A");
		content=content.replaceAll("\\bPUSH12\\b","6B");
		content=content.replaceAll("\\bPUSH13\\b","6C");
		content=content.replaceAll("\\bPUSH14\\b","6D");
		content=content.replaceAll("\\bPUSH15\\b","6E");
		content=content.replaceAll("\\bPUSH16\\b","6F");
		content=content.replaceAll("\\bPUSH17\\b","70");
		content=content.replaceAll("\\bPUSH18\\b","71");
		content=content.replaceAll("\\bPUSH19\\b","72");
		content=content.replaceAll("\\bPUSH20\\b","73");
		content=content.replaceAll("\\bPUSH21\\b","74");
		content=content.replaceAll("\\bPUSH22\\b","75");
		content=content.replaceAll("\\bPUSH23\\b","76");
		content=content.replaceAll("\\bPUSH24\\b","77");
		content=content.replaceAll("\\bPUSH25\\b","78");
		content=content.replaceAll("\\bPUSH26\\b","79");
		content=content.replaceAll("\\bPUSH27\\b","7A");
		content=content.replaceAll("\\bPUSH28\\b","7B");
		content=content.replaceAll("\\bPUSH29\\b","7C");
		content=content.replaceAll("\\bPUSH30\\b","7D");
		content=content.replaceAll("\\bPUSH31\\b","7E");
		content=content.replaceAll("\\bPUSH32\\b","7F");
		content=content.replaceAll("\\bDUP1\\b","80");
		content=content.replaceAll("\\bDUP2\\b","81");
		content=content.replaceAll("\\bDUP3\\b","82");
		content=content.replaceAll("\\bDUP4\\b","83");
		content=content.replaceAll("\\bDUP5\\b","84");
		content=content.replaceAll("\\bDUP6\\b","85");
		content=content.replaceAll("\\bDUP7\\b","86");
		content=content.replaceAll("\\bDUP8\\b","87");
		content=content.replaceAll("\\bDUP9\\b","88");
		content=content.replaceAll("\\bDUP10\\b","89");
		content=content.replaceAll("\\bDUP11\\b","8A");
		content=content.replaceAll("\\bDUP12\\b","8B");
		content=content.replaceAll("\\bDUP13\\b","8C");
		content=content.replaceAll("\\bDUP14\\b","8D");
		content=content.replaceAll("\\bDUP15\\b","8E");
		content=content.replaceAll("\\bDUP16\\b","8F");
		content=content.replaceAll("\\bSWAP1\\b","90");
		content=content.replaceAll("\\bSWAP2\\b","91");
		content=content.replaceAll("\\bSWAP3\\b","92");
		content=content.replaceAll("\\bSWAP4\\b","93");
		content=content.replaceAll("\\bSWAP5\\b","94");
		content=content.replaceAll("\\bSWAP6\\b","95");
		content=content.replaceAll("\\bSWAP7\\b","96");
		content=content.replaceAll("\\bSWAP8\\b","97");
		content=content.replaceAll("\\bSWAP9\\b","98");
		content=content.replaceAll("\\bSWAP10\\b","99");
		content=content.replaceAll("\\bSWAP11\\b","9A");
		content=content.replaceAll("\\bSWAP12\\b","9B");
		content=content.replaceAll("\\bSWAP13\\b","9C");
		content=content.replaceAll("\\bSWAP14\\b","9D");
		content=content.replaceAll("\\bSWAP15\\b","9E");
		content=content.replaceAll("\\bSWAP16\\b","9F");
		content=content.replaceAll("\\bLOG0\\b","a0");
		content=content.replaceAll("\\bLOG1\\b","a1");
		content=content.replaceAll("\\bLOG2\\b","a2");
		content=content.replaceAll("\\bLOG3\\b","a3");
		content=content.replaceAll("\\bLOG4\\b","a4");
		content=content.replaceAll("\\bCREATE\\b","f0");
		content=content.replaceAll("\\bCALL\\b","f1");
		content=content.replaceAll("\\bCALLCODE\\b","f2");
		content=content.replaceAll("\\bRETURN\\b","f3");
		content=content.replaceAll("\\bDELEGATECALL\\b","f4");
		content=content.replaceAll("\\bSTATICCALL\\b","fa");
		content=content.replaceAll("\\bREVERT\\b","fd");
		content=content.replaceAll("\\bINVALID\\b","fe");
		content=content.replaceAll("\\bSELFDESTRUCT\\b","ff");

		content=content.replaceAll("0x","");
		content=content.replaceAll("\n", "");
		content=content.replaceAll(" ", "");
		
		return content;
	}

	
	
/******************************************************************************************************************************/
/****Module Contract Source Code************************/

	
	public static boolean generating_Contract_Source_Code() {
		
		boolean bVerifiedContract =  true;
		
		try {
			
			BufferedReader br = null;
		    String content_SourceCodeEtherscan=null, lineSourceCodeEtherscan=null;
			URL url=null;
			StringBuilder sb=null;
			
			String sourceCode=null, contractName=null, compilerVersion=null, argumentsPassedToConstructor=null;
			
				  		url = new URL("https://api.etherscan.io/api?module=contract"
				  				+ "&action=getsourcecode"
				  				+ "&address="+contractAddress
				  				+ "&apikey=PUT_HERE_YOUR_API_KEY"
				  					);
				  		
				  		
			           br = new BufferedReader(new InputStreamReader(url.openStream()));
			           sb = new StringBuilder();
			           while ((lineSourceCodeEtherscan = br.readLine()) != null) {sb.append(lineSourceCodeEtherscan);}
			           
			           content_SourceCodeEtherscan = sb.toString();

			
		
			       	Pattern pattern=null;
			    	Matcher matcher=null;

					pattern = Pattern.compile("\"SourceCode\":\"(.*)\",\"ABI\"(.*),\"ContractName\":(.*),"
							+ "\"CompilerVersion\":(.*),\"OptimizationUsed\""
							+ ".*,\"ConstructorArguments\":(.*),\"EVMVersion\":");
					
					matcher = pattern.matcher(content_SourceCodeEtherscan);
					
					
					
					while (matcher.find()) {

						if(matcher.group(2).contains("Contract source code not verified")) {
							bVerifiedContract=false;
							break;
						}
						
						sourceCode=matcher.group(1).replace("\\t","\t").replace("\\r","");					
						contractName = matcher.group(3);
						
						compilerVersion = matcher.group(4);
						
						argumentsPassedToConstructor = matcher.group(5);

					}//fin while()
					if(bVerifiedContract) {
						
						createDirectory("Smart Contracts/"+contractAddress+"/Contract_Source_Code");
						
						sourceCode = sourceCode.replace("\\n", "\n");


						
						PrintWriter fileContractSourceCode= new PrintWriter(
								"Smart Contracts/"+contractAddress+"/Contract_Source_Code/"
								+ "ContractSourceCode.sol", "UTF-8");
						PrintWriter fileAdditionalInfo = new PrintWriter(
								"Smart Contracts/"+contractAddress+"/Contract_Source_Code/"
								+ "additionalInfo.md", "UTF-8");
						
						
						//
						fileContractSourceCode.append(sourceCode);
			
						fileAdditionalInfo.println("ContractName: "+contractName);
						fileAdditionalInfo.println("CompilerVersion: "+compilerVersion);
						fileAdditionalInfo.println("ArgumentsPassedToConstructor: "+argumentsPassedToConstructor);
			
						
						fileContractSourceCode.close();
						fileAdditionalInfo.close();
						
					}
							

	
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			//System.out.println(e.getMessage());
		}
		
		return bVerifiedContract;			
		
	}


}
