package thin.resources.items;

import org.joml.Vector3f;

import thin.resources.model.TexturedModel;

public class Entity {
    

    TexturedModel model;
    public Vector3f position;
    Vector3f velocity;
    Vector3f acceleration;
    public Vector3f orientation;
    public Vector3f scale;
    
    public Entity(TexturedModel model, Vector3f position, Vector3f orientation, Vector3f scale) {
        this.model       = model;
        this.position    = new Vector3f(position);
        this.orientation = new Vector3f(orientation);
        this.scale       = new Vector3f(scale);
    }
    
    public void move(Vector3f delta) {
        position = position.add(delta);
    }
    public void turn(Vector3f delta) {
        orientation = orientation.add(delta);
    }

    public TexturedModel getModel() {
        return model;
    }
    public void setModel(TexturedModel model) {
        this.model = model;
    }
    public Vector3f getPosition() {
        return position;
    }
    public void setPosition(Vector3f position) {
        this.position = position;
    }
    public Vector3f getOrientation() {
        return orientation;
    }
    public void setOrientation(Vector3f orientation) {
        this.orientation = orientation;
    }
    public Vector3f getScale() {
        return scale;
    }
    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
    
}
