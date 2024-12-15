package thin.resources.shader;

import org.joml.Matrix4f;

import thin.resources.items.Camera;
import thin.resources.items.Light;
import thin.resources.util.MathHelper;

public class ConcreteShader 
extends AbstractShaderProg {

    private static final String vertfilename = "src/main/res/glsl/main_vert_shader.glsl";
    private static final String fragfilename = "src/main/res/glsl/main_frag_shader.glsl";

    int projectionid;
    int transformid;
    int viewid;
    int lightXYZ;
    int lightRGB;
    int ambient;
    int reflectivity;
    int shinedamping;
    
    public ConcreteShader() {
        // super(vertfilename, fragfilename);
        super(new String [] {vertfilename, fragfilename});
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
        viewid = super.getUniformLocation("viewmatrix");
        lightXYZ = super.getUniformLocation("lightXYZ");
        lightRGB = super.getUniformLocation("lightRGB");
        ambient = super.getUniformLocation("ambient");
        reflectivity = super.getUniformLocation("reflectivity");
        shinedamping = super.getUniformLocation("shinedamping");
    }
    
    public void loadProjectionMatrix(Matrix4f projection) {
        loadMatrix(projectionid, projection);
    }

    public void loadTransformationMatrix(Matrix4f transform) {
        loadMatrix(transformid, transform);
    }

    public void loadViewMatrix(Matrix4f viewmatrix) {
        loadMatrix(viewid, viewmatrix);
    }

    public void loadViewMatrix(Camera camera) {
        loadMatrix(viewid, MathHelper.createCameraMatrix(camera));
    }

    public void loadLight(Light light, float ambientval) {
        super.loadVector(lightXYZ, light.location);
        super.loadVector(lightRGB, light.color);
        super.loadFloat(ambient, ambientval);
    }
    
    public void loadShininess(float reflect, float damper) {
        super.loadFloat(reflectivity, reflect);
        super.loadFloat(shinedamping, damper);
    }
    

}
