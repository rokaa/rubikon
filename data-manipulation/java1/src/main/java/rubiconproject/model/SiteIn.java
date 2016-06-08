package rubiconproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "site_id", "name", "mobile", "score" })
public class SiteIn extends Site{
	@JsonProperty("site_id")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "SiteIn [id=" + id + ", getName()=" + getName() + ", getScore()=" + getScore() + ", getMobile()="
				+ getMobile() + "]";
	}
	
	
	
}
