package thin.resources.render;

import static org.lwjgl.opengl.GL11.GL_COLOR_ARRAY;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_NORMAL_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glNormalPointer;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.nio.FloatBuffer;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import thin.resources.items.Entity;
import thin.resources.model.RawModel;
import thin.resources.model.TexturedModel;
import thin.resources.shader.ConcreteShader;
import thin.resources.texture.TextureWrapper;
import thin.resources.util.MathHelper;

public class EntityRenderer {
    

    ConcreteShader shader;
    Matrix4f projection;

    public EntityRenderer(ConcreteShader cshader, Matrix4f project) {
        shader = cshader;
        projection = project;

        // glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
        // glEnable(GL_DEPTH_TEST);
        shader.start();
        shader.loadProjectionMatrix(projection);
        shader.stop();
    }


    public void render(Map<TexturedModel, List<Entity>> m) {
        for(TexturedModel tm: m.keySet()) {
            prepareModel(tm);
            List<Entity> batch = m.get(tm);
            for(Entity e:batch) {
                loadModelMatrix(e);
                glDrawElements(GL_TRIANGLES, tm.model.vertexCount, GL_UNSIGNED_INT, 0);
            }
            unbindModel();
        }
    }

    public void prepareModel(TexturedModel m) {
        //TexturedModel m = e.getModel();
        TextureWrapper mtx = m.texture;
        RawModel rm = m.model;
        glBindVertexArray(m.model.VAO);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        
        shader.loadShininess(m.texture.reflectivity, m.texture.shinedamping);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, m.texture.textureID);
        // glDrawElements(GL_TRIANGLES, m.model.vertexCount, GL_UNSIGNED_INT, 0);
        shader.loadProjectionMatrix(projection);

    }

    public void unbindModel() {
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
    }
    
    public void loadModelMatrix(Entity e) {
        Matrix4f transform = MathHelper.createTransformationMatrix(e.position, e.orientation, e.scale);
        // shader.loadProjectionMatrix(projection);
        shader.loadTransformationMatrix(transform);
    }



    // public void render(ModelItem e, ConcreteShader sp) {
    //     Matrix4f transform = MathHelper.createTransformationMatrix(e.position, e.orientation, e.scale);
    //     sp.loadProjectionMatrix(projection);
    //     sp.loadTransformationMatrix(transform);

    //     TexturedModel m = e.getModel();
    //     glBindVertexArray(m.model.VAO);
    //     glEnableVertexAttribArray(0);
    //     glEnableVertexAttribArray(1);
    //     glEnableVertexAttribArray(2);

    //     sp.loadShininess(m.texture.reflectivity, m.texture.shinedamping);

    //     glActiveTexture(GL_TEXTURE0);
    //     glBindTexture(GL_TEXTURE_2D, m.texture.textureID);
    //     glDrawElements(GL_TRIANGLES, m.model.vertexCount, GL_UNSIGNED_INT, 0);

    //     glDisableVertexAttribArray(0);
    //     glDisableVertexAttribArray(1);
    //     glDisableVertexAttribArray(2);
    //     glBindVertexArray(0);
    // }


    /**
     * WIP
     */
    // http://www.opengl-tutorial.org/beginners-tutorials/tutorial-7-model-loading/
    void rendercs() {

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnableClientState(GL_NORMAL_ARRAY);

        // glBindBuffer();
        // glVertexPointer();
        // glNormalPointer();
        // glTexCoordPointer();
        //glColorPointer();

        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_COLOR_ARRAY);
        glDisableClientState(GL_NORMAL_ARRAY);

    }

    void scratchpad1() {
        FloatBuffer verts = BufferUtils.createFloatBuffer(100);
        FloatBuffer norms = BufferUtils.createFloatBuffer(100);
        FloatBuffer texts = BufferUtils.createFloatBuffer(100);

        glBufferSubData(GL_ARRAY_BUFFER, 0, verts);
        glVertexPointer(3, GL_FLOAT, 0, null);
    
        glBufferSubData(GL_ARRAY_BUFFER, 0, norms);
        glNormalPointer(GL_FLOAT, 0, null);
    
        glBufferSubData(GL_ARRAY_BUFFER, 0, texts);
        glTexCoordPointer(2, GL_FLOAT, 0, null);
    }

    void scratchpad2() {

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_NORMAL_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
    
        glDrawElements(GL_TRIANGLES, 4, GL_UNSIGNED_INT, 0);
    
        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_NORMAL_ARRAY);
        glDisableClientState(GL_TEXTURE_COORD_ARRAY);        
    }
}
