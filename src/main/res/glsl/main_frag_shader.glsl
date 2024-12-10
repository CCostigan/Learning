#version 400 core

in vec2 textureUV;

out vec4 out_color;

uniform sampler2D textureSampler;

void main(void) {

    //out_color = vec4(color, 1.0);
    out_color = texture(textureSampler, textureUV);

}