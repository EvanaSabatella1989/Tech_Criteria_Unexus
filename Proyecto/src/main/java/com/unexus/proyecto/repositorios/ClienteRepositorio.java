package com.unexus.proyecto.repositorios;

import com.unexus.proyecto.entidades.Cliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ClienteRepositorio extends JpaRepository<Cliente, Long>{
    
//    public Optional<Cliente> buscarPorNombre(String nombre);
//    public Optional<Cliente> buscarPorNumeroIdentificacion(Long numeroIdentificacion);
//    public boolean existsByNombre(String nombre);
}
