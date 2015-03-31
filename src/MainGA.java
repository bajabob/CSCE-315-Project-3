import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class MainGA {

	/**
	 * Collection of circuit finding threads
	 */
	static ArrayList<CircuitGARunnable> runnables;
	
	public static void main(String[] args) {
		
		runnables = new ArrayList<CircuitGARunnable>();
		
		// Carry-Out Circuit
		boolean[] co = {false, false, false, true, false, true, true, true};
		TruthTable carryOutTable = new TruthTable("Carry-Out", 3, co);
		runnables.add(new CircuitGARunnable(carryOutTable));
		
		/**
		 * Create GUI
		 */
		JPanel panes = new JPanel();
		panes.setLayout( new BoxLayout(panes, BoxLayout.Y_AXIS) );
		
		// add sub-panes to this window
		for( CircuitGARunnable runnable :  runnables)
		{
			panes.add( runnable );
		}
		
		// start processing
		for( CircuitGARunnable runnable :  runnables)
		{
			(new Thread(runnable)).start();
		}
		
		JFrame window = new JFrame( "Team #7 - Project 3" );
		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		window.setSize( CircuitGARunnable.MAX_WIDTH, runnables.size()*CircuitGARunnable.MAX_HEIGHT );
		window.setContentPane( panes );
		window.setVisible( true );
		window.setResizable( false );
		
		// update GUI
		ActionListener updateGUI = new ActionListener()
		{
			public void actionPerformed( ActionEvent evt )
			{
				for( CircuitGARunnable runnable :  runnables)
				{
					runnable.onUpdateGUI();
				}
			}
		};
		new Timer( 250, updateGUI ).start();
	}

}
