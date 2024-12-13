package thin.resources;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

import thin.resources.model.RawModel;
import thin.resources.texture.TextureLoader;
import thin.resources.texture.TextureWrapper;

public class Loader {


    private List<Integer> vaolist = new ArrayList<Integer>();
    private List<Integer> vbolist = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();



    public RawModel loadToVAO(float [] points) {
        int vao = createVAO();
        storeAttribList(0, 3, points);
        unbindVAO();
        return new RawModel(vao, points.length);
    }    

    public RawModel loadToVAO(float [] points, int[] indices) {
        int vao = createVAO();
        bindIndicesBuffer(indices);
        storeAttribList(0, 3, points);
        unbindVAO();
        return new RawModel(vao, points.length);
    }
    public RawModel loadToVAO(float [] points, float [] texuv, int[] indices) {
        int vao = createVAO();
        bindIndicesBuffer(indices);
        storeAttribList(0, 3, points);
        storeAttribList(1, 2, texuv);
        unbindVAO();
        return new RawModel(vao, points.length);
    }
    public RawModel loadToVAO(float [] points, float [] texuv,  float [] norms, int[] indices) {
        int vao = createVAO();
        bindIndicesBuffer(indices);
        storeAttribList(0, 3, points);
        storeAttribList(1, 2, texuv);
        storeAttribList(2, 3, norms);
        unbindVAO();
        return new RawModel(vao, points.length);
    }

    public TextureWrapper loadTexture(String texturefile) {
        int texture = TextureLoader.loadTexture(texturefile);
        textures.add(texture);
        return new TextureWrapper(texture);        
    }

    int createVAO() {
        int vao = glGenVertexArrays();
        vaolist.add(vao);
        glBindVertexArray(vao);        
        return vao;
    }

    void storeAttribList(int attribnum, int size, float [] attribs) {
        int vbo = glGenBuffers();
        vbolist.add(vbo);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        FloatBuffer fb = storeDataInFloatBuffer(attribs);

        glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
        glVertexAttribPointer(attribnum, size, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    void unbindVAO() {
        glBindVertexArray(0);        
    }

    void bindIndicesBuffer(int []indices) {
        int vbo = glGenBuffers();
        vbolist.add(vbo);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);
        IntBuffer b = storeDataInIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, b, GL_STATIC_DRAW);
    }


    IntBuffer storeDataInIntBuffer(int [] data) {
        IntBuffer ib = BufferUtils.createIntBuffer(data.length);
        ib.put(data);
        ib.flip();
        return ib;
    }

    FloatBuffer storeDataInFloatBuffer(float [] data) {
        FloatBuffer fb = BufferUtils.createFloatBuffer(data.length);
        fb.put(data);
        fb.flip();
        return fb;
    }

    public void cleanup() {
        for (int vao:vaolist) glDeleteVertexArrays(vao);
        for (int vbo:vbolist) glDeleteBuffers(vbo);
        for (int tex:textures) glDeleteTextures(tex);
    }


}