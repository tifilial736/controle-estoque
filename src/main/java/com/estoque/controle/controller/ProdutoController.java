package com.estoque.controle.controller;

import com.estoque.controle.model.Produto;
import com.estoque.controle.model.Fornecedor;
import com.estoque.controle.model.Categoria;
import com.estoque.controle.repository.ProdutoRepository;
import com.estoque.controle.repository.FornecedorRepository;
import com.estoque.controle.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public String listarProdutos(Model model) {
        List<Produto> produtos = produtoRepository.findAll();
        model.addAttribute("produtos", produtos);
        return "produtos/lista";
    }

    @GetMapping("/novo")
    public String novoProdutoForm(Model model) {
        model.addAttribute("produto", new Produto());
        carregarDadosForm(model);
        return "produtos/form";
    }

    @PostMapping("/salvar")
    public String salvarProduto(@ModelAttribute Produto produto,
                                @RequestParam Long fornecedorId,
                                @RequestParam Long categoriaId) {

        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor inválido"));
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("Categoria inválida"));

        produto.setFornecedor(fornecedor);
        produto.setCategoria(categoria);

        produtoRepository.save(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/editar/{id}")
    public String editarProdutoForm(@PathVariable Long id, Model model) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto inválido: " + id));
        model.addAttribute("produto", produto);
        carregarDadosForm(model);
        return "produtos/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluirProduto(@PathVariable Long id) {
        produtoRepository.deleteById(id);
        return "redirect:/produtos";
    }

    private void carregarDadosForm(Model model) {
        model.addAttribute("fornecedores", fornecedorRepository.findAll());
        model.addAttribute("categorias", categoriaRepository.findAll());
    }
}