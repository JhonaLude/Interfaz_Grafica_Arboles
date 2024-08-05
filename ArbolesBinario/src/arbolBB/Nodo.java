package arbolBB;

 /**
 *
 * @author Jhonathan Alexander Ludeña Cevallos
 */

//07-07-2024 16:41

public class Nodo {
    private int dato;
    private Nodo izq,der;

    public Nodo(int dato, Nodo izq, Nodo der) {
        this.dato = dato;
        this.izq = izq;
        this.der = der;
    }

    public int getDato() {
        return dato;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }

    public Nodo getIzq() {
        return izq;
    }

    public void setIzq(Nodo izq) {
        this.izq = izq;
    }

    public Nodo getDer() {
        return der;
    }

    public void setDer(Nodo der) {
        this.der = der;
    }
 
}
