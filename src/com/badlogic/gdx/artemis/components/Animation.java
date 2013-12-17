package com.badlogic.gdx.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation extends Component {
	public static ComponentType CType = ComponentType.getTypeFor(Animation.class);
	
	/**
	 * Time it takes to change to the next frame
	 */
	public final float rate;
	/**
	 * Time since last frame change
	 */
	public float prev;
	/**
	 * Time until next frame change
	 */
	public float next;
	/**
	 * Tell if the animation loops or not
	 */
	public final boolean loop;
	/**
	 * Tell if the animation is done
	 */
	public boolean done;
	
	/**
	 * Current frame index of the animation
	 */
	public int index;
	/**
	 * Number of frames available in the animation
	 */
	public final int frameCount;
	
	private final float frameSize;
	
	/**
	 * Region coordinates
	 */
	public float u1, u2, v1, v2;
	
	public Animation(Texture spriteSheet, int frameCount, final float rate, final boolean loop)
	{
		this(new TextureRegion(spriteSheet), frameCount, rate, loop);
	}
	
	public Animation(TextureRegion spriteSheet, int frameCount, final float rate, final boolean loop)
	{
		this.frameCount = frameCount;
		this.frameSize = 1.0f/frameCount;
		this.rate = this.next = rate;
		this.prev = 0;
		this.loop = loop;
		this.index = 0;
		this.done = false;
		
		this.u1 = 0;
		this.u2 = this.frameSize;
		this.v1 = 0;
		this.v2 = 1.0f;
	}
	
	public void update(float delta)
	{
		if (done)
			return;
		
		prev += delta;
		next -= delta;
		
		if (next <= 0)
			advance();
	}
	
	public void advance()
	{
		next = rate;
		prev = 0;
			
		if (index + 1 < this.frameCount)
		{
			index++;
			this.u1 += this.frameSize;
			this.u2 += this.frameSize;
		}
		else if (this.loop)
		{
			index = 0;
			this.u1 = 0;
			this.u2 = this.frameSize;
		}
		else
		{
			this.done = true;
		}
	}
}
