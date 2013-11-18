package no.api.candidate.webapp.dao;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import no.api.candidate.webapp.model.CandidateMediaModel;
import no.api.candidate.webapp.model.CandidateModel;

@Service
public class CandidateMediaManager {

	private static final Logger log = LoggerFactory
			.getLogger(CandidateMediaManager.class);

	@Autowired
	private CandidateMediaDAO candidateMediaDAO;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public CandidateMediaModel save(CandidateMediaModel candidateMedia) {
		log.debug("Saving candidate media for: "
				+ candidateMedia.getCandidateModel().getId());
		CandidateMediaModel cm = candidateMediaDAO
				.loadByCandidateId(candidateMedia.getCandidateModel().getId());
		if (cm != null) {
			cm.setMedia(candidateMedia.getMedia());
			return candidateMediaDAO.save(cm);
		}
		return candidateMediaDAO.save(candidateMedia);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public CandidateMediaModel load(Long id) {
		log.debug("Loading candidate media from database id: " + id);
		return candidateMediaDAO.load(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public CandidateMediaModel loadByCandidateId(Long candidateId) {
		return candidateMediaDAO.loadByCandidateId(candidateId);
	}
}
