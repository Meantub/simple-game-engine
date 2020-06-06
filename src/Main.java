import engine.entity.Entity;
import engine.graphics.Renderer;
import engine.graphics.Window;
import engine.graphics.model.Model;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements Runnable{

    // Window details
    private int windowWidth = 1280;
    private int windowHeight = 720;
    private String windowName = "Game test";

    private Window window;
    private Renderer renderer;

    // Threads
    private Thread thread;
    private boolean isRunning = false;

    // TEMP
    private ArrayList<Entity> entityArrayList = new ArrayList<Entity>();

    public void start(){
        isRunning=false;
        thread = new Thread(this, "GameEngine");
        thread.start();
    }
    public void run() {
        long lastTime = System.nanoTime();
        long curTime = lastTime;
        long diff = 0;

        long timer = System.currentTimeMillis();

        double ns = 1000000000 / 60.0;
        double delta = 0.0;

        double dfps = 1000000000/60.0;
        double d = 0.0;

        int fps = 0;
        int ups = 0;

        init();

        Model model = Model.loadModel("cube.obj");
        Random random =  new Random();

        entityArrayList.add(new Entity(new Vector3f(0,0,7), model));
//        for(int i=0; i<50; i++){
//            entityArrayList.add(new Entity(new Vector3f(random.nextInt(1000)-500,random.nextInt(1000)-500,random.nextInt(1000)), model));
//        }

        while(isRunning){
            curTime = System.nanoTime();
            diff = curTime-lastTime;
            delta+=diff/ns;
            d+=diff/dfps;
            lastTime=curTime;
            while(delta >= 1.0){
                input();
                update();
                delta--;
                ups++;
            }
            if(d >=1.0){
                render();
                fps++;
                d=0.0;
            }

            if(System.currentTimeMillis() > timer + 1000){
                window.setTitle("Test GameEngine | UPS: " + ups + "| FPS: " + fps);
                ups=0;
                fps=0;
                timer+=1000;
            }
        }
        stop();
    }
    public void init(){
        isRunning=true;

        if(!glfwInit()){
            throw new IllegalStateException("Failed to initialize GLFW!");
        }
        window = new Window(windowWidth, windowHeight, windowName);
        GL.createCapabilities();
        renderer = new Renderer();
    }
    public void update(){

    }
    public void input(){
        glfwPollEvents();
        if(window.shouldClose()) stop();
    }
    public void render(){
        for(int i=0; i<entityArrayList.size(); i++) renderer.processEntity(entityArrayList.get(i));
        renderer.render();
        window.render();
    }
    public void stop(){
        isRunning=false;
        renderer.stop();
        window.destroyWindow();
        System.exit(0);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }
}
