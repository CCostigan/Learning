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
import thin.resources.items.ModelItem;
import thin.resources.model.HCLoader;
import thin.resources.model.NewModel;
import thin.resources.model.OBJLoader;
import thin.resources.model.RawModel;
import thin.resources.model.TexturedModel;
import thin.resources.render.Renderer;
import thin.resources.shader.ConcreteShader;
import thin.resources.texture.TextureLoader;
import thin.resources.texture.TextureWrapper;
import thin.resources.util.KeyMouse;

/**
 * Loosely following the demo here https://www.youtube.com/watch?v=WMiggUPst-Q
 */

public class MainGameLoop {

    static ArrayList<NewModel>NewModels = new ArrayList<NewModel>();

    static int vtxCount = 0;
    static int width = 1600;
    static int height = 900;

    public MainGameLoop() {

    }    

    void loadNewModels() {
        NewModels.add(NewModel.createHC());
    }

    public static void main(String [] argv) {

        DisplayManager.createDisplay(false, width, height);

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        ConcreteShader shader = new ConcreteShader();

        TextureWrapper tw = new TextureWrapper(TextureLoader.loadTexture("src/main/res/imgs/Earth_Day_Light.jpg"));
        // TextureWrapper tl = new TextureWrapper(TextureLoader.loadTexture("src/main/res/imgs/Earth_City_Light.jpg"));
        // TextureWrapper tw = new TextureWrapper(TextureLoader.loadTexture("src/main/res/imgs/lighter.png"));
        // TextureWrapper tw = new TextureWrapper(TextureLoader.loadTexture("src/main/res/imgs/tiny.png"));
        tw.shinedamping = 1.0f;
        tw.reflectivity = 0.0f;
        Vector3f mloc = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f mrot = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f msca = new Vector3f(1.0f, 1.0f, 1.0f);

        // RawModel m3 = OBJLoader.loadOBJModel("src/main/res/mdls/Square.obj", loader);
        // RawModel m3 = OBJLoader.loadOBJModel("src/main/res/mdls/Cube.obj", loader);
        RawModel m3 = OBJLoader.loadOBJModel("src/main/res/mdls/Sphere.obj", loader);
        // RawModel m3 = OBJLoader.loadOBJModel("src/main/res/mdls/LM.obj", loader);

        TexturedModel m3t = new TexturedModel(m3, tw);
        ModelItem ment3 = new ModelItem(m3t, mloc, mrot, msca);

        Random r = new Random();
        float d = 20.0f;
        List<ModelItem>models = new ArrayList<ModelItem>();
        for(int i=0;i<100;i++) {
            models.add(
                new ModelItem(m3t,
                    new Vector3f(2*d*r.nextFloat()-d, 2*d*r.nextFloat()-d, 2*d*r.nextFloat()-d),
                    new Vector3f(r.nextFloat(), r.nextFloat(), r.nextFloat()),
                    new Vector3f(1.0f, 1.0f, 1.0f)
                )
            );
        }

        Camera camera = new Camera(0,0,3);        
        KeyMouse keymouse = new KeyMouse(false, width, height);

        Light light = new Light(new Vector3f(0.0f, 0.0f, 20.0f),new Vector3f(1.0f, 1.0f, 1.0f));
        float ambientval = 0.2f;

        while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {            
            renderer.prepare();

            shader.start();
            shader.loadLight(light, ambientval);

            camera.move();
            shader.loadViewMatrix(camera);

            // ment3.move(new Vector3f(0.0f, 0.0f, -0.001f));
            ment3.turn(new Vector3f(0.0f, 0.001f, 0.0f));
            if(keymouse.canshow(2)) {
                renderer.render(ment3, shader);
            }
            for (ModelItem m: models) {
                m.turn(new Vector3f(0.0f, 0.001f, 0.001f));
                renderer.render(m, shader);
            }
            
            shader.stop();
    
            DisplayManager.updateDisplay();
            keymouse.update();            
        }

        shader.cleanup();
        loader.cleanup();

        DisplayManager.destroyDisplay();
    }

}



