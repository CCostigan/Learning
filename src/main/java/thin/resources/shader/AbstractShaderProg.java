package thin.resources.shader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public abstract class AbstractShaderProg {

    int progID;
    int vertID;
    int fragID;

    protected abstract void bindAttributes();

    public void start() {
        glUseProgram(progID);
    }
    public void stop() {
        glUseProgram(0);
    }
    public void cleanup() {
        stop();
        glDetachShader(progID, vertID);
        glDetachShader(progID, fragID);        
        glDeleteShader(vertID);
        glDeleteShader(fragID);
        glDeleteProgram(progID);
    }

    protected void bindAttribute(int attribute, String variableName) {        
        glBindAttribLocation(progID, attribute, variableName);
    }

    public AbstractShaderProg(String vertf, String fragf) {        
        vertID = loadShader(vertf, GL_VERTEX_SHADER);
        fragID = loadShader(fragf, GL_FRAGMENT_SHADER);
        progID = glCreateProgram();
        glAttachShader(progID, vertID);
        glAttachShader(progID, fragID);
        bindAttributes();//??
        glLinkProgram(progID);
        glValidateProgram(progID);        
        if(glGetProgrami(progID, GL_VALIDATE_STATUS)==GL_FALSE) {
            System.out.println(glGetProgramInfoLog(progID, 512));
            System.exit(-1);
        }
    }

    private static int loadShader(String filename, int type) {
        StringBuilder shaderSource = new StringBuilder();        
        try {
            ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line=br.readLine()) != null) {
                shaderSource.append(line).append('\n');
            }
            br.close();            
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't read file "+filename);
            System.exit(-1);
        }
        int shader = glCreateShader(type);
        glShaderSource(shader, shaderSource);
        glCompileShader(shader);
        if(glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.out.println(glGetShaderInfoLog(shader, 512));
            System.out.println("Couldn't conpile shader"+filename);
            System.exit(-1);
        }
        return shader;
    }
    
}