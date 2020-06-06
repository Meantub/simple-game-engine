package engine.graphics.math;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {

    public static Matrix4f getPerspectiveProjection(float fov, int width, int height, float zNear, float zFar){
        return new Matrix4f().perspectiveLH(fov, width/height, zNear, zFar);
    }

    public static Matrix4f getTransformation(Vector3f translation, float rX, float rY, float rZ, float scale){
        Matrix4f translationMatrix = new Matrix4f().translation(translation);
        Matrix4f rotationMatrix = new Matrix4f().rotationXYZ(rX, rY, rZ);
        Matrix4f scaleMatrix = new Matrix4f().scale(scale);



        return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
    }
}
