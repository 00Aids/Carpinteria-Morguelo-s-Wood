package com.CarpinteriaSpringBoot.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.CarpinteriaSpringBoot.app.model.Cliente;
import com.CarpinteriaSpringBoot.app.repository.ClienteRepository;
import com.CarpinteriaSpringBoot.app.repository.ProyectoRepository;

import jakarta.validation.Valid;
@Controller
@RequestMapping("/clientes")
public class ClienteWebController {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    @GetMapping
    public String listarClientes(@RequestParam(value = "accion", required = false) String accion, Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("accion", accion); 
        return "clientes";
    }

    @GetMapping("/nuevo")
    public String nuevoCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        // Obtener la lista de proyectos y agregarla al modelo
        model.addAttribute("proyectos", proyectoRepository.findAll());
        return "cliente_form";
    }

    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente) {
        clienteRepository.save(cliente);
        return "redirect:/clientes";  // Redirige a la lista de clientes después de guardar
    }

    @GetMapping("/editar/{id}")
    public String editarCliente(@PathVariable String id, Model model) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        model.addAttribute("cliente", cliente);
        // Obtener la lista de proyectos y agregarla al modelo
        model.addAttribute("proyectos", proyectoRepository.findAll());
        return "cliente_form";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarCliente(@PathVariable String id, @Valid @ModelAttribute Cliente cliente, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Si hay errores, volver a cargar la lista de proyectos
            model.addAttribute("proyectos", proyectoRepository.findAll());
            return "cliente_form";
        }
        cliente.setId(id);
        clienteRepository.save(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable String id) {
        clienteRepository.deleteById(id);
        return "redirect:/clientes";
    }
    
    // Agregar la funcionalidad para guardar una nota
    @PostMapping("/guardarNota/{id}")
    public String guardarNota(@PathVariable String id, @RequestParam("nota") String nota) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        cliente.setNota(nota);
        clienteRepository.save(cliente);
        return "redirect:/indexmecanico";
    }

    // Funcionalidad para buscar cliente por nombre
    @GetMapping("/buscar")
    public String buscarCliente(@RequestParam("nombre") String nombre, Model model) {
        Cliente cliente = clienteRepository.findByNombre(nombre); // Asumimos que tienes un método findByNombre
        if (cliente != null) {
            model.addAttribute("cliente", cliente);
            return "cliente_detalle";
        } else {
            model.addAttribute("mensaje", "Cliente no encontrado");
            return "clientes";
        }
    }
}
