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


vec4 getdiffuse() {
    float nDotLight = dot(normalXYZ, tolightXYZ);
    float bright = max(nDotLight, 0.0);
    return vec4((bright * lightRGB), 1.0);
}

vec4 getspecular() {
    vec3 reflected = reflect(toCameraXYZ, normalXYZ);
    float specpower = max(dot(reflected, toCameraXYZ), 0.0);
    float damped = pow(specpower, shinedamping);
    return vec4((damped * reflectivity * lightRGB), 1.0);
}

void main(void) {
    //out_color = vec4(color, 1.0);
    out_color = texture(textureSampler, textureUV);
    out_color *= getdiffuse();
    out_color += getspecular();
}



