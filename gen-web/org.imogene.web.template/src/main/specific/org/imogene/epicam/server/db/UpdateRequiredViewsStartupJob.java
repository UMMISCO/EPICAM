package org.imogene.epicam.server.db;

import java.io.InputStream;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.imogene.web.server.startup.StartupManager.StartupJob;
import org.springframework.transaction.annotation.Transactional;

public class UpdateRequiredViewsStartupJob implements StartupJob {

	@PersistenceContext
	protected EntityManager em;

	@Override
	@Transactional
	public void run() {
		String sql = convertStreamToString(UpdateRequiredViewsStartupJob.class.getResourceAsStream("create_view_lottotal.sql"));
		try {
			em.createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String convertStreamToString(InputStream is) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(is).useDelimiter("\\A");
			return scanner.hasNext() ? scanner.next() : "";
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

}
