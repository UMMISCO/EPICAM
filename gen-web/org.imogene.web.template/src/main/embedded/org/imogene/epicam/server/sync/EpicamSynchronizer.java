package org.imogene.epicam.server.sync;

import javax.persistence.NoResultException;

import org.imogene.epicam.server.ImogActorUtils;
import org.imogene.lib.common.dao.GenericDao;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.sync.client.impl.SynchronizerImpl;
import org.springframework.transaction.annotation.Transactional;

public class EpicamSynchronizer extends SynchronizerImpl {

	private GenericDao genericDao;

	@Override
	@Transactional
	public int synchronize(String url, String login, String password, String terminal, Long offset) {
		try {
			ImogActor actor = genericDao.loadFromLogin(login);
			ImogActorUtils.setCurrentActor(actor);
		} catch (NoResultException e) {
		}
		try {
			return super.synchronize(url, login, password, terminal, offset);
		} finally {
			ImogActorUtils.setCurrentActor(null);
		}
	}

	@Override
	@Transactional
	public int synchronize() {
		return super.synchronize();
	}

	@Override
	public void setGenericDao(GenericDao genericDao) {
		super.setGenericDao(genericDao);
		this.genericDao = genericDao;
	}

}
