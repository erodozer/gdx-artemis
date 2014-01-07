package com.artemis.managers;

import com.artemis.Entity;
import com.artemis.Manager;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;

/**
 * If you need to group your entities together, e.g. tanks going into "units" group or explosions into "effects",
 * then use this manager. You must retrieve it using world instance.
 * 
 * A entity can be assigned to more than one group.
 * 
 * @author Arni Arent
 *
 */
public class GroupManager extends Manager {
	private ObjectMap<String, Bag<Entity>> entitiesByGroup;
	private ObjectMap<Entity, Bag<String>> groupsByEntity;

	public GroupManager() {
		entitiesByGroup = new ObjectMap<String, Bag<Entity>>();
		groupsByEntity = new ObjectMap<Entity, Bag<String>>();
	}
	

	@Override
	protected void initialize() {
	}
	
	
	/**
	 * Set the group of the entity.
	 * 
	 * @param group group to add the entity into.
	 * @param e entity to add into the group.
	 */
	public void add(Entity e, String group) {
		Bag<Entity> entities;
		if (!entitiesByGroup.containsKey(group))
		{
			entities = new Bag<Entity>();
			entitiesByGroup.put(group, entities);
		}
		else
		{
			entities = entitiesByGroup.get(group);
		}
		entities.add(e);
		
		Bag<String> groups;
		if (!groupsByEntity.containsKey(e))
		{
			groups = new Bag<String>();
			groupsByEntity.put(e, groups);	
		}
		else
		{
			groups = groupsByEntity.get(e);
		}
		groups.add(group);
	}
	
	/**
	 * Remove the entity from the specified group.
	 * @param e
	 * @param group
	 */
	public void remove(Entity e, String group) {
		if (entitiesByGroup.containsKey(group)){
			Bag<Entity> entities = entitiesByGroup.get(group);
			entities.remove(e);
		}
		
		if (groupsByEntity.containsKey(e)){
			Bag<String> groups = groupsByEntity.get(e);
			groups.remove(group);			
		}
	}
	
	public void removeFromAllGroups(Entity e) {
		if (groupsByEntity.containsKey(e)){
			Bag<String> groups = groupsByEntity.get(e);
			for(int i = 0; groups.size() > i; i++) {
				Bag<Entity> entities = entitiesByGroup.get(groups.get(i));
				if(entities != null) {
					entities.remove(e);
				}
			}
			groupsByEntity.remove(e);
		}
	}
	
	/**
	 * Get all entities that belong to the provided group.
	 * @param group name of the group.
	 * @return read-only bag of entities belonging to the group.
	 */
	public ImmutableBag<Entity> getEntities(String group) {
		if (!entitiesByGroup.containsKey(group)){
			entitiesByGroup.put(group, new Bag<Entity>());
		}
		return entitiesByGroup.get(group);
	}
	
	/**
	 * Get all entities that only belong to the provided group
	 * @param group
	 * @return
	 */
	public ImmutableBag<Entity> getExclusiveEntities(String group) {
		ImmutableBag<Entity> entities = getEntities(group);
	
		Bag<Entity> bag = new Bag<Entity>();
		//check for collisions
		for (int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			if (getGroups(e).size() == 1){
				bag.add(e);
			}
		}
		
		return bag;
		
	}
	
	/**
	 * Get all entities that belong to all the provided group.
	 * @param group name of the group.
	 * @return read-only bag of entities belonging to the group.
	 */
	public ImmutableBag<Entity> getEntities(String... groups) {
		ObjectSet<Entity> collisions = new ObjectSet<Entity>();
		
		//get initial set
		{
			String s = groups[0];
			ImmutableBag<Entity> entities = getEntities(s);
			
			for (int i = 0; i < entities.size(); i++)
			{
				Entity e = entities.get(i);
				collisions.add(e);
			}
		}
		
		
		for (int i = 1; i < groups.length; i++)
		{
			ObjectSet<Entity> c = new ObjectSet<Entity>();
			String s = groups[i];
			ImmutableBag<Entity> entities = getEntities(s);
			
			for (int n = 0; n < entities.size(); n++)
			{
				Entity e = entities.get(n);
				if (collisions.contains(e))
					c.add(e);
			}
			
			collisions = c;
		}
		
		Bag<Entity> e = new Bag<Entity>();
		e.addAll(collisions.iterator().toArray());
		
		return e;
	}
	
	/**
	 * @param e entity
	 * @return the groups the entity belongs to, null if none.
	 */
	public ImmutableBag<String> getGroups(Entity e) {
		return groupsByEntity.get(e);
	}
	
	/**
	 * Checks if the entity belongs to any group.
	 * @param e the entity to check.
	 * @return true if it is in any group, false if none.
	 */
	public boolean isInAnyGroup(Entity e) {
		return getGroups(e) != null;
	}
	
	/**
	 * Check if the entity is in the supplied group.
	 * @param group the group to check in.
	 * @param e the entity to check for.
	 * @return true if the entity is in the supplied group, false if not.
	 */
	public boolean isInGroup(Entity e, String group) {
		Bag<String> groups = groupsByEntity.get(e);
		return groups.contains(group);
	}

	@Override
	public void deleted(Entity e) {
		removeFromAllGroups(e);
	}
	
}
