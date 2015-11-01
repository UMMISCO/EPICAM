package org.imogene.epicam.client.ui.util;

import org.imogene.epicam.shared.proxy.CentreDiagTraitProxy;
import org.imogene.epicam.shared.proxy.DistrictSanteProxy;
import org.imogene.epicam.shared.proxy.RegionProxy;

/**
 * 
 * @author MEDES-IMPS
 *
 */
public class CommonFieldUtil {
	
	private static CommonFieldUtil instance = new CommonFieldUtil();
	
	
	private RegionProxy region = null;
	
	private DistrictSanteProxy district = null;
	
	private CentreDiagTraitProxy cdt = null;
	
	
	public CommonFieldUtil() {

	}
	
	
	public static CommonFieldUtil get() {
		return instance;
	}

	public RegionProxy getRegion() {
		return region;
	}

	public void setRegion(RegionProxy region) {
		this.region = region;
	}

	public DistrictSanteProxy getDistrict() {
		return district;
	}

	public void setDistrict(DistrictSanteProxy district) {
		this.district = district;
	}

	public CentreDiagTraitProxy getCdt() {
		return cdt;
	}

	public void setCdt(CentreDiagTraitProxy cdt) {
		this.cdt = cdt;
	}
	
	
	
	
	
	
	

}
