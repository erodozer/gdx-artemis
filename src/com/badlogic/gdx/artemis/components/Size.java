package com.badlogic.gdx.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;

public class Size extends Component {

	public static ComponentType CType = ComponentType.getTypeFor(Size.class);
	
	public float width;
	public float height;
	
	public Size()
	{
		width = 1.0f;
		height = 1.0f;
	}
	
	public Size(float width, float height)
	{
		this.width = width;
		this.height = height;
	}
	
}
