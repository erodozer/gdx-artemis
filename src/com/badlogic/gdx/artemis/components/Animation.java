package com.badlogic.gdx.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

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
	public Vector2 xy, frames;
	
	/**
	 * Number of frames available in the animation
	 */
	public final int frameCount;
	
	private Vector2 frameSize;
	
	/**
	 * Region coordinates
	 */
	public float u1, u2, v1, v2;
	public float u0, v0;
	
	public Animation(Texture spriteSheet, int frameCount, final float rate, final boolean loop)
	{
		this(new TextureRegion(spriteSheet), frameCount, 1, rate, loop);
	}
	
	public Animation(TextureRegion spriteSheet, int frameCount, final float rate, final boolean loop)
	{
		this(spriteSheet, frameCount, 1, rate, loop);
	}
	
	public Animation(Texture spriteSheet, int xFrames, int yFrames, final float rate, final boolean loop)
	{
		this(new TextureRegion(spriteSheet), xFrames, yFrames, rate, loop);
	}
	
	public Animation(TextureRegion spriteSheet, int xFrames, int yFrames, final float rate, final boolean loop)
	{
		this.frameCount = xFrames * yFrames;
		this.rate = this.next = rate;
		this.prev = 0;
		this.loop = loop;
		this.index = 0;
		this.frames = new Vector2(xFrames, yFrames);
		this.done = false;
		
		this.u0 = spriteSheet.getU();
		this.v0 = spriteSheet.getV();
		this.frameSize = new Vector2((spriteSheet.getU2()-this.u0)/(float)xFrames, 
	                                 (spriteSheet.getV2()-this.v0)/(float)yFrames);
		
		this.u1 = 0;
		this.u2 = this.frameSize.x;
		this.v1 = 0;
		this.v2 = this.frameSize.y;
		
		this.xy = new Vector2();
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
			if (xy.x + 1 < frames.x)
			{
				xy.x++;
				this.u1 += this.frameSize.x;
				this.u2 += this.frameSize.x;
			}
			else if (xy.y + 1 < frames.y)
			{
				xy.x = 0;
				xy.y++;
				this.u1 = 0;
				this.u2 = this.frameSize.x;
				this.v1 += this.frameSize.y;
				this.v2 += this.frameSize.y;
			}
		}
		else if (this.loop)
		{
			index = 0;
			xy.x = 0;
			xy.y = 0;
			this.u1 = 0;
			this.u2 = this.frameSize.x;
			this.v1 = 0;
			this.v2 = this.frameSize.y;
		}
		else
		{
			this.done = true;
		}
	}
}
