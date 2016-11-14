package com.physics;

import static com.math.Vec2.*;

import com.math.Vec2;

public abstract class Mass {
	public float m;
	public float invMass;
	public float t;
	public int size;
	public Vec2 F = new Vec2();
	public float I;
	public Vec2 r;
	public Vec2 v = new Vec2();
	public float w;
	
	public float dt = .1f;
	
	public Mass(float m, int size, Vec2 r){
		this.m = m;
		invMass = (m > 0) ? 1/m : 0;
		this.size = size;
		I=(float) (size*Math.pow(size,3))/12;
		this.r=r;
	}
	
	public void init(){
		F = new Vec2();
		t = 0;
	}
	
	public void delete(){
		
	}
	
	public void applyForce(Vec2 f, Vec2 point){
 		F = add(f, F);
		t += dot(perp(add(point, scale(r,-1))),F);
	}
	
	public void applyForce(Vec2 f){
 		F = add(f, F);
	}
	
	
	public void set(){
		r = add(r, scale(v, dt));
		v = add(v, scale(scale(F,invMass),dt));
		F.zero();
	}

	public float getMass(){
		return m;
	}
	
	
}
