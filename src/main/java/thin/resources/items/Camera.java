package thin.resources.items;

import org.joml.Vector3f;
import org.lwjgl.input.Keyboard;

public class Camera {
    public Vector3f translate = new Vector3f();
    public Vector3f rotate = new Vector3f();

    public Camera() {

    }

    public void move() {
        if(Keyboard.isKeyDown(Keyboard.KEY_W)) translate.z -= 0.01f;
        if(Keyboard.isKeyDown(Keyboard.KEY_S)) translate.z += 0.01f;
        if(Keyboard.isKeyDown(Keyboard.KEY_A)) translate.x -= 0.01f;
        if(Keyboard.isKeyDown(Keyboard.KEY_D)) translate.x += 0.01f;
    }
}
