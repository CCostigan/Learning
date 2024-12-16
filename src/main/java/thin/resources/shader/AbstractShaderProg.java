package thin.resources.shader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL44.*;
import static org.lwjgl.opengl.GL45.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;


public abstract class AbstractShaderProg {

    int progID;

    boolean done = false;
    boolean reload = false;

    String [] shadernames;
    List<Integer>shaderIDs = new ArrayList<Integer>();
    Map<String,Long>timemap = new HashMap<String,Long>();
    
    protected abstract void bindAttributes();

    public void start() {
        if(reload) loadShaders(shadernames);
        glUseProgram(progID);
    }
    public void stop() {
        glUseProgram(0);
    }
    public void cleanup() {
        stop();
        done = true;
        for(int shaderID: shaderIDs) {
            glDetachShader(progID, shaderID);
            glDeleteShader(shaderID);
        }
        glDeleteProgram(progID);
    }

    protected void bindAttribute(int attribute, String variableName) {        
        glBindAttribLocation(progID, attribute, variableName);
    }

    public int getUniformLocation(String name) {
        return glGetUniformLocation(progID, name);
    }

    protected abstract void getAllUniformLocations();

    private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
    public void loadMatrix(int location, Matrix4f matrix) {
        glUniformMatrix4(location, false, matrix.get(matrixBuffer));
    }
    protected void loadVector(int location, Vector3f v) {
        glUniform3f(location, v.x, v.y, v.z);
    }
    protected void loadVector(int location, float x, float y, float z) {
        glUniform3f(location, x, y, z);
    }
    protected void loadFloat(int location, float f) {
        glUniform1f(location, f);
    }
    protected void loadInt(int location, int i) {
        glUniform1i(location, i);
    }

    public AbstractShaderProg(String [] shadernames) { 
        loadShaders(shadernames);
    }

    private void loadShaders(String [] sourcenames) { 
        reload = false;
        int shaderType = 0;
        shadernames = sourcenames;
        progID = glCreateProgram();
        for(String shaderfile: shadernames) {
            if(shaderfile.length()==0) ;//Do nothing... NEXT!
            else if(shaderfile.contains("vert")) shaderType = GL_VERTEX_SHADER;
            else if(shaderfile.contains("frag")) shaderType = GL_FRAGMENT_SHADER;
            else if(shaderfile.contains("geom")) shaderType = GL_GEOMETRY_SHADER;
            else if(shaderfile.contains("tess")) shaderType = GL_TESS_CONTROL_SHADER;
            else if(shaderfile.contains("comp")) shaderType = GL_COMPUTE_SHADER;
            int shaderID = compileShader(shaderfile, shaderType);            
            glAttachShader(progID, shaderID);
            shaderIDs.add(shaderID);
        }
        bindAttributes();//??
        glLinkProgram(progID);
        glValidateProgram(progID);        
        if(glGetProgrami(progID, GL_VALIDATE_STATUS)==GL_FALSE) {
            System.out.println(glGetProgramInfoLog(progID, 512));
            //System.exit(-1);
        }
        getAllUniformLocations();
        shaderFileCheck();
    }


    private int compileShader(String filename, int type) {        
        StringBuilder shaderSource = new StringBuilder();
        try {
            timemap.put(filename, Files.getLastModifiedTime(Paths.get(filename)).toMillis());
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
            //System.exit(-1);
        }
        int shader = glCreateShader(type);
        glShaderSource(shader, shaderSource);
        glCompileShader(shader);
        if(glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.out.println(glGetShaderInfoLog(shader, 512));
            System.out.println("Couldn't conpile shader "+filename);
            //System.exit(-1);
        }
        return shader;
    }
    

    void shaderFileCheck() {
        System.out.println("Starting shader file check thread...");
        done = false;
        Thread fileCheckThread = new Thread(new Runnable() {
            @Override
            public void run() {   //throw new UnsupportedOperationException("Unimplemented method 'run'");
                while (!done) {
                    try {
                        Thread.sleep(1000);
                        for(String filename : shadernames) { 
                            long ftime = Files.getLastModifiedTime(Paths.get(filename)).toMillis();
                            if(ftime != timemap.get(filename)) {
                                System.out.println("Filetime "+filename+" - "+ftime+" THIS WOULD TRIGGER A RELOAD");
                                timemap.replace(filename, Files.getLastModifiedTime(Paths.get(filename)).toMillis());
                                reload = true;
                                done = true;
                            }                            
                        }
                    } catch (Exception e) { e.printStackTrace(); }
                }
                System.out.println("Stopping shader file check thread...");
            }
        });
        fileCheckThread.start();
    }

}
