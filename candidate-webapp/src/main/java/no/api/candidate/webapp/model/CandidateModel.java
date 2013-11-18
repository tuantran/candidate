package no.api.candidate.webapp.model;

import java.io.Serializable;

import org.joda.time.DateTime;

public class CandidateModel implements Serializable {
    
    private Long id;
    private String uuid;
    private String name;
    private Integer age;
    private String gender;
    private DateTime createdTime;
    private DateTime modifiedTime;
    
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(DateTime createdTime) {
        this.createdTime = createdTime;
    }

	public DateTime getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(DateTime modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
    
}
