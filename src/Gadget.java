import java.util.ArrayList;

/**
 * Universidad de La Laguna - Grado de Ingenieria Informatica <p>
 * Complejidad Computacional - Circuito Hamiltoniano <p>
 * Gadget.java - Clase para representar los subgrafos del circuito hamiltoniano, hereda de Grafo
 * @author JRJ
 */

public class Gadget extends Grafo {

	private static final int NUM_NODOS = 12;
	
	private ArrayList<Integer> extLibres;
	private ArrayList<Integer> extOcupados;
	
	/**
	 * Constructor del gadget
	 * @param nombre Identificador del gadget
	 */
	public Gadget (String nombre) {
		super(nombre);
		this.generarNodos(nombre);
		this.extLibres.add(0);
		this.extLibres.add(5);
		this.extLibres.add(6);
		this.extLibres.add(11);
		this.generarAristas();
	}

	/**
	 * Metodo para generar los nodos del gadget
	 * @param nombre Identificador del gadget
	 */
	private void generarNodos(String nombre) {		
		for (int i = 0; i < NUM_NODOS; i++) {
			String idNodo = nombre + "_" + (i + 1);
			Nodo aux = new Nodo (idNodo);
			this.addNodo(aux);
		}		
	}
	
	/**
	 * Metodo para generar las aristas del gadget
	 */
	private void generarAristas() {		
		// Aristas superiores
		for (int i = 0; i < 5; i++) {
			Arista aux = new Arista();
			aux.setNodoA(this.getNodos().get(i));
			aux.setNodoB(this.getNodos().get(i + 1));
			this.addArista(aux);
		}
		
		// Aristas inferiores
		for (int i = 6; i < 11; i++) {
			Arista aux = new Arista();
			aux.setNodoA(this.getNodos().get(i));
			aux.setNodoB(this.getNodos().get(i + 1));
			this.addArista(aux);
		}
		
		// Aristas cruzadas
		Arista aux = new Arista();
	    aux.setNodoA(this.getNodos().get(0));
	    aux.setNodoB(this.getNodos().get(8));
	    this.addArista(aux);
	    aux.setNodoA(this.getNodos().get(2));
	    aux.setNodoB(this.getNodos().get(6));
	    this.addArista(aux);
	    aux.setNodoA(this.getNodos().get(3));
	    aux.setNodoB(this.getNodos().get(11));
	    this.addArista(aux);
	    aux.setNodoA(this.getNodos().get(5));
	    aux.setNodoB(this.getNodos().get(9));
	    this.addArista(aux);
	}
	
	/**
	 * Metodo
	 * @return indice
	 */
	public int conectGadgets () {
		int indice = this.getExtLibres().get(0);
		this.getExtOcupados().add(indice);
		this.getExtLibres().remove(this.getExtLibres().get(0));
		return indice;
	}

	/**
	 * Getter Array de ext. libres
	 * @return Array de ext. libres
	 */
	public ArrayList<Integer> getExtLibres() {
		return extLibres;
	}

	/**
	 * Setter Array de ext. libres
	 * @param extLibres
	 */
	public void setExtLibres(ArrayList<Integer> extLibres) {
		this.extLibres = extLibres;
	}

	/**
	 * Getter Array de ext. ocupados
	 * @return Array de ext. ocupados
	 */
	public ArrayList<Integer> getExtOcupados() {
		return extOcupados;
	}

	/**
	 * Setter Array de ext. ocupados
	 * @param extLibres
	 */
	public void setExtOcupados(ArrayList<Integer> extOcupados) {
		this.extOcupados = extOcupados;
	}
}
