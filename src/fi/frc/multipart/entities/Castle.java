package fi.frc.multipart.entities;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import fi.frc.multipart.Main;
import fi.frc.multipart.map.Map;
/**
 *
 * @author Rallu
 */
public class Castle extends Placeable {
    private int distanceToWall = 3;
    
    public Castle(int x, int y) {
        super(x, y, 2, 2);
        setParts(new int [][] {{1, 1}, {1, 1}});
        
        build();
    }
    
    private void build() {
        /* A colored lit cube. Needs light source! */ 
        Box boxMesh = new Box(0.5f * this.width,1f,0.5f * this.height); 
        Geometry boxGeo = new Geometry("Colored Box", boxMesh); 
        Material boxMat = new Material(Main.app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md"); 
        boxMat.setBoolean("UseMaterialColors", true); 
        boxMat.setColor("Ambient", ColorRGBA.Green); 
        boxMat.setColor("Diffuse", ColorRGBA.Green); 
        boxGeo.setMaterial(boxMat); 
        this.attachChild(boxGeo);
        
        this.setLocalTranslation(this.x + 0.5f, 1f, this.y + 0.5f);
    }
    
    public void render() {
        
    }
    
    public boolean isSurroundingFree(Map map) {
        if (x - distanceToWall < 0) {
            return false;
        }
        
        if (y - distanceToWall < 0) {
            return false;
        }
        
        if (x + width + distanceToWall > map.getWidth()) {
            return false;
        }
        
        if (y + height + distanceToWall > map.getHeight()) {
            return false;
        }
        
        int [][] blocking = map.getBlockingMatrix();
        for (int i = x - distanceToWall; i < x + width + distanceToWall; i++) {
            for (int j = y - distanceToWall; j < y + height + distanceToWall; j++) {
                if (!(i >= x && i <= x + width && j >= y && j <= y + height)) {
                    if (blocking[i][j] != 0) {
                        return false;
                    }
                } 
            }
        }
        
        return true;
    }
}
