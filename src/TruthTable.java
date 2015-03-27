import java.util.ArrayList;
import java.util.List;

public class TruthTable
{

	/**
	 * Collection of inputs
	 */
	private List<Boolean[]> inputs;

	/**
	 * Desired outputs
	 */
	private List<Boolean> output;

	/**
	 * The name of this truth table
	 */
	private String name;
	
	/**
	 * Generate a new truth table Will generate a truth table starting with 0,
	 * 0, 0 -> 0, 0, 1 and so on, the width is specified by tableWidth
	 * 
	 * @param name String - the name of this truth table
	 * @param tableWidth int - the number of t/f values for each row
	 * @param output boolen[] - (ORDER MATTERS) the desired output of the truth table
	 */
	public TruthTable(String name, int tableWidth, boolean[] output)
	{
		
		int rows = (int) Math.pow(2, tableWidth);

		if(rows != output.length){
			System.err.println("TruthTable :: number of rows and outputs must match.");
			return;
		}
		
		if(rows < 4){
			System.err.println("TruthTable :: Your table must have at least four rows (2x4 truth table).");
			return;
		}
		
		this.name = name;
		this.inputs = new ArrayList<Boolean[]>();
		this.output = new ArrayList<Boolean>();
		
		for (int i = 0; i < rows; i++) {
			Boolean[] in = new Boolean[tableWidth];
			for (int j = 0; j < tableWidth; j++) {
				in[(tableWidth-j)-1] = ((i / (int) Math.pow(2, j)) % 2) == 1;
			}
			this.inputs.add(in);
			this.output.add(output[i]);
		}
	}
	
	/**
	 * Get the width of this table (the number of inputs)
	 * @return int
	 */
	public int getTableWidth()
	{
		return inputs.get(0).length;
	}
	
	/**
	 * Get the input at the designated row/column
	 * @param row int
	 * @param column int
	 * @return boolean
	 */
	public boolean getInput(int row, int column)
	{
		return inputs.get(row)[column];
	}
	
	/**
	 * Get the output for the desired row
	 * @param row int
	 * @return boolean
	 */
	public boolean getOutput(int row)
	{
		return output.get(row);
	}
	
	/**
	 * Get the total number of rows in this table
	 * @return int
	 */
	public int getRowCount()
	{
		return this.output.size();
	}
	
	/**
	 * Get the name of this truth table
	 * @return String
	 */
	public String getName()
	{
		return name;
	}
	
	@Override
	public String toString()
	{
		String rsp = "TruthTable: " + name + "\n";
		
		for(int i = 0; i < inputs.size(); i++){
			rsp += i +"\t";
			for(int j = 0; j < inputs.get(0).length; j++){
				rsp += (inputs.get(i)[j] ? "1 " : "0 ");
			}
			rsp += "-> " + (output.get(i) ? "1\n" : "0\n");
		}
		return rsp;
	}

	public static void main(String[] args)
	{
		boolean[] expOuts = {false, false, false, true, false, true, true, true};
		TruthTable tt = new TruthTable("Carry-Out", 3, expOuts);
		
		System.out.println(tt);
		System.out.println("Testing Get Input at (7,1): " + tt.getInput(7, 1));
		System.out.println("Testing Get Name: " + tt.getName());
		System.out.println("Testing Get Output at row 7: " + tt.getOutput(7));
		System.out.println("Testing Get Row Count: " + tt.getRowCount());
		System.out.println("Testing Get Table Width: " + tt.getTableWidth());
	}
}
