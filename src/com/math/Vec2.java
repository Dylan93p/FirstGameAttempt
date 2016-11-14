package com.math;

public class Vec2 {
	
	public float x, y;
	
	public Vec2(){
		x = y = 0;
	}
	
	public Vec2(float _x, float _y){
		x = _x;
		y = _y;
	}
	
	public Vec2(double _x, double _y){
		x = (float)_x;
		y = (float)_y;
	}
	
	public void zero(){
		x = y = 0;
	}
	
	public static float mag(Vec2 v){
		float mag = v.x*v.x + v.y*v.y;
		return (float)  Math.sqrt(mag);
	}
	
	public static Vec2 normalize(Vec2 v){
		float mag = mag(v);
		return new Vec2(v.x/mag,v.y/mag);
	}
	
	public static Vec2 add(Vec2 v1, Vec2 v2){
		return new Vec2(v1.x + v2.x, v1.y + v2.y);
	}
	
	public static Vec2 sub(Vec2 v1, Vec2 v2){
		return new Vec2(v1.x - v2.x, v1.y - v2.y);
	}
	
	public static Vec2 scale(Vec2 v1, float s){
		return new Vec2(v1.x * s, v1.y * s);
	}
	
	public static float dot(Vec2 v1, Vec2 v2){
		return v1.x * v2.x + v1.y * v2.y;
	}
	
	public static float dist(Vec2 v1, Vec2 v2){
		return mag(sub(v1,v2));
	}
	
	public static Vec2 perp(Vec2 v){
		return new Vec2(-v.y,v.x);
	}
	
	public boolean equals(Object v){
		if (v.getClass().getName().equals("Vec2"))
			return ((Vec2)v).x == x && ((Vec2)v).y == y;
		return false;
	}
}
