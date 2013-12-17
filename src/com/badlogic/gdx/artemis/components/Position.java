package com.badlogic.gdx.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 */
public class Position extends Component {
	public static ComponentType CType = ComponentType.getTypeFor(Position.class);
	
	public Vector2 location;
	public Vector2 offset;
	
	public Position()
	{
		this(0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	public Position(float x, float y)
	{
		this(x, y, 0.0f, 0.0f);
	}
	
	public Position(Vector2 pos, Vector2 offset) {
		this(pos.x, pos.y, offset.x, offset.y);
	}
	
	public Position(float x, float y, float xOffset, float yOffset)
	{
		location = new Vector2(x, y);
		offset = new Vector2(xOffset, yOffset);
	}

}
