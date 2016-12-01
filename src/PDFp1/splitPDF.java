package PDFp1;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * The Class splitPDF.
 */
public class splitPDF extends JFrame implements KeyListener {

	/**
	 * The Spltting Window.
	 */
	private static final long serialVersionUID = 1L;

	/** The j 1. */
	JLabel j1;
	
	/** The j 2. */
	JLabel j2;
	
	/** The t 1. */
	JTextField t1;
	
	/** The t 2. */
	JTextField t2;
	
	/** The b 1. */
	JButton b1;
	
	/** The j 4. */
	JLabel j4;
	
	/** The j 5. */
	JLabel j5;
	
	/** The t 3. */
	JTextField t3;

	/** The bc 1. */
	JButton bc1;
	
	/** The bc 2. */
	JButton bc2;

	/** The jtf. */
	//JLabel pagelimit;
	JTextArea jtf;
	
	/** The t 4. */
	JTextField t4;
	
	/** The t 5. */
	JTextField t5;
	
	/** The chk. */
	JCheckBox chk; 

	/**
	 * Instantiates a new split PDF.
	 */
	splitPDF(){
		setTitle("PDF Splitter");
		j1=new JLabel("Select the pdf file:");
		j2=new JLabel("<html>Select the target :<br>(<strong>ex. ..\\file.pdf</strong>)</html>", SwingConstants.LEFT);
		j4=new JLabel("Page limit :");
		j5=new JLabel("Number of Pages : 0");

		j1.setBounds(17,11,120,14);
		j2.setBounds(17, 68, 106, 30);
		j4.setBounds(17,194, 83, 31);
		j5.setBounds(17,140,150, 31);

		t1=new JTextField();
		t1.setToolTipText("Enter the source PDF file with path");
		t2=new JTextField();
		t2.setToolTipText("Enter the target PDF file with path");
		t4=new JTextField("From");
		t4.setToolTipText("Starting page number");

		t1.setBounds(17,27, 367, 31);
		t2.setBounds(17,105, 367, 31);
		t4.setBounds(140, 194, 100, 31);

		bc1=new JButton("Choose");
		bc1.setToolTipText("Click to choose PDF");
		bc2=new JButton("Choose");
		bc2.setToolTipText("Click to choose target path and PDF file");
		b1=new JButton("Split");
		b1.setToolTipText("Click to start Splitting");

		bc1.setBounds(284,58,100,25);
		bc2.setBounds(284,136,100,25);
		b1.setBounds(16,251,140,58);

		jtf=new JTextArea("Ready");
		jtf.setBounds(10,320,374,40);
		jtf.setEditable(false);
		jtf.setFont(new Font("Arial", Font.PLAIN, 15));
		jtf.setToolTipText("Status of Splitting");
		getContentPane().setLayout(null);

		chk=new JCheckBox("Enter ',' separated page numbers");
		chk.setToolTipText("<html>Check to enter ',' seperated page numbers<br> only in From field</html>");
		chk.setBounds(170, 251, 240, 58);
		chk.setSelected(false);

		getContentPane().add(t1);
		getContentPane().add(t2);getContentPane().add(t4);getContentPane().add(j1);getContentPane().add(j2);getContentPane().add(j4);getContentPane().add(bc1);getContentPane().add(bc2);getContentPane().add(b1);
		getContentPane().add(jtf);getContentPane().add(chk);getContentPane().add(j5);

		JLabel lblTo = new JLabel("TO");
		lblTo.setBounds(247, 194, 27, 31);
		getContentPane().add(lblTo);

		t5 = new JTextField("To");
		t5.setToolTipText("Ending page number");
		t5.setBounds(280, 194, 100, 31);
		getContentPane().add(t5);
		t5.setColumns(10);

		setSize(400,400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);

		list l=new list();
		b1.addActionListener(l);
		bc1.addActionListener(l);
		bc2.addActionListener(l);

		t4.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				t4.setText(""); // Empty the text field when it receives focus
			}
			@Override
			public void focusLost(FocusEvent e) {
				// You could do something here when the field loses focus, if you like
				if(t4.getText().equals("")){
					if(chk.isSelected()){
						t4.setText("Write here");
					}else{
						t4.setText("From");
					}
				}
			}
		});
		t5.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				t5.setText(""); // Empty the text field when it receives focus
			}
			@Override
			public void focusLost(FocusEvent e) {
				// You could do something here when the field loses focus, if you like
				if(t5.getText().equals("")){
					t5.setText("To");
				}
			}
		});
		chk.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(chk.isSelected()){
					t5.setText("");
					t4.setText("Write here");
					t5.setEnabled(false);
				}else{
					t5.setEnabled(true);
					t5.setText("To");t4.setText("From");
				}
			}
		});
	}

	/**
	 * Checknumber.
	 *
	 * @param text the text
	 * @return true, if successful
	 */
	public boolean checknumber(String text){
		boolean isNumber=false;
		try {
			double d = Double.parseDouble(text);
			if(d>0){
				isNumber=true;
			}
			d=0;
		} catch (NumberFormatException nfe) {
			// Not a number.
		}
		return isNumber;
	}

	/**
	 * The Class list.
	 */
	class list implements ActionListener{
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==b1){
				if(t1.getText().equals("")||t2.getText().equals("")){
					JOptionPane.showMessageDialog(null,"Required fields are empty! ", "Attention!", JOptionPane.ERROR_MESSAGE);	
				}
				else if(t4.getText().equals("")&&t5.getText().equals("")){
					JOptionPane.showMessageDialog(null,"Both limits should not be empty! ", "Attention!", JOptionPane.ERROR_MESSAGE);	
				}
				else
				{
					if(!t4.getText().equals("")&&!t5.getText().equals("")){

						if(t4.getText().contains(",")&&t5.getText().contains(",")){
							JOptionPane.showMessageDialog(null,"Write ',' terms in only one box and other leave empty! ", "Attention!", JOptionPane.ERROR_MESSAGE);
						}else if(!t4.getText().contains(",")&&!t5.getText().contains(",")){

							if(checknumber(t4.getText())&&checknumber(t5.getText())){

								String x=t4.getText();
								String y=t5.getText();
								//System.out.println(x[0]+"-"+x[1]);
								int i=Integer.parseInt(x);
								int j=Integer.parseInt(y);
								if(i<=j){
									String[] s=new String[(j-i)+1];
									int k=0;
									while(i<=j){
										//System.out.println(Integer.toString(i));
										s[k]=Integer.toString(i);
										i+=1;
										k+=1;
									}
									splitpdf(s);
								}else{
									JOptionPane.showMessageDialog(null,"First is greater than the second please check in Page limits! ", "Attention!", JOptionPane.ERROR_MESSAGE);
								}
							}else{
								JOptionPane.showMessageDialog(null,"Incompatible page number provided! ", "Attention!", JOptionPane.ERROR_MESSAGE);
							}

						}else{
							JOptionPane.showMessageDialog(null,"Write ',' terms in only one box and other leave empty! ", "Attention!", JOptionPane.ERROR_MESSAGE);
						}

					}else if(!t4.getText().equals("")){
						if(t4.getText().contains(",")){
							String[] s=t4.getText().split(",");
							splitpdf(s);
						}else{
							if(checknumber(t4.getText())){
								String[] s={t4.getText()};
								splitpdf(s);
							}else if(checknumber(t4.getText())==false){
								JOptionPane.showMessageDialog(null,"Incompatible page number provided! ", "Attention!", JOptionPane.ERROR_MESSAGE);
							}
							else{
								JOptionPane.showMessageDialog(null,"Page numbers should be in both boxes! ", "Attention!", JOptionPane.ERROR_MESSAGE);
							}}
					}else if(!t5.getText().equals("")){
						if(t5.getText().contains(",")){
							String[] s=t5.getText().split(",");
							splitpdf(s);
						}else{
							if(checknumber(t5.getText())){
								String[] s={t5.getText()};
								splitpdf(s);
							}else if(checknumber(t5.getText())==false){
								JOptionPane.showMessageDialog(null,"Incompatible page number provided! ", "Attention!", JOptionPane.ERROR_MESSAGE);
							}
							else{
								JOptionPane.showMessageDialog(null,"Page numbers should be in both boxes! ", "Attention!", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
			}
			if(e.getSource()==bc1){
				choose_file();	
			}
			if(e.getSource()==bc2){
				choose_dir();
			}
		}
	}

	/**
	 * Splitpdf.
	 *
	 * @param s the s
	 */
	public void splitpdf(String[] s){
		Thread th=new Thread(){
			public void run(){
				try{
					String sh="";
					if(checkPDF(t1.getText())&&checkCreationPDF(t2.getText())){
						Document doc=new Document();
						PdfReader inp=new PdfReader(t1.getText());
						PdfCopy copy = new PdfCopy(doc, new FileOutputStream(t2.getText()));
						historyDir=t2.getText();
						doc.open();

						jtf.setText("Splitting...");
						j5.setText("Number of Pages : "+inp.getNumberOfPages());

						for(int i=0;i<s.length;i++){
							//System.out.println(Integer.parseInt(s[i]));
							try{
								copy.addPage(copy.getImportedPage(inp,Integer.parseInt(s[i])));
							}catch(Exception ev){
								ev.printStackTrace();
								sh=ev.getMessage();
								if(sh.contains("Invalid page")){
									//System.out.println(s);
								}else{
									jtf.setText("Aborted Splitting page"+Integer.parseInt(s[i]));
								}
							}
						}
						doc.close(); 
						if(!sh.equals("")){
							jtf.setText("Saved pdf file: "+t2.getText()+" with some errors please check PDF");
						}else if(sh.equals("")){
							jtf.setText("Saved pdf file: "+t2.getText());
						}else{
							jtf.setText("Something went wrong during splitting!");
						}
						inp.close();
						copy.close();
					}else{
						JOptionPane.showMessageDialog(null,"PDF files or path are not correct! ", "Attention!", JOptionPane.WARNING_MESSAGE);
					}
				}catch(Exception e){
					String sh="";
					e.printStackTrace();
					sh=e.getMessage();
					if(sh.contains("not found as file or resource")){
						JOptionPane.showMessageDialog(null,"Not found PDF file for splitting", "File Error!", JOptionPane.WARNING_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null,"Something went wrong during splitting! ", "Abort!", JOptionPane.WARNING_MESSAGE);
					}}
			}
		};
		th.start();
	}
	
	/** The history file. */
	private File historyFile;
	
	/**
	 * Choose file.
	 */
	public void choose_file(){
		JFileChooser chooser = new JFileChooser();
		if(t1.getText().equals("")){
			chooser.setCurrentDirectory(historyFile);
		}else{
			chooser.setCurrentDirectory(new java.io.File(t1.getText()));
		}
		chooser.setDialogTitle("Select pdf file:");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setFileFilter(new FileNameExtensionFilter("PDF Files","pdf"));
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			j5.setText("Number of Pages : 0");
			historyFile = chooser.getSelectedFile();
			t1.setText(chooser.getSelectedFile().toString());
			jtf.setText("Ready");
			String s1=t1.getText();
			String x="";
			Boolean isfile=false;
			int i=1;
			while(isfile==false){
				x=s1.substring(0,s1.length()-4)+"("+i+")"+".pdf";
				File f = new File(x);
				if(!f.exists() && !f.isDirectory()) { 
					t2.setText(x);
					isfile=true;
				}else{
					i+=1;
					isfile=false;
				}
			}
		}
	}

	/** The history dir. */
	private String historyDir="";
	
	/**
	 * Choose dir.
	 */
	public void choose_dir(){
		JFileChooser chooser = new JFileChooser();
		boolean Check=true;
		boolean test=false;
		if(!t2.getText().equals("")&&test==false){
			if(new File(t2.getText()).isFile()){
				if(t2.getText().endsWith(".pdf")){
					chooser.setCurrentDirectory(new java.io.File(new File(t2.getText()).getParent()));test=true;
				}
			}
			else if(checkCreationPDF(t2.getText())){
				chooser.setCurrentDirectory(new java.io.File(new File(t2.getText()).getParent()));test=true;
			}else if(new File(t2.getText()).isDirectory()){
				chooser.setCurrentDirectory(new java.io.File(t2.getText()));test=true;
			}}
		if(!t1.getText().equals("")&&test==false){
			if(checkPDF(t1.getText())){
				chooser.setCurrentDirectory(new java.io.File(t1.getText()));test=true;
			}
		}
		if(!historyDir.equals("")&&test==false){
			chooser.setCurrentDirectory(new java.io.File(historyDir));test=true;
		}else{
			chooser.setCurrentDirectory(new java.io.File(""));test=true;
		}
		chooser.setDialogTitle("Choose file path with file name");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			if(new File(chooser.getSelectedFile().getAbsolutePath().toString()).isDirectory()){
				String newname=JOptionPane.showInputDialog(null,"Enter new PDF file name", "newpdf");
				if(newname!=null&&!newname.equals("")){
					if(newname.endsWith(".pdf")){
						t2.setText(chooser.getSelectedFile().getAbsolutePath().toString()+"\\"+newname);
					}else{
						t2.setText(chooser.getSelectedFile().getAbsolutePath().toString()+"\\"+newname+".pdf");
					}
					jtf.setText("Ready");
				}else if(newname==null){
					Check=true;
				}else{
					Check=false;
				}
		}
		}
		if(Check==false){choose_dir();}
	}

	/**
	 * Check PDF.
	 *
	 * @param g the g
	 * @return true, if successful
	 */
	//Validation methods
	public boolean checkPDF(String g){
		boolean check=false;
		File f = new File(g);
		if(f.exists() && !f.isDirectory()&& g.endsWith(".pdf")) { 
			// do something
			check=true;
		}
		f=null;
		return check;
	}

	/**
	 * Check creation PDF.
	 *
	 * @param file the file
	 * @return true, if successful
	 */
	public boolean checkCreationPDF(String file){
		boolean check=false;
		File f = new File(file);
		try {
			if(file.endsWith(".pdf")&&checkPDF(file)!=true){
				f.getCanonicalPath();
				if(new File(f.getParent()).isDirectory()&&!f.getName().equals(".pdf")){
					//System.out.println("Perfect - "+f.getName());
					check= true;
				}
			}
		}
		catch (IOException e) {
			check= false;
		}
		f=null;
		return check;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
