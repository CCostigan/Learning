#version 400 core

in vec3 position;
in vec2 texcoords;

out vec2 textureUV;

void main(void) {

    gl_Position = vec4(position, 1.0);
    //color = vec3(position.x+0.5, 1.0, position.y+0.5);
    textureUV = texcoords;
}