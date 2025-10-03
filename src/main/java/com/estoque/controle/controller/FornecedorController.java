package com.estoque.controle.controller;

import com.estoque.controle.model.Fornecedor;
import com.estoque.controle.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping
    public String listarFornecedores(Model model) {
        List<Fornecedor> lista = fornecedorRepository.findAll();
        model.addAttribute("fornecedores", lista);
        return "fornecedores/lista";
    }

    @GetMapping("/novo")
    public String novoFornecedorForm(Model model) {
        model.addAttribute("fornecedor", new Fornecedor());
        return "fornecedores/form";
    }

    @PostMapping("/salvar")
    public String salvarFornecedor(@ModelAttribute Fornecedor fornecedor) {
        fornecedorRepository.save(fornecedor);
        return "redirect:/fornecedores";
    }

    @GetMapping("/editar/{id}")
    public String editarFornecedorForm(@PathVariable Long id, Model model) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor inválido: " + id));
        model.addAttribute("fornecedor", fornecedor);
        return "fornecedores/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluirFornecedor(@PathVariable Long id) {
        fornecedorRepository.deleteById(id);
        return "redirect:/fornecedores";
    }
}