package thin;

public class ShaderInst 
extends ShaderProg {

    private static final String vertfilename = "src/main/res/glsl/vert_shader.glsl";
    private static final String fragfilename = "src/main/res/glsl/frag_shader.glsl";

    ShaderInst() {
        super(vertfilename, fragfilename);
    }

    @Override
    protected void bindAttributes() {//        throw new UnsupportedOperationException("Unimplemented method 'bindAttributes'");
        super.bindAttribute(0, "position");
    }
    
}
