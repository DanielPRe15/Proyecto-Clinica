package com.project.consorcio.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.consorcio.entity.Distrito;
import com.project.consorcio.entity.Especialidad;
import com.project.consorcio.entity.Medico;
import com.project.consorcio.entity.Sede;
import com.project.consorcio.services.DistritoServices;
import com.project.consorcio.services.EspecialidadServices;
import com.project.consorcio.services.MedicoServices;
import com.project.consorcio.services.SedeServices;

@Controller
@RequestMapping("/medicos")
public class MedicoController {

	@Autowired
	private MedicoServices servicioMedi;
	
	@Autowired
	private DistritoServices servicioDis;
	
	@Autowired
	private EspecialidadServices servicioEsp;
	
	@Autowired
	private SedeServices servicioSe;
	
	
	
	@RequestMapping("/lista")
	public String index(Model model) {
		
		model.addAttribute("medico",servicioMedi.listarTodos());
		model.addAttribute("distrito", servicioDis.listarTodos());
		model.addAttribute("Espec", servicioEsp.listarTodos());
		model.addAttribute("sede", servicioSe.listarTodos());
		
		return "medicos";
	}
	
	@RequestMapping("/grabar")
	public String grabar (@RequestParam("codigo") Integer cod,
						@RequestParam("nombre")String nom,
						@RequestParam("apellido")String ape,
						@RequestParam("fecha")String fec,
						@RequestParam("sexo")String sex,
						@RequestParam("estado")String est,
						@RequestParam("dni")String dni,
						@RequestParam("sueldo")double sue,
						@RequestParam("direccion")String dir,
						@RequestParam("especialidad")int codEspe,
						@RequestParam("sede")int codSede,
						@RequestParam("distrito")int codDistrito,
						RedirectAttributes redirect)
	{
		
		try {
			//crear objeto de la entidad medicamento
			Medico med = new Medico();
			//setear atrinutos 
			med.setNombre(nom);
			med.setApellido(ape);
			med.setFecha(LocalDate.parse(fec));
			med.setSexo(sex);
			med.setEstado(est);
			med.setDni(dni);
			med.setSueldo(sue);
			med.setDireccion(dir);
			
			//crear un objeto en la entidad tipoMedico
			Especialidad esp = new Especialidad();
			Sede sede = new Sede();
			Distrito dis = new Distrito();
			
			//setea atribuo "codigo" del objeto "espe"
			esp.setCodigo(codEspe);
			sede.setCodigo(codSede);
			dis.setCodigo(codDistrito);
			//invocar al metodo setSede
			med.setEspe(esp);
			med.setSede(sede);
			med.setDis(dis);
			
			//validar parametro
		
			if(cod == 0) {
				
				//Invocar metodo registrar
				servicioMedi.registrar(med);
				//crearatribut de tipo flash
				redirect.addFlashAttribute("MENSAJE","Medico registrado");
			}
			else {
				
				med.setCodigo(cod);
				servicioMedi.actualizar(med);
				redirect.addFlashAttribute("MENSAJE","Medico actualizado");
			}
					
			

			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return "redirect:/medicos/lista";
		}
		
		//ruta o direccion url para buscar medicamento segun codigo
		@RequestMapping("/buscar")
		@ResponseBody
		public Medico buscar(@RequestParam("codigo") Integer cod) {
			return servicioMedi.buscarPorID(cod);
		}
		
	}
