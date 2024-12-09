package thin.model;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Loader {


    private List<Integer> vaolist = new ArrayList<Integer>();
    private List<Integer> vbolist = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();



    public RawModel loadToVAO(float [] positions) {
        int vao = createVAO();
        storeAttribList(0, positions);
        unbindVAO();
        return new RawModel(vao, positions.length);
    }    

    public RawModel loadToVAO(float [] positions, int[] indices) {
        int vao = createVAO();
        bindIndicesBuffer(indices);
        storeAttribList(0, positions);
        unbindVAO();
        return new RawModel(vao, positions.length);
    }

    public int loadTexture(String txname) {
        int textureid = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureid);
        // glPixelStorei(GL_UNPACK_ALIGNMENT, textureid);// Do we use this?
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);// GL_NEAREST GL_LINEAR you choose
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);// GL_NEAREST GL_LINEAR

        try {
            System.out.print("Reading image :"+textureid+" file "+txname );
            BufferedImage img = ImageIO.read(Paths.get(txname).toFile());
            System.out.println(" IMG Size:"+img.getWidth()+"x"+img.getHeight()+" t="+img.getType()+" sz="+(img.getWidth()*img.getHeight()*4));

            IntBuffer imgData = BufferUtils.createIntBuffer(img.getWidth()*img.getHeight());
            for(int y=img.getHeight()-1; y >= 0; y--) {//Flip while we read
                for(int x=0; x<img.getWidth(); x++) {
                    int p = img.getRGB(x, y);
                    // imgData.put(p);
                    //               A B G R
                    int r = ( p & 0x000000FF ) << 16 ;
                    int g = ( p & 0x0000FF00 )  ;
                    int b = ( p & 0x00FF0000 ) >> 16 ;
                    int a = ( p & 0xFF000000 )  ;
                    // System.out.println("RGBA = "+r+" "+g+" "+b+" "+a);                    
                    imgData.put( r | g | b | a );
                }
            }
            imgData.flip();//
            glTexImage2D(GL_TEXTURE_2D, 0, 
                GL_RGBA, img.getWidth(), img.getHeight(), 0, 
                GL_RGBA, GL_UNSIGNED_BYTE, imgData);
            // Util.checkGLError();
            glBindTexture(GL_TEXTURE_2D, 0);
        } catch (Exception e) { e.printStackTrace(); }
        // System.out.println("Texture loaded: "+txname);
        textures.add(textureid);
        return textureid;
    }

    int createVAO() {
        int vao = glGenVertexArrays();
        vaolist.add(vao);
        glBindVertexArray(vao);        
        return vao;
    }

    void storeAttribList(int attribnum, float [] attribs) {
        int vbo = glGenBuffers();
        vbolist.add(vbo);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        FloatBuffer fb = storeDataInFloatBuffer(attribs);

        glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
        glVertexAttribPointer(attribnum, 3, GL_FLOAT, false, 0, 0);
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