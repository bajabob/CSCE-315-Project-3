import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class project3_NonGA_GUI
{

	public static void main(String[] args)
	{
		
		System.out.println("=====================================");
		System.out.println("Solutions are stored in src/solutions");
		System.out.println("=====================================");
		
		ArrayList<CircuitTreeRunnable> runnables = new ArrayList<CircuitTreeRunnable>();
		
		/**
		 * Setup circuits we want to find
		 */
		
		// Adder Circuit
		boolean[] co = {false, false, false, true, false, true, true, true};
		TruthTable carryOutTable = new TruthTable("Carry-Out", 3, co);
		boolean[] s = {false, true, true, false, true, false, false, true};
		TruthTable sumTable = new TruthTable("Sum", 3, s);
		TruthTable[] adder = {carryOutTable, sumTable};
		runnables.add( new CircuitTreeRunnable( adder, "Adder" ));

		
		// NOT(A) Circuit
		boolean[] na = {true, true, true, true, false, false, false, false};
		TruthTable notATable = new TruthTable("NOT-A", 3, na);
		boolean[] nb = {true, true, false, false, true, true, false, false};
		TruthTable notBTable = new TruthTable("NOT-B", 3, nb);
		boolean[] nc = {true, false, true, false, true, false, true, false};
		TruthTable notCTable = new TruthTable("NOT-C", 3, nc);
		TruthTable[] notABC = {notATable, notBTable, notCTable};
		runnables.add( new CircuitTreeRunnable( notABC, "Negation" ));
		
		
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
