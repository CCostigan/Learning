#version 400 core

in vec3 position;
in vec2 texcoords;
in vec3 vtxnormal;

uniform mat4 transform;
uniform mat4 projection;
uniform mat4 viewmatrix;

uniform vec3 lightXYZ;

out vec2 textureUV;
out vec3 normalXYZ;
out vec3 tolightXYZ;
out vec3 toCameraXYZ;

void main(void) {
    textureUV = texcoords;

    //Multiply our position by txfm mtx... Because models can rotate
    vec4 worldVtxXYZ = transform * vec4(position, 1.0);
    tolightXYZ = normalize(vec4(lightXYZ, 0.0) - worldVtxXYZ).xyz;

    //Multiply our normal by txfm mtx... Because normals rotate with the model
    vec4 srfNorm = transform * vec4(vtxnormal, 1.0);
    normalXYZ = normalize(srfNorm).xyz; 
    
    toCameraXYZ = normalize(worldVtxXYZ - (inverse(viewmatrix) * vec4(0.0, 0.0, 0.0, 1.0))).xyz;
    
    // color = vec3(position.x+0.5, 1.0, position.y+0.5);
    // gl_Position = vec4(position, 1.0);
    // gl_Position = transform * vec4(position, 1.0);
    // gl_Position = projection * transform * vec4(position, 1.0);
    gl_Position = projection * viewmatrix * transform * vec4(position, 1.0);
    
    // Note, down here in gl_Position we could do this 
    // gl_Position = projection * viewmatrix * worldVtxXYZ;
    // We already calculated worldXYZ, why do it again?
    // Well the answer is 'readability'  This is consistent with other examples
}
