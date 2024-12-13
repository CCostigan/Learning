#version 400 core

in vec2 textureUV;
in vec3 normalXYZ;
in vec3 tolightXYZ;

out vec4 out_color;

uniform sampler2D textureSampler;
uniform vec3 lightRGB;

void main(void) {
    // Why not normalize these in the vertex shader?  This is a lot of extra work for the poor little fragment shader.  
    vec3 unitNormal = normalize(normalXYZ);
    vec3 unitLightV = normalize(tolightXYZ);   
    
    float nDot = dot(unitNormal, unitLightV);
    float bright = max(nDot, 0.0);
    vec4 diffuse = vec4((bright * lightRGB), 1.0);

    //out_color = vec4(color, 1.0);
    out_color = diffuse * texture(textureSampler, textureUV);

}
