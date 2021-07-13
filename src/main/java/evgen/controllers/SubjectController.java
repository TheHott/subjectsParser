package evgen.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import evgen.dao.SubjectDAO;
import evgen.models.Subject;
//import javax.validation.Valid;
import evgen.parser.Parser;

@Controller
@RequestMapping("/subj")
public class SubjectController {

	private SubjectDAO subjectDAO;
	
	@Autowired
	public SubjectController(SubjectDAO subjectDAO) {
		this.subjectDAO = subjectDAO;
	}
	
	@GetMapping()
	public String index(Model model) {
		model.addAttribute("subjects", subjectDAO.index());
		return "subj/index";
	}
	
//	@GetMapping("/{id}")
//	public String show(@PathVariable("id") int id, Model model) {
//		model.addAttribute("subject", subjectDAO.show(id));
//		return "subj/show";
//	}
	
	@GetMapping("/new")
	public String newSubject(Model model) {
		model.addAttribute("subject", new Subject());
		return "subj/new";
	}
	
	@PostMapping()
	public String create(@RequestParam(name = "link") String link) throws MalformedURLException, IOException {  
		subjectDAO.delete_all();
		Parser parser = new Parser();
		ArrayList<Subject> subjects;
		subjects = parser.setSubjectsIntoClass(link);
		for(Subject subj : subjects)
			subjectDAO.add(subj);
		
		return "redirect:/subj";
	}
	
//	@GetMapping("/{id}/edit")
//	public String edit(Model model, @PathVariable("id") int id) {
//		model.addAttribute("Subject", subjectDAO.show(id));
//		return "subj/edit";
//	}
	
//	@PatchMapping("/{id}")
//	public String update(@ModelAttribute("Subject") @Valid Subject Subject, 
//			BindingResult bindingResult, @PathVariable("id") int id) {
//		if(bindingResult.hasErrors())
//			return "subj/edit";
//		
//		subjectDAO.update(id, Subject);
//		
//		return "redirect:/subj";
//	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		subjectDAO.delete_one(id);
		return "redirect:/subj";
	}
	
	@DeleteMapping("/all")
	public String delete() {
		subjectDAO.delete_all();
		return "redirect:/subj";
	}
	
}
