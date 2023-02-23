package com.unexus.proyecto.servicios;

import com.unexus.proyecto.entidades.Cliente;
import com.unexus.proyecto.entidades.Sucursal;
import com.unexus.proyecto.excepciones.MiExcepcion;
import com.unexus.proyecto.repositorios.ClienteRepositorio;
import com.unexus.proyecto.repositorios.SucursalRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@Transactional
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private SucursalRepositorio sucursalRepositorio;

    @Transactional
    public void crearCliente(String tipoIdentificacion, Long numeroIdentificacion, String nombre, String email, Integer telefono, Long idSucursal) throws MiExcepcion {

        validar(tipoIdentificacion, numeroIdentificacion, nombre, email, telefono, idSucursal);

        Optional<Sucursal> respuestaSucursal = sucursalRepositorio.findById(idSucursal);

        Sucursal sucursal = new Sucursal();

        if (respuestaSucursal.isPresent()) {

            sucursal = respuestaSucursal.get();
        }

        Cliente cliente = new Cliente();

        cliente.setTipoIdentificacion(tipoIdentificacion);
        cliente.setNumeroIdentificacion(numeroIdentificacion);
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        cliente.setSucursal(sucursal);

        clienteRepositorio.save(cliente);
    }

    public List<Cliente> listarClientes() {

        List<Cliente> clientes = new ArrayList();

        clientes = clienteRepositorio.findAll();

        return clientes;
    }

    public boolean existsById(Long numeroIdentificacion) {
        return clienteRepositorio.existsById(numeroIdentificacion);
    }

    @Transactional
    public void modificarCliente(String tipoIdentificacion, Long numeroIdentificacion, String nombre, String email, Integer telefono, Long idSucursal) throws MiExcepcion {

        validar(tipoIdentificacion, numeroIdentificacion, nombre, email, telefono, idSucursal);

        Optional<Cliente> respuesta = clienteRepositorio.findById(numeroIdentificacion);

        Optional<Sucursal> respuestaSucursal = sucursalRepositorio.findById(idSucursal);

        Sucursal sucursal = new Sucursal();

        if (respuestaSucursal.isPresent()) {

            sucursal = respuestaSucursal.get();
        }

        if (respuesta.isPresent()) {

            Cliente cliente = respuesta.get();

            cliente.setTipoIdentificacion(tipoIdentificacion);
            cliente.setNumeroIdentificacion(numeroIdentificacion);
            cliente.setNombre(nombre);
            cliente.setEmail(email);
            cliente.setTelefono(telefono);
            cliente.setSucursal(sucursal);

            clienteRepositorio.save(cliente);

        }
    }
    
        public void eliminar(Long id){
        clienteRepositorio.deleteById(id);
    }

    public Cliente getOne(Long id) {
        return clienteRepositorio.getOne(id);
    }

    private void validar(String tipoIdentificacion, Long numeroIdentificacion, String nombre, String email, Integer telefono, Long idSucursal) throws MiExcepcion {

        if (tipoIdentificacion.isEmpty() || tipoIdentificacion == null) {
            throw new MiExcepcion("el tipo de identificacion no puede ser nulo o estar vacio");
        }
        if (numeroIdentificacion == null) {
            throw new MiExcepcion("el numero de identificacion no puede ser nulo"); //
        }
        if (nombre.isEmpty() || nombre == null) {
            throw new MiExcepcion("el nombre no puede ser nulo o estar vacio");
        }
        if (email.isEmpty() || email == null) {
            throw new MiExcepcion("el email no puede ser nulo o estar vacio");
        }
        if (telefono == null) {
            throw new MiExcepcion("el telefono no puede ser nulo o estar vacio");
        }

        if (idSucursal == null) {
            throw new MiExcepcion("La Sucursal no puede ser nula o estar vacia");
        }
    }
//    public List<Cliente> list(){
//        return clienteRepositorio.findAll();
//    }
//    
//    public Optional<Cliente> getOne(Long id){
//        return clienteRepositorio.findById(id);
//    }
//    
//    public Optional<Cliente> getByNombre(String nombre){
//        return clienteRepositorio.buscarPorNombre(nombre);
//    }
//    
//    public Optional<Cliente> getByNumeroIdentificacion(Long numeroIdentificacion){
//        return clienteRepositorio.buscarPorNumeroIdentificacion(numeroIdentificacion);
//    }
//    
//    public void save(Cliente cliente){
//        clienteRepositorio.save(cliente);
//    }
//    
//    public void delete(Long id){
//        clienteRepositorio.deleteById(id);
//    }
//    
//    public boolean existsById(Long id){
//        return clienteRepositorio.existsById(id);
//    }
//    
//    public boolean existsByNombre(String nombre){
//        return clienteRepositorio.existsByNombre(nombre);
//    }
}
