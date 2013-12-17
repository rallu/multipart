package fi.frc.multipart.entities;

import com.jme3.scene.Node;

/**
 *
 * @author Rallu
 */
public class Placeable extends Node {
    protected int [][] parts;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int originX;
    protected int originY;
    
    /**
     * Create new placeable item
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param originX
     * @param originY 
     */
    public Placeable(int x, int y, int width, int height, int originX, int originY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.originX = originX;
        this.originY = originY;
    }
    
    /**
     * Create new placeble item in with origin in 0, 0
     * 
     * @param x
     * @param y
     * @param width
     * @param height 
     */
    public Placeable(int x, int y, int width, int height) {
        this(x, y, width, height, 0, 0);
    }
    
    public void setParts(int [][] parts) {
        this.parts = parts;
    }
    
    public int[][] getParts() {
        return parts;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the originX
     */
    public int getOriginX() {
        return originX;
    }

    /**
     * @param originX the originX to set
     */
    public void setOriginX(int originX) {
        this.originX = originX;
    }

    /**
     * @return the originY
     */
    public int getOriginY() {
        return originY;
    }

    /**
     * @param originY the originY to set
     */
    public void setOriginY(int originY) {
        this.originY = originY;
    }

}
