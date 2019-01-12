import java.util.ArrayList;
import java.util.Scanner;

/**
 * Universidad de La Laguna - Grado de Ingenieria Informatica <p>
 * Complejidad Computacional - Circuito Hamiltoniano <p>
 * CircuitoHam.java - Clase para la construccion del circuito hamiltoniano
 * @author Grupo 2 - Juan, Richard, Jesus
 */

public class CircuitoHam {
	
	ArrayList<Gadget> gadgets;
	ArrayList<String> idNodos; // Array que almacena los id de los nodos
	Grafo completo; // Grafo completo
	int numNodos; // Numero de nodos del grafo completo
	
	public CircuitoHam () {
		this.gadgets = new ArrayList<Gadget>();
		this.idNodos = new ArrayList<String>();
		this.completo = new Grafo();
		this.numNodos = 0;
	}

	public void cargarCircuito() {
		String nombreFichero;
		VC vertex = new VC();
		
		// Solicitar nombre del fichero
		Scanner sc = new Scanner(System.in);
		System.out.println("Ruta del fichero que contiene la salida del Vertex Cover: ");
		nombreFichero = sc.nextLine();
		sc.close();
		
		// Cargar VC
		vertex.cargarVC(nombreFichero);
		
		for (int i = 0; i < vertex.getAristas().size(); i++) {
			String idGadget = "GD";
			if (vertex.enCubrimiento(vertex.getAristas().get(i).getNodoA()))
				idGadget += vertex.getAristas().get(i).getNodoA().getId() + "-" + vertex.getAristas().get(i).getNodoB().getId();
			else
				idGadget += vertex.getAristas().get(i).getNodoB().getId() + "-" + vertex.getAristas().get(i).getNodoA().getId();
			Gadget aux = new Gadget (idGadget);
			this.addGadget(aux);
		}
		
		for (int i = 0; i < vertex.getCubrimiento().size(); i++)
		{
			// Incluir un nodo nuevo por cada uno en el cubrimiento
			Nodo nuevo = new Nodo();
			String idNuevo = "nodoHC" + (i + 1);
			nuevo.setId(idNuevo);
			this.completo.addNodo(nuevo);
			
			// Si el gadget pertenece a una arista de ese vertice hacer las conexiones
			int indiceGadget1 = -1;
			int indiceGadget2 = -1;
			
			for (int j = 0; j < this.getGadgets().size(); j++)
			{
				if (this.getGadgets().get(j).getId().contains(vertex.getCubrimiento().get(i).getId()))
				{
					if (indiceGadget1 == -1)
						indiceGadget1 = j;
					else
						if (indiceGadget2 == -1)
							indiceGadget2 = j;
					if ((indiceGadget2 != -1) && (indiceGadget1 != -1))
					{
						// Unir los gadgets correspondientes
						this.conectarGadgets(this.getGadgets().get(indiceGadget1), this.getGadgets().get(indiceGadget2));
						
						Arista aux = new Arista();
						int ultimoOcupados;
						if (this.getGadgets().get(indiceGadget1).getExtOcupados().size() > 0)
							ultimoOcupados = this.getGadgets().get(indiceGadget1).getExtOcupados().size() - 1;
						else
							ultimoOcupados = 0;							
						aux.setNodoA(this.getGadgets().get(indiceGadget1).getNodos().get(ultimoOcupados));
						
						if (this.getGadgets().get(indiceGadget2).getExtOcupados().size() > 0)
							ultimoOcupados = this.getGadgets().get(indiceGadget2).getExtOcupados().size() - 1;
						else
							ultimoOcupados = 0;
						aux.setNodoB(this.getGadgets().get(indiceGadget2).getNodos().get(ultimoOcupados));	
						
						this.completo.addArista(aux);
						
						// Reiniciar los indices y dejar la j estatica de cara a la siguiente iteracion
						indiceGadget1 = -1; 
						indiceGadget2 = -1;
	                    j--;
					}
				}
			}
		}		
		// Conectar los nodos libres de los gadgets y los nuevos
	    this.conGadgetsNodo(this.completo);
	}

	public void resolverCircuito() {		
		// Obtener todas las id de los nodos
	    this.generarIdNodos(); 
	    
	    // Crear la matriz de adyacencia
	    this.setNumNodos(this.getIdNodos().size());
	    System.out.println("Numero de nodos en el grafo " + this.getNumNodos());
	    
	    boolean [][] matAdy = new boolean [this.getNumNodos()][];
	    boolean [] visitado = new boolean [this.getNumNodos()];
	    int [] camino = new int [this.getNumNodos()];
	    
	    for(int i = 0;  i < this.getNumNodos(); i++)
	    {
	        matAdy[i] = new boolean[getNumNodos()];
	        camino[i] = -1;
	        visitado[i] = false;
	    }
	    
	    for (int i = 0; i < this.getNumNodos(); i++)
	    {
	    	// Obtener las aristas asociadas a cada nodo
	    	ArrayList<Arista> aux = new ArrayList<Arista>();
	    	aux = this.getAristasNodo(this.getIdNodos().get(i));
	    	for (int j = 0; j < this.getNumNodos(); j++)
	    	{
	    		if (i == j)
	    			matAdy[i][j] = false;
	    		else if (this.adyacencia(this.getIdNodos().get(j), aux))
	    			matAdy[i][j] = true;
	    		else
	    			matAdy[i][j] = false;
	    	}
	    }
	    
	    // Agregar el id del primer nodo al camino
	    camino[0] = 0;
	    visitado[0] = true;
	    if (!this.calcularCircuito(matAdy, visitado, camino, 1))
	    	System.out.println("No existe solucion");
	    else
	    {
	    	System.out.println("Existe solucion:");
	    	for (int i = 0; i < this.getNumNodos(); i++)
	    		System.out.print(" " + camino[i]);
	    	// Añadir el primer nodo que es a su vez el último
	    	System.out.println(" " + camino[0]);
	    }
	}

	/**
	 * Metodo recursivo para el calculo del circuito hamiltoniano
	 * @param matAdy
	 * @param visitado
	 * @param camino
	 * @param i
	 * @return
	 */
	private boolean calcularCircuito(boolean[][] matAdy, boolean[] visitado, int[] camino, int pos) {
		// Condicion de parada: todos los nodos incluidos en V
		if (pos == this.getNumNodos())
		{
			// Y si el ultimo vertice incluido es igual al primero
			if (matAdy[camino[pos - 1]][camino[0]] == true)
				return true;
			else
				return false;
		}
		
		// Buscar el siguiente candidato para el circuito
		for (int i = 1; i < this.getNumNodos(); i++)
		{
			// Comprobar si se puede agregar el nodo al circuito
			if (this.esAgregable(camino[pos - 1], i, matAdy, visitado))
			{
				visitado[i] = true;
				camino[pos] = i;
				// Llamada recursiva para construir el resto del circuito
				if (this.calcularCircuito(matAdy, visitado, camino, pos + 1))
					return true;
				// Si el nodo que se agrego no lleva a una solucion se elimina
				visitado[i] = false;
				camino[pos] = -1;
			}
		}
	    // Si no hay mas nodos que agregar se devuelve falso
	    return false;
	}

	private boolean esAgregable(int ultimoNodo, int nodoActual, boolean[][] matAdy, boolean[] visitado) {
		if(visitado[nodoActual] || matAdy[ultimoNodo][nodoActual] == false)
	        return false;
	    return true;
	}

	private void addGadget(Gadget valor) {
		this.gadgets.add(valor);		
	}
	
    private void conectarGadgets(Gadget gadget1, Gadget gadget2) {
    	int indice1 = gadget1.getExtLibres().get(0);
    	int indice2 = gadget2.getExtLibres().get(0);
    	
    	// Obtener y eliminar el elemento libre
    	ArrayList<Integer> aux = new ArrayList<Integer>();    	
    	aux = gadget1.getExtLibres();
    	aux.remove(aux.get(0));
    	gadget1.setExtLibres(aux);
    	aux.clear();
    	aux = gadget2.getExtLibres();
    	aux.remove(aux.get(0));
    	gadget2.setExtLibres(aux);
    	
    	// Insertar el elemento que ahora esta ocupado
    	aux.clear();
    	aux = gadget1.getExtOcupados();
    	aux.add(indice1);
    	gadget1.setExtOcupados(aux);        
        aux.clear();
        aux = gadget2.getExtOcupados();
        aux.add(indice2);
        gadget2.setExtOcupados(aux);
    }
    
    private void conGadgetsNodo(Grafo grafo) {
    	Arista nodoGadget = new Arista();
        for(int i = 0;  i < this.getGadgets().size(); i++)
        {
            if(this.getGadgets().get(i).getExtLibres().size() > 0)
            {
                while(this.getGadgets().get(i).getExtLibres().size() > 0)
                {
                    Nodo b = this.getGadgets().get(i).getNodos().get(this.getGadgets().get(i).conectGadgets());
                    nodoGadget.setNodoB(b);
                    for(int j = 0;  j < grafo.getNodos().size(); j++)
                    {
                        nodoGadget.setNodoA(grafo.getNodos().get(j));
                        grafo.addArista(nodoGadget);
                    }
                }
            }
        }		
	}
    
    private void generarIdNodos() {
    	for(int i = 0;  i < this.getCompleto().getNodos().size(); i++){
            this.getIdNodos().add(this.getCompleto().getNodos().get(i).getId());
        }        
        for(int i = 0; i < this.getGadgets().size(); i++){
            for(int j = 0;  j < this.getGadgets().get(i).getNodos().size(); j++){
                this.getIdNodos().add(this.getGadgets().get(i).getNodos().get(j).getId());
            }
        }		
	}
    
    /**
     * Metodo para hallar las aristas que conectan con un nodo
     * @param idNodo Identificador del nodo
     * @return Array con las aristas
     */
    private ArrayList<Arista> getAristasNodo(String idNodo) {
    	ArrayList<Arista> todas = new ArrayList<Arista>(); // Todas las aristas, incluidas las de los gadgets
    	ArrayList<Arista> aux = new ArrayList<Arista>(); // Aristas que si contienen este nodo
    	todas = this.getCompleto().getAristas(); // Añadir primero las aristas del grafo completo
    	// Añadir luego las de los gadgets
    	for (int i = 0; i < this.getGadgets().size(); i++)
    	{
    		for (int j = 0; j < this.getGadgets().get(i).getAristas().size(); j++)
    		{
    			todas.add(this.getGadgets().get(i).getAristas().get(j));
    		}
    	}
    	// Buscar y añadir las que si estan
    	for (int i = 0; i < todas.size(); i++)
    		if (todas.get(i).contieneNodo(idNodo))
    			aux.add(todas.get(i));
    	
    	return aux;
	}
    
    /**
     * Metodo para comprobar si el nodo B se encuentra unido al nodo A por alguna arista
     * @param idNodoB Id del nodo a comprobar
     * @param aristasA Aristas del nodo A
     * @return true en caso de que si se unan, es decir, son adyacentes
     */
    private boolean adyacencia(String idNodoB, ArrayList<Arista> aristasA) {
    	for(int i = 0; i < aristasA.size(); i++){
            if(aristasA.get(i).contieneNodo(idNodoB)){
                return true;
            }
        }
        return false;
	}
    
    // Getters y Setters

	public ArrayList<Gadget> getGadgets() {
		return gadgets;
	}

	public void setGadgets(ArrayList<Gadget> gadgets) {
		this.gadgets = gadgets;
	}

	public ArrayList<String> getIdNodos() {
		return idNodos;
	}

	public void setIdNodos(ArrayList<String> idNodos) {
		this.idNodos = idNodos;
	}

	public Grafo getCompleto() {
		return completo;
	}

	public void setCompleto(Grafo completo) {
		this.completo = completo;
	}

	public int getNumNodos() {
		return numNodos;
	}

	public void setNumNodos(int numNodos) {
		this.numNodos = numNodos;
	}

}
