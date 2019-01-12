import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Universidad de La Laguna - Grado en Ingeniería Informática
 * Complejidad Computacional - Circuito Hamiltoniano
 * VC.java - Clase para representar ua entrada del vertex cover
 * JMHM
*/

public class VC extends Grafo {

	private ArrayList<Nodo> cubrimiento;

	public VC (){
		ident = "VC";
	}

	public VC (VC orig){
		ident = orig.getIdent();
		nodos = orig.getNodos();
		aristas = orig.getAristas();
	}

	public cargarVC (string nomFichero){
		char cover[] = new char[256];
		String entrada;
		String line;
		try {
			BufferedReader bvc = new BufferedReader(new FileReader(nomFichero));
			line = vc.readLine();
            		entrada = line;
			int numNodos = entrada.substring(0,1);
			generarNodos(numNodos);
      		
			line = vc.readLine();
			entrada = line;
			for (int i = 0; i < entrada.length()-1;++){
				int indice = entrada.charAt(i) - '1';
				addNodoCubri(getNodos()[indice]);
				i++;
			}

		while ((line = bufferreader.readLine()) != null) {
            		entrada = line;
			Nodo a = getNodos()[entrada.charAt(0) - '1'];
			Nodo b = getNodos()[entrada.charAt(2) - '1'];
			Arista aux;
			aux.setNodoA(a);
			aux.setNodoB(b);
			addArista(aux); 
        	}

    		} catch (FileNotFoundException ex) {
        		ex.printStackTrace();
    		} catch (IOException ex) {
        		ex.printStackTrace();
    		}


	}

	public generarNodos(int numNodos){
		for (int i = 0; i < numNodos; i++){
			Nodo aux;
			String nomNodo = "vc" + toString(i+1);
			aux.setId(nomNodo);
			addNodo(aux);
		}
	}

	public addNodCubri (Nodo valor){
		cubrimiento.push_back(valor);
	}
	/**
         * Método Comprueba si un nodo es parte del cubrimiento o no
	 * Parámetros El nodo
         * @return True or false
         */
	public enCubrimiento (Nodo valor){
		for (int i = 0; i < getcubrimient().length(); i++){
			if (getcubrimiento()[i].getId() == valor.getId()){
				return  true;
			}
		}
		return false;	
	}

	public imprimir(){
		String salida = "";
		salida = salida + "Número de nodos: " + toString(getNodos().length()) + "\nCubrimiento: ";
		for (int i = 0; i < getCubrimiento().length();i++{
			salida = salida + getCubrimiento()[i].getId() + " ":
		}
		salida = salida + "\nAristas_\n";
		for(int i = 0; i < getAristas().length(); i++){
			salida = salida + getAristas()[i].imprimir() + "\n";
		}
		return salida;
	}

	/** Funciones de acceso **/

	public setCubrimiento(ArrayList<Nodo> valor){
		cubrimiento = valor;
	}

	ArrayList<Nodo> getCubrimiento(){
		return cubrimiento;
	}
}
