import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Universidad de La Laguna - Grado de Ingenieria Informatica <p>
 * Complejidad Computacional - Circuito Hamiltoniano <p>
 * VC.java - Clase para representar una entrada del vertex cover
 * @author Grupo 2 - Juan, Richard, Jesus
*/
public class VC extends Grafo {

	private ArrayList<Nodo> cubrimiento;

	/**
	 * Contructor por defecto
	 */
	public VC (){
		super();
		this.id = "VC";
		this.cubrimiento = new ArrayList<Nodo>();
	}

	/**
	 * Constructor copia
	 * @param orig Vertex Cover original
	 */
	public VC (VC orig){
		this.id = orig.getId();
		this.nodos = orig.getNodos();
		this.aristas = orig.getAristas();
	}

	/**
	 * Metodo para cargar un vertex cover desde un fichero de texto
	 * @param nomFichero Nombre del fichero
	 */
	public void cargarVC (String nomFichero)
	{
		String line;
		try {
			BufferedReader vc = new BufferedReader(new FileReader(nomFichero));
			line = vc.readLine();
			int numNodos = Integer.parseInt(line);
			this.generarNodos(numNodos);
      		
			line = vc.readLine();
			String [] indiceNodosCubrimiento = line.split(" ");
			for (int i = 0; i < indiceNodosCubrimiento.length; i++)
			{
				int indice = Integer.parseInt(indiceNodosCubrimiento[i]) - 1;
				this.addNodoCubri(this.getNodos().get(indice));
			}

			while ((line = vc.readLine()) != null)
			{
				String [] indicesNodos = line.split(" ");
				int indiceNodoA = Integer.parseInt(indicesNodos[0]) - 1;
				int indiceNodoB = Integer.parseInt(indicesNodos[1]) - 1;
				Nodo a = this.getNodos().get(indiceNodoA);
				Nodo b = this.getNodos().get(indiceNodoB);
				
				Arista aux = new Arista();
				aux.setNodoA(a);
				aux.setNodoB(b);
				this.addArista(aux); 
	        }
			vc.close();
		} catch (FileNotFoundException ex) {
        	ex.printStackTrace();
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	}
	}

	/**
	 * Metodo para generar los nodos del vertex cover
	 * @param numNodos
	 */
	public void generarNodos(int numNodos)
	{
		for (int i = 0; i < numNodos; i++)
		{
			Nodo aux = new Nodo();
			String idNodo = "vc" + (i + 1);
			aux.setId(idNodo);
			this.addNodo(aux);
		}
	}

	/**
	 * Metodo para añadir un nodo de cubrimiento
	 * @param valor
	 */
	public void addNodoCubri (Nodo valor)
	{
		this.cubrimiento.add(valor);
	}
	
	/**
	 * Metodo para comprobar si un nodo es parte del cubrimiento
	 * @param valor Nodo a comprobar
	 * @return true en caso de que si sea parte del cubrimiento
	 */
	public boolean enCubrimiento (Nodo valor){
		for (int i = 0; i < this.getCubrimiento().size(); i++)
		{
			if (this.getCubrimiento().get(i).getId().equals(valor.getId()))
			{
				return true;
			}
		}
		return false;	
	}

	/**
	 * Metodo para mostrar un vertex cover como String
	 */
	public String toString()
	{
		String salida = "";
		salida += "Numero de nodos: " + this.getNodos().size() + "\nCubrimiento: ";
		for (int i = 0; i < this.getCubrimiento().size(); i++)
		{
			salida += this.getCubrimiento().get(i).getId() + " ";
		}
		salida += "\nAristas:\n";
		for(int i = 0; i < this.getAristas().size(); i++)
		{
			salida += this.getAristas().get(i) + "\n"; 
		}
		return salida;
	}

	/**
	 * Setter para el array de los nodos de cubrimiento
	 * @param valor Array de los nodos de cubrimiento
	 */
	public void setCubrimiento(ArrayList<Nodo> valor){
		this.cubrimiento = valor;
	}

	/**
	 * Getter del conjunto de nodos de cubrimiento
	 * @return Array con los nodos de cubrimiento
	 */
	public ArrayList<Nodo> getCubrimiento(){
		return cubrimiento;
	}
}
