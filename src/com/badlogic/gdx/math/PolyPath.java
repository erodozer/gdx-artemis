package com.badlogic.gdx.math;

import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;

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
	FloatArray times;
	
	public PolyPath(T... points)
	{
		this.points = new Array<T>();
		this.points.addAll(points);
		
		calculateTimes();
	}
	
	public PolyPath(Array<T> points)
	{
		this.points = new Array<T>();
		this.points.addAll(points);
		
		calculateTimes();
	}
	
	/**
	 * Calculates the travel time of each segment to allow for 
	 * smooth lerping along the path between 0 and 1
	 */
	private void calculateTimes()
	{
		//find total length of the path
		float length = 0, dst = 0;
		for (int i = 1; i < points.size; i++)
		{
			dst = points.get(i).dst(points.get(i-1));
			length += dst;
		}
		
		//calculate percentage that each segment takes of the path's total length
		times = new FloatArray();
		times.add(0);
		float l = 0;
		for (int i = 1; i < points.size; i++)
		{
			dst = points.get(i).dst(points.get(i-1));
			l += dst;
			times.add(l/length);
		}
	}
	
	@Override
	public T valueAt(T out, float t) {
		//clamp to edges
		if (t >= 1f)
			out.set(points.peek());
		else if (t <= 0f)
			out.set(points.first());
		else
		{
			//find set of points to lerp between
			int i = 1;
			float s = 0, dst = 0, r;
			while ((s = times.get(i)) < t) i++;
			
			//allow approximation
			r = times.get(i-1);
			dst = (t-r)/(s-r);
			out.set(points.get(i-1));
			T v = points.get(i);
			out.lerp(v, dst);
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
