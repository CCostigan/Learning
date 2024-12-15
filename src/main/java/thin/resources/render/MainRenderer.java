package thin.resources.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import thin.resources.items.Camera;
import thin.resources.items.Light;
import thin.resources.items.Entity;
import thin.resources.model.TexturedModel;
import thin.resources.shader.ConcreteShader;

public class MainRenderer {
    

    ConcreteShader shader = new ConcreteShader();
    EntityRenderer renderer = new EntityRenderer(shader);

    Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();

    public void render(Camera camera, Light sun, float ambient) {
        renderer.prepare();
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

    public void cleanup() {
        shader.cleanup();
    }
}
