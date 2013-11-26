package no.api.candidate.webapp.dao;

import java.util.UUID;

import no.api.candidate.webapp.model.CandidateMediaModel;
import no.api.candidate.webapp.model.CandidateModel;


import org.joda.time.DateTime;
import org.mockito.Mockito;

public class CandidateManagerTestTool {


    public static CandidateModel createCandidate(Long id) {
        CandidateModel candidate = new CandidateModel();
        candidate.setAge(10);
        candidate.setCreatedTime(DateTime.now());
        candidate.setModifiedTime(DateTime.now());
        candidate.setGender("M");
        candidate.setName("Tester test");
        String uuid = UUID.randomUUID().toString();
        candidate.setUuid(uuid);
        candidate.setId(id);
        return candidate;
    }
    
    public static CandidateMediaModel createCandidateMedia(CandidateModel candidate) {
    	CandidateMediaModel candidateMedia = new CandidateMediaModel();
    	candidateMedia.setCandidateModel(candidate);
    	candidateMedia.setMedia("SOME PICTURE HERE".getBytes());
    	return candidateMedia;
    }

}
