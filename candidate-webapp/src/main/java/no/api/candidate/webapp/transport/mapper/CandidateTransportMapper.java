package no.api.candidate.webapp.transport.mapper;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import no.api.candidate.webapp.model.CandidateModel;
import no.api.candidate.webapp.transport.CandidateTransport;

@Component
public class CandidateTransportMapper {
    
    public CandidateModel toCandidateModel(CandidateTransport candidateTransport) {
        CandidateModel model = new CandidateModel();
        model.setId(candidateTransport.getId());
        model.setAge(candidateTransport.getAge());
        model.setCreatedTime(new DateTime(candidateTransport.getCreatedTime()));
        model.setGender(candidateTransport.getGender());
        model.setName(candidateTransport.getName());
        model.setUuid(candidateTransport.getUuid());
        model.setModifiedTime(new DateTime(candidateTransport.getModifiedTime()));
        return model;
    }

    public CandidateTransport fromCandidateModel(CandidateModel model) {
        CandidateTransport transport = new CandidateTransport();
        transport.setAge(model.getAge());
        transport.setCreatedTime(model.getCreatedTime().getMillis());
        transport.setId(model.getId());
        transport.setGender(model.getGender());
        transport.setName(model.getName());
        transport.setUuid(model.getUuid());
        transport.setModifiedTime(model.getModifiedTime().getMillis());
        return transport;
    }
}
