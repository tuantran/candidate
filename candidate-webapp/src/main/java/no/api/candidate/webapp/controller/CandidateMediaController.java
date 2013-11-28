package no.api.candidate.webapp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import no.api.candidate.webapp.dao.CandidateManager;
import no.api.candidate.webapp.dao.CandidateMediaManager;
import no.api.candidate.webapp.model.CandidateMediaModel;
import no.api.candidate.webapp.model.CandidateModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Api(value="", description="View Candidate Photo")
public class CandidateMediaController {

	@Autowired
	private CandidateMediaManager candidateMediaManager;
	
	@Autowired
	private CandidateManager candidateManager;
	
	@RequestMapping("/candidates/{uuid}/image/{lastModifiedTime}")
    @ApiOperation(value = "View candidate image")
	public String loadMedia(HttpServletResponse response,
			@PathVariable String uuid, @PathVariable Long lastModifiedTime) throws IOException {
		CandidateModel candidate = candidateManager.loadByUuid(uuid);
		if (candidate == null) 
			return null;
		CandidateMediaModel media = candidateMediaManager.loadByCandidateId(candidate.getId());
		if (media==null)
			return null;
		response.setContentType("image/png");
		response.getOutputStream().write(media.getMedia());
		response.getOutputStream().flush();
		return null;
	}
}
