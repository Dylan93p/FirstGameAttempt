package com.game;

import org.lwjgl.glfw.GLFW;

import com.Main;
import com.graphics.Shader;
import com.graphics.Texture;
import com.graphics.VertexArray;
import com.input.Input;
import com.math.Matrix4f;
import com.math.Vec2;
import com.math.Vector3f;
import com.physics.Mass;

public class Player extends Mass {

	private float SIZE = 0.5f;
	
	private VertexArray mesh;
	private Texture texture;
	
	private boolean faceRight = true;
	
	private World world;
	
	public Player (float m, Vec2 r, World w) {
		super(m, 5, r);
		float[] verts = new float[]{
				-SIZE, -SIZE, 1.0f,
				-SIZE,  SIZE, 1.0f,
				 SIZE,  SIZE, 1.0f,
				 SIZE, -SIZE, 1.0f
		};
		
		byte[] indices = new byte[] {
				0, 1, 2,
				2, 3, 0
		};
		
		float [] tcs = new float[] {
				0, 1,
				0, 0,
				1, 0,
				1, 1
		};
		
		mesh = new VertexArray(verts, indices, tcs);
		texture = new Texture("res/player.png");
		
		world = w;
	}
	
	public void update() {
		if(Input.keys[GLFW.GLFW_KEY_W]){
			applyForce(new Vec2(0,150));
		}
		if(Input.keys[GLFW.GLFW_KEY_S]){
			r.y -= 1f;
		}
		if(Input.keys[GLFW.GLFW_KEY_A]){
			if(faceRight){
				Matrix4f pr_matrix = 
						Matrix4f.orthographic(Main.pr_wid, -Main.pr_wid, 
								-Main.pr_height, Main.pr_height, -1.0f, 1.0f);
				Shader.PLAYER.setUniformMat4f("pr_matrix", pr_matrix);
				faceRight = false;
			}
			world.w += 0.01f;
		}
		if(Input.keys[GLFW.GLFW_KEY_D]){
			if(!faceRight){
				Matrix4f pr_matrix = 
						Matrix4f.orthographic(-Main.pr_wid, Main.pr_wid, 
								-Main.pr_height, Main.pr_height, -1.0f, 1.0f);
				Shader.PLAYER.setUniformMat4f("pr_matrix", pr_matrix);
				faceRight = true;
			}
			world.w -= 0.01f;
		}
		set();
	}
	
	public void render() {
		Shader.PLAYER.enable();
		Shader.PLAYER.setUniformMat4f("ml_matrix", Matrix4f.translate(new Vector3f(0,-5,0)));
		texture.bind();
		mesh.render();
		Shader.PLAYER.disable();
	}
}
