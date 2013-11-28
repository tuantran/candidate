package no.api.candidate.webapp.controller.rest;

import java.io.IOException;

import javax.xml.bind.DatatypeConverter;

import com.wordnik.swagger.annotations.ApiOperation;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.wordnik.swagger.annotations.Api;

@Controller
@Api(value="", description="Candidate Service Operation")
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

    @RequestMapping(value="/candidates",method = RequestMethod.POST, produces="application/json", consumes="application/json")
    @ApiOperation(value = "CREATE a new candidate with JSON format")
    @ResponseBody
    public String saveCandidateFromJSON(@RequestBody CandidateTransport candidateTransport) throws IOException {
    	log.info("Saving candidate: {}", candidateTransport.getName());
        CandidateModel candidateModel = candidateTransportMapper.toCandidateModel(candidateTransport);
        String json= "";
        if (candidateModel.getAge()==null || candidateModel.getName()==null || candidateModel.getGender()==null) {
            candidateTransport.setStatus(-1);
            candidateTransport.setErrorMessage("name, age, and gender can not be empty");
            candidateTransport.setErrorCode(-1);
            json = candidateTransportMapper.convertToJsonString(candidateTransport);
            return json;
        }
        candidateModel = candidateManager.save(candidateModel);
        candidateTransport = candidateTransportMapper.fromCandidateModel(candidateModel);
        json = candidateTransportMapper.convertToJsonString(candidateTransport);


        return json;
    }

    @RequestMapping(value="/candidates",method = RequestMethod.POST, produces="application/json", consumes="application/xml")
    @ApiOperation(value = "CREATE a new candidate with XML format")
    @ResponseBody
    public String saveCandidateFromXML(@RequestBody CandidateTransport candidateTransport) throws IOException {
        log.info("Saving candidate: {}", candidateTransport.getName());
        CandidateModel candidateModel = candidateTransportMapper.toCandidateModel(candidateTransport);
        if (candidateModel.getAge()==null || candidateModel.getName()==null || candidateModel.getGender()==null) {
            candidateTransport.setStatus(-1);
            candidateTransport.setErrorMessage("name, age, and gender can not be empty");
            candidateTransport.setErrorCode(-1);
            return candidateTransportMapper.convertToJsonString(candidateTransport);
        }
        candidateModel = candidateManager.save(candidateModel);
        candidateTransport = candidateTransportMapper.fromCandidateModel(candidateModel);
        String json=  candidateTransportMapper.convertToJsonString(candidateTransport);
        return json;
    }




    @RequestMapping(value="/candidates/{uuid}",method = RequestMethod.GET, produces="application/json")
    @ApiOperation(value = "READ candidate in JSON format by using UUID")
    @ResponseBody
    public String loadCandidate(@PathVariable String uuid) throws IOException {
        CandidateModel candidateModel = candidateManager.loadByUuid(uuid);
        CandidateTransport candidateTransport = new CandidateTransport();
        String json= "";
        if (candidateModel==null) {
            candidateTransport.setStatus(-1);
            candidateTransport.setErrorCode(-2);
            candidateTransport.setErrorMessage("Candidate does not exist");
            json = candidateTransportMapper.convertToJsonString(candidateTransport);
            return json;
        }
        candidateTransport = candidateTransportMapper.fromCandidateModel(candidateModel);
        json = candidateTransportMapper.convertToJsonString(candidateTransport);


        return json;
    }

    @RequestMapping(value="/candidates/{uuid}",method = RequestMethod.GET, produces="application/xml")
    @ApiOperation(value = "READ candidate in XML format by using UUID")
    @ResponseBody
    public String loadXMLCandidate(@PathVariable String uuid) throws IOException {
        CandidateModel candidateModel = candidateManager.loadByUuid(uuid);
        CandidateTransport candidateTransport = new CandidateTransport();
        String xml= "";
        if (candidateModel==null) {
            candidateTransport.setStatus(-1);
            candidateTransport.setErrorCode(-2);
            candidateTransport.setErrorMessage("Candidate does not exist");
            xml = candidateTransportMapper.convertToXML(candidateTransport);
            return xml;
        }
        candidateTransport = candidateTransportMapper.fromCandidateModel(candidateModel);
        xml = candidateTransportMapper.convertToXML(candidateTransport);

        return xml;
    }

    @RequestMapping(value="/candidates/{uuid}",method = RequestMethod.DELETE,produces="application/json")
    @ApiOperation(value = "DELETE candidate by using UUID")
    @ResponseBody
    public String deleteCandidate(@PathVariable String uuid) throws IOException {

        String deletedUuid = candidateManager.deleteByUuid(uuid);

        CandidateModel candidateModel = candidateManager.loadByUuid(deletedUuid);
        CandidateTransport candidateTransport = new CandidateTransport();
        String json= "";
        if (candidateModel==null) {
            candidateTransport.setStatus(-1);
            candidateTransport.setErrorCode(-2);
            candidateTransport.setErrorMessage("Candidate does not exist");
            json = candidateTransportMapper.convertToJsonString(candidateTransport);
            return json;
        }
        candidateTransport = candidateTransportMapper.fromCandidateModel(candidateModel);
        json = candidateTransportMapper.convertToJsonString(candidateTransport);

        return json;
    }


    /*
    @RequestMapping(value="/list",method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public String loadAllCandidate() {

        List<CandidateModel> candidates = new ArrayList<CandidateModel>();
        candidates = candidateManager.loadAll();
        String json= "";
        try {
            json=candidateTransportMapper.convertToJsonListString(candidates);
        } catch (IOException e) {
            log.error(""+e);
        }

        return json;
    }
    */

    @RequestMapping(value="/candidates/{uuid}",params ={"fields"},method=RequestMethod.GET,produces="application/json")
    @ApiOperation(value = "READ partial responses of candidate in JSON format by using UUID")
    @ResponseBody
    public String loadCandidateFilter(@PathVariable String uuid,@RequestParam("fields") String fields) throws IOException {
        CandidateModel candidateModel = candidateManager.loadByUuid(uuid);
        CandidateTransport candidateTransport = new CandidateTransport();
        String json= "";
        if (candidateModel==null) {
            candidateTransport.setStatus(-1);
            candidateTransport.setErrorCode(-2);
            candidateTransport.setErrorMessage("Candidate does not exist");
            json = candidateTransportMapper.convertToJsonString(candidateTransport);
            return json;
        }
        candidateTransport = candidateTransportMapper.fromCandidateModel(candidateModel);
        json = candidateTransportMapper.filterJSONFromFields(candidateTransport,fields);


        return json;
    }

    @RequestMapping(value="/candidates/{uuid}",params ={"fields"},method=RequestMethod.GET,produces="application/xml")
    @ApiOperation(value = "READ partial responses of candidate in XML format by using UUID")
    @ResponseBody
    public String loadXMLCandidateFilter(@PathVariable String uuid,@RequestParam("fields") String fields) throws IOException {
        CandidateModel candidateModel = candidateManager.loadByUuid(uuid);
        CandidateTransport candidateTransport = new CandidateTransport();
        String xml= "";
        if (candidateModel==null) {
            candidateTransport.setStatus(-1);
            candidateTransport.setErrorCode(-2);
            candidateTransport.setErrorMessage("Candidate does not exist");
            xml = candidateTransportMapper.convertToXML(candidateTransport);
            return xml;
        }
        candidateTransport = candidateTransportMapper.fromCandidateModel(candidateModel);
        xml = candidateTransportMapper.filterXMLFromFields(candidateTransport,fields);

        return xml;
    }

    @RequestMapping(value="/candidates/{uuid}/image",method = RequestMethod.PUT, produces="application/json", consumes="application/json")
    @ApiOperation(value = "UPDATE candidate image")
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
