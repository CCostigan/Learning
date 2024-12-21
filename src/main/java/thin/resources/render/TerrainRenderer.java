package thin.resources.render;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.List;

import org.joml.Matrix4f;

import thin.resources.model.RawModel;
import thin.resources.shader.TerrainShader;
import thin.resources.terrain.Terrain;
import thin.resources.texture.TextureWrapper;
import thin.resources.util.MathHelper;

public class TerrainRenderer {
    
    TerrainShader shader;
    Matrix4f projection;

    public TerrainRenderer(TerrainShader tshader, Matrix4f project) {
        shader = tshader; 
        projection = project;
       
        shader.start();
        shader.loadProjectionMatrix(projection);
        shader.stop();
    }

    public void render(List<Terrain> terrains) {
        for(Terrain tm: terrains) {
            prepareTerrain(tm);
            loadModelMatrix(tm);
            glDrawElements(GL_TRIANGLES, tm.model.vertexCount, GL_UNSIGNED_INT, 0);
            unbindModel();
        }
    }

    public void prepareTerrain(Terrain tm) {
            TextureWrapper mtx = tm.texture;
            RawModel rm = tm.model;
            glBindVertexArray(tm.model.VAO);
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
            glEnableVertexAttribArray(2);
            
            shader.loadShininess(tm.texture.reflectivity, tm.texture.shinedamping);
    
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, tm.texture.textureID);
        // glDrawElements(GL_TRIANGLES, m.model.vertexCount, GL_UNSIGNED_INT, 0);
        shader.loadProjectionMatrix(projection);

    }

    public void unbindModel() {
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
    }
    
    public void loadModelMatrix(Terrain t) {
        Matrix4f transform = MathHelper.createTransformationMatrix(
            t.position, t.orientation, t.scale);
        // shader.loadProjectionMatrix(projection);
        shader.loadTransformationMatrix(transform);
    }

}






