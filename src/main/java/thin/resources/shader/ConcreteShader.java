package thin.resources.shader;

import org.joml.Matrix4f;

public class ConcreteShader 
extends AbstractShaderProg {

    private static final String vertfilename = "src/main/res/glsl/main_vert_shader.glsl";
    private static final String fragfilename = "src/main/res/glsl/main_frag_shader.glsl";

    int projectionid;
    int transformid;

    public ConcreteShader() {
        super(vertfilename, fragfilename);
    }

    @Override
    protected void bindAttributes() {//        throw new UnsupportedOperationException("Unimplemented method 'bindAttributes'");
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "texcoords");
    }

    @Override
    protected void getAllUniformLocations() {
        projectionid = super.getUniformLocation("projection");
        transformid = super.getUniformLocation("transform");
    }
    
    public void loadProjectionMatrix(Matrix4f projection) {
        loadMatrix(projectionid, projection);
    }

    public void loadTransformationMatrix(Matrix4f transform) {
        loadMatrix(transformid, transform);
        //System.out.println(transform);
    }

}
