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

import com.baozistore.api.model.Cliente;
import com.baozistore.api.repository.ClienteRepository;

@RestController()
@RequestMapping("/clientes")
public class ClienteController {

  private final ClienteRepository repository;

  ClienteController(ClienteRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public List<Cliente> listar() {
    return repository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
    return repository.findById(id)
        .map(cliente -> ResponseEntity.ok(cliente))
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public Cliente cadastrar(@RequestBody Cliente cliente) {
    return repository.save(cliente);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void excluir(@PathVariable Long id) {
    repository.deleteById(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
    if (!repository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }

    clienteAtualizado.setId(id);

    Cliente clienteSalvo = repository.save(clienteAtualizado);

    return ResponseEntity.ok(clienteSalvo);
  }

}
