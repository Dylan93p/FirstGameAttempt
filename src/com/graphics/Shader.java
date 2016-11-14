package com.graphics;

import com.math.Matrix4f;
import com.utils.ShaderUtils;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
	
	public static final int VERTEX_ATTRIB = 0;
	public static final int TCOORD_ATTRIB = 1;
	
	public static Shader BG, PLAYER, BOX;
	
	private boolean enabled;
	boolean valid = true;
	
	private final int ID;
	private Map<String, Integer> locationCache = new HashMap<String, Integer>();	
	
	public Shader(String vertex, String fragment){
		ID = ShaderUtils.load(vertex, fragment);
		if (ID < 0)
			valid = false;
	}
	
	public static void loadAll(){
		BG = new Shader("shaders/bg.vert","shaders/bg.frag");
		PLAYER = new Shader("shaders/player.vert","shaders/player.frag");
		BOX = new Shader("shaders/vox.vert","shaders/vox.frag");
	}
	
	public int getUniform(String name){
		if(locationCache.containsKey(name))
			return locationCache.get(name);
		
		int res = glGetUniformLocation(ID, name);
		
		if (res == -1)
			System.err.println("Could not find uniform variale " + name);
		else
			locationCache.put(name, res);
		return res;
	}
	
	public void setUniformali(String name, int value){
		if(!enabled) enable();
		glUniform1i(getUniform(name), value);
	}
	
	public void setUniformal1f(String name, float value){
		if(!enabled) enable();
		glUniform1f(getUniform(name), value);
	}
	
	public void setUniformal2f(String name, float x, float y){
		if(!enabled) enable();
		glUniform2f(getUniform(name), x, y);
	}
	
	public void setUniformal3f(String name, float x, float y, float z){
		if(!enabled) enable();
		glUniform3f(getUniform(name), x, y, z);
	}
	
	public void setUniformMat4f(String name, Matrix4f m){
		if(!enabled) enable();
		glUniformMatrix4fv(getUniform(name), false, m.toFloatBuffer());
	}
	
	public void enable(){
		if(!valid)
			return;
		glUseProgram(ID);
		enabled = true;
	}
	
	public void disable(){
		glUseProgram(0);
		enabled = false;
	}
}
