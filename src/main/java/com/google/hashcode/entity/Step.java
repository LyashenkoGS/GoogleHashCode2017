package com.google.hashcode.entity;

/**
 * Step as an entity is a slice tha can be added to a particular slice inside a particular pizza, considering
 * pizza's slice instructions
 *
 * @author Grigoriy Lyashenko (Grog).
 */
public class Step {

    public Step(Slice startPosition, Slice delta) {
		super();
		this.startPosition = startPosition;
		this.delta = delta;
	}

	public Slice startPosition;

    public Slice delta;
}
