package thin;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import thin.resources.model.RawModel;
import thin.resources.model.TexturedModel;

public class Renderer {
    

    void prepare() {
        glClearColor(1, 0, 0, 1);
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
    }

    // void render(RawModel m) {
    //     glBindVertexArray(m.VAO);
    //     glEnableVertexAttribArray(0);
    //     glDrawArrays(GL_TRIANGLES, 0, m.vertexCount);
    //     glDisableVertexAttribArray(0);
    //     glBindVertexArray(0);
    // }
    void render(RawModel m) {
        glBindVertexArray(m.VAO);
        glEnableVertexAttribArray(0);
        // glDrawArrays(GL_TRIANGLES, 0, m.vertexCount);
        glDrawElements(GL_TRIANGLES, m.vertexCount, GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
    void render(TexturedModel m) {
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
