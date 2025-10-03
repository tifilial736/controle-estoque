package com.estoque.controle.repository;

import com.estoque.controle.model.EntradaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EntradaEstoqueRepository extends JpaRepository<EntradaEstoque, Long> {

    // JPQL
    @Query("SELECT e FROM EntradaEstoque e WHERE e.dataEntrada > :data")
    List<EntradaEstoque> buscarEntradasDepoisDe(LocalDate data);

    @Query("SELECT e FROM EntradaEstoque e WHERE e.fornecedor.nome = :nomeFornecedor")
    List<EntradaEstoque> buscarPorFornecedor(String nomeFornecedor);

    // Native Query
    @Query(value = "SELECT * FROM entrada_estoque WHERE quantidade > ?1", nativeQuery = true)
    List<EntradaEstoque> buscarEntradasComQuantidadeMaiorQue(int quantidade);
}
