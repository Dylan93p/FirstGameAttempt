package com.input;

import org.lwjgl.glfw.GLFWScrollCallback;

public class ScrollInput extends GLFWScrollCallback{

	public static double[] in = new double[2];
	@Override
	public void invoke(long window, double xoffset, double yoffset) {
		in[0] = xoffset;
		in[1] = yoffset;
	}


}
