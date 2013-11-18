package no.api.candidate.webapp.controller.rest;

import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

import no.api.candidate.webapp.dao.CandidateManager;
import no.api.candidate.webapp.dao.CandidateMediaManager;
import no.api.candidate.webapp.model.CandidateMediaModel;
import no.api.candidate.webapp.model.CandidateModel;
import no.api.candidate.webapp.transport.CandidateMediaTransport;
import no.api.candidate.webapp.transport.CandidateTransport;
import no.api.candidate.webapp.transport.mapper.CandidateMediaTransportMapper;
import no.api.candidate.webapp.transport.mapper.CandidateTransportMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/rest/candidateService")
public class CandidateService {

	private static final Logger log = LoggerFactory.getLogger(CandidateService.class);
    @Autowired
    private CandidateTransportMapper candidateTransportMapper;
    
    @Autowired
    private CandidateManager candidateManager;
    
    @Autowired
    private CandidateMediaManager candidateMediaManager;
    
    @Autowired
    private CandidateMediaTransportMapper candidateMediaTransportMapper;

    @RequestMapping(value="/save.json",method = RequestMethod.POST, produces="application/json", consumes="application/json")
    @ResponseBody
    public CandidateTransport saveCandidate(@RequestBody CandidateTransport candidateTransport) {
    	log.info("Saving candidate: {}", candidateTransport.getName());
        CandidateModel candidateModel = candidateTransportMapper.toCandidateModel(candidateTransport);
        if (candidateModel.getAge()==null || candidateModel.getName()==null || candidateModel.getGender()==null) {
            candidateTransport.setStatus(-1);
            candidateTransport.setErrorMessage("name, age, and gender can not be empty");
            candidateTransport.setErrorCode(-1);
            return candidateTransport;
        }
        candidateModel = candidateManager.save(candidateModel);
        candidateTransport = candidateTransportMapper.fromCandidateModel(candidateModel);
        return candidateTransport;
    }

    @RequestMapping(value="/load/{uuid}.json",method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public CandidateTransport loadCandidate(@PathVariable String uuid) {
        CandidateModel candidateModel = candidateManager.loadByUuid(uuid);
        CandidateTransport candidateTransport = new CandidateTransport();
        if (candidateModel==null) {
            candidateTransport.setStatus(-1);
            candidateTransport.setErrorCode(-2);
            candidateTransport.setErrorMessage("Candidate does not exist");
            return candidateTransport;
        }
        candidateTransport = candidateTransportMapper.fromCandidateModel(candidateModel);
        return candidateTransport;
    }

    @RequestMapping(value="/upload/image/{uuid}.json",method = RequestMethod.PUT, produces="application/json", consumes="application/json")
    @ResponseBody
    public CandidateMediaTransport uploadImageCandidate(@PathVariable String uuid, @RequestBody CandidateMediaTransport candidateMediaTransport) {
    	if (candidateMediaTransport.getUuid()==null || candidateMediaTransport.getImageBase64()==null) {
    		candidateMediaTransport.setStatus(-1);
    		candidateMediaTransport.setErrorCode(-1);
    		candidateMediaTransport.setErrorMessage("uuid and imagebase64 can not be empty.");
    		return candidateMediaTransport;
    	}
    	CandidateModel candidateModel = candidateManager.loadByUuid(uuid);
        if (candidateModel==null) {
            candidateMediaTransport.setStatus(-1);
            candidateMediaTransport.setErrorCode(-2);
            candidateMediaTransport.setErrorMessage("Candidate does not exist");
            return candidateMediaTransport;
        }
        CandidateMediaModel candidateMediaModel = new CandidateMediaModel();
        candidateMediaModel.setCandidateModel(candidateModel);
        byte[] imgByte = DatatypeConverter.parseBase64Binary(candidateMediaTransport.getImageBase64());
        candidateMediaModel.setMedia(imgByte);
        candidateMediaModel = candidateMediaManager.save(candidateMediaModel);
        candidateModel = candidateManager.save(candidateModel);
        return candidateMediaTransportMapper.fromCandidateMediaModel(candidateMediaModel);
        
    }

}
