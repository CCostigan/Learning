#version 400 core

in vec3 position;
in vec2 texcoords;

out vec2 textureUV;

uniform mat4 transform;
uniform mat4 projection;

void main(void) {
    textureUV = texcoords;
    // color = vec3(position.x+0.5, 1.0, position.y+0.5);
    // gl_Position = vec4(position, 1.0);
    // gl_Position = transform * vec4(position, 1.0);
    gl_Position = projection * transform * vec4(position, 1.0);
}
