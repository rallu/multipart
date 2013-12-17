package fi.frc.multipart.map;

/**
 *
 * @author Rallu
 */
public class WallFactory {

    
    
    public static Wall getRandomWall() {
        int r = (int) (Math.random() * 6);
        switch (r) {
            case 0: 
                return new Wall(new int[][] {{1}}, 1, 1);
            case 1:
                int [][] parts = {{0, 0, 0, 0}, {0, 1, 1, 1}, { 1, 1, 1, 0}, {0, 0, 0, 0} };
                return new Wall(parts, 2, 2);
            case 2:
                return new Wall(new int [][] {{0, 0, 1}, {1, 1, 1}, { 0, 0, 0}}, 1, 1);
            case 3:
                return new Wall(new int [][] {{1, 0, 1}, {1, 1, 1}, { 0, 0, 0}}, 1, 1);
            case 4:
                return new Wall(new int [][] {{0, 0, 1}, {1, 1, 1}, { 1, 0, 0}}, 1, 1);
            case 5:
                return new Wall(new int [][] {{0, 0, 0}, {1, 1, 1}, { 0, 0, 0}}, 1, 1);
        }
        
        return null;
    }

}
