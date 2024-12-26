package thin.resources.terrain;

import org.joml.Vector3f;

import thin.resources.Loader;
import thin.resources.model.RawModel;
import thin.resources.texture.TextureWrapper;



public class Terrain {

    static final float SIZE = 800;
    static final int VTXCOUNT = 128;
    float x;
    float y;

    public Vector3f position = new Vector3f(0.0f, 0.0f, 0.0f);
    public Vector3f orientation = new Vector3f(0.0f, 0.0f, 0.0f);;
    public Vector3f scale = new Vector3f(1.0f, 1.0f, 1.0f);;

    public RawModel model;
    public TextureWrapper texture;

    public Terrain(int gridX, int gridY, Loader loader, TextureWrapper tex) {
        texture = tex;
        x = gridX;
        y = gridY;
        model = generateTerrain(loader);
    }

    int [] indices = new int[256];

    public RawModel generateTerrain(Loader loader) {
        int count = VTXCOUNT * VTXCOUNT;
        float [] vertices = new float[count*3];
        float [] normals = new float[count*3];
        float [] texuvs = new float[count*2];
        int [] indices = new int[6*(VTXCOUNT)*(VTXCOUNT)];
        int vertexPointer = 0;
        for (int i=0; i<VTXCOUNT; i++) {
            for (int j=0; j<VTXCOUNT; j++) {
                vertices[vertexPointer*3] = -(float)j/((float)VTXCOUNT-1) * SIZE;
                vertices[vertexPointer*3+1] = 0;
                vertices[vertexPointer*3+2] = -(float)i/((float)VTXCOUNT-1) * SIZE;
                normals[vertexPointer*3] = 0;
                normals[vertexPointer*3+1] = 1;
                normals[vertexPointer*3+2] = 0;
                texuvs[vertexPointer*2] = -(float)i/((float)VTXCOUNT-1);
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
        
        return loader.loadToVAO(vertices, texuvs, normals, indices);
    }
/*


	private RawModel generateTerrain(Loader loader){
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer*3+1] = 0;
				vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
				normals[vertexPointer*3] = 0;
				normals[vertexPointer*3+1] = 1;
				normals[vertexPointer*3+2] = 0;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}
 
*/
}
