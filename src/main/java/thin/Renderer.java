package thin;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import thin.resources.model.ModelEntity;
import thin.resources.model.TexturedModel;
import thin.resources.shader.ConcreteShader;
import thin.resources.util.*;;

public class Renderer {
    

    void prepare() {
        glClearColor(1, 0, 0, 1);
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
    }



    void render(ModelEntity e, ConcreteShader sp) {
        Matrix4f transform = MathHelper.createTransformationMatrix(e.position, e.orientation, e.scale);
        int transformid = sp.getUniformLocation("transform");
        System.out.println(transform);
        sp.loadMatrix(transformid, transform);

        TexturedModel m = e.getModel();
        glBindVertexArray(m.model.VAO);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, m.texture.textureID);
        glDrawElements(GL_TRIANGLES, m.model.vertexCount, GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }
}
