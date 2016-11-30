package PDFp1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;

import com.sun.pdfview.FullScreenWindow;
import com.sun.pdfview.OutlineNode;
import com.sun.pdfview.PDFDestination;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFObject;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PageChangeListener;
import com.sun.pdfview.PagePanel;
import com.sun.pdfview.action.GoToAction;
import com.sun.pdfview.action.PDFAction;


// TODO: Auto-generated Javadoc
/**
 * The Class PDFs.
 */
public class PDFs extends JFrame implements KeyListener, TreeSelectionListener,PageChangeListener {
	
	/**
	 * The Main GUI Window for the Tiny PDF interface.
	 */
	private static final long serialVersionUID = 1L;

	/** The Constant header. */
	public final static String header = "Tiny PDF";
	
	/** The Constant FIRST_PAGE. */
	private static final int FIRST_PAGE = 1;
	
	/** The current page. */
	private int currentPage = FIRST_PAGE;
	
	/** The page field. */
	JTextField pageField;
	
	/**  The current PDFFile. */
	PDFFile curFile;
	
	/**  the name of the current document. */
	String docName;
	
	/**  The page display. */
	PagePanel page;
	
	/**  The full screen page display, or null if not in full screen mode. */
	PagePanel fspp;
	
	/** The num pages. */
	int numPages;
	
	/** The scrollpane. */
	JScrollPane scrollpane;
	
	/** The jp. */
	JPanel jp;
	
	/**  the root of the outline, or null if there is no outline. */
	OutlineNode outline = null;
	
	/**  the window containing the pdf outline, or null if one doesn't exist. */
	JDialog olf;
	
	/**  the full screen button. */
	JToggleButton fullScreenButton;
	
	/**  the full screen window, or null if not in full screen mode. */
	FullScreenWindow fullScreen;

	/**
	 * Checks for next page.
	 *
	 * @param numPages the num pages
	 * @return true, if successful
	 */
	/*Utilities*/
	private boolean hasNextPage(int numPages) {
		return (currentPage+1) <= numPages;
	}

	/**
	 * Checks for previous page.
	 *
	 * @return true, if successful
	 */
	private boolean hasPreviousPage() {
		return (currentPage-1) >= FIRST_PAGE;
	}

	/** The close pdf. */
	/// FILE MENU And ACTIONS
	JMenuItem closePdf;
	
	/** The flscmode. */
	JMenuItem flscmode;

	/**
	 * Gets the filemenu.
	 *
	 * @return the filemenu
	 */
	JMenu getfilemenu(){
		return this.getJMenuBar().getMenu(0);
	}
	
	/**
	 * Gets the viewmenu.
	 *
	 * @return the viewmenu
	 */
	JMenu getviewmenu(){
		return this.getJMenuBar().getMenu(1);
	}
	
	/**
	 * Gets the toolsmenu.
	 *
	 * @return the toolsmenu
	 */
	JMenu gettoolsmenu(){
		return this.getJMenuBar().getMenu(2);
	}
	
	/**
	 * Gets the tinymenu.
	 *
	 * @return the tinymenu
	 */
	JMenu getTinymenu(){
		return this.getJMenuBar().getMenu(3);
	}

	/**
	 * The Class listenmenu.
	 */
	class listenmenu implements ActionListener{
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			//new menu in file menu
			if(e.getSource()==getfilemenu().getItem(0))
			{
				PdfOpen();
			}else if(e.getSource()==getfilemenu().getItem(1))
			{
				doClose();
			}else if(e.getSource()==getfilemenu().getItem(3))
			{
				doQuit();
			}else if(e.getSource()==gettoolsmenu().getItem(0))
			{
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						new splitPDF();
					}
				});
			}else if(e.getSource()==gettoolsmenu().getItem(1))
			{
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						new mergePDF();
					}
				});

			}
			else if(e.getSource()==getTinymenu().getItem(0))
			{
				new aboutPDF();
			}
		}
	}

	/** The full screen action. */
	Action fullScreenAction = new AbstractAction("Enter Full Screen") {
		public void actionPerformed(ActionEvent evt) {
			doFullScreen((evt.getModifiers() & evt.SHIFT_MASK) != 0);
		}
	};

	/** The next action. */
	Action nextAction = new AbstractAction("Next") {

		public void actionPerformed(ActionEvent evt) {
			pageNext();
		}
	};
	
	/** The first action. */
	Action firstAction = new AbstractAction("First") {

		public void actionPerformed(ActionEvent evt) {
			pageFirst();
		}
	};
	
	/** The last action. */
	Action lastAction = new AbstractAction("Last") {

		public void actionPerformed(ActionEvent evt) {
			pageLast();
		}
	};
	
	/** The prev action. */
	Action prevAction = new AbstractAction("Prev") {

		public void actionPerformed(ActionEvent evt) {
			pagePrev();
		}
	};


	/** The zoom tool action. */
	Action zoomToolAction = new AbstractAction("Zoom Allow") {

		public void actionPerformed(ActionEvent evt) {
			doZoomTool();
		}
	};
	
	/** The fit in window action. */
	Action fitInWindowAction = new AbstractAction("Fit in window") {

		public void actionPerformed(ActionEvent evt) {
			doFitInWindow();
		}
	};

	/**
	 * Open PDF.
	 *
	 * @param file the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void openPDF(File file) throws IOException {
		//System.out.println("Heap Size = " + heapSize);
		// first open the file for random access
		@SuppressWarnings({ "resource" })
		RandomAccessFile raf = new RandomAccessFile(file, "r");

		// extract a file channel
		FileChannel channel = raf.getChannel();

		// now memory-map a byte-buffer
		ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
		// readPDFBuffer(buf, file.getPath(), file.getName());
		curFile = new PDFFile(buf);
		numPages=curFile.getNumPages();
		// show the first page
		PDFPage pg = curFile.getPage(0);
		currentPage=FIRST_PAGE;
		pageField.setText(String.valueOf(currentPage));
		page.showPage(pg);
		setEnabling();
		setTitle(header + ": " + file.getName());

		// if the PDF has an outline, display it.
		try {
			outline = curFile.getOutline();
		} catch (IOException ioe) {
		}
		if (outline != null) {
			if (outline.getChildCount() > 0) {
				olf = new JDialog(this, "Outline");
				olf.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				olf.setLocation(this.getLocation());
				JTree jt = new JTree(outline);
				jt.setRootVisible(false);
				jt.addTreeSelectionListener(this);
				JScrollPane jsp = new JScrollPane(jt);
				olf.getContentPane().add(jsp);
				olf.pack();
				olf.setVisible(true);
			} else {
				if (olf != null) {
					olf.setVisible(false);
					olf = null;
				}
			}
		}
	}

	/**
	 * Go page.
	 *
	 * @param pageNumber the page number
	 */
	public void goPage(int pageNumber){
		currentPage = pageNumber;
		PDFPage pageNew = curFile.getPage(currentPage);
		page.showPage(pageNew);
		pageField.setText(String.valueOf(currentPage));
		if (fspp != null) {
			fspp.showPage(pageNew);
			fspp.requestFocus();
		}
	}

	//Navigations
	/**
	 * Goes to the next page.
	 */
	public void pageNext() {
		numPages = curFile.getNumPages();
		if(hasNextPage(numPages)){
			currentPage++;
			goPage(currentPage);
		}
		//System.out.println(">");
	}

	/**
	 * Goes to the previous page.
	 */
	public void pagePrev() {
		if(hasPreviousPage()){
			currentPage--;
			goPage(currentPage);
		}
		//System.out.println("<");
	}

	/**
	 * Goes to the first page.
	 */
	public void pageFirst() {
		goPage(FIRST_PAGE);
		//System.out.println("<<");
	}

	/**
	 * Goes to the last page.
	 */
	public void pageLast() {
		goPage(numPages);
		//System.out.println(">>");
	}

	/**
	 * Goes to the page that was typed in the page number text field.
	 */
	public void doPageTyped(){
		String text = pageField.getText();
		boolean isValid = false;
		if (text!=null&&text!="") {
			boolean isNumber=false;
			try {
				double d = Double.parseDouble(text); 
				isNumber=true;
				d=0;
			} catch (NumberFormatException nfe) {
				// Not a number.
			}
			if (isNumber) {
				int pageNumber = Integer.valueOf(text);
				if (pageNumber >= 1 && pageNumber <= numPages) {
					goPage(Integer.valueOf(text));
					isValid = true;
				}
			}
		}
		if (!isValid) {
			pageField.setText(String.valueOf(currentPage));
			goPage(currentPage);
		}
	}

	/**
	 * Turns on zooming.
	 */
	public void doZoomTool() {
		if (fspp == null) {
			page.useZoomTool(true);
		}
	}

	/**
	 * Turns off zooming; makes the page fit in the window.
	 */
	public void doFitInWindow() {
		if (fspp == null) {
			page.useZoomTool(false);
			page.setClip(null);
		}
	}

	//PDF Chooser
	/**
	 * A file filter for PDF files.
	 */
	FileFilter pdfFilter = new FileFilter() {

		public boolean accept(File f) {
			return f.isDirectory() || f.getName().endsWith(".pdf");
		}

		public String getDescription() {
			return "Choose a PDF file";
		}
	};
	
	/** The history file. */
	private File historyFile;

	/**
	 * Ask the user for a PDF file to open from the local file system.
	 */
	public void PdfOpen(){
		try{
			JFileChooser fch=new JFileChooser();
			fch.setCurrentDirectory(historyFile);
			fch.setFileFilter(pdfFilter);
			fch.setMultiSelectionEnabled(false);
			int returnVal=fch.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				historyFile = fch.getSelectedFile();
				//System.out.println(historyFile.toString());
				openPDF(fch.getSelectedFile());
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"File not supported :"+e.getMessage(), "Error opening file",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			PdfOpen();
		}
	}


	/**
	 * Close the current document.
	 */
	public void doClose() {
		setFullScreenMode(false, false);
		page.showPage(null);
		curFile = null;
		setTitle(header);
		pageField.setText(" - ");
		setEnabling();
	}
	/**
	 * Shuts down all known threads.  This ought to cause the JVM to quit
	 * if the PDFViewer is the only application running.
	 */
	public void doQuit() {
		doClose();
		dispose();
		System.exit(0);
	}

	/**
	 * Initialize the main GUI.
	 */
	public void init(){
		//set up the frame and panel
		setTitle(header);
		page = new PagePanel();
		page.addKeyListener(this);
		page.setBackground(Color.GRAY);
		this.add(page);

		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		JButton jb;
		toolbar.add(Box.createHorizontalGlue());

		jb = new JButton(firstAction);
		jb.setToolTipText("First Page");
		jb.setBackground(Color.GRAY);
		jb.setForeground(Color.WHITE);
		jb.setFocusPainted(false);
		jb.setFont(new Font("Tahoma", Font.BOLD, 20));
		jb.setText("<<");
		toolbar.add(jb);
		jb = new JButton(prevAction);
		jb.setBackground(new Color(55, 89, 182));
		jb.setForeground(Color.WHITE);
		jb.setFocusPainted(false);
		jb.setFont(new Font("Tahoma", Font.BOLD, 20));
		jb.setToolTipText("Prev Page");
		jb.setText("<");
		toolbar.add(jb);
		pageField = new JTextField(" - ", 3);
		pageField.setToolTipText("Enter the page number");
		//	pageField.setEnabled(false);
		pageField.setMaximumSize(new Dimension(45, 32));
		pageField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				doPageTyped();
			}
		});
		toolbar.add(pageField);
		jb = new JButton(nextAction);
		jb.setBackground(new Color(55, 89, 182));
		jb.setForeground(Color.WHITE);
		jb.setFocusPainted(false);
		jb.setFont(new Font("Tahoma", Font.BOLD, 20));
		jb.setToolTipText("Next Page");
		jb.setText(">");
		toolbar.add(jb);
		jb = new JButton(lastAction);
		jb.setBackground(Color.GRAY);
		jb.setForeground(Color.WHITE);
		jb.setFocusPainted(false);
		jb.setFont(new Font("Tahoma", Font.BOLD, 20));
		jb.setToolTipText("Last Page");
		jb.setText(">>");
		toolbar.add(jb);

		toolbar.add(Box.createHorizontalGlue());

		JToggleButton jtb;
		ButtonGroup bg = new ButtonGroup();

		jtb = new JToggleButton(zoomToolAction);
		jtb.setBackground(Color.MAGENTA);
		jtb.setForeground(Color.black);
		jtb.setFocusPainted(false);
		jtb.setFont(new Font("Tahoma", Font.BOLD, 20));
		jtb.setToolTipText("Zoom Mode Enter");
		jtb.setText("+");
		bg.add(jtb);
		toolbar.add(jtb);
		toolbar.add(Box.createHorizontalStrut(10));
		jtb = new JToggleButton(fitInWindowAction);
		jtb.setBackground(Color.ORANGE);
		jtb.setForeground(Color.black);
		jtb.setFocusPainted(false);
		jtb.setFont(new Font("Tahoma", Font.BOLD, 20));
		jtb.setToolTipText("Fit to Window");
		jtb.setText("-");
		bg.add(jtb);
		jtb.setSelected(true);
		toolbar.add(jtb);

		toolbar.add(Box.createHorizontalGlue());
		fullScreenButton = new JToggleButton(fullScreenAction);
		fullScreenButton.setBackground(SystemColor.menu);
		fullScreenButton.setForeground(Color.black);
		fullScreenButton.setFocusPainted(false);
		fullScreenButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		fullScreenButton.setText(" F ");
		fullScreenButton.setToolTipText("Enter the fullscreen mode");
		toolbar.add(fullScreenButton);
		fullScreenButton.setEnabled(true);

		JMenuBar mb = new JMenuBar();
		JMenu filem = new JMenu(" File");
		filem.setMnemonic('f');
		filem.setToolTipText("File");
		JMenu view = new JMenu(" View");
		view.setToolTipText("View");
		view.setMnemonic('v');
		JMenu tools = new JMenu(" Tools");
		tools.setToolTipText("Tools");
		tools.setMnemonic('t');
		JMenu tiny = new JMenu(" Tiny PDF");
		tiny.setToolTipText("Tiny PDF");
		tiny.setMnemonic('p');

		JMenuItem openPdf=new JMenuItem("Open PDF");
		openPdf.setToolTipText("Open PDF");
		openPdf.setMnemonic('o');
		openPdf.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.ALT_MASK | ActionEvent.SHIFT_MASK));//Open PDF
		openPdf.addActionListener(new listenmenu());
		filem.add(openPdf);

		closePdf=new JMenuItem("Close PDF");
		closePdf.setToolTipText("Close current open PDF");
		closePdf.setMnemonic('c');
		closePdf.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.ALT_MASK | ActionEvent.SHIFT_MASK));//Close current PDF
		closePdf.addActionListener(new listenmenu());
		filem.add(closePdf);
		filem.addSeparator();

		JMenuItem quitPdf=new JMenuItem("Quit Program");
		quitPdf.setToolTipText("Quit");
		quitPdf.setMnemonic('q');
		quitPdf.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,ActionEvent.ALT_MASK | ActionEvent.SHIFT_MASK));//Quit Program
		quitPdf.addActionListener(new listenmenu());
		filem.add(quitPdf);

		flscmode=new JMenuItem("Enter Full Screen");
		flscmode.setToolTipText("Full Screen Mode");
		flscmode.setAction(fullScreenAction);
		flscmode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.ALT_MASK | ActionEvent.SHIFT_MASK));//Enter full Screen
		flscmode.setMnemonic('e');
		view.add(flscmode);

		JMenuItem splitPdf=new JMenuItem("Split PDF");
		splitPdf.setToolTipText("Split PDF");
		splitPdf.setMnemonic('s');
		splitPdf.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.ALT_MASK | ActionEvent.SHIFT_MASK));//Quit Program
		splitPdf.addActionListener(new listenmenu());
		tools.add(splitPdf);

		JMenuItem mergePdf=new JMenuItem("Merge PDF");
		mergePdf.setToolTipText("Merge PDF");
		mergePdf.setMnemonic('m');
		mergePdf.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,ActionEvent.ALT_MASK | ActionEvent.SHIFT_MASK));//Quit Program
		mergePdf.addActionListener(new listenmenu());
		tools.add(mergePdf);

		JMenuItem aboutPdf=new JMenuItem("About");
		aboutPdf.setToolTipText("About Tiny PDF");
		aboutPdf.setMnemonic('a');
		aboutPdf.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.ALT_MASK | ActionEvent.SHIFT_MASK));//Quit Program
		aboutPdf.addActionListener(new listenmenu());
		tiny.add(aboutPdf);

		mb.add(filem);
		mb.add(view);
		mb.add(tools);
		mb.add(tiny);
		setJMenuBar(mb);
		getContentPane().add(toolbar, BorderLayout.NORTH);
		/*
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
				int x = (screen.width - frame.getWidth()) / 2;
				int y = (screen.height - frame.getHeight()) / 2;
				frame.setLocation(x, y);
		 */
		//frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setLocationRelativeTo(null);

		PDFPage panel = null;
		page.showPage(panel);
		setEnabling();
	}

	/**
	 * Enable or disable all of the actions based on the current state.
	 */
	public void setEnabling() {
		boolean isopenfile=(curFile != null&&((fspp != null) ? fspp.getPage() != null : page.getPage() != null));
		boolean pageenough=(isopenfile&&numPages>1);
		pageField.setEnabled(pageenough);
		closePdf.setEnabled(isopenfile);
		prevAction.setEnabled(pageenough);
		nextAction.setEnabled(pageenough);
		firstAction.setEnabled(pageenough);
		lastAction.setEnabled(pageenough);
		zoomToolAction.setEnabled(isopenfile);
		fitInWindowAction.setEnabled(isopenfile);
		flscmode.setEnabled(isopenfile);
		fullScreenButton.setEnabled(isopenfile);
		fullScreenAction.setEnabled(isopenfile);
	}


	/* (non-Javadoc)
	 * @see com.sun.pdfview.PageChangeListener#gotoPage(int)
	 */
	@Override
	public void gotoPage(int arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * Handle a key press for navigation.
	 *
	 * @param evt the evt
	 */
	public void keyPressed(KeyEvent evt) {
		int code = evt.getKeyCode();
		if (code == KeyEvent.VK_LEFT) {
			pagePrev();
		} else if (code == KeyEvent.VK_RIGHT) {
			pageNext();
		} else if (code == KeyEvent.VK_UP) {
			pagePrev();
		} else if (code == KeyEvent.VK_DOWN) {
			pageNext();
		} else if (code == KeyEvent.VK_HOME) {
			pageFirst();
		} else if (code == KeyEvent.VK_END) {
			pageLast();
		} else if (code == KeyEvent.VK_PAGE_UP) {
			pagePrev();
		} else if (code == KeyEvent.VK_PAGE_DOWN) {
			pageNext();
		} else if (code == KeyEvent.VK_SPACE) {
			pageNext();
		} else if (code == KeyEvent.VK_ESCAPE) {
			setFullScreenMode(false, false);
		}
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

	/**
	 * Enter full screen mode.
	 *
	 * @param force true if the user should be prompted for a screen to
	 * use in a multiple-monitor setup.  If false, the user will only be
	 * prompted once.
	 */
	public void doFullScreen(boolean force) {
		setFullScreenMode(fullScreen == null, force);
	}

	/**
	 * Runs the FullScreenMode change in another thread.
	 */
	class PerformFullScreenMode implements Runnable {
		
		/** The force. */
		boolean force;
		
		/**
		 * Instantiates a new perform full screen mode.
		 *
		 * @param forcechoice the forcechoice
		 */
		public PerformFullScreenMode(boolean forcechoice) {
			force = forcechoice;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			fspp = new PagePanel();
			fspp.setBackground(Color.black);
			page.showPage(null);
			fullScreen = new FullScreenWindow(fspp, force);
			fspp.addKeyListener(PDFs.this);
			goPage(currentPage);
			flscmode.setEnabled(true);
		}
	}

	/**
	 * Starts or ends full screen mode.
	 * @param full true to enter full screen mode, false to leave
	 * @param force true if the user should be prompted for a screen
	 * to use the second time full screen mode is entered.
	 */
	public void setFullScreenMode(boolean full, boolean force) {
		//	curpage= -1;
		if (full && fullScreen == null) {
			flscmode.setEnabled(false);
			new Thread(new PerformFullScreenMode(force),
					getClass().getName() + ".setFullScreenMode").start();
			fullScreenButton.setSelected(true);
		} else if (!full && fullScreen != null) {
			fullScreen.close();
			fspp = null;
			fullScreen = null;
			goPage(currentPage);
			fullScreenButton.setSelected(false);
		}
	}

	/**
	 * Someone changed the selection of the outline tree.  Go to the new
	 * page.
	 *
	 * @param e the e
	 */
	public void valueChanged(TreeSelectionEvent e) {
		if (e.isAddedPath()) {
			OutlineNode node = (OutlineNode) e.getPath().getLastPathComponent();
			if (node == null) {
				return;
			}
			try {
				PDFAction action = node.getAction();
				if (action == null) {
					return;
				}
				if (action instanceof GoToAction) {
					PDFDestination dest = ((GoToAction) action).getDestination();
					if (dest == null) {
						return;
					}
					PDFObject page = dest.getPage();
					if (page == null) {
						return;
					}
					int pageNum = curFile.getPageNumber(page);
					if (pageNum > 0) {
						goPage(pageNum);
					}
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}