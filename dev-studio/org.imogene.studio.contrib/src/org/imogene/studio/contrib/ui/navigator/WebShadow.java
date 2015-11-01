package org.imogene.studio.contrib.ui.navigator;

import org.eclipse.core.resources.IProject;
import org.imogene.studio.contrib.ImogeneStudioPlugin;

public class WebShadow extends AbstractShadow {

	public static final String TYPE = "web";

	public static final String NATURE = "org.imogene.nature.gen.web";

	public WebShadow(IProject parent) {
		super(parent, TYPE);
		setLabel("Web");
		setIcon(ImogeneStudioPlugin.getImageDescriptor("icons/gwtLogo.png").createImage());
	}

	@Override
	public Object[] getChildren() {
		return getProjects(NATURE).toArray();
	}

	@Override
	public boolean hasChildren() {
		return true;
	}

}
