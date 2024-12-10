package thin.resources.texture;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.nio.IntBuffer;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class TextureLoader {



    public static int loadTexture(String txname) {
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
        // textures.add(textureid);
        return textureid;
    }    
}
