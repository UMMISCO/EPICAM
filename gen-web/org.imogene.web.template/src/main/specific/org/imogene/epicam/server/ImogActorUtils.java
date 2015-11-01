package org.imogene.epicam.server;

import org.imogene.lib.common.entity.ImogActor;

public class ImogActorUtils {

	private static final ThreadLocal<ImogActor> currentActor = new ThreadLocal<ImogActor>();

	public static void setCurrentActor(ImogActor actor) {
		currentActor.set(actor);
	}

	public static ImogActor getCurrentActor() {
		return currentActor.get();
	}

}
