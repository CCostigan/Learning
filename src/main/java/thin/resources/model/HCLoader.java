package thin.resources.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import thin.resources.Loader;



class vert {
    Vector3f p;
    Vector2f t;
    Vector3f n;
    public vert(Vector3f vp, Vector2f vt, Vector3f vn) {
        p=vp; t=vt; n=vn;
    }
}

public class HCLoader {
    

    public static RawModel loadHCModel(Loader loader) {
        float [] verts = {
            -0.5f, 0.5f,-0.5f,
            -0.5f,-0.5f,-0.5f,
             0.5f,-0.5f,-0.5f,
             0.5f, 0.5f,-0.5f,
            
            -0.5f, 0.5f, 0.5f,
            -0.5f,-0.5f, 0.5f,
             0.5f,-0.5f, 0.5f,
             0.5f, 0.5f, 0.5f,
            
             0.5f, 0.5f,-0.5f,
             0.5f,-0.5f,-0.5f,
             0.5f,-0.5f, 0.5f,
             0.5f, 0.5f, 0.5f,
            
            -0.5f, 0.5f,-0.5f,
            -0.5f,-0.5f,-0.5f,
            -0.5f,-0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            
            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f,-0.5f,
             0.5f, 0.5f,-0.5f,
             0.5f, 0.5f, 0.5f,
            
            -0.5f,-0.5f, 0.5f,
            -0.5f,-0.5f,-0.5f,
             0.5f,-0.5f,-0.5f,
             0.5f,-0.5f, 0.5f
        };
        float [] texuv = {
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0
        };
        float [] norms = {
             0.0f, 0.0f,-1.0f,
             0.0f, 0.0f,-1.0f,
             0.0f, 0.0f,-1.0f,
             0.0f, 0.0f,-1.0f,

             0.0f, 0.0f, 1.0f,
             0.0f, 0.0f, 1.0f,
             0.0f, 0.0f, 1.0f,
             0.0f, 0.0f, 1.0f,

             1.0f, 0.0f, 1.0f,
             1.0f, 0.0f, 1.0f,
             1.0f, 0.0f, 1.0f,
             1.0f, 0.0f, 1.0f,

            -1.0f, 0.0f, 1.0f,
            -1.0f, 0.0f, 1.0f,
            -1.0f, 0.0f, 1.0f,
            -1.0f, 0.0f, 1.0f,

             0.0f, 1.0f, 1.0f,
             0.0f, 1.0f, 1.0f,
             0.0f, 1.0f, 1.0f,
             0.0f, 1.0f, 1.0f,

             0.0f,-1.0f, 1.0f,
             0.0f,-1.0f, 1.0f,
             0.0f,-1.0f, 1.0f,
             0.0f,-1.0f, 1.0f,
        };
        int [] indxs = {
            0,1,3,	
            3,1,2,	

            4,5,7,
            7,5,6,

            8,9,11,
            11,9,10,

            12,13,15,
            15,13,14,	

            16,17,19,
            19,17,18,

            20,21,23,
            23,21,22
        };        
        return loader.loadToVAO(verts, texuv, norms, indxs);        
    }
}
