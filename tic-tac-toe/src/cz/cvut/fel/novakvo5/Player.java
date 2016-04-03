/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.novakvo5;

import java.awt.Color;

/**
 *
 * @author vonovak
 */
public class Player {

    private Color myColor;
    private String name = new String();

    /**
     *vraci barvu hrace
     * @return  barvu hrace
     */
    public Color getColor() {
        return myColor;
    }

    /**
     * nastavuje barvu hrace
     * @param myColor barva hrace
     */
    public void setColor(Color myColor) {
        this.myColor = myColor;
    }

    /**
     *vraci jmeno hrace
     * @return jmeno hrace
     */
    public String getName() {
        return name;
    }

    /**
     * nastavuje jmeno hrace
     * @param name jmeno hrace
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *konstruktor - vytvori instanci hrace se jmenem a barvou
     * @param name jmeno noveho hrace
     * @param color barva noveho hrace
     */
    public Player(String name, Color color) {
        this.name = name;
        this.myColor = color;
    }

    @Override
    public String toString() {
        return "player \n{ color=" + myColor + ", name=" + name + '}';
    }
}
