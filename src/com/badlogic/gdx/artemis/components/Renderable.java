package com.badlogic.gdx.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Renderable extends Component {
	public static ComponentType CType = ComponentType.getTypeFor(Renderable.class);
	
	public Sprite sprite;
	boolean visible;
	
	public Renderable(Sprite sprite)
	{
		this.sprite = sprite;
		this.visible = true;
	}
	
	public Renderable(TextureRegion tr)
	{
		this.sprite = new Sprite(tr);
		this.visible = true;
	}
}
