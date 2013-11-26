package no.api.candidate.webapp.dao;

import no.api.candidate.webapp.model.CandidateMediaModel;
import no.api.candidate.webapp.model.CandidateModel;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CandidateMediaManagerTest extends ATestDAOManager {

	@Autowired
	private CandidateMediaManager candidateMediaManager;
	
	@Autowired
	private CandidateManager candidateManager;

	@Test
	public void testCandidateMediaDAO() {
        CandidateModel candidate = new CandidateModel();
        candidate.setAge(10);
        candidate.setGender("M");
        candidate.setName("Tester test");
        //save Candidate
        candidate = candidateManager.save(candidate);
        // save CandidateMedia
        CandidateMediaModel candidateMedia = new CandidateMediaModel();
        candidateMedia.setCandidateModel(candidate);
        candidateMedia.setMedia("TEST".getBytes());
        candidateMedia=candidateMediaManager.save(candidateMedia);
        Assert.assertNotNull(candidateMedia.getModifiedTime());
        CandidateMediaModel cm = candidateMediaManager.loadByCandidateId(candidate.getId());
        Assert.assertNotNull(cm);
        Assert.assertEquals("TEST",new String(cm.getMedia()));
        cm.setMedia("TEST2".getBytes());
        cm = candidateMediaManager.save(cm);
        Assert.assertNotEquals("TEST",new String(cm.getMedia()));
	}

}
