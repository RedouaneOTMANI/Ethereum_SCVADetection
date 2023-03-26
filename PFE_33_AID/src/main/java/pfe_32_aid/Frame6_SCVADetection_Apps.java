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
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
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

public class Frame6_SCVADetection_Apps extends JFrame {

	private static JPanel contentPane;
	public static String arrayYesNo[] = {"Yes", "No"};

	public static HashMap<String, BigInteger> step4_hm_SmartContractAddress = new HashMap<String, BigInteger>();
	public static BigInteger step4_NumberOfLastMinedBlock = BigInteger.valueOf(0);
	
	public static String contractAddress ="";
	public static JFrame step4_WindowProgressBar;
	public static JPanel step4_contentPaneWindoProgressBar;
	public static JProgressBar step4_progressBarWindowProgressBar;
	public static JLabel step4_LabelBlockNumberWindowProgressBar;
	
	public static BigInteger step4_BlockStartAnalysis=BigInteger.valueOf(0);
	public static BigInteger step4_BlockEndAnalysis=BigInteger.valueOf(0);
	
	
	/**
	 * Launch the application.
	 */
	
	public static void initializeAllVariables() {
		contentPane=null;

		step4_hm_SmartContractAddress = new HashMap<String, BigInteger>();
		step4_NumberOfLastMinedBlock = BigInteger.valueOf(0);
		
		contractAddress ="";
		step4_WindowProgressBar=null;
		step4_contentPaneWindoProgressBar=null;
		step4_progressBarWindowProgressBar=null;
		step4_LabelBlockNumberWindowProgressBar=null;

		step4_BlockStartAnalysis=BigInteger.valueOf(0);
		step4_BlockEndAnalysis=BigInteger.valueOf(0);

		
	}
	
	/**
	 * Create the frame. 
	 */

	
	public Frame6_SCVADetection_Apps(HashMap<String, BigInteger> hm_SmartContractAddress, 
			BigInteger numberOfLastMinedBlock, String contractAddressConstructor,
			BigInteger BlockStartAnalysisConstructor, BigInteger BlockEndAnalysisConstructor
			) {

		initializeAllVariables();

		step4_hm_SmartContractAddress=hm_SmartContractAddress;
		step4_NumberOfLastMinedBlock=numberOfLastMinedBlock;
		contractAddress=contractAddressConstructor;
		step4_BlockStartAnalysis = BlockStartAnalysisConstructor;
		step4_BlockEndAnalysis = BlockEndAnalysisConstructor;
		
		
UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("merriweather", Font.BOLD, 12)));

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("SCVADetection apps");
		setBounds(0, 0, 1020, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 51, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Launch SCVADetection applications");
		
		lblNewLabel_1.setForeground(new Color(46, 139, 87));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 35));
		
		lblNewLabel_1.setBounds(0, 40, 949, 48);
		contentPane.add(lblNewLabel_1);
		
		
		
		
		int defaultDismissTimeout = ToolTipManager.sharedInstance().getDismissDelay();
		
		
		JButton btnGeneralReport = new JButton("General report");
		btnGeneralReport.setForeground(new Color(0, 0, 0));
		btnGeneralReport.setToolTipText(
				"<html>\r\n" + 
				"<body style=\"font-family:merriweather;\">\r\n" + 
				"This report contains all the information about the contract"
				+"</body>\r\n" + 
				"</html>"		
				);
		
		btnGeneralReport.addMouseListener(new MouseAdapter() {
			  public void mouseEntered(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(10000);}
			  public void mouseExited(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);}
		});
		
		
		btnGeneralReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGeneralReport.setEnabled(false);

				Thread_GeneralReport thread_GeneralReport  = new Thread_GeneralReport();
				thread_GeneralReport.start();
				
			}
		});

		btnGeneralReport.setBackground(new Color(245, 245, 245));
		btnGeneralReport.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		btnGeneralReport.setBounds(363, 156, 288, 35);
		contentPane.add(btnGeneralReport);

		
		
		JButton btnApp1 = new JButton("Application 1");
		btnApp1.setToolTipText("<html>\r\n<body style=\"width:450;font-family:merriweather;\">\r\nThis application detects whether the contract is vulnerable to Reentrancy, Integer overflow and underflow, Transaction order dependency<br> and Timestamp dependency\r\n</body>\r\n</html>");
		btnApp1.addMouseListener(new MouseAdapter() {
			  public void mouseEntered(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(10000);}
			  public void mouseExited(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);}
		});

		
		btnApp1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnApp1.setEnabled(false);
				Thread_App1 thread_App1 = new Thread_App1();
				thread_App1.start();
				
			}
		});

		btnApp1.setBackground(new Color(245, 245, 245));
		btnApp1.setForeground(new Color(0, 0, 0));
		btnApp1.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));

		btnApp1.setBounds(363, 218, 288, 35);
		contentPane.add(btnApp1);

		
		
		JButton btnApp2 = new JButton("Application 2");
		btnApp2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnApp2.setEnabled(false);
				
				Thread_App2 thread_App2 = new Thread_App2();
				thread_App2.start();
				
				

			}
		});
		btnApp2.setForeground(new Color(0, 0, 0));
		btnApp2.setToolTipText("<html>\r\n<body style=\"font-family:merriweather;\">\r\nThis application detects whether the contract has suffered a Reentrancy attack\r\n</body>\r\n</html>\r\n");
		btnApp2.addMouseListener(new MouseAdapter() {
			  public void mouseEntered(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(10000);}
			  public void mouseExited(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);}
		});

		btnApp2.setBackground(new Color(245, 245, 245));
		btnApp2.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		btnApp2.setBounds(363, 282, 288, 35);
		contentPane.add(btnApp2);


		
		
		JButton btnApp3 = new JButton("Application 3");
		btnApp3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnApp3.setEnabled(false);
				
				Thread_App3 thread_App3 = new Thread_App3();
				thread_App3.start();

			}
		});
		btnApp3.setForeground(new Color(0, 0, 0));
		btnApp3.setToolTipText("<html>\r\n<body style=\"width:450;font-family:merriweather;\">\r\nThis application detects whether the contract is vulnerable to the Reentrancy attack and specifies the details of the vulnerability exploitation\r\n</body>\r\n</html>");
		btnApp3.addMouseListener(new MouseAdapter() {
			  public void mouseEntered(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(10000);}
			  public void mouseExited(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);}
		});

		btnApp3.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		btnApp3.setBackground(new Color(245, 245, 245));
		btnApp3.setBounds(363, 346, 288, 35);
		contentPane.add(btnApp3);
		
		
		JButton btnApp4 = new JButton("Application 4");
		btnApp4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnApp4.setEnabled(false);
				
				Thread_App4 thread_App4 = new Thread_App4();
				thread_App4.start();
			}
		});
		btnApp4.setForeground(new Color(0, 0, 0));
		btnApp4.setToolTipText("<html>\r\n<body style=\"width:450;font-family:merriweather;\">\r\nThis application detects whether the contract is vulnerable to attacks exploiting vulnerabilities of pseudo-random number generators (PRNG) and specifies the details of the exploitation of each vulnerability\r\n</body>\r\n</html>");
		btnApp4.addMouseListener(new MouseAdapter() {
			  public void mouseEntered(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(10000);}
			  public void mouseExited(MouseEvent me) {ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);}
		});

		btnApp4.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		btnApp4.setBackground(new Color(245, 245, 245));
		btnApp4.setBounds(363, 405, 288, 35);
		contentPane.add(btnApp4);

		
		
		JButton btnChooseAnotherContract = new JButton("Choose another contract");
		btnChooseAnotherContract.setToolTipText("");
		btnChooseAnotherContract.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showOptionDialog(null, "Are you sure you want to choose another contract?", 
						"Confirmation",
						0, JOptionPane.QUESTION_MESSAGE, null,arrayYesNo,null) == JOptionPane.OK_OPTION) {
					
					
					dispose();

					Frame2_Application_DetectionVulnerabilityAttack_Step2 frame2_Application_DetectionVulnerabiliteAttaque_Etape2 =
							new Frame2_Application_DetectionVulnerabilityAttack_Step2(
									step4_hm_SmartContractAddress,
									step4_NumberOfLastMinedBlock
									);
					frame2_Application_DetectionVulnerabiliteAttaque_Etape2.frmDetectVulnerabilityAttackStep2.setVisible(true);
					
				}
				
				
			}
		});
		btnChooseAnotherContract.setForeground(new Color(0, 0, 0));
	
		btnChooseAnotherContract.setBackground(new Color(245, 245, 245));
		btnChooseAnotherContract.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		
		btnChooseAnotherContract.setBounds(714, 489, 225, 35);
		contentPane.add(btnChooseAnotherContract);
		

		
		
		JButton btnBackToStep1 = new JButton("Back to Step 1");
		btnBackToStep1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showOptionDialog(null, 
						"Are you sure you want to go back to the Step 1?", 
						"Back to Step 1", 
						0, JOptionPane.QUESTION_MESSAGE, null,arrayYesNo,null) == JOptionPane.OK_OPTION) {

				dispose();
				_Main frame2_Application_DetectionVulnerabiliteAttaque_Etape1 =
						new _Main();

				frame2_Application_DetectionVulnerabiliteAttaque_Etape1.frmDetectVulnerabilyAttackStep1.setVisible(true);
				}
			}
		});
		btnBackToStep1.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		btnBackToStep1.setBackground(new Color(245, 245, 245));
		btnBackToStep1.setForeground(new Color(0, 0, 0));
		btnBackToStep1.setBounds(484, 489, 220, 35);
		contentPane.add(btnBackToStep1);
		
		JButton btnNewButton_4 = new JButton("Quit");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if(JOptionPane.showOptionDialog(null, "Are you sure you want to leave SCVADetection?", 
					"Disconnect",
					0, JOptionPane.QUESTION_MESSAGE, null, arrayYesNo, null) == JOptionPane.OK_OPTION) {
				//dispose();
				System.exit(0);
			}

			}
		});
		btnNewButton_4.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
		btnNewButton_4.setBackground(new Color(245, 245, 245));
		btnNewButton_4.setForeground(new Color(0, 0, 0));
		btnNewButton_4.setBounds(58, 489, 180, 35);
		contentPane.add(btnNewButton_4);
		
		JLabel lblYouCanGenerate = new JLabel("You can generate the general report on the contract and launch the detection applications");
		
		lblYouCanGenerate.setFont(new Font("Merriweather Light", Font.PLAIN, 20));
		lblYouCanGenerate.setHorizontalTextPosition(SwingConstants.CENTER);
		lblYouCanGenerate.setHorizontalAlignment(SwingConstants.CENTER);
		lblYouCanGenerate.setForeground(Color.LIGHT_GRAY);

		lblYouCanGenerate.setBounds(0, 88, 1016, 57);		
		
		contentPane.add(lblYouCanGenerate);
	
	
	
	}

		
	public static class Thread_GeneralReport extends Thread{
		public void run() {
			//We delete the report
			File file = new File("General Report.pdf");
			file.delete();
		  
			GeneralReport generalReport= new GeneralReport();
			
			//We launch the progress bar.
			CreatingProgressBar("General Report generating..");
			generalReport.initGenerateReport();

			//We stop the progress bar here.
			step4_WindowProgressBar.dispose();
			
			JOptionPane.showMessageDialog(null,"End of General Report generating","Information",JOptionPane.INFORMATION_MESSAGE);
	
		}
	}


	public static class Thread_App1 extends Thread{
		public void run() {
			//
			File file = new File("App_01_Report.pdf");
			file.delete();

			//
			CreatingProgressBar("Application 1..");
			
			//
			try {
				BufferedReader br = new BufferedReader(new FileReader("Smart Contracts/"+contractAddress+"/Bytecode-OPCode/bytecode.txt"));
				StringBuilder sb = new StringBuilder();
			    String line = br.readLine();

			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        
						line = br.readLine();
			    }
			    String everything = sb.toString();
			    br.close();
			    
		   		Pattern pattern= Pattern.compile(",\"result\":\"(.*)\"");
				Matcher matcher= pattern.matcher(everything);

				//We create a 2nd file bytecodeML.txt specially designed for our Machine Learning script.
				PrintWriter writer = new PrintWriter("Smart Contracts/"+contractAddress+"/Bytecode-OPCode/bytecodeML.txt", "UTF-8");
				
				while(matcher.find()) {
					//System.out.println(matcher.group(1));
					writer.println(matcher.group(1));
				}	           
				
				writer.close();
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
				e.printStackTrace();
				}

			
			
			File f = new File("Smart Contracts/"+contractAddress+"/Bytecode-OPCode/bytecodeML.txt");
			
			
			if(f.length()<=4096) {
				
				//...Launch the application, generate the report and everything.
				SC_App_01_ML.initApplicationML(contractAddress);
				
				//We stop the progress bar here.
				step4_WindowProgressBar.dispose();

				JOptionPane.showMessageDialog(null,"End of App1 report generating.",
					"Information",JOptionPane.INFORMATION_MESSAGE);
			
			}else {
				//We stop the progress bar here also
				step4_WindowProgressBar.dispose();

				JOptionPane.showMessageDialog(null,
					"The size of the contract bytecode exceeds 4096 bytes."
				  + "Please try again with another smart contract address",
					"Attention",JOptionPane.WARNING_MESSAGE);


			}
			
			

			
			
		}
	}

	
	public static class Thread_App2 extends Thread{
		public void run() {
			//
			File file = new File("App_02_Report.pdf");
			file.delete();

			//We launch the progress bar
			CreatingProgressBar("Application 2..");
			
			SC_App_02_Reentrancy.initApplicationReentrancy(contractAddress,step4_BlockStartAnalysis,step4_BlockEndAnalysis);			
			
			
			//
			step4_WindowProgressBar.dispose();

			//...
			JOptionPane.showMessageDialog(null,"End of App2 report generating",
					"Information",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	public static class Thread_App3 extends Thread{
		public void run() {
			//
			File file = new File("App_03_Report.pdf");
			file.delete();

			//
			CreatingProgressBar("Application 3..");

			SC_App_03_Reentrancy.initReentrancy_App3(contractAddress);

			
			//
			step4_WindowProgressBar.dispose();
			
			
			//...
			JOptionPane.showMessageDialog(null,"End of App3 report generating",
					"Information",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	public static class Thread_App4 extends Thread{
		public void run() {
			//
			File file = new File("App_04_Report.pdf");
			file.delete();

			//
			CreatingProgressBar("Application 4..");

			SC_App_04_Bad_Randomness.initBad_Randomness_App4(contractAddress);
			
			//
			step4_WindowProgressBar.dispose();

			//...
			JOptionPane.showMessageDialog(null,"End of App3 report generating",
					"Information",JOptionPane.INFORMATION_MESSAGE);

		}
	}

	
	public static void CreatingProgressBar(String titre) {
		
		step4_WindowProgressBar = new JFrame();
		
		step4_WindowProgressBar.setTitle(titre);
		step4_WindowProgressBar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		step4_WindowProgressBar.setBounds(100, 100, 350, 100);
		step4_WindowProgressBar.setResizable(false);
		step4_WindowProgressBar.setLocationRelativeTo(null);

		step4_contentPaneWindoProgressBar = new JPanel();
		step4_contentPaneWindoProgressBar.setBorder(new EmptyBorder(5, 5, 5, 5));
		step4_WindowProgressBar.setContentPane(step4_contentPaneWindoProgressBar);
		step4_contentPaneWindoProgressBar.setLayout(null);

		
		step4_progressBarWindowProgressBar = new JProgressBar();
		step4_progressBarWindowProgressBar.setForeground(Color.GREEN);
		step4_progressBarWindowProgressBar.setBounds(20, 21, 300, 27);
		step4_contentPaneWindoProgressBar.add(step4_progressBarWindowProgressBar);

		step4_progressBarWindowProgressBar.setIndeterminate(true);

		step4_WindowProgressBar.setVisible(true);
	}

	
}
