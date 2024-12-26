package thin.resources.texture;

public class TextureWrapper {
    
    public int textureID;

    public float shinedamping = 1.0f;
    public float reflectivity = 0.0f;
    public boolean hastransparency = false;

    public TextureWrapper(int id) {
        textureID = id;
    }

}
