#version 400 core

in vec2 textureUV;
in vec3 normalXYZ;
in vec3 tolightXYZ;
in vec3 toCameraXYZ;

uniform sampler2D textureSampler;
uniform vec3 lightRGB;

uniform float reflectivity;
uniform float shinedamping;

out vec4 out_color;

void main(void) {
    // Why not normalize these in the vertex shader?  This is a lot of extra work for the poor little fragment shader.  
    vec3 unitNormal = normalize(normalXYZ);
    vec3 unitLightV = normalize(tolightXYZ);   
    vec3 unitCameraV = normalize(toCameraXYZ);   
    
    float nDot = dot(unitNormal, unitLightV);
    float bright = max(nDot, 0.0);
    vec4 diffuse = vec4((bright * lightRGB), 1.0);

    vec3 reflected = reflect(unitCameraV, unitNormal);
    float specpower = dot(reflected, unitCameraV);
    specpower = max(specpower, 0.0);
    float damped = pow(specpower, shinedamping);
    vec3 finalspec = damped * lightRGB * reflectivity;
    
    //out_color = vec4(color, 1.0);
    out_color = diffuse * texture(textureSampler, textureUV) * vec4(finalspec, 1.0);

}
