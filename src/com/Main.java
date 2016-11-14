package com;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import com.graphics.Shader;
import com.input.Input;
import com.input.MouseInput;
import com.input.MousePos;
import com.input.ScrollInput;
import com.game.World;
import com.math.Matrix4f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {

	private int width = 1280;
	private int height = 720;
	
	public static float pr_wid = 10;
	public static float pr_height = 10.0f;
	
	private boolean running = false;
	// The window handle
	private long window;
	
	private World world;

	private void init() {
		if(!glfwInit()) {
			System.err.print("Could not initialize GLFW, OH NO!");
			return;
		}
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(width, height, "Hello", NULL, NULL);

		// Get the resolution of the primary monitor
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		// Center our window
		glfwSetWindowPos(
			window,
			(vidmode.width() - width) / 2,
			(vidmode.height() - height) / 2
		);
		glfwSetKeyCallback(window, new Input());
		glfwSetMouseButtonCallback(window, new MouseInput());
		glfwSetScrollCallback(window, (new ScrollInput()));
		glfwSetCursorPosCallback(window, new MousePos());
		
		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		GL.createCapabilities();
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glEnable(GL_DEPTH_TEST);
		glActiveTexture(GL_TEXTURE1);
		Shader.loadAll();

		Matrix4f pr_matrix = Matrix4f.orthographic(-pr_wid, pr_wid, 
				-pr_height, pr_height, -1.0f, 1.0f);
		Shader.BG.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BG.setUniformali("tex", 1);
		
		Shader.PLAYER.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.PLAYER.setUniformali("tex", 1);
		
		Shader.BOX.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BOX.setUniformali("tex", 1);
		
		world = new World();
		
	}

	private void run() {
		running = true;
		init();
		
		long timer = System.currentTimeMillis();

		int updates = 0;
		int frames = 0;
		while (!glfwWindowShouldClose(window) && running) {
			update();
			updates++;
			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				//System.out.println(updates + " ups; " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
	}
	
	private void update(){
		glfwPollEvents();
		//TODO
		if(ScrollInput.in[1] != 0){
			if(pr_wid - ScrollInput.in[1] >= 7){
				pr_wid -= ScrollInput.in[1];
				pr_height -= ScrollInput.in[1];
				Matrix4f pr_matrix = 
						Matrix4f.orthographic(-pr_wid, pr_wid, 
								-pr_height, pr_height, -1.0f, 1.0f);
				Shader.BG.setUniformMat4f("pr_matrix", pr_matrix);
				Shader.PLAYER.setUniformMat4f("pr_matrix", pr_matrix);
				Shader.BOX.setUniformMat4f("pr_matrix", pr_matrix);
				ScrollInput.in[1] = 0;
			}
		}
		world.update();
	}
	
	private void render(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		world.render();

		int i = glGetError();
		if (i != GL_NO_ERROR){
			System.out.println(i);
		}
		glfwSwapBuffers(window); // swap the color buffers
		
	}

	public static void main(String[] args) {
		new Main().run();
	}

}	
