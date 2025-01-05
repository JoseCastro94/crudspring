package com.edu.pe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.pe.models.Empleado;
import com.edu.pe.service.IEmpleadoService;

@Controller
public class EmpleadoController {

	@Autowired
	private IEmpleadoService empleadoService;
	
	@GetMapping("/")
	public String Inicio(Model model) {
		model.addAttribute("empleados", empleadoService.ListarTodos());
		return "listar";
	}
	
	@GetMapping("/nuevo")
	public String Nuevo(Model model) {
		model.addAttribute("empleado", new Empleado());
		return "nuevo";
	}
	
	@PostMapping("/guardar")
	public String Guardar(@ModelAttribute Empleado empleado, Model model, RedirectAttributes attributes) {
		boolean result = empleadoService.Guardar(empleado);
		
		if(result == false) {
			model.addAttribute("error", "No se pudo guardar datos");
			return "nuevo";
		}
		
		attributes.addFlashAttribute("success","Los datos se guardaron de forma correcta");
		return "redirect:/";
	}
	
	@GetMapping("/editar/{id}")
	public String Editar(@PathVariable("id") int id , Model model, RedirectAttributes attributes) {
		Empleado empleado = empleadoService.BuscarPorId(id);
		
		if(empleado != null) {
			model.addAttribute("empleado", empleado);
			return "nuevo";
		}
		
		attributes.addFlashAttribute("error", "No se encontró empleado con ID "+ id);
		return "redirect:/";
	}
	
	@GetMapping("/eliminar/{id}")
	public String Eliminar(@PathVariable("id") int id , Model model, RedirectAttributes attributes) {
		boolean result = empleadoService.Eliminar(id);
		
		if(result) {
			attributes.addFlashAttribute("success", "El empleado con ID "+ id+" fue eliminado de forma exitosa!");
		}else {
			attributes.addFlashAttribute("error", "No se pudo eliminar empleado con ID "+ id);
		}
		return "redirect:/";
	}
	
	@ModelAttribute
	public void CargarDatos(Model model) {
		String cargos[] = {
				"Analista Programador",
				"Analista QA",
				"Analista Funcional",
				"Jefe de Proyectos",
				"Lider de Proyectos",
				"Scrum Master"
		};
		model.addAttribute("cargos", cargos);
	}
}
