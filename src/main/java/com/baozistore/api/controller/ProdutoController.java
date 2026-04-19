package com.baozistore.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.baozistore.api.model.Produto;
import com.baozistore.api.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
  private final ProdutoRepository repository;

  public ProdutoController(ProdutoRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public List<Produto> listar() {
    return repository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
    return repository.findById(id)
        .map(produto -> ResponseEntity.ok(produto))
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public Produto cadastrar(@RequestBody Produto produto) {
    return repository.save(produto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void deletar(@PathVariable Long id) {
    repository.deleteById(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
    if (!repository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }

    produtoAtualizado.setId(id);

    Produto produtoSalvo = repository.save(produtoAtualizado);

    return ResponseEntity.ok(produtoSalvo);
  }
}
