package com.badlogic.gdx.artemis.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.artemis.components.*;

/**
 * Takes care of managing and updated entities with animations or scrolling
 * @author nhydock
 */
public class AnimationSystem extends EntityProcessingSystem {

	@SuppressWarnings("unchecked")
	public AnimationSystem() {
		super(Aspect.getAspectForAll(Renderable.class).one(Animation.class, Scrollable.class));
	}

	@Mapper ComponentMapper<Animation> animationMap;
	@Mapper ComponentMapper<Scrollable> scrollableMap;
	@Mapper ComponentMapper<Renderable> renderableMap;

	@Override
	protected void process(Entity e) {
		Renderable r = renderableMap.get(e);
		
		Animation a = animationMap.getSafe(e);
		if (a != null)
		{
			a.update(world.delta);		
			r.sprite.setRegion(a.u1, a.v1, a.u2, a.v2);
		}
		else
		{
			Scrollable s = scrollableMap.getSafe(e);
		
			//scroll sprite's texture if renderable has scrollable as well
			if (s != null)
			{
				s.update(world.delta);
				r.sprite.setRegion(s.u1, s.v1, s.u2, s.v2);
			}
		}	
	}
}
