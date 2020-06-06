package engine.graphics.model;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Vector;

import static org.lwjgl.opengl.GL15.*;

public class Model {
    private int vbo;
    private int ibo;
    private int size;

    public Model(){
        vbo = glGenBuffers();
        ibo = glGenBuffers();

        size = 0;
    }

    public void bufferVerticies(Vertex[] vertices, int[] indices){
        bufferVertices(vertices, indices, false);

    }

    public void bufferVertices(Vertex[] vertices, int[] indices, boolean calcNormals){

        if(calcNormals){
            calcNormals(vertices, indices);
        }

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * Vertex.SIZE * 2);

        for(Vertex vertex : vertices){
            buffer.put(vertex.getPos().x);
            buffer.put(vertex.getPos().y);
            buffer.put(vertex.getPos().z);
            buffer.put(vertex.getNormal().x);
            buffer.put(vertex.getNormal().y);
            buffer.put(vertex.getNormal().z);
        }

        buffer.flip();

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        IntBuffer intBuffer = BufferUtils.createIntBuffer(indices.length);
        intBuffer.put(indices);
        intBuffer.flip();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, intBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        size = indices.length;
    }

    private void calcNormals(Vertex[] vertices, int[] indices){
        for(int i = 0; i<indices.length; i+=3){
            int i0 = indices[i];
            int i1 = indices[i+1];
            int i2 = indices[i+2];

            Vector3f v1 = vertices[i1].getPos().sub(vertices[i0].getPos());
            Vector3f v2 = vertices[i2].getPos().sub(vertices[i0].getPos());

            Vector3f normal = v1.cross(v2).normalize();

            vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
            vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
            vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
        }

        for(int i=0; i<vertices.length; i++){
            vertices[i].setNormal(vertices[i].getNormal().normalize());
        }

    }

    public int getIbo(){ return ibo; }

    public int getSize() {
        return size;
    }

    public int getVbo() {
        return vbo;
    }

    public static Model loadModel(String fileName){
        Model res = new Model();
        BufferedReader reader = null;
        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        ArrayList<Integer> indices = new ArrayList<Integer>();
        try{
            reader = new BufferedReader(new FileReader("./res/" + fileName));

            String line;
            while((line = reader.readLine()) !=null){
                if(line.startsWith("v ")){
                    String[] vertexLine = line.split(" ");
                    vertices.add(new Vertex(Float.parseFloat(vertexLine[1]), Float.parseFloat(vertexLine[2]), Float.parseFloat(vertexLine[3]), new Vector3f(0,0,0)));
                }
                else if(line.startsWith("f ")){
                    String[] indicesLine = line.split(" ");
                    indices.add(Integer.parseInt(indicesLine[1])-1);
                    indices.add(Integer.parseInt(indicesLine[2])-1);
                    indices.add(Integer.parseInt(indicesLine[3])-1);
                }
            }
            reader.close();
        } catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        Vertex[] vertexArray = new Vertex[vertices.size()];
        for(int i=0; i<vertexArray.length; i++){
            vertexArray[i]=vertices.get(i);
        }
        int[] indexArray = new int[indices.size()];
        for(int i=0; i<indexArray.length; i++){
            indexArray[i]=indices.get(i);
        }
        res.bufferVertices(vertexArray, indexArray, true);

        return res;
    }
}
