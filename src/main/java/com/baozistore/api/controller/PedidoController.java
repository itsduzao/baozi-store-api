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

import com.baozistore.api.dto.PedidoRequestDTO;
import com.baozistore.api.dto.PedidoResponseDTO;
import com.baozistore.api.model.Cliente;
import com.baozistore.api.model.Pedido;
import com.baozistore.api.model.Produto;
import com.baozistore.api.repository.ClienteRepository;
import com.baozistore.api.repository.PedidoRepository;
import com.baozistore.api.repository.ProdutoRepository;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

  private final PedidoRepository pedidoRepository;
  private final ClienteRepository clienteRepository;
  private final ProdutoRepository produtoRepository;

  public PedidoController(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository,
      ClienteRepository clienteRepository) {
    this.pedidoRepository = pedidoRepository;
    this.produtoRepository = produtoRepository;
    this.clienteRepository = clienteRepository;
  }

  @GetMapping()
  public List<Pedido> listar() {
    return pedidoRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
    return pedidoRepository.findById(id)
        .map((Pedido pedido) -> ResponseEntity.ok(pedido))
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping()
  @ResponseStatus(code = HttpStatus.CREATED)
  public PedidoResponseDTO cadastrar(@RequestBody PedidoRequestDTO dto) {
    Cliente cliente = clienteRepository.findById(dto.getClienteId())
        .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

    Produto produto = produtoRepository.findById(dto.getProdutoId())
        .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

    Pedido novoPedido = new Pedido();
    novoPedido.setCliente(cliente);
    novoPedido.setProduto(produto);
    novoPedido.setQuantidade(dto.getQuantidade());

    Pedido pedidoSalvo = pedidoRepository.save(novoPedido);

    PedidoResponseDTO resposta = new PedidoResponseDTO();

    resposta.setIdPedido(pedidoSalvo.getId());
    resposta.setQuantidade(pedidoSalvo.getQuantidade());
    resposta.setNomeCliente(cliente.getNome());
    resposta.setNomeProduto(produto.getNome());

    return resposta;
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void excluir(@PathVariable Long id) {
    pedidoRepository.deleteById(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @RequestBody Pedido pedidoAtualizado) {
    if (!pedidoRepository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }

    pedidoAtualizado.setId(id);

    Pedido pedidoSalvo = pedidoRepository.save(pedidoAtualizado);

    return ResponseEntity.ok(pedidoSalvo);
  }

}
