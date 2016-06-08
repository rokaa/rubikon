package rubiconproject.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "name", "mobile", "keywords", "score" })
public class SiteOut extends Site{
	
	private String id;
	private String keywords;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	public void siteCopy(SiteIn s){
		this.setId(s.getId());
		this.setName(s.getName());
		this.setMobile(s.getMobile());
		this.setScore(s.getScore());
	}
	
	/**
	 * Copy properties from array
	 * @param props of format - id, name, mobile, score
	 */
	public void arrayCopy(String[] props){
		this.setId(props[0]);
		this.setName(props[1]);
		if (props[2].equals("true")){
			this.setMobile(1);
		} else{
			this.setMobile(0);
		}
		this.setScore(Integer.parseInt(props[3]));
	}

	@Override
	public String toString() {
		return "SiteOut [id=" + id + ", keywords=" + keywords + ", getName()=" + getName() + ", getMobile()="
				+ getMobile() + ", getScore()=" + getScore() + "]";
	}
	
	

}
