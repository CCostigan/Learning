package thin.resources.terrain;

import org.joml.Vector2f;
import org.joml.Vector3f;

import thin.resources.Loader;
import thin.resources.model.RawModel;
import thin.resources.model.TexturedModel;
import thin.resources.texture.TextureWrapper;



public class Terrain {

    static final float SIZE = 800;
    static final int VTXCOUNT = 128;
    float x;
    float y;

    RawModel model;
    TextureWrapper texture;

    public Terrain(int gridX, int gridY, Loader loader, TextureWrapper tex) {
        texture = tex;
        x = gridX;
        y = gridY;
        model = generateTerrain(loader);
    }

    int [] indices = new int[256];

    public RawModel generateTerrain(Loader l) {
        int count = VTXCOUNT * VTXCOUNT;
        float [] vertices = new float[count*3];
        float [] normals = new float[count*3];
        float [] texuvs = new float[count*2];
        int [] indices = new int[6*(VTXCOUNT-1)*(VTXCOUNT-1)];
        int vertexPointer = 0;
        for (int i=0; i<VTXCOUNT; i++) {
            for (int j=0; j<VTXCOUNT; j++) {
                vertices[vertexPointer*3] = -(float)j/((float)VTXCOUNT-1) * SIZE;
                vertices[vertexPointer*3+1] = -(float)i/((float)VTXCOUNT-1) * SIZE;
                vertices[vertexPointer*3+2] = 0;
                normals[vertexPointer*3] = 0;
                normals[vertexPointer*3+1] = 0;
                normals[vertexPointer*3+2] = 1;
                texuvs[vertexPointer*2] = -(float)j/((float)VTXCOUNT-1);
                texuvs[vertexPointer*2+1] = -(float)j/((float)VTXCOUNT-1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for (int gy=0; gy<VTXCOUNT; gy++) {
            for (int gx=0; gx<VTXCOUNT; gx++) {
                int topleft = gy*VTXCOUNT+gx;
                int topright = topleft + 1;
                int btmleft = (gy+1)*VTXCOUNT+gx;
                int btmright = btmleft + 1;
                indices[pointer++] = topleft;
                indices[pointer++] = btmleft;
                indices[pointer++] = topright;
                indices[pointer++] = topright;
                indices[pointer++] = btmleft;
                indices[pointer++] = btmright;
            }
        }
        return null;
    }

}
