package engine.graphics.shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class BasicShader extends Shader{

    private static final String VERTEX_FILE = "basicVertex.vs";
    private static final String FRAGMENT_FILE = "basicFragment.fs";


    public BasicShader(){
        super();

        addVertexShader(loadShader(VERTEX_FILE));
        addFragmentShader(loadShader(FRAGMENT_FILE));
        compileShader();

        addUniform("projectionMatrix");
        addUniform("worldMatrix");
        addUniform("sun");
    }

    @Override
    public void bindAttributes() {
        bindAttribute(0, "position");
    }

    public void updateWorldMatrix(Matrix4f worldMatrix){
        setUniform("worldMatrix", worldMatrix);
    }

    public void updateSun(Vector3f sun){
        setUniform("sun", sun);
    }

    public void updateProjectionMatrix(Matrix4f projectionMatrix){
        setUniform("projectionMatrix", projectionMatrix);

    }
}
