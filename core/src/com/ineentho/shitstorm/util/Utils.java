package com.ineentho.shitstorm.util;

import com.badlogic.gdx.math.Vector2;

public class Utils {
	public static Vector2 moveTowards(Vector2 source, Vector2 target) {
		Vector2 diff = target.sub(source);
		return source.add(diff.scl(.8f));
	}
}
