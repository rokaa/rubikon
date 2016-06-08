package rubiconproject.model;

import java.util.List;

public class SiteCollection {
	
	private String collectionId;
	private List<SiteOut> sites;
	
	public String getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}
	public List<SiteOut> getSites() {
		return sites;
	}
	public void setSites(List<SiteOut> sites) {
		this.sites = sites;
	}
	
	

}
