package fi.frc.multipart.entities;

import com.jme3.scene.Node;
import fi.frc.multipart.map.Map;
import fi.frc.multipart.map.Wall;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Rallu
 */
public class Player extends Node {

    private String name;
    //private Color playerColor;
    
    private boolean firstWallPlace = true;
    private int wallMinX = 0;
    private int wallMaxX = 0;
    private int wallMinY = 0;
    private int wallMaxY = 0;
    private int[][] playerWalls;
    private int[][] playerInsides;
    private int[][] drawWalls;
    private ArrayList<Wall> placedWalls = new ArrayList<Wall>();
    private int playerNumber;
    private Map currentMap;
    
    public Player(String name) {
        this.name = name;
        //playerColor = color;
    }
    
    public String getName() {
        return name;
    }
    /*
    public Color getColor() {
        return playerColor;
    }
    */
    public void initMap(Map map) {
        playerWalls = new int[map.getWidth()][map.getHeight()];
        playerInsides = new int[map.getWidth()][map.getHeight()];
        drawWalls = new int[map.getWidth()][map.getHeight()];
        for (int i = 0; i < playerInsides.length; i++) {
            for (int j = 0; j < playerInsides[0].length; j++) {
                playerInsides[i][j] = 1;
            }
        }
        currentMap = map;
        map.addPlayer(this);
    }
    
    public void setPlayerNumber(int number) {
        playerNumber = number;
    }
    
    public int getPlayerNumber() {
        return playerNumber;
    }
    
    public int [][] getPlayerWalls() {
        return playerWalls;
    }
    
    public int [][] getPlayerInsides() {
        return playerInsides;
    }
    
    public void placeWall(Wall w) {
        int [][] wallParts = w.getParts();
        int wallx = w.getX() - w.getOriginX();
        int wally = w.getY() - w.getOriginY();
        
        if (wallx < 0) {
            wallx = 0;
        }
        if (wally < 0) {
            wally = 0;
        }
        
        for (int i = 0; i < wallParts.length; i++) {
            for (int j = 0; j < wallParts[i].length; j++) {
                if (wallParts[i][j] == 1) {
                    int x = wallx + i;
                    int y = wally + j;
                    
                    /**
                     * Calc area for inside calculation
                     */
                    if (firstWallPlace) {
                        wallMinX = x;
                        wallMaxX = x;
                        wallMinY = y;
                        wallMaxY = y;
                        firstWallPlace = false;
                    } else {
                        if (x < wallMinX) {
                            wallMinX = x;
                        } else if (x > wallMaxX) {
                            wallMaxX = x;
                        } 
                        if (y < wallMinY) {
                            wallMinY = y;
                        } else if (y > wallMaxY) {
                            wallMaxY = y;
                        }
                    }
                    
                    playerWalls[x][y] = 1;
                    currentMap.setPlaceBlocking(x, y);
                }
            }
        }
        
        placedWalls.add(w);
        calculateWallInsides();
        updatePlayerWallNumbers();
    }
    
    private void updatePlayerWallNumbers() {
        for (int i = 0; i < playerWalls.length; i++) {
            System.arraycopy(playerWalls[i], 0, drawWalls[i], 0, playerWalls[i].length);
        }
        
        for (int i = 0; i < playerWalls.length; i++) {
            for (int j = 0; j < playerWalls[i].length; j++) {
                if (playerWalls[i][j] == 0) {
                    continue;
                }
                
                if ( 
                    playerWalls[i][j-1] > 0 && 
                    playerWalls[i-1][j] > 0 && 
                    playerWalls[i+1][j] > 0 && 
                    playerWalls[i][j+1] > 0
                        ) {
                    drawWalls[i][j] = 3;
                } else if ( 
                    playerWalls[i][j-1] > 0 && 
                    playerWalls[i-1][j] > 0 && 
                    playerWalls[i][j+1] > 0
                        ) {
                    drawWalls[i][j] = 9;
                } else if ( 
                    playerWalls[i][j-1] > 0 && 
                    playerWalls[i-1][j] > 0 && 
                    playerWalls[i+1][j] > 0
                        ) {
                    drawWalls[i][j] = 10;
                } else if ( 
                    playerWalls[i][j-1] > 0 && 
                    playerWalls[i][j+1] > 0 &&
                    playerWalls[i+1][j] > 0
                        ) {
                    drawWalls[i][j] = 11;
                } else if ( 
                    playerWalls[i][j+1] > 0 && 
                    playerWalls[i-1][j] > 0 && 
                    playerWalls[i+1][j] > 0
                        ) {
                    drawWalls[i][j] = 12;
                } else if ( 
                    playerWalls[i][j-1] > 0 && 
                    playerWalls[i+1][j] > 0
                        ) {
                    drawWalls[i][j] = 5;
                } else if ( 
                    playerWalls[i][j+1] > 0 && 
                    playerWalls[i+1][j] > 0
                        ) {
                    drawWalls[i][j] = 6;
                } else if ( 
                    playerWalls[i][j+1] > 0 && 
                    playerWalls[i-1][j] > 0
                        ) {
                    drawWalls[i][j] = 7;
                } else if ( 
                    playerWalls[i][j-1] > 0 && 
                    playerWalls[i-1][j] > 0
                        ) {
                    drawWalls[i][j] = 8;
                } else if ( 
                    playerWalls[i][j-1] > 0 && 
                    playerWalls[i][j+1] > 0
                        ) {
                    drawWalls[i][j] = 1;
                } else if (
                    playerWalls[i-1][j] > 0 && 
                    playerWalls[i+1][j] > 0
                        ) {
                    drawWalls[i][j] = 2;
                } else if (
                    playerWalls[i][j-1] > 0
                        ) {
                    drawWalls[i][j] = 13;
                } else if (
                    playerWalls[i+1][j] > 0
                        ) {
                    drawWalls[i][j] = 14;
                } else if (
                    playerWalls[i][j+1] > 0
                        ) {
                    drawWalls[i][j] = 15;
                } else if (
                    playerWalls[i-1][j] > 0
                        ) {
                    drawWalls[i][j] = 16;
                }
            }
        }
    }
    
    public int[][] getDrawWalls() {
        return drawWalls;
    }
    
    private void calculateWallInsides() {
        for (int i = wallMinX; i <= wallMaxX; i++) {
            for (int j = wallMinY; j <= wallMaxY; j++) {
                playerInsides[i][j] = playerWalls[i][j];
            }
        }
        
        LinkedList<Point> fillQueue = new LinkedList<Point>();
        for (int i = wallMinX; i <= wallMaxX; i++) {
            for (int j = wallMinY; j <= wallMaxY; j++) {
                if (((i == wallMinX || i == wallMaxX) && playerInsides[i][j] == 0) || ((j == wallMinY || j == wallMaxY) && playerInsides[i][j] == 0)) {
                    fillQueue.add(new Point(i,j));
                    while (fillQueue.size() > 0) {
                        Point p = fillQueue.pop();
                        checkSurroundings(playerInsides, p.x, p.y, fillQueue);
                        playerInsides[p.x][p.y] = 2;
                    }
                }
            }
        }
        
    }
    
    private void checkSurroundings(int [][] array, int x, int y, LinkedList queue) {
        for (int i = x - 1; i <= x+1; i++) {
            for (int j = y - 1; j <= y+1; j++) {
                if (i >= wallMinX && j >= wallMinY && i < wallMaxX && j < wallMaxY && array[i][j] == 0 && !(i == x && j == y)) {
                    array[i][j] = 2;
                    queue.add(new Point(i, j));
                }
            }
        }
    }
    
    private void fillUsed(int x, int y) {
        for (int i = x - 1; i <= x+1; i++) {
            for (int j = y - 1; j <= y+1; j++) {
                if (i > 0 && j > 0 && i < playerInsides.length && j < playerInsides[i].length && playerInsides[i][j] == 0) {
                    playerInsides[i][j] = 2;
                }
            }
        }
        
    }
    
    private void printArray(int [][] array) {
        for (int i = wallMinX; i <= wallMaxX; i++) {
            for (int j = wallMinY; j <= wallMaxY; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }
    
    private class Point {
        public int x;
        public int y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    

}
