package PDFp1;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

// TODO: Auto-generated Javadoc
/**
 * The Class mergePDF.
 */
public class mergePDF extends JFrame implements KeyListener {
	
	/**
	 * The Merging Window.
	 */
	private static final long serialVersionUID = 1L;

	/** The j 2. */
	JLabel j2;
	
	/** The j 3. */
	JLabel j3;
	
	/** The t 2. */
	JTextField t2;
	
	/** The t 3. */
	JTextField t3;
	
	/** The b 2. */
	JButton b2;

	/** The bc 2. */
	JButton bc2;
	
	/** The bc 3. */
	JButton bc3;
	
	/** The jtf. */
	JTextArea jtf;
	
	/** The scroll. */
	JScrollPane scroll;

	/**
	 * Instantiates a new merge PDF.
	 */
	mergePDF() {
		setTitle("PDF Merger");
		j2=new JLabel("<html>Select the target :<br>(<strong>ex. ..\\file.pdf</strong>)</html>", SwingConstants.LEFT);
		j2.setToolTipText("Select target");
		j3=new JLabel("Select multiple PDFs:");
		j3.setToolTipText("Select Multiple PDFs");
		t2=new JTextField("");
		t2.setToolTipText("Enter the target PDF file with path");
		t3=new JTextField("");
		t3.setToolTipText("Enter Multiple PDFs files with  path separated by ';'");

		b2=new JButton("Merge");
		bc2=new JButton("Choose");
		bc3=new JButton("Choose");
		bc2.setToolTipText("Click to choose target path and PDF file");
		bc3.setToolTipText("Click to choose multiple PDFs");
		jtf=new JTextArea("Ready");

		j3.setBounds(17,11,120,14);
		j2.setBounds(17, 68, 106, 30);
		t3.setBounds(17,27, 367, 31);
		t2.setBounds(17,105, 367, 31);
		bc3.setBounds(284,58,100,25);
		bc2.setBounds(284,136,100,25);
		b2.setBounds(16,151,140,58);
		b2.setToolTipText("Click to start Merging");
		jtf.setBounds(1,1,370,600);
		jtf.setEditable(false);
		jtf.setFont(new Font("Arial", Font.PLAIN, 15));
		getContentPane().setLayout(null);

		scroll = new JScrollPane(jtf,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setAutoscrolls(true);
		scroll.setBounds(10,220,374,140);
		jtf.setLineWrap(true);
		jtf.setWrapStyleWord(true);
		jtf.setEditable(false);
		jtf.setToolTipText("Status/Errors of Merging");

		getContentPane().add(t2);
		getContentPane().add(t3);
		getContentPane().add(j2);
		getContentPane().add(j3);
		getContentPane().add(b2);
		getContentPane().add(bc2);
		getContentPane().add(bc3);
		getContentPane().add(scroll);

		setSize(400,400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

		list l=new list();
		b2.addActionListener(l);
		bc2.addActionListener(l);
		bc3.addActionListener(l);

	}

	/**
	 * Mergepdf.
	 */
	public void mergepdf(){
		Thread th=new Thread(){
			public void run(){
				try {
					String[] files =t3.getText().split(";");
					Document doc = new Document();
					PdfCopy copy = new PdfCopy(doc, new FileOutputStream(t2.getText()));
					doc.open();
					PdfReader inp;
					int number_of_pages;
					for (int i = 0; i < files.length; i++) {
						if(checkPDF(files[i])){
							inp = new PdfReader(files[i]);
							historyDir=files[i];
							number_of_pages = inp.getNumberOfPages();
							for (int page = 0; page < number_of_pages; ) {
								copy.addPage(copy.getImportedPage(inp, ++page));
							}
							jtf.append("\nMerged : "+files[i]);
						}else{
							jtf.append("\nNot Valid PDF File : "+files[i]);
						}
					}
					doc.close();
					jtf.append("\n=======================================");
					jtf.append("\nSaved pdf file: "+t2.getText());
					jtf.append("\n=======================================");
					copy.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					jtf.append("\nSomething went wrong during merging!");
					JOptionPane.showMessageDialog(null,"Something went wrong during merging! ", "Abort!", JOptionPane.WARNING_MESSAGE);
				}
			}
		};
		th.start();
	}

	/**
	 * The Class list.
	 */
	class list implements ActionListener{
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==bc3){
				choose_multi();
			}
			if(e.getSource()==bc2){
				choose_dir();
			}
			if(e.getSource()==b2){
				if(t3.getText().equals("")||t2.getText().equals("")){
					JOptionPane.showMessageDialog(null,"Required fields are empty! ", "Attention!", JOptionPane.ERROR_MESSAGE);	
				}
				else if(checkCreationPDF(t2.getText())){
					mergepdf();
					jtf.setText("Ready");
				}else{
					JOptionPane.showMessageDialog(null,"Check PDF files or path may be incorrect! ", "Attention!", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
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

	/** The history value. */
	private String historyDir="";
	
	/**
	 * Choose target PDF.
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
			}else if(checkCreationPDF(t2.getText())){
				chooser.setCurrentDirectory(new java.io.File(new File(t2.getText()).getParent()));test=true;
			}else if(new File(t2.getText()).isDirectory()){
				chooser.setCurrentDirectory(new java.io.File(t2.getText()));test=true;
			}}
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
	 * Choose multiple PDFs.
	 */
	public void choose_multi(){//Choose multiple pdf
		JFileChooser chooser = new JFileChooser();
		if(t3.getText().equals("")){
			chooser.setCurrentDirectory(new java.io.File(""));
		}else{
			if(t3.getText().contains(".pdf")){
				if(t3.getText().contains(";")){
					String[] files =t3.getText().split(";");
					if(checkPDF(files[0])){
						chooser.setCurrentDirectory(new java.io.File(files[0]));
					}else{
						chooser.setCurrentDirectory(new java.io.File(""));
					}
				}else{
					if(checkPDF(t3.getText())){
						chooser.setCurrentDirectory(new java.io.File(t3.getText()));
					}else{
						chooser.setCurrentDirectory(new java.io.File(""));
					}
				}
			}
		}
		chooser.setDialogTitle("Choose file path with file name");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setMultiSelectionEnabled(true);
		chooser.setFileFilter(new FileNameExtensionFilter("PDF Files","pdf"));
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File[] file=chooser.getSelectedFiles();
			String x="";
			int i=0;
			while(i<=file.length-1){
				if(i==file.length-1){
					x+=file[i].toString();
				}else{
					x+=file[i].toString()+";";
				}
				i+=1;
			}
			t3.setText(x);
			jtf.setText("Ready");
		}
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
