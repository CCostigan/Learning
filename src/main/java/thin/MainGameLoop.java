package thin;

import java.util.ArrayList;

import org.joml.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import thin.resources.DisplayManager;
import thin.resources.Loader;
import thin.resources.Renderer;
import thin.resources.items.Camera;
import thin.resources.items.ModelItem;
import thin.resources.model.NewModel;
import thin.resources.model.OBJLoader;
import thin.resources.model.RawModel;
import thin.resources.model.TexturedModel;
import thin.resources.shader.ConcreteShader;
import thin.resources.texture.TextureLoader;
import thin.resources.texture.TextureWrapper;

/**
 * Loosely following the demo here https://www.youtube.com/watch?v=WMiggUPst-Q
 */

public class MainGameLoop {

    static ArrayList<NewModel>NewModels = new ArrayList<NewModel>();

    static int vtxCount = 0;

    public MainGameLoop() {

    }    

    void loadNewModels() {
        NewModels.add(NewModel.createHC());
    }

    public static void main(String [] argv) {

        DisplayManager.createDisplay(false);

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        ConcreteShader shader = new ConcreteShader();

        TextureWrapper tw = new TextureWrapper(TextureLoader.loadTexture("src/main/res/imgs/Earth_Day_Light.jpg"));
        // TextureWrapper tl = new TextureWrapper(TextureLoader.loadTexture("src/main/res/imgs/Earth_City_Light.jpg"));
        Vector3f mloc = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f mrot = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f msca = new Vector3f(1.0f, 1.0f, 1.0f);

        float [] verts = {
            -0.5f, 0.5f,-0.5f,	
            -0.5f,-0.5f,-0.5f,	
             0.5f,-0.5f,-0.5f,	
             0.5f, 0.5f,-0.5f,		
            
            -0.5f, 0.5f, 0.5f,	
            -0.5f,-0.5f, 0.5f,	
             0.5f,-0.5f, 0.5f,	
             0.5f, 0.5f, 0.5f,
            
             0.5f, 0.5f,-0.5f,	
             0.5f,-0.5f,-0.5f,	
             0.5f,-0.5f, 0.5f,	
             0.5f, 0.5f, 0.5f,
            
            -0.5f, 0.5f,-0.5f,	
            -0.5f,-0.5f,-0.5f,	
            -0.5f,-0.5f, 0.5f,	
            -0.5f, 0.5f, 0.5f,
            
            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f,-0.5f,
             0.5f, 0.5f,-0.5f,
             0.5f, 0.5f, 0.5f,
            
            -0.5f,-0.5f, 0.5f,
            -0.5f,-0.5f,-0.5f,
             0.5f,-0.5f,-0.5f,
             0.5f,-0.5f, 0.5f
        };
        float [] texuv = {
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0
        };
        int [] indxs = {
            0,1,3,	
            3,1,2,	

            4,5,7,
            7,5,6,

            8,9,11,
            11,9,10,

            12,13,15,
            15,13,14,	

            16,17,19,
            19,17,18,

            20,21,23,
            23,21,22
        };
        RawModel m2 = loader.loadToVAO(verts, texuv, indxs);
        TexturedModel m2t = new TexturedModel(m2, tw);
        ModelItem ment2 = new ModelItem(m2t, mloc, mrot, msca);

        RawModel m3 = OBJLoader.loadOBJModel("src/main/res/mdls/Cube.obj", loader);
        TexturedModel m3t = new TexturedModel(m3, tw);
        ModelItem ment3 = new ModelItem(m3t, mloc, mrot, msca);

        Camera camera = new Camera();

        while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {            
            renderer.prepare();

            shader.start();

            camera.move();
            shader.loadViewMatrix(camera);

            ment2.move(new Vector3f(0.0f, 0.0f, -0.0001f));
            ment2.turn(new Vector3f(0.0f, 0.01f, 0.01f));
            renderer.render(ment2, shader);

            ment3.move(new Vector3f(0.0f, 0.0f, -0.0001f));
            ment3.turn(new Vector3f(0.0f, 0.01f, 0.01f));
            renderer.render(ment3, shader);

            shader.stop();

            // for(NewModel m: NewModels) {  
            //     m.render();
            // }
    
            DisplayManager.updateDisplay();
        }

        shader.cleanup();
        loader.cleanup();

        DisplayManager.destroyDisplay();
    }

}



