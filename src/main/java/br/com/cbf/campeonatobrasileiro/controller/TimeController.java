package br.com.cbf.campeonatobrasileiro.controller;

import br.com.cbf.campeonatobrasileiro.dto.NovoTimeDTO;
import br.com.cbf.campeonatobrasileiro.dto.TimeDTO;
import br.com.cbf.campeonatobrasileiro.service.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/times")
@RequiredArgsConstructor
public class TimeController {

    private final TimeService timeService;

    @GetMapping
    public ResponseEntity<List<TimeDTO>> getTimes() {
        return ResponseEntity.ok().body(timeService.getTimes());
    }

    @PostMapping
    public ResponseEntity<TimeDTO> adicionarTime(@Valid @RequestBody NovoTimeDTO novoTimeDTO) {
        return ResponseEntity.ok().body(timeService.adicionarTime(novoTimeDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TimeDTO> atualizarTime(@RequestParam(value = "id", required = true) Integer id, @Valid @RequestBody NovoTimeDTO novoTimeDTO) {
        return ResponseEntity.ok().body(timeService.atualizarTime(id, novoTimeDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarTime(@PathVariable Integer id) {
        timeService.deletarTime(id);
        return ResponseEntity.ok().build();
    }

}
