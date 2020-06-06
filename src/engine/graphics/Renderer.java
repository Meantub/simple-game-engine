package engine.graphics;

import engine.entity.Entity;
import engine.graphics.math.Transform;
import engine.graphics.model.Model;
import engine.graphics.model.Vertex;
import engine.graphics.shader.BasicShader;
import engine.graphics.shader.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class Renderer {
    private Model model;
    private BasicShader basicShader;
    private Matrix4f projectionMatrix;
    private EntityRenderer entityRenderer;
    private HashMap<Model, ArrayList<Entity>> entities = new HashMap<Model, ArrayList<Entity>>();

    public Renderer(){
        init();

        basicShader = new BasicShader();
        updateProjection(90f, 1280, 720, 0.1f, 1000f);

        entityRenderer = new EntityRenderer(basicShader);

        model = Model.loadModel("cube.obj");

    }

    public void render(){
        prepareFrameBuffer();

        basicShader.bind();
        basicShader.updateSun(new Vector3f(0,1,0));
        entityRenderer.render(entities);

        Shader.unbind();
        entities.clear();
    }

    public void processEntity(Entity entity){
        Model model = entity.getModel();
        ArrayList<Entity> batch = entities.get(model);
        if(batch == null){
            batch = new ArrayList<Entity>();
            entities.put(model, batch);
        }
        batch.add(entity);
    }

    private void prepareFrameBuffer(){
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    }

    private void init(){
        glEnable(GL_DEPTH_TEST);
    }

    public void stop(){

    }

    private void updateProjection(float fov, int width, int height, float zNear, float zFar){
        projectionMatrix = Transform.getPerspectiveProjection(fov, width, height, zNear, zFar);

        basicShader.bind();
        basicShader.updateProjectionMatrix(projectionMatrix);

        Shader.unbind();
    }
}
