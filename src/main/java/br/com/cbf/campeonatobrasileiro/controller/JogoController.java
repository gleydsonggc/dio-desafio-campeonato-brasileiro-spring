package br.com.cbf.campeonatobrasileiro.controller;

import br.com.cbf.campeonatobrasileiro.dto.ClassificacaoDTO;
import br.com.cbf.campeonatobrasileiro.dto.JogoDTO;
import br.com.cbf.campeonatobrasileiro.service.JogoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/jogos")
@RequiredArgsConstructor
public class JogoController {

    private final JogoService jogoService;

    @ApiOperation(value = "Gera os jogos do campeonato!")
    @PostMapping
    public ResponseEntity<Void> gerarJogos() {
        jogoService.gerarJogos(LocalDateTime.now(), Arrays.asList());
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Retorna um jogo específico")
    @GetMapping(value = "/{id}")
    public ResponseEntity<JogoDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(jogoService.findById(id));
    }

    @ApiOperation(value = "Retorna todos os jogos")
    @GetMapping
    public ResponseEntity<List<JogoDTO>> findAll() {
        return ResponseEntity.ok().body(jogoService.findAll());
    }

    @ApiOperation(value = "Retorna a classificação")
    @GetMapping(value = "/classificacao")
    public ResponseEntity<ClassificacaoDTO> getClassificacao() {
        return ResponseEntity.ok().body(jogoService.getClassificacao());
    }

    @ApiOperation(value = "Finaliza um jogo")
    @PostMapping(value = "/finalizar/{id}")
    public ResponseEntity<Void> finalizarJogo(@PathVariable Integer id, @RequestBody JogoDTO jogoDTO) {
        jogoService.finalizarJogo(id, jogoDTO);
        return ResponseEntity.ok().build();
    }


}
