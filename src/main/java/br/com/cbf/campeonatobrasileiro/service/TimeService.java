package br.com.cbf.campeonatobrasileiro.service;

import br.com.cbf.campeonatobrasileiro.dto.NovoTimeDTO;
import br.com.cbf.campeonatobrasileiro.dto.TimeDTO;
import br.com.cbf.campeonatobrasileiro.entity.Time;
import br.com.cbf.campeonatobrasileiro.repository.TimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final TimeRepository timeRepository;

    public List<TimeDTO> getTimes() {
        return findAll().stream().map(this::entityToDto).toList();
    }

    public List<Time> findAll() {
        return timeRepository.findAll();
    }

    public TimeDTO entityToDto(Time entity) {
        TimeDTO timeDTO = new TimeDTO();
        timeDTO.setId(entity.getId());
        timeDTO.setNome(entity.getNome());
        timeDTO.setSigla(entity.getSigla());
        return timeDTO;
    }

    public TimeDTO adicionarTime(NovoTimeDTO novoTimeDTO) {
        if (timeJaExiste(novoTimeDTO.getNome(), 0)) {
            throw new RuntimeException(String.format("Time %s já existe.", novoTimeDTO.getNome()));
        }
        Time time = dtoToEntity(novoTimeDTO);
        return entityToDto(timeRepository.save(time));
    }

    public TimeDTO atualizarTime(Integer id, NovoTimeDTO novoTimeDTO) {
        if (timeJaExiste(novoTimeDTO.getNome(), id)) {
            throw new RuntimeException(String.format("Time %s já existe.", novoTimeDTO.getNome()));
        }
        final Optional<Time> optionalTime = timeRepository.findById(id);
        if (optionalTime.isPresent()) {
            final Time time = optionalTime.get();
            return entityToDto(timeRepository.save(dtoToEntity(novoTimeDTO, time)));
        } else {
            throw new RuntimeException(String.format("Time com id %d inexistente", id));
        }
    }

    private Boolean timeJaExiste(String nome, Integer id) {
        return timeRepository.findByNomeIgnoreCaseAndAndIdNot(nome, id).size() > 0;
    }

    public Time dtoToEntity(NovoTimeDTO novoTimeDTO) {
        Time time = new Time();
        return dtoToEntity(novoTimeDTO, time);
    }

    private Time dtoToEntity(NovoTimeDTO novoTimeDTO, Time time) {
        time.setEstado(novoTimeDTO.getEstado());
        time.setNome(novoTimeDTO.getNome());
        time.setSigla(novoTimeDTO.getSigla());
        return time;
    }

    public void deletarTime(Integer id) {
        final Optional<Time> optionalTime = timeRepository.findById(id);
        if (optionalTime.isPresent()) {
            timeRepository.delete(optionalTime.get());
        } else {
            throw new RuntimeException(String.format("Time com id %d inexistente", id));
        }
    }
}
