package engine.entity;

import engine.graphics.model.Model;
import org.joml.Vector3f;

public class Entity {
    private Model model;

    private Vector3f pos;

    public Entity(Vector3f pos, Model model){
        this.pos = pos;
        this.model = model;

    }

    public Model getModel(){
        return model;
    }

    public Vector3f getPos(){
        return pos;
    }
}
