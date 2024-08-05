package arbolBB;

/**
 *
 * @author Jhonathan Alexander Ludeña Cevallos
 */

//07-07-2024 16:41

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import javax.swing.JPanel;

public class ArbolBB {

    private Nodo raiz;

    public ArbolBB() {
        raiz = null;
    }

    public void pintarNodo(Integer dato, Color color) {
        pintarNodoRecursivo(raiz, dato, color);
    }

    private boolean pintarNodoRecursivo(Nodo nodo, Integer dato, Color color) {
        if (nodo == null) {
            return false;
        }
        if (nodo.getDato().equals(dato)) {
            nodo.setColor(color);
            return true;
        }
        return pintarNodoRecursivo(nodo.getIzquierdo(), dato, color)
                || pintarNodoRecursivo(nodo.getDerecho(), dato, color);
    }

    public JPanel getdibujo() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (raiz != null) {
                    dibujarArbol(g, getWidth() / 2, 30, raiz);
                }
            }
        };
    }

    private void dibujarArbol(Graphics g, int x, int y, Nodo nodo) {
        if (nodo == null) {
            return;
        }

        int DIAMETRO = 30;
        int RADIO = DIAMETRO / 2;
        int ANCHO = 50;

        g.setColor(nodo.getColor());
        g.fillOval(x - RADIO, y - RADIO, DIAMETRO, DIAMETRO);
        g.setColor(Color.BLACK);
        g.drawOval(x - RADIO, y - RADIO, DIAMETRO, DIAMETRO);
        g.drawString(nodo.getDato().toString(), x - 6, y + 4);

        if (nodo.getIzquierdo() != null) {
            g.drawLine(x - RADIO, y + RADIO, x - ANCHO, y + ANCHO);
            dibujarArbol(g, x - ANCHO, y + ANCHO, nodo.getIzquierdo());
        }

        if (nodo.getDerecho() != null) {
            g.drawLine(x + RADIO, y + RADIO, x + ANCHO, y + ANCHO);
            dibujarArbol(g, x + ANCHO, y + ANCHO, nodo.getDerecho());
        }
    }

    public boolean agregar(Integer dato) {
        if (raiz == null) {
            raiz = new Nodo(dato);
            return true;
        } else {
            return agregarRecursivo(raiz, dato);
        }
    }

    private boolean agregarRecursivo(Nodo nodo, Integer dato) {
        if (dato < nodo.getDato()) {
            if (nodo.getIzquierdo() == null) {
                nodo.izquierdo = new Nodo(dato);
                return true;
            } else {
                return agregarRecursivo(nodo.getIzquierdo(), dato);
            }
        } else if (dato > nodo.getDato()) {
            if (nodo.getDerecho() == null) {
                nodo.derecho = new Nodo(dato);
                return true;
            } else {
                return agregarRecursivo(nodo.getDerecho(), dato);
            }
        } else {
            // El dato ya existe en el árbol
            return false;
        }
    }

    public LinkedList<Integer> preOrden() {
        LinkedList<Integer> recorrido = new LinkedList<>();
        preOrdenRecursivo(raiz, recorrido);
        return recorrido;
    }

    private void preOrdenRecursivo(Nodo nodo, LinkedList<Integer> recorrido) {
        if (nodo != null) {
            recorrido.add(nodo.getDato());
            preOrdenRecursivo(nodo.getIzquierdo(), recorrido);
            preOrdenRecursivo(nodo.getDerecho(), recorrido);
        }
    }

    public LinkedList<Integer> inOrden() {
        LinkedList<Integer> recorrido = new LinkedList<>();
        inOrdenRecursivo(raiz, recorrido);
        return recorrido;
    }

    private void inOrdenRecursivo(Nodo nodo, LinkedList<Integer> recorrido) {
        if (nodo != null) {
            inOrdenRecursivo(nodo.getIzquierdo(), recorrido);
            recorrido.add(nodo.getDato());
            inOrdenRecursivo(nodo.getDerecho(), recorrido);
        }
    }

    public LinkedList<Integer> postOrden() {
        LinkedList<Integer> recorrido = new LinkedList<>();
        postOrdenRecursivo(raiz, recorrido);
        return recorrido;
    }

    private void postOrdenRecursivo(Nodo nodo, LinkedList<Integer> recorrido) {
        if (nodo != null) {
            postOrdenRecursivo(nodo.getIzquierdo(), recorrido);
            postOrdenRecursivo(nodo.getDerecho(), recorrido);
            recorrido.add(nodo.getDato());
        }
    }

    public boolean eliminar(Integer dato) {
        if (raiz == null) {
            return false; 
        } else {
            
            boolean eliminado = eliminarRecursivo(null, raiz, dato);
            if (eliminado) {
               
            }
            return eliminado;
        }
    }

    private boolean eliminarRecursivo(Nodo padre, Nodo nodo, Integer dato) {
        if (nodo == null) {
            return false;
        }

        if (dato < nodo.getDato()) {
            // Buscar en el subárbol izquierdo
            return eliminarRecursivo(nodo, nodo.getIzquierdo(), dato);
        } else if (dato > nodo.getDato()) {
            // Buscar en el subárbol derecho
            return eliminarRecursivo(nodo, nodo.getDerecho(), dato);
        } else {
            

            // Caso 1: Nodo sin hijos (hoja)
            if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                if (padre == null) {
                    raiz = null; 
                } else if (padre.getIzquierdo() == nodo) {
                    padre.izquierdo = null; 
                } else {
                    padre.derecho = null; 
                }
            }
            // Caso 2: Nodo con un hijo
            else if (nodo.getIzquierdo() == null) {
                // Solo tiene hijo derecho
                if (padre == null) {
                    raiz = nodo.getDerecho(); 
                } else if (padre.getIzquierdo() == nodo) {
                    padre.izquierdo = nodo.getDerecho(); 
                } else {
                    padre.derecho = nodo.getDerecho(); 
                }
            } else if (nodo.getDerecho() == null) {
                // Solo tiene hijo izquierdo
                if (padre == null) {
                    raiz = nodo.getIzquierdo(); 
                } else if (padre.getIzquierdo() == nodo) {
                    padre.izquierdo = nodo.getIzquierdo(); 
                } else {
                    padre.derecho = nodo.getIzquierdo(); 
                }
            }
            // Caso 3: Nodo con dos hijos
            else {
                
                Nodo sucesor = encontrarSucesor(nodo.getDerecho());
                
                nodo.dato = sucesor.getDato();
                
                eliminarRecursivo(nodo, nodo.getDerecho(), sucesor.getDato());
            }

            return true; // Nodo encontrado y eliminado
        }
    }

    private Nodo encontrarSucesor(Nodo nodo) {
        // Recorrer hacia la izquierda para encontrar el mínimo
        while (nodo.getIzquierdo() != null) {
            nodo = nodo.getIzquierdo();
        }
        return nodo;
    }

    //No posee función alguna
    arbolBB.Nodo getRaiz() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    private class Nodo {

        private Integer dato;
        private Nodo izquierdo;
        private Nodo derecho;
        private Color color = Color.WHITE;

        public Nodo(Integer dato) {
            this.dato = dato;
            this.izquierdo = null;
            this.derecho = null;
        }

        public Integer getDato() {
            return dato;
        }

        public Nodo getIzquierdo() {
            return izquierdo;
        }

        public Nodo getDerecho() {
            return derecho;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }
}
