import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Simple runnable class for creating algorithm searching threads and managing
 *  the display of its results in a GUI pane
 */
public class CircuitGARunnable extends JPanel implements Runnable
{
	
	public final static int MAX_HEIGHT = 500;
	public final static int MAX_WIDTH = 1000;
	
	/**
	 * the solution we are searching for
	 */
	private TruthTable[] tableToFind;
	
	/**
	 * collection of labels that are updated in realtime
	 */
	private JLabel foundSolutions;
	
	/**
	 * handles drawing the gates to screen
	 */
	private GatesDisplay gatesDisplay;
	
	/**
	 * Start stop button
	 */
	protected JButton startStopButton;
	
	/**
	 * Is the GA currnetly running?
	 */
	private boolean isRunning;
	
	
	public CircuitGARunnable( TruthTable[] tableToFind, String algorithmName)
	{
		super();
		
		this.tableToFind = tableToFind;
		
		/**
		 * Create left control panel
		 */
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout( new BoxLayout(controlPanel, BoxLayout.Y_AXIS) );
		controlPanel.setPreferredSize(new Dimension(200, MAX_HEIGHT));
		
		// add the name of the circuit we are searching for
		Font font = this.getFont();
		Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize()+2);
		final JLabel name = new JLabel(algorithmName);
		name.setForeground( Color.RED );
		name.setFont( boldFont );
		controlPanel.add( name );
		
		foundSolutions = new JLabel();
		controlPanel.add(foundSolutions);
		
		// add start/stop button
		startStopButton = new JButton("Start");
		startStopButton.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent e )
			{
				isRunning = !isRunning;
				if(isRunning){
					name.setForeground( Color.GREEN );
					startStopButton.setText( "Stop" );
				}else{
					name.setForeground( Color.RED );
					startStopButton.setText( "Start" );
				}
			}
		});
		startStopButton.setMaximumSize(new Dimension(200, 40));
		controlPanel.add( startStopButton );
		controlPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		/**
		 * Create right gate GUI
		 */
		gatesDisplay = new GatesDisplay(tableToFind, algorithmName);
		gatesDisplay.setBounds(0, 0, 800, MAX_HEIGHT);
		
		/**
		 * Add to this panel
		 */
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(controlPanel);
		this.add(gatesDisplay);
		this.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));

	}
	
	
	/**
	 * thread entry
	 */
	public void run()
	{
		while(true)
		{
			System.out.print("");
			if(isRunning)
			{
				gatesDisplay.getCircuitGA().reproduce(tableToFind);
			}
		}
    }
	
	/**
	 * Called when the GUI wants the stats updated
	 */
	public void onUpdateGUI()
	{
		foundSolutions.setText( "Found Solutions: "+gatesDisplay.getCircuitGA().getTotalSolutionsFound());
		gatesDisplay.repaint();
	}
	
	private static class GatesDisplay extends JPanel
	{
		
		/**
		 * the algorithm searching for circuits
		 */
		private CircuitGA searchAlgorithm;
		
		public GatesDisplay(TruthTable[] tt, String algorithmName){
			searchAlgorithm = new CircuitGA( tt, algorithmName );
		}
		
		/**
		 * get this instance of CircuitGA
		 * @return
		 */
		public CircuitGA getCircuitGA(){
			return this.searchAlgorithm;
		}
		
		public void paintComponent( Graphics g )
		{
			super.paintComponent( g );
			g.setColor( Color.BLACK );
			g.fillRect( 0, 0, 800, MAX_HEIGHT );
			searchAlgorithm.onPaint(g, MAX_HEIGHT/2);
		}
	}
	
}
