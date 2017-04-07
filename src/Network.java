import java.util.Vector;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Clasa Network: implementarea tuturor Task-urilor
 * @author Osman Maria-Ruxandra, Grupa 324CA
 *
 */
public class Network {
	
	public Vector<User> Utilizatori;
	
	/**
	 * Obiect de tip Network, ce respecta Pattern-ul Singleton
	 */
	private static Network mainNetwork;
	
	
	/**
	 * Constructor private pentru initializarea Utilizatorilor
	 */
	private Network() {
		Utilizatori = new Vector<User>();
	}
	
	/**
	 *Metoda pentru initializarea mainNetwork
	 */
	public static void initNetwork() {
		mainNetwork = new Network();
	}
	
	/**
	 * Getter pentru mainNetwork
	 * @return mainNetwork: un obiect de tip Network, respectandu-se
	 * Pattern-ul Singleton
	 */
	public static Network getNetwork() {
		return mainNetwork;
	}
	
	/**
	 * Metoda de adaugare a unui utilizator in retea
	 * @param ID: ID-ul utilizatorului
	 * @param Nume: Numele utilizatorului
	 */
	public void add(int ID, String Nume) {
		User u = new User(ID, Nume);
		/**
		 * cauta in toti utilizatorii si daca gaseste ID-ul dat ca parametru,
		 * afiseaza "DUPLICATE"
		 */
		for(User user:Utilizatori) {
			if(user.ID == ID){
				System.out.println("DUPLICATE");
				return;
			}
		}
		/**
		 * adauga utilizatorul u in retea, sorteaza reteaua dupa ID si afiseaza
		 * "OK"
		 */
		Utilizatori.add(u);
		Collections.sort(Utilizatori);
		System.out.println("OK");
	}
	
	/**
	 * Metoda de cautare a unui utilizator in toata reteaua
	 * @param ID: ID-ul utilizatorului ce trebuie cautat in retea
	 * @return u: utlizatorul cu ID-ul dat ca parametru, daca a fost gasit
	 * @return null: daca utilizatorul nu a fost gasit in retea
	 */
	User searchUser(int ID) {
		for (User u : Utilizatori) {
			if (u.ID == ID) 
				return u;
		}
		return null;
	}

	// 
	/**
	 * Metoda de eliminare a unui utilizator din retea si din lista de prieteni
	 * a tuturor utilizatorilor care il au ca prieten
	 * @param ID: ID-ul utilizatorului ce este eliminat din retea
	 */
	public void remove(int ID) {
		User u = searchUser(ID);
		/**
		 * Daca utilizatorul este in retea, se parcurg si toti prietenii fiecaruia
		 *dintre ei si se sterge utilizatorul cu id-ul ID daca se afla in lista 
		 *prietenilor
		 */
		if(u != null) {
			for(User u1 : Utilizatori) {
				if(u1.friendship(ID) == true) {
					u1.removeFriend(ID);
				}
			}
			/**
			 * se sterge utilizatorul si se afiseaza "OK"
			 */
			Utilizatori.remove(u);
			System.out.println("OK");
		}
		/**
		 * altfel, se afiseaza "INEXISTENT"
		 */
		else {
			System.out.println("INEXISTENT");
		}
	}
	
	/**
	 * Metoda prin care se realizeaza relatia de prietenie dintre 2 utilizatori
	 * @param ID1: ID-ul primului utilizator
	 * @param ID2: ID-ul celui de-al doilea utilizator
	 */
	public void friend(int ID1, int ID2) {
		/**
		 * se cauta cei doi utilizatori in retea
		 */
		User u1 = searchUser(ID1);
		User u2 = searchUser(ID2);
			
		/**
		 * daca u1 sau u2 nu sunt in retea => INEXISTENT
		 */
		if(u1 == null || u2 == null) {
			System.out.println("INEXISTENT");			
		}
			
		/**
		 * daca u1 si u2 sunt in retea => se imprietenesc si se afiseaza "OK"
		 * dupa adaugarea unui prieten in lista unui utilizator, se sorteaza
		 * lista de prieteni dupa ID
		 */
		if(u1 != null && u2 != null) {
			u1.addFriend(u2);
			Collections.sort(u1.getFriends());
			u2.addFriend(u1);
			Collections.sort(u2.getFriends());
			System.out.println("OK");
		}
	}
	
	/**
	 * Metoda de unfriend a utilizatorilor retelei
	 * @param ID1: ID-ul primului utilizator
	 * @param ID2: ID-ul celui de-al doilea utilizator
	 */
	public void unfriend(int ID1, int ID2) {
		/**
		 * se cauta cei doi utilizatori in retea
		 */
		User u1 = searchUser(ID1);
		User u2 = searchUser(ID2);

		/**
		 * daca nu se gasesc in retea, se afiseaza "INEXISTENT"
		 */
		if((u1 == null || u2 == null)) {
			System.out.println("INEXISTENT");
			return;
		}
		
		/**
		 *daca nu sunt prieteni, se afiseaza "INEXISTENT" 
		 */
		if(u1.friendship(ID2) == false && u2.friendship(ID1) == false) {
			System.out.println("INEXISTENT");// prietenie");
		}
		
		/**
		 * daca sunt prieteni, se elimina din lista de prieteni a fiecaruia si
		 * se afiseaza "OK"
		 */
		if(u1.friendship(ID2) == true && u2.friendship(ID1) == true) {;
			u1.removeFriend(ID2);
			u2.removeFriend(ID1);
			System.out.println("OK");
		}
	}
	
	/**
	 * Metoda pentru implementarea DFS-ului (Task 2)
	 * @param user: User-ul de la care incepe parcurgerea in adancime
	 * @param comunitate: un ArrayList in care se adauga prietenii utilizatorului,
	 * in urma parcugerii in adancime 
	 */
	private void DFS(User user,ArrayList<User> comunitate) {
		/**
		 * se seteaza masca cu 1 pt primul utilizator
		 */
		user.setMask(1);
		/**
		 * pentru toti prietenii utilizatorului u, daca masca este 0, se adauga
		 * in comunitate, dupa care, se apeleaza recursiv metoda DFS
		 */
		for (User u:user.getFriends()) {
			if (u.getMask()==0) {
				comunitate.add(u);
				DFS(u,comunitate);
			}
		}
		/**
		 * dupa ce s-au parcurs toti prietenii userului u, masca se marcheaza cu 2
		 */
		user.setMask(2);
	}
	
	/**
	 * Metoda pentru prinatarea comunitatilor
	 */
	public void printCommunities() {
		/**
		 * pentru fiecare utilizator,se seteaza masca 0
		 */
		for (User u:Utilizatori) {
			u.setMask(0);
		}
		ArrayList<User> comunitate;
		
		/**
		 *pentru toti utilizatorii din retea:
		 *daca masca este 0, se creeaza un ArrayList comunitate, in care se adauga
		 *u, iar ulterior se face DFS pe acest u si pe aceasta comunitate
		 */
		for(User u:Utilizatori) {
			if(u.getMask()==0) {
				comunitate = new ArrayList<User>();
				comunitate.add(u);
				DFS(u,comunitate);
				String theString = "" + comunitate.size() + ": ";
				for (User u2:comunitate) {
					theString = theString + u2.ID + " ";
				}
				System.out.println(theString);
			}
		}
	}
	
	/**
	 * Metoda pentru implementarea BFS-ului (Task 3)
	 * @param user: User-ul de la care incepe parcurgerea in latime
	 * @param comunitate: un ArrayList in care se adauga utilizatorii
	 * @return distanta
	 */
	private int BFS(User user,ArrayList<User> comunitate) {
		int distanta;
		
		/**
		 * se folosesc o coada(FIFO) pentru implementarea BFS-ului si un
		 * vector de dimensiunea comunitatii
		 */
		ArrayList<User> queue = new ArrayList<>();
		int[] array = new int[comunitate.size()];
		
		/**
		 * in array, fiecare element are valoarea maxima pt int
		 */
		for (int j = 0; j < array.length; j++) {
			array[j] = Integer.MAX_VALUE;
		}
		
		/**
		 * se adauga user in coada si valoarea de la indexul acestuia se face 0
		 */
		queue.add(user);
		array[comunitate.indexOf(user)] = 0;
		
		/**
		 * se iau primul element si indexul acestuia din coada, dupa care se elimina din coada
		 */
		while(!queue.isEmpty()) {
			User currentUser = queue.get(0);
			int indexOfCurrentUser = comunitate.indexOf(currentUser);
			queue.remove(0);
			
			/**
			 * pentru fiecare prieten al lui u, se retine indexul lui;
			 * daca indexul este MAX_VALUE, atunci u se adauga in coada si la 
			 * pozitia acestuia se pune valoarea parintelui + 1
			 */
			for(User u:currentUser.getFriends()) {
				int indexInMask = comunitate.indexOf(u);
				if(array[indexInMask]==Integer.MAX_VALUE) {
					queue.add(u);
					array[indexInMask] = array[indexOfCurrentUser] + 1;
				}
			}
		}
		distanta = 0;
		/**
		 * din array se ia cea mai mare valoare, ce este atribuita distantei
		 */
		for (int i=0;i<comunitate.size();i++) {
			if (distanta<array[i])
				distanta = array[i];
		}
		return distanta;
	}
	
	/**
	 * Metoda de afisare a gradului de socializare
	 * @param ID: Id-ul utilizatorului de unde se calculeaza gradul de
	 * socializare
	 */
	public void printStrength(int ID) {
		User startUser = null;
		ArrayList<User> comunitate;
		/**
		 * se afla de la ce utilizator incepe calculul gradului de socializare,
		 * iar daca nu s-a gasit, afiseaza "INEXISTENT"
		 */
		for (User u:Utilizatori) {
			if (u.ID==ID) {
				startUser = u;
			}
		}
		if(startUser == null) {
			System.out.println("INEXISTENT");
			return;
		}
		/**
		 * toti utilizatorii au initial masca 0
		 */
		for (User u:Utilizatori) {
			u.setMask(0);
		}
		/**
		 * se adauga la comunitate primul user, dupa care se face DFS pe el
		 */
		comunitate = new ArrayList<>();
		comunitate.add(startUser);
		DFS(startUser,comunitate);
		int distanta = 0;
		/**
		 * pentru fiecare user din comunitate, se face BFS si se afla distanta maxima
		 */
		for (User u:comunitate) {
			int d = BFS(u,comunitate);
			if(distanta<d) distanta = d;
		}
		/**
		 * se aplica formula din enunt pentru calculul gradului de socializare 
		 * doar daca numitorul este diferit de 0
		 */
		if (comunitate.size()!=1) {
			int toPrint = (comunitate.size()-distanta)*100/(comunitate.size()-1);
			System.out.println(toPrint);
		}
		else System.out.println(0);
	}
	
	
	/**
	 * Metoda de printare cu numar variabil de parametri
	 * @param c: in functie de acest caracter, se printeaza user-ul, network-ul
	 * sau gradul de socializare
	 * @param ID: ID-ul utilizatorului(valabil doar pentru 'u' si 's')
	 */
	public void print(char c, int... ID) {
		if(c == 'u') {
			/**
			 * cauta utilizatorul si il printeaza, folosind suprascrierea metodei
			 * toString(); daca nu il gaseste, se afiseaza "INEXISTENT"
			 */
			for(int numar : ID) {
				User u = searchUser(numar);
				if(u != null) {	
					System.out.println(u);
				}
				else {System.out.println("INEXISTENT");}
			}
		}
		/**
		 * pentru afisarea lui network
		 */
		if(c == 'n') {
			for(User u : Utilizatori) {
				System.out.println(u);
			}
		}
		/**
		 * pentru afisarea comunitatiilor
		 */
		if (c == 'c')
			printCommunities();
		/**
		 * pentru afisarea gradului de socializare
		 */
		if(c == 's')
			printStrength(ID[0]);
	}
}