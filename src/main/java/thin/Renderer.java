package thin;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.joml.Matrix4f;

import thin.resources.items.ModelItem;
import thin.resources.model.TexturedModel;
import thin.resources.shader.ConcreteShader;
import thin.resources.util.*;
import static thin.resources.util.MathHelper.*;

public class Renderer {
    

    void prepare() {
        glClearColor(1, 0, 0, 1);
        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
    }



    static final float fov = 45.0f * d2r;


    Matrix4f projection = new Matrix4f().perspective(fov, 1600.0f/900.0f, 0.01f, 10000.0f);

    void render(ModelItem e, ConcreteShader sp) {
        Matrix4f transform = MathHelper.createTransformationMatrix(e.position, e.orientation, e.scale);
        sp.loadProjectionMatrix(projection);
        sp.loadTransformationMatrix(transform);

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
