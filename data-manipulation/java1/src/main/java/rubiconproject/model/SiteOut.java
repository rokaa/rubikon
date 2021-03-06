package rubiconproject.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import rubiconproject.KeywordServiceImpl;

@JsonPropertyOrder({ "id", "name", "mobile", "keywords", "score" })
public class SiteOut extends Site{
	
	private KeywordServiceImpl keyGen = new KeywordServiceImpl();
	
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
	 * Generate keywords
	 * @param o
	 * @return
	 */
	public String genKeywords(Object o){
		return keyGen.resolveKeywords(o);
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((keywords == null) ? 0 : keywords.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SiteOut other = (SiteOut) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (keywords == null) {
			if (other.keywords != null)
				return false;
		} else if (!keywords.equals(other.keywords))
			return false;
		return true;
	}
	
	
	
	

}
