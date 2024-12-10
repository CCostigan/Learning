package thin;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import thin.resources.Loader;
import thin.resources.model.NewModel;
import thin.resources.model.RawModel;
import thin.resources.model.TexturedModel;
import thin.resources.shader.FirstShader;
import thin.resources.shader.TexturedShader;
import thin.resources.texture.TextureLoader;
import thin.resources.texture.TextureWrapper;

/**
 * Loosely following the demo here https://www.youtube.com/watch?v=WMiggUPst-Q
 */

public class MainGameLoop {

    ArrayList<NewModel>NewModels = new ArrayList<NewModel>();

    int vtxCount = 0;

    public MainGameLoop() {

    }    

    void loadNewModels() {
        NewModels.add(NewModel.createHC());
    }

    void render() {
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
        for(NewModel m: NewModels) {  
            m.render();
        }
    }

    public static void main(String [] argv) {

        DisplayManager.createDisplay(false);

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        FirstShader fshader = new FirstShader();
        TexturedShader tshader = new TexturedShader();

        float [] verts = {
             0.5f, 0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            -0.5f,-0.5f, 0.0f,
             0.5f,-0.5f, 0.0f,
        };
        RawModel m1 = loader.loadToVAO(verts);

        int [] indxs = {
            0,1,2,
            0,3,2
        };
        RawModel m2 = loader.loadToVAO(verts, indxs);

        float [] texuv = {
            1.0f,1.0f,
            0.0f,1.0f,
            0.0f,0.0f,
            1.0f,0.0f,
        };
        TextureWrapper tw = new TextureWrapper(TextureLoader.loadTexture("src/main/res/imgs/Earth_Day_Light.jpg"));
        RawModel m3 = loader.loadToVAO(verts, texuv, indxs);

        TexturedModel m3t = new TexturedModel(m3, tw);

        while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {            
            renderer.prepare();
            
            // fshader.start();
            // renderer.render(m2);
            // fshader.stop();

            tshader.start();
            renderer.render(m3t);
            tshader.stop();

            DisplayManager.updateDisplay();
        }

        fshader.cleanup();
        tshader.cleanup();
        loader.cleanup();

        DisplayManager.destroyDisplay();
    }

}



