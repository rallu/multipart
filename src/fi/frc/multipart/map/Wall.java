package fi.frc.multipart.map;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import fi.frc.multipart.Main;
import fi.frc.multipart.entities.Placeable;
/**
 *
 * @author Rallu
 */
public class Wall extends Placeable {
    private int rotation = 0;
    
    public Wall() {
        super(0, 0, 4, 4);
        
        //collision
        parts = new int[4][4];
        for (int i = 0; i < parts.length; i++) {
            for (int j = 0; j < parts[i].length; j++) {
                parts[i][j] = 0;
            }
        }
        
    }
    
    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
        
        this.setLocalTranslation(this.x, 1f, this.y);
    }
    
    public Wall(int [][] parts, int originX, int originY) {
        super(0, 0, parts.length, parts[0].length, originX, originY);
        this.parts = parts;
        build();
    }
    
    private void build() {
        for (int i = 0; i < parts.length; i++) {
            for (int j = 0; j < parts[i].length; j++) {
                /* A colored lit cube. Needs light source! */
                if (parts[i][j] == 1) {
                    Box boxMesh = new Box(0.5f,0.5f,0.5f); 
                    Geometry boxGeo = new Geometry("Colored Box", boxMesh); 
                    Material boxMat = new Material(Main.app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md"); 
                    boxMat.setBoolean("UseMaterialColors", true); 
                    boxMat.setColor("Ambient", ColorRGBA.Green); 
                    boxMat.setColor("Diffuse", ColorRGBA.Green); 
                    boxGeo.setMaterial(boxMat); 
                    boxGeo.setLocalTranslation(i, 0, j);
                    this.attachChild(boxGeo);
                }
            }
        }
    }
    
    public void rotate() {
        rotation++;
        rotation = rotation % 4;
        
        int [][] newparts = new int[width][height];
        for (int i = 0; i < parts.length; i++) {
            for (int j = 0; j < parts[i].length; j++) {
                //newparts[i][j] = parts[width - j - 1][i];
                newparts[i][j] = parts[j][width - i - 1];
            }
        }
        parts = newparts;
        
        this.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI/2 * rotation, new Vector3f(0,1,0)));
    }
    
}
