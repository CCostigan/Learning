package thin.resources.render;

import static org.lwjgl.opengl.GL11.*;
import static thin.resources.util.MathHelper.d2r;
import static thin.resources.util.MathHelper.xAxis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.opengl.Display;

import thin.resources.items.Camera;
import thin.resources.items.Entity;
import thin.resources.items.Light;
import thin.resources.model.TexturedModel;
import thin.resources.shader.ConcreteShader;

public class MainRenderer {
    
    float FOV = 60.0f;
    float NEAR_PLANE = 0.01f;
    float FAR_PLANE = 100000.0f;
    static final float fov = 45.0f * d2r;
    
    Matrix4f projection = new Matrix4f().perspective(fov, 1600.0f/900.0f, 0.01f, 10000.0f);

    Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();

    ConcreteShader shader = new ConcreteShader();
    EntityRenderer renderer = new EntityRenderer(shader, projection);

    public MainRenderer() {

    }

    private void createProjectionMatrix() {
        float aspect = (float)Display.getWidth()/(float)Display.getHeight();
        float yscale = (float) ((1f/Math.tan(Math.toRadians(FOV/2f)))*aspect);
        float xscale = yscale / aspect;
        float frustumlen = FAR_PLANE - NEAR_PLANE;
        projection = new Matrix4f();
        // projection.set = xscale;
        // projection.m11 = yscale;
    }

    public void render(Camera camera, Light sun, float ambient) {
        prepare();
        shader.start();
        shader.loadLight(sun, ambient);
        shader.loadViewMatrix(camera);
        renderer.render(entities);
        shader.stop();
        entities.clear();
    }

    public void peocessEntity(Entity entity) {
        TexturedModel m = entity.getModel();
        List<Entity> batch = entities.get(m);
        if(batch != null) {
            batch.add(entity);
        } else {
            List<Entity> newbatch = new ArrayList<Entity>();
            newbatch.add(entity);
            entities.put(m, newbatch);
        }
    }
    
    public void prepare() {
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void cleanup() {
        shader.cleanup();
    }
}
