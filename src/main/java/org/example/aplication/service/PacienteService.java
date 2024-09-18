package org.example.aplication.service;

import org.example.domain.Paciente;

import java.util.List;

public interface PacienteService {
    List<Paciente> findAll();
    Paciente findById(int id);
    void save(Paciente paciente);
    void update(Paciente paciente);
    void delete(int id);
}
