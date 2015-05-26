package com.ineentho.shitstorm;

import com.badlogic.gdx.math.Vector2;

public class Utils {
	public static Vector2 moveTowards(Vector2 source, Vector2 target) {
		Vector2 diff = target.sub(source);
		return source.add(diff.scl(.95f));
	}
}
