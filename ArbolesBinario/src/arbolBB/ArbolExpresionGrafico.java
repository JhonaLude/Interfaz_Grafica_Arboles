package arbolBB;

 /**
 *
 * @author Jhonathan Alexander Ludeña Cevallos
 */

//07-07-2024 16:41

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class ArbolExpresionGrafico extends JPanel {
    private ArbolBB miArbol;
    private HashMap<Nodo, Rectangle> posicionNodos = null;
    private HashMap<Nodo, Dimension> subtreeSizes = null;
    private boolean dirty = true;
    private int parent2child = 20, child2child = 30;
    private Dimension empty = new Dimension(0, 0);
    private FontMetrics fm = null;

    /**
     * Constructor para inicializar el gráfico del árbol.
     */
    public ArbolExpresionGrafico(ArbolBB miArbol) {
        this.miArbol = miArbol;
        this.setBackground(Color.WHITE);
        posicionNodos = new HashMap<>();
        subtreeSizes = new HashMap<>();
        dirty = true;
        repaint();
    }

    /**
     * Calcula las posiciones de los nodos y subárboles para el dibujo.
     */
    private void calcularPosiciones() {
        posicionNodos.clear();
        subtreeSizes.clear();
        Nodo root = this.miArbol.getRaiz();
        if (root != null) {
            calcularTamañoSubarbol(root);
            calcularPosicion(root, Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
        }
    }

    /**
     * Calcula el tamaño de cada subárbol.
     * @param n Nodo raíz del subárbol.
     * @return Las dimensiones del subárbol.
     */
    private Dimension calcularTamañoSubarbol(Nodo n) {
        if (n == null) return new Dimension(0, 0);

        Dimension ld = calcularTamañoSubarbol(n.getIzq());
        Dimension rd = calcularTamañoSubarbol(n.getDer());

        int h = fm.getHeight() + parent2child + Math.max(ld.height, rd.height);
        int w = ld.width + child2child + rd.width;

        Dimension d = new Dimension(w, h);
        subtreeSizes.put(n, d);

        return d;
    }

    /**
     * Calcula la posición de cada nodo en el subárbol.
     * @param n Nodo actual.
     * @param left Límite izquierdo.
     * @param right Límite derecho.
     * @param top Posición superior.
     */
    private void calcularPosicion(Nodo n, int left, int right, int top) {
        if (n == null) return;

        Dimension ld = subtreeSizes.get(n.getIzq());
        if (ld == null) ld = empty;

        Dimension rd = subtreeSizes.get(n.getDer());
        if (rd == null) rd = empty;

        int center = 0;

        if (right != Integer.MAX_VALUE)
            center = right - rd.width - child2child / 2;
        else if (left != Integer.MAX_VALUE)
            center = left + ld.width + child2child / 2;
        int width = fm.stringWidth(n.getDato() + "");

        posicionNodos.put(n, new Rectangle(center - width / 2 - 3, top, width + 6, fm.getHeight()));

        calcularPosicion(n.getIzq(), Integer.MAX_VALUE, center - child2child / 2, top + fm.getHeight() + parent2child);
        calcularPosicion(n.getDer(), center + child2child / 2, Integer.MAX_VALUE, top + fm.getHeight() + parent2child);
    }

    /**
     * Dibuja el árbol en el componente gráfico.
     * @param g Contexto gráfico para dibujar.
     * @param n Nodo actual.
     * @param puntox Coordenada x de la línea.
     * @param puntoy Coordenada y de la línea.
     * @param yoffs Desplazamiento en y para la altura del texto.
     */
    private void dibujarArbol(Graphics2D g, Nodo n, int puntox, int puntoy, int yoffs) {
        if (n == null) return;

        Rectangle r = posicionNodos.get(n);
        g.draw(r);
        g.drawString(n.getDato() + "", r.x + 3, r.y + yoffs);

        if (puntox != Integer.MAX_VALUE)
            g.drawLine(puntox, puntoy, (int) (r.x + r.width / 2), r.y);

        dibujarArbol(g, n.getIzq(), (int) (r.x + r.width / 2), r.y + r.height, yoffs);
        dibujarArbol(g, n.getDer(), (int) (r.x + r.width / 2), r.y + r.height, yoffs);
    }

    /**
     * Sobreescribe el método paint para dibujar el árbol.
     * @param g Contexto gráfico para dibujar.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        fm = g.getFontMetrics();

        if (dirty) {
            calcularPosiciones();
            dirty = false;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(getWidth() / 2, parent2child);
        dibujarArbol(g2d, this.miArbol.getRaiz(), Integer.MAX_VALUE, Integer.MAX_VALUE, fm.getLeading() + fm.getAscent());
        fm = null;
    }
}
