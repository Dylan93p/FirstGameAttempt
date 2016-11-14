package com.physics;

import com.math.Vec2;

import static com.math.Vec2.*;

public class Contact{
	
	private Mass m1, m2;
	private double dist;
	private Vec2 n;
	private Vec2 pt;
	private float J;
	private Vec2 I;
	private float relNv;
	private float remove;

	public Contact(Mass m1, Mass m2){
		this.m1 = m1;
		this.m2 = m2;
	}
	
	public void generate(){
		n = normalize(sub(m1.r,m2.r));
//		pt = sub(m1.r,scale(n,m1.size));
		relNv = dot(sub(m1.v,m2.v),n);
		dist = dist(m2.r,m1.r)-(m2.size + m1.size);
		
		remove = (float) (relNv + dist/0.1);
		if(remove<=0){
			J = remove;
//			J = (float)((1.2f*dot(sub(add(m2.v,perp(normalize(sub(pt,m2.r)))),add(m1.v,perp(sub(pt,m1.r)))),n))
//					/((m1.invMass+m2.invMass)
//					+Math.pow(dot(n,perp(sub(pt,m1.r))),2)/(m1.I)
//					+Math.pow(dot(n,perp(sub(pt,m2.r))),2)/(m2.I)));
			m1.v = add(m1.v,scale(n,-J));
			m2.v = add(m2.v,scale(n,m2.invMass * J));
			I = scale(n, (0.8f * relNv)/(m1.invMass + m2.invMass));
//			m1.v = add(m1.v,(scale(I,m1.invMass)));
//			m2.applyForce(scale(I,m2.invMass),pt);
		}
	}

}

