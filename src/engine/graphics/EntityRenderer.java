package engine.graphics;

import engine.entity.Entity;
import engine.graphics.math.Transform;
import engine.graphics.model.Model;
import engine.graphics.model.Vertex;
import engine.graphics.shader.BasicShader;
import engine.graphics.shader.Shader;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;

import static engine.graphics.math.Transform.*;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.newdawn.slick.opengl.renderer.SGL.GL_TRIANGLES;

public class EntityRenderer {
    private BasicShader shader;

    public EntityRenderer(BasicShader shader){
        this.shader=shader;
    }

    public void render(HashMap<Model, ArrayList<Entity>> entities){
        for(Model model : entities.keySet()){
            loadModel(model);

            for(Entity entity : entities.get(model)){
                loadInstance(entity);
                glDrawElements(GL_TRIANGLES, model.getSize(), GL_UNSIGNED_INT, 0);
            }

            unloadModel();
        }
    }

    private void loadModel(Model model){
        glBindBuffer(GL_ARRAY_BUFFER, model.getVbo());
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, model.getIbo());

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, Vertex.SIZE * 4, 12);
    }

    private void unloadModel(){
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    private void loadInstance(Entity entity){
        shader.updateWorldMatrix(getTransformation(entity.getPos(), 0.0f, 0.0f, 0.0f, 3.0f));
    }
}
