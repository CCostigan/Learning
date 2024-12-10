package thin.resources.model;

public class RawModel {
    
    public int VAO;
    public int VBO;
    public int vertexCount;

    public RawModel(int vao, int vtxs) {
        VAO = vao;
        vertexCount = vtxs; 
    }
}
