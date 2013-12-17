package com.badlogic.gdx.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentType;
import com.artemis.Entity;

/**
 * Anchors the position of one entity to another
 * @author nhydock
 *
 */
public class Anchor extends Component {
	public static ComponentType CType = ComponentType.getTypeFor(Anchor.class);
	
	public Entity link;
	
	public Anchor(Entity link)
	{
		this.link = link;
	}
}
