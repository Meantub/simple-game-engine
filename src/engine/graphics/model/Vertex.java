package engine.graphics.model;

import org.joml.Vector3f;

public class Vertex {

    public static final int SIZE = 3;

    private Vector3f pos;
    private Vector3f normal;

    public Vertex(float x, float y, float z, Vector3f normal){
        pos=(new Vector3f(x, y, z));
        this.normal = normal;
    }

    public Vertex(Vector3f pos, Vector3f normal){
        this.pos=pos;
        this.normal = normal;
    }

    public Vector3f getPos(){
        return pos;
    }

    public void setPos(Vector3f pos){
        this.pos=pos;
    }

    public void setPos(float x, float y, float z){
        pos=new Vector3f(x, y, z);
    }

    public Vector3f getNormal() {
        return normal;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }
}
