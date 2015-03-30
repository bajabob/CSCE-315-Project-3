import java.awt.Color;
import java.awt.Font;
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
public class CircuitTreeRunnable extends JPanel implements Runnable
{
	
	/**
	 * the solution we are searching for
	 */
	private TruthTable tableToFind;
	
	/**
	 * the algorithm searching for circuits
	 */
	private CircuitTree searchAlgorithm;
	
	/**
	 * collection of labels that are updated in realtime
	 */
	private JLabel solutions, treeDepth, nodesAnalyzed;
	
	
	/**
	 * Start stop button
	 */
	protected JButton startStopButton;
	
	
	public CircuitTreeRunnable( TruthTable tableToFind)
	{
		super();
		
		this.tableToFind = tableToFind;
		searchAlgorithm = new CircuitTree();
		
		// init this layout 
		this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
		
		// add the name of the circuit we are searching for
		Font font = this.getFont();
		Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize()+2);
		JLabel name = new JLabel(tableToFind.getName());
		name.setForeground( Color.RED );
		name.setFont( boldFont );
		this.add( name );
		
		this.solutions = new JLabel();
		this.add(solutions);
		
		this.treeDepth = new JLabel();
		this.add( treeDepth );
		
		this.nodesAnalyzed = new JLabel();
		this.add(nodesAnalyzed);
		
		// add start/stop button
		startStopButton = new JButton("Start");
		startStopButton.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent e )
			{
				if(searchAlgorithm.toggleRunning()){
					name.setForeground( Color.GREEN );
					startStopButton.setText( "Stop" );
				}else{
					name.setForeground( Color.RED );
					startStopButton.setText( "Start" );
				}
			}
		});
		this.add( startStopButton );
		
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	}
	
	
	/**
	 * thread entry
	 */
	public void run()
	{
		searchAlgorithm.findCircuit(tableToFind);
    }
	
	/**
	 * Called when the GUI wants the stats updated
	 */
	public void onUpdateGUI()
	{
		solutions.setText( "Found Solutions: "+searchAlgorithm.getTotalSolutionsFoundCount() );
		treeDepth.setText( "Tree Depth: "+searchAlgorithm.getCurrentTreeDepth() );
		nodesAnalyzed.setText( "Nodes Analyzed: "+searchAlgorithm.getTotalNodesAnalyzed());
	}
	
}
