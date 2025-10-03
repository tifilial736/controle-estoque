package com.estoque.controle.repository;

import com.estoque.controle.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Métodos básicos já estão implementados pelo JpaRepository:
    // findAll(), findById(), save(), deleteById(), etc.
}
