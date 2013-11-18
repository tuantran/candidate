package no.api.candidate.webapp.controller;

import no.api.candidate.webapp.dao.CandidateManager;
import no.api.candidate.webapp.model.CandidateModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CandidateAdminController {
	
	@Autowired
	private CandidateManager candidateManager;
	
	@RequestMapping(value="/view.html")
	public String viewCandidate(@RequestParam String uuid,Model model) {
		CandidateModel candidate = candidateManager.loadByUuid(uuid);
		model.addAttribute("candidate", candidate);
		return "view";
	}
}
