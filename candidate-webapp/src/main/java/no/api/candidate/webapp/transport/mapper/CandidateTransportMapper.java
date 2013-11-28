package no.api.candidate.webapp.transport.mapper;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import no.api.candidate.webapp.transport.BaseTransport;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import no.api.candidate.webapp.model.CandidateModel;
import no.api.candidate.webapp.transport.CandidateTransport;

import java.io.IOException;
import java.util.Arrays;

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


    public String filterJSONFromFields(CandidateTransport candidateTransport, String fields) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String[] selFieldNames;
        boolean excludeFields=false;
        if(fields.startsWith("!")){
            fields=fields.substring(1,fields.length());
            excludeFields=true;
        }
        fields=fields.replaceAll(".*\\(|\\).*", "");
        selFieldNames=fields.split("\\,");
        FilterProvider filters;
        if(excludeFields){
            filters = new SimpleFilterProvider().addFilter("candidateFilter", SimpleBeanPropertyFilter.serializeAllExcept(selFieldNames));
        }else{
            filters = new SimpleFilterProvider().addFilter("candidateFilter", SimpleBeanPropertyFilter.filterOutAllExcept(selFieldNames));
        }

        mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
        ObjectWriter writer = mapper.writer(filters);
        String jsonStr="";
        jsonStr=writer.writeValueAsString(candidateTransport);

        return jsonStr;
    }

    public String filterXMLFromFields(CandidateTransport candidateTransport, String fields) throws IOException {

        String[] selFieldNames;
        boolean excludeFields=false;
        if(fields.startsWith("!")){
            fields=fields.substring(1,fields.length());
            excludeFields=true;
        }
        fields=fields.replaceAll(".*\\(|\\).*", "");
        selFieldNames=fields.split("\\,");

        XStream xstream = new XStream(new DomDriver());
        xstream.alias("candidateTransport",CandidateTransport.class);
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
        if(excludeFields){
            for(int i=0;i<selFieldNames.length;i++){
                xstream.omitField(CandidateTransport.class,selFieldNames[i]);
            }

        }else{
            String[] fieldNames={"status","errorMessage","errorCode","id", "uuid", "name", "age", "gender", "createdTime", "modifiedTime"};
            for(int i=0;i<fieldNames.length;i++){
                if(!Arrays.asList(selFieldNames).contains(fieldNames[i])){
                    xstream.omitField(BaseTransport.class,fieldNames[i]);
                    xstream.omitField(CandidateTransport.class,fieldNames[i]);
                }
            }
        }
        xml+=xstream.toXML(candidateTransport);
        return xml;
    }


    public String convertToJsonString(CandidateTransport candidateTransport) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String[] ignorableFieldNames = {""};
        FilterProvider filters = new SimpleFilterProvider().addFilter("candidateFilter", SimpleBeanPropertyFilter.serializeAllExcept(ignorableFieldNames));
        mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
        ObjectWriter writer = mapper.writer(filters);
        String out_json="";
        out_json=writer.writeValueAsString(candidateTransport);

        return out_json;
    }

    public String convertToXML(CandidateTransport candidateTransport) throws IOException {
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("candidateTransport",CandidateTransport.class);
        String xml ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +xstream.toXML(candidateTransport);
        return xml;
    }




    /*
    public String convertToJsonListString(List<CandidateModel> candidates)throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        OutputStream out = new ByteArrayOutputStream();
        String out_json="";
        out_json=mapper.writeValueAsString(candidates);

        return out_json;
    }
    */




}
