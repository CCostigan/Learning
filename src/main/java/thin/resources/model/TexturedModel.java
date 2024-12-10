package thin.resources.model;

import thin.resources.texture.TextureWrapper;

public class TexturedModel {
    
    public RawModel model;
    public TextureWrapper texture;

    public TexturedModel(RawModel m, TextureWrapper tex) {
        model = m;
        texture = tex;
    }
}
