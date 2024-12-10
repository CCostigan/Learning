package thin.resources.util;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MathHelper {
    
    static final Vector3f xAxis = new Vector3f(1.0f, 0.0f, 0.0f);
    static final Vector3f yAxis = new Vector3f(0.0f, 1.0f, 0.0f);
    static final Vector3f zAxis = new Vector3f(0.0f, 0.0f, 1.0f);

    public static Matrix4f createTransformationMatrix(Vector3f translate, Vector3f rotate, Vector3f scale) {
        return new Matrix4f().identity()
        .scale(scale)
        .translate(translate)
        .rotate(rotate.x, xAxis)
        .rotate(rotate.y, yAxis)
        .rotate(rotate.z, zAxis)
        ;
    }
}
