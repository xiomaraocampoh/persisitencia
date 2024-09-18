package org.example.infraestructure.repository;

import org.example.domain.Paciente;
import org.example.interfaces.PacienteRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PacienteRepositoryimpl implements PacienteRepository {

    private static final String FILE_NAME = "pacientes.dat";

    @Override
    public List<Paciente> findAll() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (ArrayList<Paciente>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Paciente findById(int id) {
        return findAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Paciente paciente) {
        List<Paciente> pacientes = findAll();
        pacientes.add(paciente);
        saveAll(pacientes);
    }

    @Override
    public void update(Paciente paciente) {
        List<Paciente> pacientes = findAll();
        pacientes = pacientes.stream()
                .map(p -> p.getId() == paciente.getId() ? paciente : p)
                .collect(Collectors.toList());
        saveAll(pacientes);
    }

    @Override
    public void delete(int id) {
        List<Paciente> pacientes = findAll();
        pacientes = pacientes.stream()
                .filter(p -> p.getId() != id)
                .collect(Collectors.toList());
        saveAll(pacientes);
    }

    private void saveAll(List<Paciente> pacientes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(pacientes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
