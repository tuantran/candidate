package no.api.candidate.webapp.model;

import org.joda.time.DateTime;

public class CandidateMediaModel {
	
	private Long id;
	private CandidateModel candidateModel;
	private byte [] media;
	private DateTime modifiedTime;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public byte[] getMedia() {
		return media;
	}
	public void setMedia(byte[] media) {
		this.media = media;
	}
	public DateTime getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(DateTime modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public CandidateModel getCandidateModel() {
		return candidateModel;
	}
	public void setCandidateModel(CandidateModel candidateModel) {
		this.candidateModel = candidateModel;
	}

}
