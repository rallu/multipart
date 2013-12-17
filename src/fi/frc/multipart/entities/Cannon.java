package fi.frc.multipart.entities;

import fi.frc.multipart.map.Map;


/**
 *
 * @author Rallu
 */
public class Cannon extends Placeable {

    private Player owner;

    private float projectileX;
    private float projectileY;
    private float projectileInterpolation;
   
    private float targetX;
    private float targetY;
    private float distanceToTarget;
    
    private float shootAmount = 0;
    private float shootSpeed = 100;
    
    private boolean shooting = false;
    
    
    public Cannon(int x, int y, int xSize, int ySize, Player owner) {
        super(x, y, xSize, ySize);
        parts = new int[xSize][ySize];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                parts[i][j] = 1;
            }
        }

        this.owner = owner;
    }
    
    public void shoot(int x, int y) {
        if (shooting)
            return;
        
        this.targetX = x * Map.tileSize + 8f;
        this.targetY = y * Map.tileSize + 8f;
        projectileX = this.x * Map.tileSize + 24f;
        projectileY = this.y * Map.tileSize + 24f;
        shooting = true;
        shootAmount = 0;
        
        float tmpx = (targetX - projectileX);
        float tmpy = (targetY - projectileY);
        distanceToTarget = (float) Math.sqrt(tmpx * tmpx + tmpy * tmpy) / Map.tileSize;
    }
    
    public void update(float delta) {
        if (shooting) {
            shootAmount += (float) delta;
            
            projectileInterpolation = (float) shootAmount / (float) (shootSpeed * distanceToTarget);
            if (projectileInterpolation >= 1) {
                shooting = false;
            } else {
                float [] projectileplace = interpolate(projectileInterpolation, x * Map.tileSize, y * Map.tileSize, targetX, targetY);
                projectileX = projectileplace[0];
                projectileY = projectileplace[1] - (float) Math.sin(Math.PI * projectileInterpolation) * 8 * distanceToTarget;
            }
        }
    }
    
    public void render() {
    }
    
    private float [] interpolate(float amount, float x1, float y1, float x2, float y2) {
        
        float x = x1 * (1 - amount) + x2 * amount;
        float y = y1 * (1 - amount) + y2 * amount;
        return new float[] { x, y };
    }
}
