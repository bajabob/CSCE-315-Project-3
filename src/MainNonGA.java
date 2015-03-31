import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainNonGA
{

	/**
	 * Collection of circuit finding threads
	 */
	static ArrayList<CircuitTreeRunnable> runnables;
	
	
	public static void main(String[] args)
	{
		
		runnables = new ArrayList<CircuitTreeRunnable>();
		
		/**
		 * Setup circuits we want to find
		 */
		
		// Carry-Out Circuit
		boolean[] co = {false, false, false, true, false, true, true, true};
		TruthTable carryOutTable = new TruthTable("Carry-Out", 3, co);
		System.out.println(carryOutTable);
		runnables.add( new CircuitTreeRunnable( carryOutTable ));

		
		// NOT(A) Circuit
		boolean[] na = {true, true, true, true, false, false, false, false};
		TruthTable notATable = new TruthTable("NOT-A", 3, na);
		System.out.println(notATable);
		runnables.add( new CircuitTreeRunnable( notATable ));

		
		// NOT(B) Circuit
		boolean[] nb = {true, true, false, false, true, true, false, false};
		TruthTable notBTable = new TruthTable("NOT-B", 3, nb);
		System.out.println(notBTable);
		runnables.add( new CircuitTreeRunnable( notBTable ));
		
		
		// NOT(C) Circuit
		boolean[] nc = {true, false, true, false, true, false, true, false};
		TruthTable notCTable = new TruthTable("NOT-C", 3, nc);
		System.out.println(notCTable);
		runnables.add( new CircuitTreeRunnable( notCTable ));
		
		
		// Sum Circuit
		boolean[] s = {false, true, true, false, true, false, false, true};
		TruthTable sumTable = new TruthTable("Sum", 3, s);
		System.out.println(sumTable);
		runnables.add( new CircuitTreeRunnable( sumTable ));
		
		
		/**
		 * Create GUI
		 */
		JPanel panes = new JPanel();
		panes.setLayout( new BoxLayout(panes, BoxLayout.Y_AXIS) );
		
		// add sub-panes to this window
		for( CircuitTreeRunnable runnable :  runnables)
		{
			panes.add( runnable );
		}
		
		// start processing
		for( CircuitTreeRunnable runnable :  runnables)
		{
			(new Thread(runnable)).start();
		}
		
		JFrame window = new JFrame( "Team #7 - Project 3" );
		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		window.setSize( 300, 630 );
		window.setContentPane( panes );
		window.setVisible( true );
		window.setResizable( false );
		
		// update GUI
		ActionListener updateGUI = new ActionListener()
		{
			public void actionPerformed( ActionEvent evt )
			{
				for( CircuitTreeRunnable runnable :  runnables)
				{
					runnable.onUpdateGUI();
				}
			}
		};
		new Timer( 250, updateGUI ).start();
	}
	

	
	
}
