package com.project.consorcio.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.consorcio.entity.Medicamento;
import com.project.consorcio.entity.TipoMedicamento;
import com.project.consorcio.services.MedicamentoServices;
import com.project.consorcio.services.TipoMedicamentoServices;

//Anotacion que indica que la clase es un controlador, por lo tanto 
//permite recibir peticiones de los clientes y envia respuesta.
@Controller

@RequestMapping("/medicamento")
public class MedicamentoController {
	
	@Autowired
	private MedicamentoServices servicioMed;
	
	@Autowired
	private TipoMedicamentoServices servicioTipo;
	
	@RequestMapping("/lista")
	public String index(Model model) {
		
		model.addAttribute("medicamento",servicioMed.listarTodos());
		model.addAttribute("tipos", servicioTipo.listarTodos());
		
		
		return "medicamento";
	}
	
	@RequestMapping("/grabar")
	public String grabar(@RequestParam("codigo")Integer cod,
			             @RequestParam("nombre")String nom,
			             @RequestParam("descripcion")String des,
			             @RequestParam("stock")int stock,
			 			 @RequestParam("precio")double pre,
			 			 @RequestParam("fecha")String fec,
						 @RequestParam("tipo")int codTipo,
						RedirectAttributes redirect) {
		
		try {
			
			//Crear objeto de la entidad medicamento
			Medicamento med = new Medicamento();
			
			//Setear atributos del objeto med con los parametros
			med.setNombre(nom);
			med.setDescripcion(des);
			med.setStock(stock);
			med.setPrecio(pre);
			med.setFecha(LocalDate.parse(fec));
			
			//Crear un objeto de la entidad TipoMedicamento
			TipoMedicamento tm = new TipoMedicamento();
			
			//Setear atributo "codigo" del objeto "tm" con el parametro codTipo
			tm.setCodigo(codTipo);
			
			//Invocar al metodo setTipo y enviar el objeto "tm"
			med.setTipo(tm);
			
			if(cod == 0) {
				servicioMed.registrar(med);
				redirect.addFlashAttribute("MENSAJE","Medicamento Registrado");
			}
			else {
				med.setCodigo(cod);
				servicioMed.actualizar(med);
				redirect.addFlashAttribute("MENSAJE","Medicamento Actualizado");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		return "redirect:/medicamento/lista";
	}
	
	//Ruta url para buscar medicamento segun codigo
	@RequestMapping("/buscar")
	@ResponseBody
	public Medicamento buscar(@RequestParam("codigo") Integer cod) {
		return servicioMed.buscarPorID(cod);
	}
	
/*	@RequestMapping("/eliminar")
	public String eliminarPorCodigo(@RequestParam("codigoEliminar") Integer cod,
			                        RedirectAttributes redirect) {
		
		if(cod == 0) {
			redirect.addFlashAttribute("MENSAJE1","Medicamento no Existe");
			
		}
		else{
			servicioMed.eliminarPorID(cod);
			redirect.addFlashAttribute("MENSAJE","Medicamento Eliminado");
		}
		
		
		return "redirect:/medicamento/lista";
	}*/
	
	

}
