package thin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joml.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import thin.resources.DisplayManager;
import thin.resources.Loader;
import thin.resources.items.Camera;
import thin.resources.items.Light;
import thin.resources.items.Entity;
import thin.resources.model.NewModel;
import thin.resources.model.OBJLoader;
import thin.resources.model.RawModel;
import thin.resources.model.TexturedModel;
import thin.resources.render.MainRenderer;
import thin.resources.texture.TextureLoader;
import thin.resources.texture.TextureWrapper;
import thin.resources.util.KeyMouse;

/**
 * Loosely following the demo here https://www.youtube.com/watch?v=WMiggUPst-Q
 */

public class MainLoop {

    static ArrayList<NewModel>NewModels = new ArrayList<NewModel>();

    static int vtxCount = 0;
    static int width = 1600;
    static int height = 900;

    public MainLoop() {

    }    

    void loadNewModels() {
        NewModels.add(NewModel.createHC());
    }

    public static void main(String [] argv) {

        DisplayManager.createDisplay(false, width, height);

        Loader loader = new Loader();
        // ConcreteShader shader = new ConcreteShader();
        // Renderer renderer = new Renderer(shader);
        MainRenderer mainrend = new MainRenderer();

        // TextureWrapper tw = new TextureWrapper(TextureLoader.loadTexture("src/main/res/imgs/Earth_Day_Light.jpg"));
        // TextureWrapper tw = new TextureWrapper(TextureLoader.loadTexture("src/main/res/imgs/Earth_City_Light.jpg"));
        // TextureWrapper tw = new TextureWrapper(TextureLoader.loadTexture("src/main/res/imgs/Earth_Water_Alpha.png"));
        // TextureWrapper tw = new TextureWrapper(TextureLoader.loadTexture("src/main/res/imgs/TinyBubbles.png"));
        // TextureWrapper tw = new TextureWrapper(TextureLoader.loadTexture("src/main/res/imgs/lighter.png"));
        TextureWrapper tw = new TextureWrapper(TextureLoader.loadTexture("src/main/res/imgs/tiny.png"));
        tw.reflectivity = 1.0f;
        tw.shinedamping = 8.0f;

        // RawModel m3 = OBJLoader.loadOBJModel("src/main/res/mdls/Square.obj", loader);
        // RawModel m3 = OBJLoader.loadOBJModel("src/main/res/mdls/Cube.obj", loader);
        RawModel m3 = OBJLoader.loadOBJModel("src/main/res/mdls/Sphere.obj", loader);
        // RawModel m3 = OBJLoader.loadOBJModel("src/main/res/mdls/LM.obj", loader);
        // RawModel m3 = OBJLoader.loadOBJModel("src/main/res/mdls/dragon.obj", loader);
        // RawModel m3 = OBJLoader.loadOBJModel("src/main/res/mdls/bunny.obj", loader);

        TexturedModel m3t = new TexturedModel(m3, tw);

        Random r = new Random();
        float d = 25.0f;
        List<Entity>models = new ArrayList<Entity>();
        for(int i=0;i<100;i++) {
            models.add(
                new Entity(m3t,
                    new Vector3f(2*d*r.nextFloat()-d, 2*d*r.nextFloat()-d, 2*d*r.nextFloat()-d),
                    new Vector3f(6.2f*r.nextFloat(), 6.2f*r.nextFloat(), 6.2f*r.nextFloat()),
                    // new Vector3f(0.0f, 0.0f, 0.0f),
                    new Vector3f(1.0f, 1.0f, 1.0f)
                )
            );
        }

        Camera camera = new Camera(0,0,3);        
        KeyMouse keymouse = new KeyMouse(false, width, height);

        Light light = new Light(new Vector3f(100.0f, 200.0f, 400.0f),new Vector3f(1.0f, 1.0f, 1.0f));
        float ambientval = 0.2f;
        models.add( new Entity(m3t, new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(2.0f, 2.0f, 2.0f)) );

        while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {            
  
            camera.move();
  
            for (Entity m: models) {
                m.turn(new Vector3f(0.0f, 0.01f, 0.0f));
                mainrend.peocessEntity(m);
            }
            
            mainrend.render(camera, light, ambientval);

            DisplayManager.updateDisplay();
            keymouse.update();            
        }

        // shader.cleanup();
        loader.cleanup();
        mainrend.cleanup();

        DisplayManager.destroyDisplay();
    }

}



