package examples.thin;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;


class NewModel {
    ArrayList<Integer>vaolist = new ArrayList<Integer>();
    ArrayList<Integer>vbolist = new ArrayList<Integer>();
    int vtxCount;
    int stride_bytes;// 4 * size of one vertex including 3XYZ, 2UV, 3Normal, 4Color, etc


    NewModel(int stride, float [] vertexdata, int [] indxs) {
        init(stride, vertexdata, indxs);// Call this once per material
    }

    void init(int stride, float [] vertexdata, int [] indxs) {

        vtxCount = vertexdata.length/stride;
        stride_bytes = 4 * stride;

        FloatBuffer vtxData = BufferUtils.createFloatBuffer(vertexdata.length);
        IntBuffer vtxIndxs = BufferUtils.createIntBuffer(indxs.length);
        
        vtxData.put(vertexdata);
        vtxData.flip();

        int VAO = glGenVertexArrays();        
        vaolist.add(VAO);
        glBindVertexArray(VAO);

        int VBO = glGenBuffers();
        vbolist.add(VBO);
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, vtxData, GL_STATIC_DRAW);           // Tell where the data is

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, stride_bytes, 0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, stride_bytes, 12);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, stride_bytes, 20);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        // if(stride >= 3) {
        //     glEnableVertexAttribArray(0);
        //     glVertexAttribPointer(0, 3, GL_FLOAT, false, stride_bytes, 0);                
        // }
        glBindVertexArray(0); //Done updating that VAO
    }

    void render() {
        glBindVertexArray(vaolist.get(0));
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
            glEnableVertexAttribArray(2);
            glDrawArrays(GL_TRIANGLES, 0, vtxCount);    
            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glDisableVertexAttribArray(2);
            glBindVertexArray(0);
    }

    static NewModel createHC() {
        float [] vertices = {
            -1.0f,  4.0f, -9.0f,     0.875f,  0.5f,      0.0f,  1.0f,  0.0f,   //   0.0f,  1.0f,  0.0f,  1.0f,
             1.0f,  4.0f,  9.0f,     0.625f,  0.75f,     0.0f,  1.0f,  0.0f,   //   0.0f,  1.0f,  0.0f,  1.0f,
             1.0f,  4.0f, -9.0f,     0.625f,  0.5f,      0.0f,  1.0f,  0.0f,   //   0.0f,  1.0f,  0.0f,  1.0f,

             1.0f,  4.0f,  9.0f,     0.625f,  0.75f,     0.0f,  0.0f,  1.0f,   //   0.0f,  0.0f,  1.0f,  1.0f,
            -1.0f, -4.0f,  9.0f,     0.375f,  1.0f,      0.0f,  0.0f,  1.0f,   //   0.0f,  0.0f,  1.0f,  1.0f,
             1.0f, -4.0f,  9.0f,     0.375f,  0.75f,     0.0f,  0.0f,  1.0f,   //   0.0f,  0.0f,  1.0f,  1.0f,

            -1.0f,  4.0f,  9.0f,     0.625f,  0.0f,     -1.0f,  0.0f,  0.0f,   //   1.0f,  0.0f,  0.0f,  1.0f,
            -1.0f, -4.0f, -9.0f,     0.375f,  0.25f,    -1.0f,  0.0f,  0.0f,   //   1.0f,  0.0f,  0.0f,  1.0f,
            -1.0f, -4.0f,  9.0f,     0.375f,  0.0f,     -1.0f,  0.0f,  0.0f,   //   1.0f,  0.0f,  0.0f,  1.0f,

             1.0f, -4.0f, -9.0f,     0.375f,  0.5f,      0.0f, -1.0f,  0.0f,   //   0.0f,  1.0f,  0.0f,  1.0f,
            -1.0f, -4.0f,  9.0f,     0.125f,  0.75f,     0.0f, -1.0f,  0.0f,   //   0.0f,  1.0f,  0.0f,  1.0f,
            -1.0f, -4.0f, -9.0f,     0.125f,  0.5f,      0.0f, -1.0f,  0.0f,   //   0.0f,  1.0f,  0.0f,  1.0f,

             1.0f,  4.0f, -9.0f,     0.625f,  0.5f,      1.0f,  0.0f,  0.0f,   //   1.0f,  0.0f,  0.0f,  1.0f,
             1.0f, -4.0f,  9.0f,     0.375f,  0.75f,     1.0f,  0.0f,  0.0f,   //   1.0f,  0.0f,  0.0f,  1.0f,
             1.0f, -4.0f, -9.0f,     0.375f,  0.5f,      1.0f,  0.0f,  0.0f,   //   1.0f,  0.0f,  0.0f,  1.0f,

            -1.0f,  4.0f, -9.0f,     0.625f,  0.25f,     0.0f,  0.0f, -1.0f,   //   0.0f,  0.0f,  1.0f,  1.0f,
             1.0f, -4.0f, -9.0f,     0.375f,  0.5f,      0.0f,  0.0f, -1.0f,   //   0.0f,  0.0f,  1.0f,  1.0f,
            -1.0f, -4.0f, -9.0f,     0.375f,  0.25f,     0.0f,  0.0f, -1.0f,   //   0.0f,  0.0f,  1.0f,  1.0f,

            -1.0f,  4.0f, -9.0f,     0.875f,  0.5f,      0.0f,  1.0f,  0.0f,   //   0.0f,  1.0f,  0.0f,  1.0f,
            -1.0f,  4.0f,  9.0f,     0.875f,  0.75f,     0.0f,  1.0f,  0.0f,   //   0.0f,  1.0f,  0.0f,  1.0f,
             1.0f,  4.0f,  9.0f,     0.625f,  0.75f,     0.0f,  1.0f,  0.0f,   //   0.0f,  1.0f,  0.0f,  1.0f,

             1.0f,  4.0f,  9.0f,     0.625f,  0.75f,     0.0f,  0.0f,  1.0f,   //   0.0f,  0.0f,  1.0f,  1.0f,
            -1.0f,  4.0f,  9.0f,     0.625f,  1.0f,      0.0f,  0.0f,  1.0f,   //   0.0f,  0.0f,  1.0f,  1.0f,
            -1.0f, -4.0f,  9.0f,     0.375f,  1.0f,      0.0f,  0.0f,  1.0f,   //   0.0f,  0.0f,  1.0f,  1.0f,

            -1.0f,  4.0f,  9.0f,     0.625f,  0.0f,     -1.0f,  0.0f,  0.0f,   //   1.0f,  0.0f,  0.0f,  1.0f,
            -1.0f,  4.0f, -9.0f,     0.625f,  0.25f,    -1.0f,  0.0f,  0.0f,   //   1.0f,  0.0f,  0.0f,  1.0f,
            -1.0f, -4.0f, -9.0f,     0.375f,  0.25f,    -1.0f,  0.0f,  0.0f,   //   1.0f,  0.0f,  0.0f,  1.0f,

             1.0f, -4.0f, -9.0f,     0.375f,  0.5f,      0.0f, -1.0f,  0.0f,   //   0.0f,  1.0f,  0.0f,  1.0f,
             1.0f, -4.0f,  9.0f,     0.375f,  0.75f,     0.0f, -1.0f,  0.0f,   //   0.0f,  1.0f,  0.0f,  1.0f,
            -1.0f, -4.0f,  9.0f,     0.125f,  0.75f,     0.0f, -1.0f,  0.0f,   //   0.0f,  1.0f,  0.0f,  1.0f,

             1.0f,  4.0f, -9.0f,     0.625f,  0.5f,      1.0f,  0.0f,  0.0f,   //   1.0f,  0.0f,  0.0f,  1.0f,
             1.0f,  4.0f,  9.0f,     0.625f,  0.75f,     1.0f,  0.0f,  0.0f,   //   1.0f,  0.0f,  0.0f,  1.0f,
             1.0f, -4.0f,  9.0f,     0.375f,  0.75f,     1.0f,  0.0f,  0.0f,   //   1.0f,  0.0f,  0.0f,  1.0f,

            -1.0f,  4.0f, -9.0f,     0.625f,  0.25f,     0.0f,  0.0f, -1.0f,   //   0.0f,  0.0f,  1.0f,  1.0f,
             1.0f,  4.0f, -9.0f,     0.625f,  0.5f,      0.0f,  0.0f, -1.0f,   //   0.0f,  0.0f,  1.0f,  1.0f,
             1.0f, -4.0f, -9.0f,     0.375f,  0.5f,      0.0f,  0.0f, -1.0f,   //   0.0f,  0.0f,  1.0f,  1.0f,
        };
        // just klidding
        float [] verts = {
             0.5f, 0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            -0.5f,-0.5f, 0.0f,
             0.5f,-0.5f, 0.0f,
        };
        int [] indxs = {
            0, 1, 3,
            3, 1, 2,
        };

        // return new Model(8, vertices);
        return new NewModel(3, verts, indxs);
    }
    
    void destroy() {
        for (int vao:vaolist) glDeleteVertexArrays(vao);
        for (int vbo:vbolist) glDeleteBuffers(vbo);
    }
}