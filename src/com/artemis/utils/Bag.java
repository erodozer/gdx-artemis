package com.artemis.utils;

import com.badlogic.gdx.utils.Array;

/**
 * Artemis compatibility Bag utilizing libGDX's Array class.  Can provide a read-only copy
 * since it is an ImmutableBag
 */
public class Bag<E> extends Array<E> implements ImmutableBag<E> {

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean contains(E e) {
		return this.contains(e, true);
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	public boolean remove(E e) {
		return this.removeValue(e, true);
	}
}
