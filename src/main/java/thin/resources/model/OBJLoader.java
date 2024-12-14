package thin.resources.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import thin.resources.Loader;


class vertex {
    Vector3f p;
    Vector2f t;
    Vector3f n;
    public vertex(Vector3f vp, Vector2f vt, Vector3f vn) {
        p = new Vector3f(vp); 
        t = new Vector2f(vt); 
        n = new Vector3f(vn);
    }
}

public class OBJLoader {
    
    static final NumberFormat nf = NumberFormat.getNumberInstance();
    static boolean logeverything = false;

    public static RawModel loadOBJModel(String filename, Loader loader) {
        try  {
            FileReader fr = new FileReader(new File(filename));
            BufferedReader br = new BufferedReader(fr);
            List<Vector3f>vtxs = new ArrayList<Vector3f>();
            List<Vector2f>txuv = new ArrayList<Vector2f>();
            List<Vector3f>norm = new ArrayList<Vector3f>();
    
            List<vertex>vlist = new ArrayList<vertex>();
    
            String line="";
            while((line=br.readLine())!=null) {
                if(logeverything) System.out.println("Line = "+line);
                String [] tokens = line.split(" ");
                if(tokens[0].equals("#")) { 
                    /* Comment line, nothing to do  */    
                } else if(tokens[0].equals("v"))  {
                    vtxs.add(new Vector3f( Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3]) ));
                } else if(tokens[0].equals("vt")) {
                    txuv.add(new Vector2f( Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]) ));
                } else if(tokens[0].equals("vn")) {
                    norm.add(new Vector3f( Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3]) ));
                } else if(tokens[0].equals("f"))  {
                    // Start with the second token and step the one before the last
                    // So if there are 3 we get one triangle, 4 gets us 2, 5 gets 3 etc...
                    int vtxoff;
                    int texoff;
                    int nrmoff;
        
                    for(int i=2; i < tokens.length-1; i++) {
                        String[] subs = tokens[1].split("/");
                        vtxoff = Integer.parseInt(subs[0])-1;
                        texoff = Integer.parseInt(subs[1])-1;
                        nrmoff = Integer.parseInt(subs[2])-1;
                        vlist.add(new vertex(vtxs.get(vtxoff),txuv.get(texoff),norm.get(nrmoff)));
                        if(logeverything) System.out.println(vtxs.get(vtxoff).toString(nf) +"  "+ txuv.get(texoff).toString(nf) +"  "+ norm.get(nrmoff).toString(nf));

                        subs = tokens[i].split("/");
                        vtxoff = Integer.parseInt(subs[0])-1;
                        texoff = Integer.parseInt(subs[1])-1;
                        nrmoff = Integer.parseInt(subs[2])-1;
                        vlist.add(new vertex(vtxs.get(vtxoff),txuv.get(texoff),norm.get(nrmoff)));
                        if(logeverything) System.out.println(vtxs.get(vtxoff).toString(nf) +"  "+ txuv.get(texoff).toString(nf) +"  "+ norm.get(nrmoff).toString(nf));

                        subs = tokens[i+1].split("/");
                        vtxoff = Integer.parseInt(subs[0])-1;
                        texoff = Integer.parseInt(subs[1])-1;
                        nrmoff = Integer.parseInt(subs[2])-1;
                        vlist.add(new vertex(vtxs.get(vtxoff),txuv.get(texoff),norm.get(nrmoff)));
                        if(logeverything) System.out.println(vtxs.get(vtxoff).toString(nf) +"  "+ txuv.get(texoff).toString(nf) +"  "+ norm.get(nrmoff).toString(nf));
                    }
                }                
            } br.close();   // End while - file is all done

            float [] fpoint = new float[vlist.size()*3];
            float [] ftexuv = new float[vlist.size()*2];            
            float [] fnorms = new float[vlist.size()*3];
            int [] indxs = new int[vlist.size()];
            if(logeverything) System.out.println("Len = "+vlist.size());
            for (int i=0; i<vlist.size(); i++) {
                vertex v = vlist.get(i);
                fpoint[3*i+0] = v.p.x;
                fpoint[3*i+1] = v.p.y;
                fpoint[3*i+2] = v.p.z;

                ftexuv[2*i+0] = v.t.x;
                ftexuv[2*i+1] = v.t.y;

                fnorms[3*i+0] = v.n.x;
                fnorms[3*i+1] = v.n.y;
                fnorms[3*i+2] = v.n.z;

                indxs[i] = i;
            }

            // vtxs = fpoint.length;

            return loader.loadToVAO(fpoint, ftexuv, fnorms, indxs);
            } catch (IOException e) {
            e.printStackTrace();
        }        
        return null;
    }
}
