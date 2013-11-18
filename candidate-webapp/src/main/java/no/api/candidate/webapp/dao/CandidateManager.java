package no.api.candidate.webapp.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import no.api.candidate.webapp.model.CandidateModel;

@Service
public class CandidateManager {
    
    private static final Logger log = LoggerFactory.getLogger(CandidateManager.class);
    
    @Autowired
    private CandidateDAO candidateDAO;
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public CandidateModel save(CandidateModel candidate){
        log.debug("Saving candidate: "+candidate.getName());
       return candidateDAO.save(candidate);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public CandidateModel load(Long id){
        log.debug("Loading candidate from database id: "+id);
        return candidateDAO.load(id);
    }
 
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public CandidateModel loadByUuid(String uuid) {
        log.debug("Loading candidate from database uuid: "+uuid);
        return candidateDAO.loadByUuid(uuid);
    }
}
