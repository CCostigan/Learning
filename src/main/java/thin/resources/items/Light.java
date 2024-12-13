package thin.resources.items;

import org.joml.Vector3f;

public class Light {
    public Vector3f location;
    public Vector3f color;
    public Light(Vector3f xyz, Vector3f rgb) {
        location = new Vector3f(xyz);
        color = new Vector3f(rgb);
    }
}
