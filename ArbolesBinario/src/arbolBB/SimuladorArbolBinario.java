package arbolBB;

/**
 *
 * @author Jhonathan Alexander Ludeña Cevallos
 */

//07-07-2024 16:41

import java.awt.Color;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SimuladorArbolBinario {
    private ArbolBB miArbol = new ArbolBB();
    private boolean estaPintando = false;
    private JPanel panelArbol;
    private StringBuilder resultadoRecorrido;

    public SimuladorArbolBinario() {
        panelArbol = miArbol.getdibujo();
        resultadoRecorrido = new StringBuilder();
    }

    public boolean insertar(Integer dato) {
        boolean resultado = this.miArbol.agregar(dato);
        if (resultado) {
            actualizarDibujo();
        }
        return resultado;
    }

    public String preOrden() {
        if (!estaPintando) {
            LinkedList<Integer> recorrido = this.miArbol.preOrden();
            pintarNodosSecuencialmente(recorrido);
            return obtenerRecorridoComoString(recorrido);
        }
        return "";
    }

    public String inOrden() {
        if (!estaPintando) {
            LinkedList<Integer> recorrido = this.miArbol.inOrden();
            pintarNodosSecuencialmente(recorrido);
            return obtenerRecorridoComoString(recorrido);
        }
        return "";
    }

    public String postOrden() {
        if (!estaPintando) {
            LinkedList<Integer> recorrido = this.miArbol.postOrden();
            pintarNodosSecuencialmente(recorrido);
            return obtenerRecorridoComoString(recorrido);
        }
        return "";
    }

    private String obtenerRecorridoComoString(LinkedList<Integer> recorrido) {
        StringBuilder sb = new StringBuilder();
        for (Integer dato : recorrido) {
            sb.append(dato).append(" ");
        }
        return sb.toString().trim();
    }

    private void pintarNodosSecuencialmente(LinkedList<Integer> recorrido) {
        estaPintando = true;
        resultadoRecorrido.setLength(0);
        new Thread(() -> {
            try {
                for (Integer dato : recorrido) {
                    SwingUtilities.invokeLater(() -> {
                        miArbol.pintarNodo(dato, Color.RED);
                        actualizarDibujo();
                        resultadoRecorrido.append(dato).append(" ");
                    });
                    Thread.sleep(1000); // Espera 1 segundo entre cada nodo
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                estaPintando = false;
                // Volver a pintar todos los nodos en negro al finalizar
                SwingUtilities.invokeLater(() -> {
                    for (Integer dato : recorrido) {
                        miArbol.pintarNodo(dato, Color.WHITE);
                    }
                    actualizarDibujo();
                });
            }
        }).start();
    }

    private void actualizarDibujo() {
        panelArbol.repaint();
    }

    public JPanel getDibujo() {
        return panelArbol;
    }

    public String getResultadoRecorrido() {
        return resultadoRecorrido.toString().trim();
    }

    public boolean eliminar(Integer dato) {
        boolean resultado = this.miArbol.eliminar(dato);
        if (resultado) {
            actualizarDibujo();
        }
        return resultado;
    }

    public void resetear() {
        miArbol = new ArbolBB();
        panelArbol = miArbol.getdibujo();
        resultadoRecorrido.setLength(0);
        actualizarDibujo();
    }
}