package com.badlogic.gdx.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Allows a texture on an entity to repeat and even scroll over time
 * @author nhydock
 *
 */
public class Scrollable extends Component {
	public static ComponentType CType = ComponentType.getTypeFor(Scrollable.class);
	
	private TextureRegion region = null;
	
	private final Vector2 rate;
	private final Vector2 repeat;
	
	private float s1, s2, t1, t2;
	
	public float u1, u2, v1, v2;
	
	/**
	 * @param x - horizontal scroll rate
	 * @param y - vertical scroll rate
	 * @param repeatX - number of times the texture repeats horizontally
	 * @param repeatY - number of times the texture repeats vertically
	 */
	public Scrollable(final float x, final float y, final float repeatX, final float repeatY)
	{
		rate = new Vector2(x, y);
		repeat = new Vector2(repeatX, repeatY);
		
		this.u1 = 0.0f;
		this.v1 = 0.0f;
		this.u2 = repeatX;
		this.v2 = repeatY;
	}
	
	public Scrollable(final float x, final float y, final float repeatX, final float repeatY, TextureRegion r) {
		this(x, y, repeatX, repeatY);
		this.region = r;
	}

	public void update(float delta)
	{
		
		if (s1 > repeat.x) s1 -= repeat.x;
		else if (s1 < -repeat.x) s1 += repeat.x;
		else s1 += rate.x*delta;
		
		if (t1 > repeat.y) t1 -= repeat.y;
		else if (t1 < -repeat.y) t1 += repeat.y;
		else t1 += rate.y*delta;
		
		s2 = s1+repeat.x;	
		t2 = t1+repeat.y;
		
		if (region != null)
		{
			float w = region.getU2()-region.getU();
			float h = region.getV2()-region.getV();
			u1 = region.getU() + s1*w;
			u2 = region.getU() + s2*w;
			v1 = region.getV() + t1*h;
			v2 = region.getV() + t2*h;
		}
		else
		{
			u1 = s1;
			u2 = s2;
			v1 = t1;
			v2 = t2;
		}
	}
}
