package thin.resources.shader;

public class TexturedShader 
extends AbstractShaderProg {

    private static final String vertfilename = "src/main/res/glsl/textured_vert_shader.glsl";
    private static final String fragfilename = "src/main/res/glsl/textured_frag_shader.glsl";

    public TexturedShader() {
        super(vertfilename, fragfilename);
    }

    @Override
    protected void bindAttributes() {//        throw new UnsupportedOperationException("Unimplemented method 'bindAttributes'");
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "texcoords");
    }
    
}
