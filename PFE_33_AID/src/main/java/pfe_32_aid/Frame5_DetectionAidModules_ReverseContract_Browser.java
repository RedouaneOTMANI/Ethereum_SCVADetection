/**
 * @author Mélissa MAZROU
 * @author Redouane OTMANI
 * 
 */
package pfe_32_aid;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.apache.commons.io.FileUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Frame5_DetectionAidModules_ReverseContract_Browser {


	protected static Shell shlBrowser;

	public static String contractAddress=null;
	public static Button btnGetSourceCode=null;
	public static Button btnConfirm=null;
	public static boolean b=false;
	
	
	/**
	 * Launch the application.
	 * @param args
	 */


	public static void initializeAllVariables() {
		shlBrowser=null;

		contractAddress=null;
		btnGetSourceCode=null;
		btnConfirm=null;
		b=false;
		
	}
	
	/**
	 * Open the window.
	 */
	public void open(String contractAddressConstructor) {
		initializeAllVariables();
		
		this.contractAddress=contractAddressConstructor;
UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("merriweather", Font.BOLD, 12)));

		Display display = Display.getDefault();
		createContents();
		shlBrowser.open();
		shlBrowser.layout();
		while (!shlBrowser.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlBrowser = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.ON_TOP);
		shlBrowser.setBackground(SWTResourceManager.getColor(0, 51, 102));
		shlBrowser.setSize(428, 600);

		//To center the window.
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - shlBrowser.getSize().x) / 2);
	    int y = (int) ((dimension.getHeight() - shlBrowser.getSize().y) / 2);
		shlBrowser.setLocation(x, y);
		
	    
	    
		shlBrowser.setText("SCVADetection Browser");
		shlBrowser.setLayout(null);


		Browser browser = new Browser(shlBrowser, SWT.NONE);
		browser.setFont(SWTResourceManager.getFont("Bell MT", 15, SWT.NORMAL));
		browser.setUrl("https://ethervm.io/decompile#decompile");

		browser.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if(!b) {

					JOptionPane optionPane = new JOptionPane("Please paste the bytecode of the contract below into the reserved field.  "
			        		+ "\nThen click on\"Decompile\"", JOptionPane.INFORMATION_MESSAGE);
			        
			        JDialog dialog = optionPane.createDialog("Decompilating");
			        
			        dialog.setAlwaysOnTop(true);
			        dialog.setVisible(true);
					
			        
					b=true;	
				}else {browser.removeMouseMoveListener(this);}
			}
		});
		
		browser.setBounds(21, 24, 378, 406);

		//**********************************************************************
		//**********************************************************************
		btnGetSourceCode = new Button(shlBrowser, SWT.NONE);

		btnGetSourceCode.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e){
				btnGetSourceCode.setEnabled(false);
				getReversedCodeSource(browser.getText());
				btnConfirm.setEnabled(true);
		    }
		});
		
		
		btnGetSourceCode.setText("Retrieve contract source code");
		btnGetSourceCode.setFont(SWTResourceManager.getFont("Brandon Grotesque Regular", 12, SWT.NORMAL));
		btnGetSourceCode.setBounds(21, 451, 378, 35);

		
		
		
		//***********************************************************************
		btnConfirm = new Button(shlBrowser, SWT.NONE);

		btnConfirm.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e){
					//shlBrowser.dispose();
					shlBrowser.close();
					
			}
		});
		
		btnConfirm.setFont(SWTResourceManager.getFont("Brandon Grotesque Regular", 12, SWT.NORMAL));
		btnConfirm.setBounds(114, 504, 180, 35);
		btnConfirm.setText("Confirm");
		btnConfirm.setEnabled(false);


	}
	
	
	public static void getReversedCodeSource(String contentBrowser) {
		
		try {
			
			creatingDirectory("Smart Contracts/"+contractAddress+"/Contract_Reverted_Source_Code/");

			// parse file as HTML document
		    Document doc = Jsoup.parse(contentBrowser, "UTF-8");
			
			
		    
		    /********Get public and internal methods**************/
		    String elements[] = 

		    		Jsoup.parse(
			    		Jsoup.parse(doc.getElementsByAttribute("data-server-rendered").toString(),"UTF-8")
			    		.getElementsByTag("tt").toString()
		    		).wholeText().toString().split("\n");
		    

			String linePublicMethods="";
			String lineInternalMethods="";

			
		    for (int i = 0; i < elements.length ; i++) {
		    	if(elements[i].contains("0x")) {
		    		linePublicMethods += "Signature: "+elements[i]+" Function:"+elements[i+1];
		    		linePublicMethods += "\n";
		    		i++;
		    	}else {
		    		lineInternalMethods += elements[i];
		    		lineInternalMethods += "\n";
		    	}
			}
		    
		    

			PrintWriter filePublicMethods = 
					new PrintWriter("Smart Contracts/"
							+contractAddress
							+"/Contract_Reverted_Source_Code/"
							+"PublicMethods.sol", "UTF-8");
			PrintWriter fichierInternalMethods = new PrintWriter("Smart Contracts/"
					+contractAddress
					+"/Contract_Reverted_Source_Code/"
					+"InternalMethods.sol","UTF-8");
			
			
			filePublicMethods.append(linePublicMethods);
			fichierInternalMethods.append(lineInternalMethods);
			
			filePublicMethods.close();
			fichierInternalMethods.close();
				
				
		    /**********************SOURCE CODE ******************************************************/
		 String elements1 = doc.getElementsByClass("code javascript hljs").text();
			
		    PrintWriter file = new PrintWriter("Smart Contracts/"
					+contractAddress
					+"/Contract_Reverted_Source_Code/"
		    		+"SourceCode.sol", "UTF-8");

			
			file.append(
					elements1.replace("{", "{\n").
					replace("}", "}\n").
					replace(";", ";\n").
					replace("\n }","}").
					replace("function", "\nfunction").
					replace("() var","() \n var").
					replace(") var", ") \n var").
					replace("! }","!\n}")
					);
			
			file.close();
		    
			
			//System.out.println("Fin");
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			} 
	}
	
	
	public static void creatingDirectory(String dirName) {
	    //Creating a File object
	    File file = new File(dirName);
	    //Creating the directory
	    boolean boolCreationRepertoire = file.mkdir();

	    //
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


	
	
	
}
