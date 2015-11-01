package org.imogene.epicam.server.locator;

import javax.servlet.http.HttpServletRequest;

import org.imogene.epicam.server.birtPojo.Report;
import org.imogene.epicam.server.handler.ReportHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.Locator;

public class RapportLocator extends Locator<Report, String> {

	private ReportHandler handler;
	
	public RapportLocator() {
	}

	@Override
	public Report create(Class<? extends Report> clazz) {
		return new Report();
	}

	@Override
	public Report find(Class<? extends Report> clazz, String id) {

//		if(handler == null)
//			initHandler();
//		return handler.getReportHandler();
		return null;
	}

	private void initHandler() {
		
		HttpServletRequest request = RequestFactoryServlet.getThreadLocalRequest();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		handler = (ReportHandler) context.getBean("reportHandler");
		
	}

	@Override
	public Class<Report> getDomainType() {
		// TODO Auto-generated method stub
		return Report.class;
	}

	@Override
	public String getId(Report domainObject) {
		return null;
	}

	@Override
	public Class<String> getIdType() {
		return null;
	}

	@Override
	public Object getVersion(Report domainObject) {
		// TODO Auto-generated method stub
		return domainObject.getVersion();
	}



}
