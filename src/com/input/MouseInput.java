package com.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseInput extends GLFWMouseButtonCallback {
	
	public static boolean[] keys = new boolean[3];
	
    @Override
    public void invoke(long window, int button, int action, int mods) {
    	if(button < 3)
    		keys[button] = action != GLFW.GLFW_RELEASE;
    }
} 
