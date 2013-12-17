package fi.frc.multipart.map;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import fi.frc.multipart.Main;
import fi.frc.multipart.entities.Player;
import fi.frc.multipart.entities.Castle;
import fi.frc.multipart.entities.Placeable;

/**
 *
 * @author Rallu
 */
public class Map extends Node {
    
    public static final int tileSize = 32;
    
    private int [][] intblocking;
    
    private int height;
    private int width;
    
    private boolean displayBlocking = false;
    
    Node castles = new Node();
    Node players = new Node();
    Node walls = new Node();
    private int playerIndex = 0;
    
    
    private Node terrainNode;
    
    public Map() {
        terrainNode = new Node();
        this.attachChild(terrainNode);
    }
    
    public void loadMap(String name)  {

    }
    
    public void generateRandomMap() {
        intblocking = new int[50][50];
        
        Box boxMesh = new Box(0.5f,0.5f,0.5f); 
        Geometry boxGeo = new Geometry("A Textured Box", boxMesh); 
        Material boxMat = new Material(Main.app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md"); 
        Texture monkeyTex = Main.app.getAssetManager().loadTexture("Textures/dirt.png"); 
        boxMat.setTexture("ColorMap", monkeyTex); 
        boxGeo.setMaterial(boxMat); 
        
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                Geometry tmp = new Geometry("box " + i + ", " + j, boxMesh);
                tmp.setMaterial(boxMat);
                tmp.setLocalTranslation(i, 0, j);
                tmp.setUserData("x", i);
                tmp.setUserData("y", j);
                terrainNode.attachChild(tmp);
            }
        }
        
        this.attachChild(terrainNode);
        this.attachChild(castles);
    }
    
    public Node getTerrainNode() {
        return terrainNode;
    }
    
    public int[][] getBlockingMatrix() {
        return intblocking;
    }
    
    public void addPlayer(Player p) {
        p.setPlayerNumber(playerIndex);
        players.attachChild(p);
        playerIndex++;
    }

    public void AddCastle(Castle c) {
        castles.attachChild(c);
        
        intblocking[c.getX()][c.getY()] = 1;
        intblocking[c.getX() + 1][c.getY()] = 1;
        intblocking[c.getX()][c.getY() + 1] = 1;
        intblocking[c.getX() + 1][c.getY() + 1] = 1;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setPlaceBlocking(int x, int y) {
        intblocking[x][y] = 1;
    }
    
    public boolean canPlace(Placeable w) {
        int [][] wallParts = w.getParts();
        int wallx = w.getX() - w.getOriginX();
        int wally = w.getY() - w.getOriginY();
        
        if (wallx < 0) {
            wallx = 0;
        }
        if (wally < 0) {
            wally = 0;
        }
        
        //check for map blocking
        for (int i = 0; i < wallParts.length; i++) {
            for (int j = 0; j < wallParts[i].length; j++) {
                if (wallParts[i][j] == 1) {
                    if (intblocking[wallx + i][wally + j] == 1) {
                        return false;
                    }
                }
            }
        }
        
        //check for playerblocking
        for (Spatial spatial : players.getChildren()) {
            Player player = (Player) spatial;
            int [][] walls = player.getPlayerWalls();
            for (int i = 0; i < wallParts.length; i++) {
                for (int j = 0; j < wallParts[i].length; j++) {
                    if (wallParts[i][j] > 0) {
                        if (walls[wallx + i][wally + j] == 1) {
                            return false;
                        }
                    }
                }
            }
        }
        
        return true;
    }
    
    public void toggleDisplayBlocking() {
        displayBlocking = !displayBlocking;
    }
    
}
