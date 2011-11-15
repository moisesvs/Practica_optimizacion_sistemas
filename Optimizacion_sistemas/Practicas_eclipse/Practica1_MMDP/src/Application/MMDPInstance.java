package Application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class MMDPInstance {

	private File file;
	private int n;
	private int m;
	private double [][] matrixDistance;

	public MMDPInstance(File fichero) {
		
		this.file = fichero;
		
	}

	public void loading() {
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			StringTokenizer st = new StringTokenizer(line);
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			matrixDistance = new double[n][n];
			
			line = br.readLine();
			
			while(line != null) {
				
				st = new StringTokenizer(line);
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				double distance = Double.parseDouble(st.nextToken());
				matrixDistance[x][y] = distance;
				matrixDistance[y][x] = distance;
				line = br.readLine();
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void print() {
		
		System.out.println(file.getName());
		System.out.println(n + " " + m);
		
		for (int i = 0; i < matrixDistance.length - 1; i++) {
			
			for (int j = i + 1; j < matrixDistance.length; j++) {
				System.out.println(i + " " + j + " " + matrixDistance[i][j]);
			}
			
		}
	}

	/**
	 * Solution is factible if the length array not higher that the number nodes M,
	 * and the number position true not higher that number nodes N
	 * @return
	 */
	public boolean isFactible(SolutionMMDP solution){
		
		// Check if not higher that numNodes
		if (solution.getSolution().length != this.matrixDistance.length)
			return false;
		
		int numNodesSelection = 0;
		
		// Check if not higher that numNodesSelection
		for (int i = 0; i < solution.getSolution().length; i ++){
			if (solution.getSolution()[i])
				numNodesSelection ++;
		}
		
		if (numNodesSelection > matrixDistance.length)
			return false;
		
		return true;
	}
	
	public double getValueObjetiveFunction(SolutionMMDP solution){
		 // Function objetive min distance between nodes
		 double valueObjetiveFunction = Double.MAX_VALUE;

		 if (!isFactible(solution)){
			 // Return the worse result objetive
			 return valueObjetiveFunction;
		 }

		 for (int nodeSelection = 0; nodeSelection < n; nodeSelection ++){
			 if (solution.getSolution()[nodeSelection]){
				 
				 // Distance with adyacents
				 for (int otherNode = nodeSelection; otherNode < n; otherNode ++){
					 if ((nodeSelection != otherNode) && (solution.getSolution()[otherNode]) && (valueObjetiveFunction > matrixDistance[nodeSelection][otherNode])){
						 valueObjetiveFunction = matrixDistance[nodeSelection][otherNode];
					 }
				 }
			 }
		 }
		 
		 return valueObjetiveFunction;
	}
	
	public boolean bestValueObjetiveFunction (double valueBest, double otherValue){
		
		// Maximun value
		if (valueBest < otherValue)
			return true;
		
		return false;
	}
	
	// Gets ans Sets
	public int getNumNodesSelection (){
		return m;
	}
	
	public int getNumNodes (){
		return n;
	}
	
	public File getFile(){
		return file;
	}
}