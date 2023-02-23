package com.unexus.proyecto.controladores;

import com.unexus.proyecto.entidades.Cliente;
import com.unexus.proyecto.excepciones.MiExcepcion;
import com.unexus.proyecto.servicios.ClienteServicio;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/cliente")
public class ClienteControlador {

    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping("/lista")
    public ResponseEntity<List<Cliente>> listar() {
        List<Cliente> list = clienteServicio.listarClientes();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody Cliente cliente) {
        if (clienteServicio.existsById(cliente.getNumeroIdentificacion())) {
            return new ResponseEntity(new Mensaje("Ese cliente existe"), HttpStatus.BAD_REQUEST);
        }

        try {
            clienteServicio.crearCliente(cliente.getTipoIdentificacion(), cliente.getNumeroIdentificacion(), cliente.getNombre(), cliente.getEmail(), cliente.getTelefono(), cliente.getSucursal().getId());
            return new ResponseEntity(new Mensaje("Cliente agregado"), HttpStatus.OK);
        }catch (MiExcepcion ex) {
            return new ResponseEntity(new Mensaje("No se pudo agregar"), HttpStatus.BAD_REQUEST);
        }

        
    }
    
    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificar(@PathVariable("id") Long id, @RequestBody Cliente cliente){
        //Validamos si existe el ID
        if(!clienteServicio.existsById(cliente.getNumeroIdentificacion()))
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.NOT_FOUND);
        
        //No puede estar vacio
        if(StringUtils.isBlank(cliente.getNombre()))
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        
//        cliente = clienteServicio.getOne(id);
        try {
            clienteServicio.modificarCliente(cliente.getTipoIdentificacion(), cliente.getNumeroIdentificacion(), cliente.getNombre(), cliente.getEmail(), cliente.getTelefono(), cliente.getSucursal().getId());
            return new ResponseEntity(new Mensaje("Cliente modificado"), HttpStatus.OK);
        }catch (MiExcepcion ex) {
            return new ResponseEntity(new Mensaje("No se pudo modificar"), HttpStatus.BAD_REQUEST);
        }
             
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Long id) {
        if (!clienteServicio.existsById(id)) {
            return new ResponseEntity(new Mensaje("el ID no existe"), HttpStatus.NOT_FOUND);
        }
        clienteServicio.eliminar(id);
        return new ResponseEntity(new Mensaje("Cliente eliminado"), HttpStatus.OK);
    }
    
//     @GetMapping("/detail/{id}")
//    public ResponseEntity<Cliente> getById(@PathVariable("id") Long id){
//        if(!clienteServicio.existsById(id))
//            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
//        Cliente cliente = clienteServicio.getOne(id);
//        return new ResponseEntity(cliente, HttpStatus.OK);
//    }
}
