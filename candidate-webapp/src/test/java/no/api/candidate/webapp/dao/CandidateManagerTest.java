package no.api.candidate.webapp.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;

import no.api.candidate.webapp.model.CandidateModel;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CandidateManagerTest extends ATestDAOManager {

    @Autowired
    private CandidateManager candidateManager;

    @Test
    public void testCandidateDAO() {
        CandidateModel candidate = new CandidateModel();
        candidate.setAge(10);
        candidate.setGender("M");
        candidate.setName("Tester test");
        //save
        candidate = candidateManager.save(candidate);
        Assert.assertNotNull(candidate);
        Long id = candidate.getId();
        String uuid = candidate.getUuid();
       //load
        candidate = candidateManager.loadByUuid(uuid);
        Assert.assertEquals(id, candidate.getId());
        Assert.assertEquals("M", candidate.getGender());
        Assert.assertEquals("Tester test", candidate.getName());
        Assert.assertEquals("10", candidate.getAge().toString());
        Assert.assertNotNull(candidate.getCreatedTime());
        Assert.assertNotNull(candidate.getModifiedTime());


        candidate.setGender("F");
        candidate=candidateManager.save(candidate);
        Assert.assertNotEquals("M", candidate.getGender());
        Assert.assertEquals("Tester test", candidate.getName());
        Assert.assertEquals("10", candidate.getAge().toString());
        Assert.assertEquals(uuid,candidate.getUuid());

        //delete
        String deletedUuid=candidateManager.deleteByUuid(candidate.getUuid());
        candidate = candidateManager.loadByUuid(deletedUuid);
        Assert.assertNull(candidate);
        System.out.println("candidate: "+candidate);

        //load all
     //   List <CandidateModel>candidates=new ArrayList<CandidateModel>();
     //   candidates= candidateManager.loadAll();

    //    System.out.println("candidate id:"+candidates.get(0).getId());


    }

}
