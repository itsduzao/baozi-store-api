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

import com.baozistore.api.model.Pedido;
import com.baozistore.api.repository.PedidoRepository;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

  private final PedidoRepository repository;

  public PedidoController(PedidoRepository repository) {
    this.repository = repository;
  }

  @GetMapping()
  public List<Pedido> listar() {
    return repository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
    return repository.findById(id)
        .map((Pedido pedido) -> ResponseEntity.ok(pedido))
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping()
  @ResponseStatus(code = HttpStatus.CREATED)
  public Pedido postMethodName(@RequestBody Pedido pedido) {
    return repository.save(pedido);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void excluir(@PathVariable Long id) {
    repository.deleteById(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @RequestBody Pedido pedidoAtualizado) {
    if (!repository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }

    pedidoAtualizado.setId(id);

    Pedido pedidoSalvo = repository.save(pedidoAtualizado);

    return ResponseEntity.ok(pedidoSalvo);
  }

}
