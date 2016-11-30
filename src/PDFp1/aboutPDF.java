package PDFp1;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class aboutPDF.
 */
public class aboutPDF extends JFrame{
	
	/**
	 * The About Window.
	 */
	private static final long serialVersionUID = 1L;

	/** The j 1. */
	JLabel j1;
	
	/** The j 2. */
	JLabel j2;
	
	/** The jtf. */
	JTextPane jtf;
	
	/** The scroll. */
	JScrollPane scroll;

	/**
	 * Instantiates a new about PDF.
	 */
	aboutPDF(){
		setTitle("About Tiny PDF");
		j1=new JLabel("Tiny PDF");
		j1.setHorizontalAlignment(SwingConstants.CENTER);
		j1.setBackground(SystemColor.textHighlight);
		j1.setToolTipText("Tiny PDF");
		j1.setBounds(110, 0, 176, 80);
		j2=new JLabel("Copyright 2016 by Sameer Satyam");
		j2.setBounds(90, 351, 230, 20);
		jtf=new JTextPane();
		jtf.setBounds(1,1,370,600);
		jtf.setEditable(false);
		jtf.setFont(new Font("Arial", Font.PLAIN, 15));
		j1.setFont(new Font("Arial Black", Font.PLAIN, 36));

		jtf.setText("Tiny PDF is a lighweight PDF viewer and spliting/merging tool developed in JAVA prgramming language. This is not a complete PDF suite but can only be used for minor works specifically on PDF files.\n\nThis is a small project made by the author Sameer Satyam while learning JAVA Prgramming Language and Swing/Awt GUI libraries.\n\nLinks:- \nGithub Repo - https://github.com/satyamsameer/TinyPDF \nEmail - sameersatyam01@gmail.com \n\nPlease read ReadMe file for more information. Thanks for using and trying this tiny software. :)");

		scroll = new JScrollPane(jtf,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(10, 80, 374, 270);
		scroll.setAutoscrolls(true);
		jtf.setEditable(false);
		getContentPane().setLayout(null);

		getContentPane().add(j1);
		getContentPane().add(scroll);
		getContentPane().add(j2);

		setSize(400,400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
}