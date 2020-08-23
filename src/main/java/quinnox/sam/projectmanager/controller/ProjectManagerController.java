package quinnox.sam.projectmanager.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import quinnox.sam.projectmanager.entity.Applied;
import quinnox.sam.projectmanager.entity.Project;
import quinnox.sam.projectmanager.entity.User;
import quinnox.sam.projectmanager.service.AppliedService;
import quinnox.sam.projectmanager.service.ProjectService;
import quinnox.sam.projectmanager.service.UserServices;



@Controller

public class ProjectManagerController {
	@Autowired
	private ProjectService service;
	@Autowired
	private UserServices userService;
	@Autowired
	private AppliedService appliedService;
	
	
	 @RequestMapping(value = "/registration", method = RequestMethod.GET)
	    public String registration(Model model) {
	        model.addAttribute("user", new User());

	        return "registration";
	    }

	    @RequestMapping(value = "/registration", method = RequestMethod.POST)
	    public String registration(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
	    	 User userExists = userService.getUserByUsername(user.getUsername());
	    	 if(userExists != null) {
	    		   bindingResult.rejectValue("username", "error.user", "This user already exists!");
	    		  }

	        if (bindingResult.hasErrors()) {
	            return "registration";
	        }

	        userService.save(user);

	        return "redirect:/registration?success";
	    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {

        return "login";
    }
	
	@RequestMapping("/")
	public String viewHomePage(Model model) {
	//	List<Project> listProject = service.listAll();
	//	model.addAttribute("listProject", listProject);
		return "index";
	}
	@RequestMapping("/view")
	public String viewProject(Model model)
	{
		List<Project> listProject = service.listAll();
		model.addAttribute("listProject", listProject);
			return "view_project";
		
	}
	@RequestMapping("/new")
	public String showNewProjectForm(Model model) {
		Project project = new Project();
		model.addAttribute("project", project);	
		return "new_project";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("project") Project project,Model model) {
		service.save(project);
		
		model.addAttribute("saveMessage", "Saved Successfully");
		return "redirect:/";
	}
	
	@RequestMapping(value="/editordeletebysearch", method = RequestMethod.GET)
    public String projectinfo(Model model){
		return "searchproject"; 
    }
	 
	@RequestMapping(value="/search", method = RequestMethod.GET)
	    public String findprojectByname(Model model, @RequestParam String name) {
	        List<Project> project = service.findprojectByname(name);
	        model.addAttribute("project", project);
	        return "search_to_edit";
	    }
	
	
	
	
	@RequestMapping("/exit")
	public String exitProject(Model model) {
		return "index";
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProjectForm(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edit_project");
		
		Project project = service.get(id);
		mav.addObject("project", project);
		
		return mav;
		
		
	}	
	
	@RequestMapping("/delete/{id}")
	public String deleteProject(@PathVariable(name = "id") Long id) {
		service.delete(id);
		
		return "redirect:/";
	}
	
	@RequestMapping("/applieddetails")
    public String projectapplied(Model model) {
        List<Applied> applied = appliedService.listAll();
        model.addAttribute("applied", applied);
        return "viewapplied";
    }
	
	@RequestMapping("/apply")
	public String applyproject(Model model){

		Applied applied = new Applied();
		model.addAttribute("applied", applied);	
		return "apply";
	}
	
	@RequestMapping(value = "/saveapplication", method = RequestMethod.POST)
	public String saveapplication(@ModelAttribute("applied") Applied applied,Model model) {
		appliedService.save(applied);
		return "redirect:/";
	}
	
	@RequestMapping(value="/searchapplication", method = RequestMethod.GET)
    public String showinfo(Model model){
		return "searchapplication"; 
    }
	 
	@RequestMapping(value="/searchbyname", method = RequestMethod.GET)
	    public String findappliedByprojectname(Model model, @RequestParam String projectname) {
	        List<Applied> applied = appliedService.findappliedByprojectname(projectname);
	        model.addAttribute("applied", applied);
	        return "view_applied";
	    }
	@RequestMapping("/exitapplied")
	public String exitApplied(Model model) {
		return "index";
	}
	

}
