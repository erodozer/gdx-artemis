package com.badlogic.gdx.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;
import com.badlogic.gdx.math.Vector2;

public class Bound extends Component {
	public static ComponentType CType = ComponentType.getTypeFor(Bound.class);
	
	private final float width;
	private final float height;
	public final float radius;
	public final Vector2 center;
	
	public Bound(float width, float height)
	{
		this.width = width;
		this.height = height;
		this.center = new Vector2(width/2.0f, height/2.0f);
		this.radius = this.center.dst(Vector2.Zero);
	}
}
