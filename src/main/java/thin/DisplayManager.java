package thin;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;


import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {


    public static int width = 1600;
    public static int height = 900;
    public static int maxfps = 120;

    public static void createDisplay(boolean fullscreen) {
        try {
            Display.setFullscreen(fullscreen);
            if (!fullscreen) {
                DisplayMode dmode = new DisplayMode(width, height);
                Display.setDisplayMode(dmode);
                Display.setTitle("LWJGL Using 3.3");
            } else {
                DisplayMode [] modes = Display.getAvailableDisplayModes();
                for(DisplayMode mode : modes) {
                    System.out.println("Mode "+mode+" "+mode.hashCode());
                    // if(mode.toString().equals("1920 x 1080 x 24 @60Hz")) Display.setDisplayMode(mode);
                    // if(mode.toString().equals("3840 x 2160 x 24 @60Hz")) Display.setDisplayMode(mode);                    
                }
            }
            PixelFormat pfd = new PixelFormat();
            ContextAttribs ctx = new ContextAttribs(3,2)
            .withForwardCompatible(true)
            .withProfileCore(true)
            ;

            Display.create(pfd, ctx);
            Display.setTitle("TESTING");
            glViewport(0, 0, width, height);        

            Keyboard.create();
            Keyboard.enableRepeatEvents(true);

            Mouse.setGrabbed(fullscreen);//True seems to work better on old hw
            Mouse.create();             

        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        glClearColor(0.4f, 0.4f, 0.4f, 1.0f);
        
        //# I believe was told with shaders you don't use 'glEnable' any more            
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS); 
        // glEnable(GL_CULL_FACE);

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);         
    }

    public static void destroyDisplay() {
        // for(NewModel m: NewModels) m.destroy();
        Display.destroy();
    }


    public static void updateDisplay() {
        Display.sync(maxfps);
        Display.update();
    }

}
