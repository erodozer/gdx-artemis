package com.badlogic.gdx.artemis.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.artemis.components.*;

/**
 * System that renders a 2D field
 * @author nhydock
 */
abstract public class RenderSystem2D extends EntitySystem {

	public RenderSystem2D() {
		super(Aspect.getAspectForAll(Renderable.class));
	}

	@Mapper protected ComponentMapper<Position> pmap;
	@Mapper protected ComponentMapper<Angle> amap;
	@Mapper protected ComponentMapper<Size> smap;
	@Mapper protected ComponentMapper<Renderable> rmap;

	//system has its own drawing components
	protected Matrix4 camera;

	@Override
	protected void initialize()
	{
		camera = new Matrix4();
		camera.setToOrtho2D(0, 0, getInternalRes()[0], getInternalRes()[1]);
		camera.translate(getFOV()[0], getFOV()[1], 0);
	}
	
	/**
	 * 
	 * @return
	 */
	abstract protected float[] getInternalRes();
		
	/**
	 * Get the location on screen that this system will render from
	 * @return float[]
	 */
	abstract protected float[] getFOV();

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for (int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			Renderable r = rmap.get(e);
			
			Angle a = amap.getSafe(e);
			if (a != null)
				r.sprite.setRotation(a.degrees);
			Position p = pmap.getSafe(e);
			if (p != null)
				r.sprite.setPosition(p.location.x+p.offset.x, p.location.y+p.offset.y);
			Size s = smap.getSafe(e);
			if (s != null)
				r.sprite.setScale(s.width, s.height);
		}
	}
	
	/**
	 * Check if a location is within the renderable area.  This can be used for culling out objects
	 * @param loc
	 * @return
	 */
	public boolean inView(Vector2 loc)
	{	
		return loc.x >= getFOV()[0] && 
			   loc.x <= getFOV()[2] && 
			   loc.y >= getFOV()[1] && 
			   loc.y <= getFOV()[3];
	}

	/**
	 * Performs 2D drawing of the system
	 * @param batch
	 */
	abstract public void draw(SpriteBatch batch);
	
}
