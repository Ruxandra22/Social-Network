import java.util.List;
import java.util.ArrayList;

/*
/**
 * <p>
 * This execution entry point class handles parsing and executing commands from the input
 * file.
 * </p>
 */
/**
 * 
 * @author Osman Maria-Ruxandra, Grupa 324CA
 *
 */
public class Main {

	/**
	 * <p>
	 * Execution entry point.
	 *</p>
	 *
	 * @param args the name of the file containing commands to be executed
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: java -cp <classpath> Main <input file>");
			System.exit(1);
		}
		/**
		 * se deschide fisierul, iar fiecare rand este o linie de cuvinte
		 */
		FileParser parser = new FileParser(args[0]);
		parser.open();
		List<String> rand = new ArrayList<String>();
		rand = parser.parseNextLine();
		/**
		 * crearea unui obiect de tip Network, respectandu-se pattern-ul
		 * Singleton
		 */
		Network.initNetwork();
		Network mainNetwork = Network.getNetwork();
		
		//
		/**
		 * Cat timp lista nu este goala, se ia fiecare cuvant si in functie de
		 * ceea ce exprima el, se apeleaza funtiile specifice implementate
		 */
		while(rand != null) {
			if(rand.get(0).equals("ADD")) {
				mainNetwork.add(Integer.parseInt(rand.get(1)), rand.get(2));
			}
			
			if(rand.get(0).equals("REMOVE")) {
				mainNetwork.remove(Integer.parseInt(rand.get(1)));
			}
			
			if(rand.get(0).equals("FRIEND")) {
				mainNetwork.friend(Integer.parseInt(rand.get(1)), Integer.parseInt(rand.get(2)));
			}
			
			if(rand.get(0).equals("UNFRIEND")) {
				mainNetwork.unfriend(Integer.parseInt(rand.get(1)), Integer.parseInt(rand.get(2)));
			}
			
			if(rand.get(0).equals("PRINT")) {
				if(rand.get(1).equals("USER")) {
					mainNetwork.print('u', Integer.parseInt(rand.get(2)));
				}
				
				if(rand.get(1).equals("NETWORK")) {
					mainNetwork.print('n');
				}
				
				if(rand.get(1).equals("COMMUNITIES")) {
					mainNetwork.print('c');
				}
				
				if(rand.get(1).equals("STRENGTH")) {
					mainNetwork.print('s', Integer.parseInt(rand.get(2)));
				}
			}
			rand = parser.parseNextLine();
		}
		parser.close();
	}
}