package com.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MousePos extends GLFWCursorPosCallback{
	
	public static double[] pos = new double[2];
	@Override
	public void invoke(long window, double xpos, double ypos) {
		pos[0] = xpos;
		pos[1] = ypos;
	}

}
