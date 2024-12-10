package thin.resources.shader;

public class FirstShader 
extends AbstractShaderProg {

    private static final String vertfilename = "src/main/res/glsl/first_vert_shader.glsl";
    private static final String fragfilename = "src/main/res/glsl/first_frag_shader.glsl";

    public FirstShader() {
        super(vertfilename, fragfilename);
    }

    @Override
    protected void bindAttributes() {//        throw new UnsupportedOperationException("Unimplemented method 'bindAttributes'");
        super.bindAttribute(0, "position");
    }
    
}
