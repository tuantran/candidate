package no.api.candidate.webapp.transport;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonFilter;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFilter("candidateFilter")

public class CandidateTransport extends BaseTransport {

    private Long id;

    private String uuid;

    private String name;

    private Integer age;

    private String gender;

    private Long createdTime;

    private Long modifiedTime;

    public CandidateTransport() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Long getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }
	public Long getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Long modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
    
}
