package com.badlogic.gdx.math;

import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.utils.Array;

/**
 * A linear track path with many points.  Distance between 0 and 1 for t is dependent
 * on the total length of the track instead of just the number of points on the track
 * to help create a consistent travel speed.  
 * 
 * @author nhydock
 * @param <T>
 */
public class PolyPath<T extends Vector<T>> implements Path<T>, Cloneable {

	Array<T> points;
	float length;
	
	public PolyPath(T... points)
	{
		this.points = new Array<T>();
		this.points.addAll(points);
		
		length = 0;
		for (int i = 0; i < points.length; i++)
		{
			length += points[i].len();
		}
	}
	
	public PolyPath(Array<T> points)
	{
		this.points = new Array<T>();
		this.points.addAll(points);
		
		length = 0;
		for (int i = 0; i < points.size; i++)
		{
			length += points.get(i).len();
		}
	}
	
	@Override
	public T valueAt(T out, float t) {
		// 
		if (t > 1f)
			out.set(points.peek());
		else if (t < 0f)
			out.set(points.first());
		else
		{
			float l = length * t;
			int i = 0;
			//find set to lerp between
			for (i = 0; l > 0; i++){
				l -= points.get(i).len();
			}
			
			//allow approximation
			if (l > -.05f)
			{
				out.set(points.get(i));
			}
			else
			{
				l = -l;
				out.set(points.get(i-1));
				T v = points.get(i);
				out.lerp(v, l/out.dst(v));
			}
		}
		
		return out;
	}
	
	public PolyPath<T> clone()
	{
		PolyPath<T> p = new PolyPath<T>();
		p.points = new Array<T>();
		for (int i = 0; i < points.size; i++)
		{
			T v = points.get(i).cpy();
			p.points.add(v);
		}
		
		return p;
	}

	@Override
	public float approximate(T v) {
		// TODO make something that approximates a point onto the line
		return 0;
	}

	@Override
	public float locate(T v) {
		// TODO make a real thing
		return 0;
	}

}
