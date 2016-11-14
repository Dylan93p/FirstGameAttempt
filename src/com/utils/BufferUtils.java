package com.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtils {
	private BufferUtils(){}
	
	public static ByteBuffer createByteBuffer(byte[] arr){
		ByteBuffer buff = ByteBuffer.allocateDirect(arr.length).order(ByteOrder.nativeOrder());
		buff.put(arr).flip();
		return buff;
	}
	
	public static FloatBuffer createFloatBuffer(float[] arr){
		FloatBuffer buff = ByteBuffer.allocateDirect(arr.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		buff.put(arr).flip();
		return buff;
	}
	
	public static IntBuffer createIntBuffer(int[] arr){
		IntBuffer buff = ByteBuffer.allocateDirect(arr.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		buff.put(arr).flip();
		return buff;
	}
}
