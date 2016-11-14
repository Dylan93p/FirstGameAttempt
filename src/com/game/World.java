package com.game;

import java.util.LinkedList;
import java.util.List;

import com.graphics.Shader;
import com.graphics.Texture;
import com.graphics.VertexArray;
import com.input.MouseInput;
import com.input.MousePos;
import com.math.Matrix4f;
import com.math.Vec2;
import com.math.Vector3f;
import com.physics.Contact;
import com.physics.Mass;

public class World extends Mass{
	
	private static final short BG_WID = 40;
	

	private VertexArray background, vox;
	private Texture bgTexture, voxTexture;
	
	private Player player;
	private List<Mass> masses;
	private List<Contact> contacts;
	private byte[][] voxels;
	
	private int xScroll = 0;
	private int yScroll = 0;
	
	
	
	public World(){
		super(0, 600000, new Vec2(0,-600010));
		float[] verts = new float[]{
				-BG_WID/2, -10.0f * 9.0f / 16.0f, 0.0f,
				-BG_WID/2,  60.0f * 9.0f / 16.0f, 0.0f,
				 BG_WID/2,  60.0f * 9.0f / 16.0f, 0.0f,
				 BG_WID/2, -10.0f * 9.0f / 16.0f, 0.0f
		};
		
		float[] vox_verts = new float[]{
				-0.25f, -0.5f, 1.0f,
				-0.25f,  0.5f, 1.0f,
				 0.25f,  0.5f, 1.0f,
				 0.25f, -0.5f, 1.0f
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
		background = new VertexArray(verts, indices, tcs);
		bgTexture = new Texture("res/bg.gif");
		vox = new VertexArray(vox_verts, indices, tcs);
		voxTexture = new Texture("res/dirt.gif");
		
		masses = new LinkedList<Mass>();
		contacts = new LinkedList<Contact>();

		player = new Player(10, new Vec2(0,0), this);
		masses.add(player);
		contacts.add(new Contact(player,this));
		voxels = new byte[2024][256];
		for(int i = 0; i < 2024; i++){
			for(int j = 0; j < 128; j++){
				voxels[i][j] = 1;
			}
		}

	}
	
//	public static Vec2 vox(double x, double y, float w, float r){
//		return new Vec2((int)(x * 1012) );
//	}
	
	public void update() {
		if(MouseInput.keys[0]){
			//Vec2 pos = vox(MousePos.pos[0], MousePos.pos[1], w, player.r.y);
			Vec2 pos = new Vec2(MousePos.pos[0], MousePos.pos[1]);
			
			System.out.println(pos.x + "  " + pos.y + " " + w + " " + (player.r.y));
		}
		if(MouseInput.keys[1]){
			Vec2 pos = new Vec2(MousePos.pos[0], MousePos.pos[1]);
			System.out.println(pos.x + "  " + pos.y + " " + w);
		}
		xScroll = (int) (w * 100);
		yScroll = (int) -player.r.y + 5;
		for(Mass m : masses){
			m.applyForce(new Vec2(0,-9.8f * m.m));
		}
		for(Contact c : contacts){
			c.generate();
		}
		player.update();

	}
	
	public void render(){
		bgTexture.bind();
		Shader.BG.enable();
		background.bind();
		for(int i = (-xScroll / (BG_WID * 40)) - 1; i <= (-xScroll / (BG_WID * 40)) + 1; i++) {
			Shader.BG.setUniformMat4f("vw_matrix", 
					Matrix4f.translate(new Vector3f(i * BG_WID + xScroll * 0.025f, 
														yScroll * .02f, 0.0f)));
			background.draw();
		}
		player.render();
		bgTexture.unbind();
		Shader.BG.disable();
		Shader.BOX.enable();
		voxTexture.bind();
		vox.bind();
		for(int i = -31 - (xScroll/10); i <= 31 - (xScroll/10); i++){
			for(int j = -50; j < 3; j++){
				if(voxels[i + 1028][127 - j] > 0){
					Shader.BOX.setUniformMat4f("vw_matrix", 
							Matrix4f.translate(new Vector3f(i/2.0f + xScroll * 0.05f, 
									yScroll * .02f - (j+6.1f), 0.0f)));
					vox.draw();
				}
			}
		}
		Shader.BOX.disable();
		voxTexture.unbind();

	}

}



