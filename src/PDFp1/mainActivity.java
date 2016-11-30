package PDFp1;

import javax.swing.UIManager;

// TODO: Auto-generated Javadoc
/**
 * The Class main.
 */
public class mainActivity {
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				PDFs p = new PDFs();
				p.init();
			}
		});
	}
}
