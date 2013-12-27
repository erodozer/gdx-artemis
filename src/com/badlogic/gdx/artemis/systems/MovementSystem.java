package com.badlogic.gdx.artemis.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.artemis.components.*;

public class MovementSystem extends EntityProcessingSystem {

	public MovementSystem() {
		/**
		 * Select out all entities with positions
		 */
		super(Aspect.getAspectForAll(Position.class).one(Velocity.class, Anchor.class, Angle.class, Rotation.class));
	}

	@Mapper ComponentMapper<Position> posmap;
	@Mapper ComponentMapper<Angle> angmap;
	@Mapper ComponentMapper<Time> timemap;
	@Mapper ComponentMapper<Velocity> velmap;
	@Mapper ComponentMapper<Rotation> rotmap;
	@Mapper ComponentMapper<Anchor> ancmap;
	@Mapper ComponentMapper<Path> pathmap;
	
	@Override
	protected void process(Entity e) {
		Position p = posmap.get(e);
		Velocity v = velmap.getSafe(e);
		Anchor anchor = ancmap.getSafe(e);
		Path path = pathmap.getSafe(e);
		
		Angle a = angmap.getSafe(e);
		
		//rotate the angle
		if (a != null)
		{
			Rotation r = rotmap.getSafe(e);
			if (r != null)
			{
				a.degrees += r.rate * world.delta;
			}
		}
		
		//if entity is anchored
		if (anchor != null)
		{
			Position p1 = posmap.get(e);
			Position p2 = posmap.getSafe(anchor.link);
			//if the object is anchored to another then it should be removed from the world
			// ex. the head of a multi-part actor dies, then its body should too
			if (p2 != null)
			{
				p1.location.x = p2.location.x+p2.offset.x;
				p1.location.y = p2.location.y+p2.offset.y;
			}
			else
			{
				e.deleteFromWorld();
			}
		}
		//entity follows a path
		else if (path != null)
		{
			if (path.isDone())
			{
				e.deleteFromWorld();
			}
			else
			{
				path.update(world.delta);
				path.getValue(p.location);
			}
		}
		//entity has velocity
		else if (v != null)
		{
			p.location.x += v.x * world.delta;
			p.location.y += v.y * world.delta;
		}
		
	}

}
