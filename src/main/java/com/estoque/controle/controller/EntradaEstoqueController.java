package com.estoque.controle.controller;

import com.estoque.controle.model.EntradaEstoque;
import com.estoque.controle.repository.EntradaEstoqueRepository;
import com.estoque.controle.repository.ProdutoRepository;
import com.estoque.controle.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/entradas")
public class EntradaEstoqueController {

    @Autowired
    private EntradaEstoqueRepository entradaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping
    public String listarEntradas(Model model) {
        List<EntradaEstoque> entradas = entradaRepository.findAll();
        model.addAttribute("entradas", entradas);
        return "entradas/lista";
    }

    @GetMapping("/novo")
    public String novaEntradaForm(Model model) {
        model.addAttribute("entrada", new EntradaEstoque());
        model.addAttribute("produtos", produtoRepository.findAll());
        model.addAttribute("fornecedores", fornecedorRepository.findAll());
        return "entradas/form";
    }

    @PostMapping("/salvar")
    public String salvarEntrada(@ModelAttribute EntradaEstoque entrada,
                                @RequestParam Long produtoId,
                                @RequestParam Long fornecedorId) {
        entrada.setProduto(produtoRepository.findById(produtoId).orElseThrow());
        entrada.setFornecedor(fornecedorRepository.findById(fornecedorId).orElseThrow());

        if (entrada.getDataEntrada() == null) {
            entrada.setDataEntrada(LocalDate.now());
        }

        entradaRepository.save(entrada);
        return "redirect:/entradas";
    }

    @GetMapping("/editar/{id}")
    public String editarEntradaForm(@PathVariable Long id, Model model) {
        EntradaEstoque entrada = entradaRepository.findById(id).orElseThrow();
        model.addAttribute("entrada", entrada);
        model.addAttribute("produtos", produtoRepository.findAll());
        model.addAttribute("fornecedores", fornecedorRepository.findAll());
        return "entradas/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluirEntrada(@PathVariable Long id) {
        entradaRepository.deleteById(id);
        return "redirect:/entradas";
    }
}