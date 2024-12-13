package thin.resources.util;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;




public class KeyMouse {

    boolean [] showlist = {true, true, true, true, true, true, true, true, true, true, true, true};

    public KeyMouse(boolean fullscreen, int screenwidth, int screenheight) {
        try {
            Keyboard.create();
            Keyboard.enableRepeatEvents(true);
            Mouse.setGrabbed(fullscreen);
            Mouse.create(); 
        } catch (LWJGLException e) { e.printStackTrace(); }
    }


    public void show(int item) {
        showlist[item] = true;
    }
    public void hide(int item) {
        showlist[item] = false;
    }
    public boolean canshow(int item) {
        return showlist[item];
    }
    
    public void update() {

        // These are not events, they are states, as a result many can be held down at the same time
        if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0)) { }
        if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1)) { }
        if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)) { }
        if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3)) { }
        if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4)) { }
        if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5)) { }
        if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6)) { }
        if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD7)) { }
        if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8)) { }
        if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD9)) { }
        
        // These are actual events, we don't want to fire these a zillion times while you are lifting your finger off the key
        while (Keyboard.getNumKeyboardEvents() > 0) {
            Keyboard.next();             
            if(Keyboard.getEventKeyState()) {
                int k = Keyboard.getEventKey();
                char c = Keyboard.getEventCharacter();
                boolean s = Keyboard.getEventKeyState();
                String n = Keyboard.getKeyName(k);
                switch (k) {
                    case Keyboard.KEY_POWER:  ; break;

                    case Keyboard.KEY_0:  showlist[0]=!showlist[0]; break;
                    case Keyboard.KEY_1:  showlist[1]=!showlist[1]; break;
                    case Keyboard.KEY_2:  showlist[2]=!showlist[2]; break;
                    case Keyboard.KEY_3:  showlist[3]=!showlist[3]; break;
                    case Keyboard.KEY_4:  showlist[4]=!showlist[4]; break;
                    case Keyboard.KEY_5:  showlist[5]=!showlist[5]; break;
                    case Keyboard.KEY_6:  showlist[6]=!showlist[6]; break;
                    case Keyboard.KEY_7:  showlist[7]=!showlist[7]; break;
                    case Keyboard.KEY_8:  showlist[8]=!showlist[8]; break;
                    case Keyboard.KEY_9:  showlist[9]=!showlist[9]; break;
                }
            }
        }        
    }



}
