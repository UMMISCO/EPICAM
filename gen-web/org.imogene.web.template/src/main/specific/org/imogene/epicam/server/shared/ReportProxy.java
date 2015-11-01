package org.imogene.epicam.server.shared;

import java.util.Date;

import javax.persistence.Entity;

import org.imogene.epicam.domain.entity.OutBox;
import org.imogene.epicam.server.birtPojo.Report;
import org.imogene.epicam.server.locator.OutBoxLocator;
import org.imogene.epicam.server.locator.RapportLocator;
import org.imogene.epicam.shared.proxy.PatientProxy;
import org.imogene.web.shared.proxy.ImogBeanProxy;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;


@ProxyFor(value = Report.class, locator = RapportLocator.class)
public interface ReportProxy extends EntityProxy{
	
	/*Déclarer toutes les méthodes utilisées dans reportHandler ici */

	public String getNom();

	public void setNom(String nom) ;

	public int getVersion() ;

	public void setVersion(int version);	
	
}
