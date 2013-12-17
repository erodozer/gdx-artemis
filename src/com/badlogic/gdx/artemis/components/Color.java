package com.badlogic.gdx.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;

public class Color extends Component{

	public static ComponentType CType = ComponentType.getTypeFor(Size.class);
	
	public float r,g,b,a;
	
	public Color(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public Color(com.badlogic.gdx.graphics.Color c)
	{
		this.r = c.r;
		this.g = c.g;
		this.b = c.b;
		this.a = c.a;
	}
}
