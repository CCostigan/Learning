package examples.thin;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

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

        float [] verts = {
            -0.5f, 0.5f, 0.0f,
             0.5f,-0.5f, 0.0f,
             -0.5f,-0.5f, 0.0f,

            //  -0.5f, 0.5f, 0.0f,
              0.5f, 0.5f, 0.0f,
            //   0.5f,-0.5f, 0.0f,
        };
        int [] indxs = {
            0,1,2,
            0,1,3
        };


        RawModel m = loader.loadToVAO(verts, indxs);

        while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {            
            renderer.prepare();
            renderer.render(m);
            DisplayManager.updateDisplay();
        }

        
        loader.cleanup();

        DisplayManager.destroyDisplay();
    }

}



