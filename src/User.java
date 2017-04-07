import java.util.Vector;

/**
 * Clasa User, in care apar ID, Nume, Prieteni(vector de tip User) si 
 * masca(Task 2: DFS)
 * @author Osman Maria-Ruxandra, Grupa 324CA
 *
 */
public class User implements Comparable<User>{
	public int ID;
	public String Nume;
	public Vector<User> Prieteni = new Vector<User>();
	public int mask;
	
	/**
	 * Constructor default
	 */
	public User() {
	}
	
	/**
	 * Constructor 
	 * 
	 * @param ID: ID-ul ultilizatorului
	 * @param Nume: Numele utilizatorului
	 */
	public User(int ID, String Nume) {
		this.ID = ID;
		this.Nume = Nume;
	}
	
	/**
	 * Constructor 
	 * @param ID: ID-ul unui utilizator
	 */
	public User(int ID) {
		this.ID = ID;
	}
	
	/**
	 * Getter pentru prieteni
	 * @return Prietenii unui utilizator
	 */
	public Vector<User> getFriends(){
		return Prieteni;
	}
	
	/**
	 * Setter pentru masca (Task 2)
	 * @param c : un numar(0,1 sau 2) pentru masca
	 */
	public void setMask(int c){
		mask = c;
	}
	
	/**
	 * Getter pentru masca (Task 2)
	 * @return masca
	 */
	public int getMask(){
		return mask;
	}
	
	/**
	 * Metoda de adaugare a unui prieten, apelata in Network
	 * @param newfriend: prietenul ce se adauga in lista unui utilizator
	 */
	public void addFriend(User newfriend) {
		for(User u : Prieteni) {
			if(u.ID == ID) {
				return;
			}
		}
		this.Prieteni.add(newfriend);
	}
	
	/**
	 * Metoda de stergere a unui prieten, apelata in Network
	 * @param ID: ID-ul prietenului care se sterge
	 */
	public void removeFriend(int ID) {
		for(User u : Prieteni) {
			if(u.ID == ID) {
				getFriends().remove(u);
				break;
			}
		}
	}
	
	/**
	 * Metoda care verifica daca doi utilizatori ai retelei sunt prieteni,
	 * apelata in Network
	 * @param ID: ID-ul unui utilizator
	 * @return true daca cei doi sunt prieteni
	 * @return false daca nu sunt prieteni
	 */
	public boolean friendship(int ID) {
		for(User u : Prieteni) {
			if(u.ID == ID) return true;
		}
		return false;
	}
	
	@Override
	/**
	 * Suprascrierea metodei toString() pentru a se indeplini afisarea ceruta\
	 * in enunt
	 */
	public String toString(){
		String theString = "" + ID + " " + Nume.toLowerCase() + ": ";
		for (User u:Prieteni){
			theString = theString + u.ID + " ";
		}
		return theString;
	}

	@Override
	/**
	 * Suprascrierea metodei compareTo, pentru folosirea metodei sort() in clasa Network
	 */
	public int compareTo(User arg0) {
		return ID-arg0.ID;
	}
}