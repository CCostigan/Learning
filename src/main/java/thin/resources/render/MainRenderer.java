package thin.resources.render;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import thin.resources.items.Camera;
import thin.resources.items.Light;
import thin.resources.items.ModelItem;
import thin.resources.model.TexturedModel;
import thin.resources.shader.ConcreteShader;

public class MainRenderer {
    

    ConcreteShader shader = new ConcreteShader();
    Renderer renderer = new Renderer(shader);

    Map<TexturedModel, List<ModelItem>> entities = new HashMap<TexturedModel, List<ModelItem>>();

    void render(Light sun, Camera camera) {
        renderer.prepare();
        shader.start();
        shader.loadLight(sun, 0.2f);
        shader.loadViewMatrix(camera);

        shader.stop();
        entities.clear();
    }

    void cleanup() {
        shader.cleanup();
    }
}
