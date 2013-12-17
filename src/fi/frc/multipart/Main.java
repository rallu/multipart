package fi.frc.multipart;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import fi.frc.multipart.entities.Castle;
import fi.frc.multipart.map.Map;
import fi.frc.multipart.map.Wall;
import fi.frc.multipart.map.WallFactory;


/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    private Map map;
    public static Main app;
    private Wall wallToPlace;
    private PointLight lamp;

    public static void main(String[] args) {
        app = new Main();
        AppSettings cfg = new AppSettings(true);
        cfg.setFrameRate(60); // set to less than or equal screen refresh rate
        cfg.setVSync(true);   // prevents page tearing
        cfg.setFrequency(60); // set to screen refresh rate
        cfg.setResolution(1024, 768);
        cfg.setFullscreen(false);
        cfg.setSamples(2);    // anti-aliasing
        cfg.setTitle("MultiPart"); // branding: window name
        app.setShowSettings(false); // or don't display splashscreen
        app.setSettings(cfg);
        app.start();
    }

    @Override
    public void simpleInitApp() {

        /**
         * An unshaded textured cube. Uses texture from jme3-test-data library!
         */
        Box boxMesh = new Box(0.5f, 0.5f, 0.5f);
        Geometry boxGeo = new Geometry("A Textured Box", boxMesh);
        Material boxMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture monkeyTex = assetManager.loadTexture("Textures/dirt.png");
        boxMat.setTexture("ColorMap", monkeyTex);
        boxGeo.setMaterial(boxMat);
        rootNode.attachChild(boxGeo);

        map = new Map();
        map.generateRandomMap();
        rootNode.attachChild(map);

        Castle c1 = new Castle(10, 10);
        map.AddCastle(c1);


        /**
         * Must add a light to make the lit object visible!
         */
        /*
         DirectionalLight sun = new DirectionalLight();
         sun.setDirection(new Vector3f(0,4,-2).normalizeLocal());
         sun.setColor(ColorRGBA.White);
         rootNode.addLight(sun);
         */
        /**
         * A white, spot light source.
         */
        lamp = new PointLight();
        lamp.setPosition(Vector3f.ZERO);
        lamp.setColor(ColorRGBA.White);
        rootNode.addLight(lamp);

        flyCam.setMoveSpeed(100f);
        flyCam.setEnabled(false);

        cam.setLocation(new Vector3f(30, 30f, 10));
        cam.setRotation(new Quaternion().fromAngleAxis(FastMath.PI / 2, new Vector3f(0, 1, 0)));
        cam.setRotation(new Quaternion().fromAngleAxis(FastMath.PI / 3, new Vector3f(1, 0, 0)));
        wallToPlace = WallFactory.getRandomWall();
        rootNode.attachChild(wallToPlace);


        inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("rotate", new KeyTrigger(KeyInput.KEY_SPACE));

        inputManager.addListener(actionListener, "rotate");
        inputManager.addListener(analogListener, "left", "right", "up", "down");
    }

    @Override
    public void simpleUpdate(float tpf) {

        lamp.setPosition(cam.getLocation());

        Ray ray = new Ray(cam.getLocation(), cam.getDirection());
        CollisionResults results = new CollisionResults();
        map.getTerrainNode().collideWith(ray, results);
        if (results.size() > 0) {
            int x = (Integer) results.getClosestCollision().getGeometry().getUserData("x");
            int y = (Integer) results.getClosestCollision().getGeometry().getUserData("y");
            wallToPlace.moveTo(x, y);
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    private AnalogListener analogListener = new AnalogListener() {
        private float speed = 20f;

        public void onAnalog(String name, float value, float tpf) {
            if (name.equals("right")) {
                Vector3f v = cam.getLocation();
                cam.setLocation(new Vector3f(v.x - value * speed, v.y, v.z));
            }
            if (name.equals("left")) {
                Vector3f v = cam.getLocation();
                cam.setLocation(new Vector3f(v.x + value * speed, v.y, v.z));
            }
            if (name.equals("up")) {
                Vector3f v = cam.getLocation();
                cam.setLocation(new Vector3f(v.x, v.y, v.z + value * speed));
            }
            if (name.equals("down")) {
                Vector3f v = cam.getLocation();
                cam.setLocation(new Vector3f(v.x, v.y, v.z - value * speed));
            }
        }
    };
    
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("rotate")) {
                wallToPlace.rotate();
            }
        }
    };
}
