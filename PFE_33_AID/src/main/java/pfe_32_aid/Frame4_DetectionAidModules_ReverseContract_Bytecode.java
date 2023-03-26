/**
 * @author Mélissa MAZROU
 * @author Redouane OTMANI
 * 
 */
package pfe_32_aid;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

import java.awt.Font;


public class Frame4_DetectionAidModules_ReverseContract_Bytecode extends JFrame {

	public static String contractAddress = null;	
	public static JButton btnCopyToClipboard=null;
	public static JButton btnConfirm=null;
	public static String arrayYesNo[] = {"Yes", "No"};
	
	private JPanel contentPane;



	/**
	 * Create the frame.
	 */
	
	public static void initializeAllVariables() {
		
		contractAddress = null;	
		btnCopyToClipboard=null;
		btnConfirm=null;
		
	}

	
	public Frame4_DetectionAidModules_ReverseContract_Bytecode(String contractAddressConstructor) {
		initializeAllVariables();

		
		this.contractAddress = contractAddressConstructor;
UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("merriweather", Font.BOLD, 12)));
		
		setTitle("SCVADetection Bytecode");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		setBounds(450, 30, 420, 600);
		setLocationRelativeTo(null);		
	
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 51, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Merriweather Light", Font.PLAIN, 20));
        textArea.setLineWrap(true);
        textArea.setForeground(new Color(0, 0, 0));
        textArea.setEditable(false);
        
        
        textArea.setText(generateBytecode());
        
        JScrollPane scrollPane = 
                new JScrollPane(textArea, 
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        scrollPane.setBounds(10, 10, 380, 405);
		
        contentPane.add(scrollPane);
        
        btnCopyToClipboard = new JButton("Copy the bytecode to clipboard");
        btnCopyToClipboard.setForeground(new Color(0, 0, 0));
        btnCopyToClipboard.setBackground(new Color(245, 245, 245));
        btnCopyToClipboard.setToolTipText(
        		"<html>\r\n" + 
        		"<body style=\"font-family:merriweather;\">\r\n" + 
        		"Copy the bytecode to clipboard"
        		+"</body>\r\n" + 
        		"</html>"
        		
        		);
        btnCopyToClipboard.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
        btnCopyToClipboard.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        		clipboard.setContents(new StringSelection(textArea.getText()), null);

        		JOptionPane.showMessageDialog(null,
        				"The bytecode has been copied to the clipboard", 
        				"Information", JOptionPane.INFORMATION_MESSAGE);
        		
        		btnCopyToClipboard.setEnabled(false);
        		btnConfirm.setEnabled(true);
        		
        	}
        });
        btnCopyToClipboard.setBounds(10, 446, 380, 35);
        contentPane.add(btnCopyToClipboard);

        
        
        btnConfirm = new JButton("Confirm");
        btnConfirm.setFont(new Font("Brandon Grotesque Regular", Font.PLAIN, 20));
        btnConfirm.setBackground(new Color(245, 245, 245));
        btnConfirm.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
				if(JOptionPane.showOptionDialog(null, "Are you sure you want to confirm?", 
						"Confirmation",
						0, JOptionPane.QUESTION_MESSAGE, null, arrayYesNo,null) == JOptionPane.OK_OPTION) {

					dispose();
					
	        		Frame5_DetectionAidModules_ReverseContract_Browser frame5_DetectionAidModules_ReverseContract_Browser = 
	        				new Frame5_DetectionAidModules_ReverseContract_Browser();
	        		frame5_DetectionAidModules_ReverseContract_Browser.open(contractAddress);

				}
        	}
        });
        btnConfirm.setBounds(108, 504, 180, 35);
        btnConfirm.setEnabled(false);
        
        contentPane.add(btnConfirm);
	
        setVisible(true);
	}

	public static String generateBytecode() 
	{
        
    	String lines_bytecode = "";
    	
    	Pattern pattern;
    	Matcher matcher;
    	
    	
    	try (BufferedReader br = new BufferedReader(new FileReader(new File("Smart Contracts/"+contractAddress+
    			"/Bytecode-OPCode/bytecode.txt")))) {
    	    String line;
    	    while ((line = br.readLine()) != null) {
    	       lines_bytecode +=line;
    	       lines_bytecode +="\n";
    	    }
    	} catch (Exception e) {
    		JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
    		//e.printStackTrace();
    		} 

		
//    	System.out.println(lines_bytecode);

    	
		pattern = Pattern.compile(",\"result\":\"0x(.*)\"}");
		matcher = pattern.matcher(lines_bytecode);
		
		while (matcher.find()) {
			lines_bytecode= matcher.group(1);
		}
    	
    	
		return lines_bytecode;
	}

}
