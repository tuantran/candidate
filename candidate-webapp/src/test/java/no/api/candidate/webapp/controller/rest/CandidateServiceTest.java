package no.api.candidate.webapp.controller.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import no.api.candidate.webapp.dao.CandidateManagerTestTool;
import no.api.candidate.webapp.model.CandidateModel;
import no.api.candidate.webapp.transport.CandidateMediaTransport;
import no.api.candidate.webapp.transport.CandidateTransport;
import no.api.candidate.webapp.transport.mapper.CandidateTransportMapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import org.codehaus.jackson.map.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/candidate-jdbc-test.xml",
		"classpath:/candidate-applicationContext-test.xml" })
@WebAppConfiguration
public class CandidateServiceTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Autowired
	private CandidateTransportMapper candidateTransportMapper;

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}
	@Test
	public void candidateRestTest() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        CandidateModel candidate = CandidateManagerTestTool
                .createCandidate(null);
        CandidateTransport c = candidateTransportMapper
                .fromCandidateModel(candidate);

        // save json to rest
        String cjson = candidateTransportMapper.convertToJsonString(c);
        System.out.println("JSON input:"+cjson);
        MvcResult result = mockMvc
                .perform(
                        post("/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(cjson)).andDo(print()) // print the
                        // request/response
                        // in the
                        // console
                .andReturn();

        String json = result.getResponse().getContentAsString();
        CandidateTransport c1 = mapper
                .readValue(json, CandidateTransport.class);
        Assert.assertNotNull(c1.getId());

        // save xml to rest
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("candidateTransport",CandidateTransport.class);

        String xml ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +xstream.toXML(c);
        System.out.println("XML input:\n"+xml);


        result = mockMvc
                .perform(
                        post("/save")
                                .contentType(MediaType.APPLICATION_XML)
                                .content(xml.getBytes())).andDo(print()) // print the
                        // request/response
                        // in the
                        // console
                .andReturn();


        json = result.getResponse().getContentAsString();
        c1 = mapper.readValue(json, CandidateTransport.class);
        Assert.assertNotNull(c1.getId());


		// load json from rest
		result = mockMvc
				.perform(
						get(
								"/load/json/" + c1.getUuid()).accept(
								MediaType.APPLICATION_JSON)).andDo(print())
				.andReturn();
		json = result.getResponse().getContentAsString();

        CandidateTransport c2 = mapper
				.readValue(json, CandidateTransport.class);
		Assert.assertEquals(c1.getUuid(), c2.getUuid());

        // load json with partial responses from rest
        //to include specific fields
        result = mockMvc
                .perform(
                        get(
                                "/query/json/" + c1.getUuid()+"?fields=(name,age)").accept(
                                MediaType.APPLICATION_JSON)).andDo(print())
                .andReturn();
        json = result.getResponse().getContentAsString();

        CandidateTransport c23 = mapper
                .readValue(json, CandidateTransport.class);
        Assert.assertEquals(c1.getName(), c23.getName());
        Assert.assertEquals(c1.getAge(), c23.getAge());
        Assert.assertNull(c23.getUuid());

        //to exclude specific fields
        result = mockMvc
                .perform(
                        get(
                                "/query/json/" + c1.getUuid()+"?fields=!(name,age)").accept(
                                MediaType.APPLICATION_JSON)).andDo(print())
                .andReturn();
        json = result.getResponse().getContentAsString();

        CandidateTransport c24 = mapper
                .readValue(json, CandidateTransport.class);
        Assert.assertEquals(c1.getUuid(), c24.getUuid());
        Assert.assertNull(c24.getName());
        Assert.assertNull(c24.getAge());

        // load xml from rest
        result = mockMvc
                .perform(
                        get(
                                "/load/xml/" + c1.getUuid()).accept(
                                MediaType.APPLICATION_XML)).andDo(print())
                .andReturn();
        xml = result.getResponse().getContentAsString();

        CandidateTransport c22 = (CandidateTransport)xstream.fromXML(xml);
        Assert.assertEquals(c1.getUuid(), c22.getUuid());


        // load xml with partial responses from rest
        //to include specific fields
        result = mockMvc
                .perform(
                        get(
                                "/query/xml/" + c1.getUuid()+"?fields=(name,age)").accept(
                                MediaType.APPLICATION_XML)).andDo(print())
                .andReturn();
        xml = result.getResponse().getContentAsString();

        CandidateTransport c25 = (CandidateTransport)xstream.fromXML(xml);
        Assert.assertEquals(c1.getName(), c25.getName());
        Assert.assertEquals(c1.getAge(), c25.getAge());
        Assert.assertNull(c25.getUuid());

        //to exclude specific fields
        result = mockMvc
                .perform(
                        get(
                                "/query/xml/" + c1.getUuid()+"?fields=!(name,age)").accept(
                                MediaType.APPLICATION_XML)).andDo(print())
                .andReturn();
        xml = result.getResponse().getContentAsString();

        CandidateTransport c26 = (CandidateTransport)xstream.fromXML(xml);
        Assert.assertEquals(c1.getUuid(), c26.getUuid());
        Assert.assertNull(c26.getName());
        Assert.assertNull(c26.getAge());



        // delete from rest by uuid
        result = mockMvc
                .perform(
                        get(
                                "/delete/" + c1.getUuid()).accept(
                                MediaType.APPLICATION_JSON)).andDo(print())
                .andReturn();
        json = result.getResponse().getContentAsString();

        CandidateTransport c27 = mapper
                .readValue(json, CandidateTransport.class);
        Assert.assertNull(c27.getUuid());


		// garbage to json rest
		c = new CandidateTransport();
        cjson = candidateTransportMapper.convertToJsonString(c);
		//cjson = mapper.writeValueAsString(c);
		result = mockMvc
				.perform(
						post("/save")
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.content(cjson)).andDo(print()).andReturn();
		json = result.getResponse().getContentAsString();

        CandidateTransport c3 = mapper
				.readValue(json, CandidateTransport.class);
		Assert.assertEquals("-1", c3.getStatus().toString());


        // garbage to xml rest
        c = new CandidateTransport();
        xstream = new XStream(new DomDriver());
        xstream.alias("candidateTransport",CandidateTransport.class);

        xml ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +xstream.toXML(c);
        System.out.println("XML input:\n"+xml);

        result = mockMvc
                .perform(
                        post("/save")
                                .contentType(MediaType.APPLICATION_XML)
                                .content(xml.getBytes())).andDo(print()) // print the
                        // request/response
                        // in the
                        // console
                .andReturn();

        json = result.getResponse().getContentAsString();

        CandidateTransport c4 = mapper
                .readValue(json, CandidateTransport.class);
        Assert.assertEquals("-1", c4.getStatus().toString());


        // put an image to rest
		CandidateMediaTransport mediaTransport = new CandidateMediaTransport();
		mediaTransport.setUuid(c1.getUuid());
		mediaTransport.setImageBase64("iVBORw0KGgoAAAANSUhEUgAAAGAAAAAQCAYAAADpunr5AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAvBJREFUWMPtWAFx4zAQdBkIgiAYwkEQBEEwBDFwGMQMWgYOA4dBzCBhoFfm92a2F8nfTKf+zn81s1PHkiVr97R3btf9u60vSAUzcCwYCtwOa/uC14JccNlpzW/THAi/bzxCCIEYV5Ay7PAegrXS5qiX9/Avj/f2xtPvYNoC1KIuQIT+OwlwKMiEvQnvC1ZaP31CgIRNy4Y1uIZdCeAaz2m/3yDcwgrgHubBps8Fw1+K+Dvht4IJYnSfEOACPJMnFuSHhGf5hDh4+Uz5JMPatP+IOS7ou8LirAARY3iecN+AFAQI4SgqJ5wOASL6J1wrARH9+lswZiA7STTv/fpEgns882ai32PMm1kvYH6vaxOhGZv7aFPyOdKvwJ3csSLoQqfsSOs5EtAZAXTeQGN/J2jaWCYi1RJOuFbShPpUmBOE6oEM0lYQ1ZG1JfSp7UVzEk70e6W5eewNGNCfjADLkx4dzP0jJeqah0fc13FjwwJZAJ3LY/yC+64mgMO1ErgaciL5tZKhBB3IxwP6ukZusRHPawiRPAGJ5jrgPWsC5A/4uICIWkUU6SRlc0J0Hu6/mCSfKaeoAInmCq0qiE9AAKEr/tro1ChX8ieyjYyTIER8bnj/lgAaBDcjlp7KriLA0ojanrw8U4laI1jMCajlFH1upGvB+mOlCoqV01IV4EyJ8EBETxUBhEkwCTURicmItCWAPutp7bMZezNipEoE5kapKRS9jvyeK5uRfHyu2FQwlVYkIeZGGeroG0TeVUVEwJkinvOAEnOiTWeDRPa0AkLE3yqnwAoQ6FkOCGfGPqzd8PBrxV7EENVj3AJiU6UKWnAvAleqgjqTVDnRspVF+p3py3y0tiCmCvF03VMlIwbe1PUOkMpc3R/uOXoXVxn7sHbjg2shUvTfEVr1RENeBPlh4wNOrcSbfKHzzkRwMN8WnvLHQGv9F02+6Mt3qFhKT4L/tC9uc6PkHX8E2KcdK2Wso3zy03b4r+uryTOXZ8n/BU9eWMEQdj6wAAAAAElFTkSuQmCC");
		cjson = mapper.writeValueAsString(mediaTransport);
		System.out.println(cjson);

		mockMvc.perform(
				put("/upload/image/"+c1.getUuid())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(cjson))
				.andDo(print());


	}


}
