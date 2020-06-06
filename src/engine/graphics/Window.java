package engine.graphics;

import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

    private long window;

    public Window(int width, int height, String title){

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_DECORATED, GLFW_TRUE);
        glfwWindowHint(GLFW_FOCUSED, GLFW_TRUE);

        window = glfwCreateWindow(width, height, title, 0, 0);

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidMode.width() - width)/2, (vidMode.height() - height)/2);

        glfwMakeContextCurrent(window);
    }

    public void destroyWindow(){
        glfwDestroyWindow(window);
    }

    public void hide(){
        glfwHideWindow(window);
    }

    public void show(){
        glfwShowWindow(window);
    }

    public void render(){
        glfwSwapBuffers(window);
    }

    public void setTitle(String t){
        glfwSetWindowTitle(window, t);
    }

    public boolean shouldClose(){
        if(glfwWindowShouldClose(window)){
            return true;
        }
        return false;
    }

    public long getWindow() {
        return window;
    }
}
