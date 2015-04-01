import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;


public class MainGA {

	
	
	public static void main(String[] args) {
		/**
		 * Collection of circuit finding threads
		 */
		ArrayList<CircuitGARunnable> runnables = new ArrayList<CircuitGARunnable>();
		
		// Carry-Out Circuit
		boolean[] co = {false, false, false, true, false, true, true, true};
		TruthTable carryOutTable = new TruthTable("Carry-Out", 3, co);
		runnables.add(new CircuitGARunnable(carryOutTable));
		
		// NOT(A) Circuit
		boolean[] na = {true, true, true, true, false, false, false, false};
		TruthTable notATable = new TruthTable("NOT-A", 3, na);
		runnables.add( new CircuitGARunnable( notATable ));

		
		// NOT(B) Circuit
		boolean[] nb = {true, true, false, false, true, true, false, false};
		TruthTable notBTable = new TruthTable("NOT-B", 3, nb);
		runnables.add( new CircuitGARunnable( notBTable ));
		
		
		// NOT(C) Circuit
		boolean[] nc = {true, false, true, false, true, false, true, false};
		TruthTable notCTable = new TruthTable("NOT-C", 3, nc);
		runnables.add( new CircuitGARunnable( notCTable ));
		
		
		// Sum Circuit
		boolean[] s = {false, true, true, false, true, false, false, true};
		TruthTable sumTable = new TruthTable("Sum", 3, s);
		runnables.add( new CircuitGARunnable( sumTable ));
		
		
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
		window.setSize( CircuitGARunnable.MAX_WIDTH, 1024 );
		JScrollPane scrPane = new JScrollPane(panes);
		window.setContentPane( scrPane );
		window.setVisible( true );
		window.setResizable( true );
		
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
