package no.api.candidate.webapp.transport.mapper;

import org.springframework.stereotype.Component;

import no.api.candidate.webapp.model.CandidateMediaModel;

import no.api.candidate.webapp.transport.CandidateMediaTransport;
import javax.xml.bind.DatatypeConverter;

@Component
public class CandidateMediaTransportMapper {
    
    public CandidateMediaModel toCandidateMediaModel(CandidateMediaTransport transport) {
    	CandidateMediaModel model = new CandidateMediaModel();
    	model.setMedia(DatatypeConverter.parseBase64Binary(transport.getImageBase64()));
    	return model;
    }
	
    public CandidateMediaTransport fromCandidateMediaModel(CandidateMediaModel model) {
        CandidateMediaTransport transport = new CandidateMediaTransport();
        transport.setUuid(model.getCandidateModel().getUuid());
        transport.setImageBase64(DatatypeConverter.printBase64Binary(model.getMedia()));
        return transport;
    }
    
}
