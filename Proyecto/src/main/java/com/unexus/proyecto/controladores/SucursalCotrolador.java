package com.unexus.proyecto.controladores;

import com.unexus.proyecto.entidades.Sucursal;
import com.unexus.proyecto.excepciones.MiExcepcion;
import com.unexus.proyecto.servicios.SucursalServicio;
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
@RequestMapping("/sucursal")
public class SucursalCotrolador {
    
    @Autowired
    private SucursalServicio sucursalServicio;
    
    @GetMapping("/lista")
    public ResponseEntity<List<Sucursal>> listar() {
        List<Sucursal> list = sucursalServicio.listarSucursales();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody Sucursal sucursal) {
        if (sucursalServicio.existsById(sucursal.getId())) {
            return new ResponseEntity(new Mensaje("Esa sucursal existe"), HttpStatus.BAD_REQUEST);
        }

        try {
            sucursalServicio.crearSucursal(sucursal.getProvincia(), sucursal.getCiudad(), sucursal.getDireccion());
            return new ResponseEntity(new Mensaje("Sucursal agregada"), HttpStatus.OK);
        }catch (MiExcepcion ex) {
            return new ResponseEntity(new Mensaje("No se pudo agregar"), HttpStatus.BAD_REQUEST);
        }

        
    }
    
    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificar(@PathVariable("id") Long id, @RequestBody Sucursal sucursal){
        //Validamos si existe el ID
        if(!sucursalServicio.existsById(sucursal.getId()))
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.NOT_FOUND);
        
        //No puede estar vacio
        if(StringUtils.isBlank(sucursal.getProvincia()))
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(sucursal.getCiudad()))
            return new ResponseEntity(new Mensaje("La ciudad es obligatoria"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(sucursal.getDireccion()))
            return new ResponseEntity(new Mensaje("La dirección es obligatorio"), HttpStatus.BAD_REQUEST);
        
        try {
            sucursalServicio.modificarSucursal(sucursal.getId(), sucursal.getProvincia(), sucursal.getCiudad(), sucursal.getDireccion());
            return new ResponseEntity(new Mensaje("Sucursal modificado"), HttpStatus.OK);
        }catch (MiExcepcion ex) {
            return new ResponseEntity(new Mensaje("No se pudo modificar"), HttpStatus.BAD_REQUEST);
        }
             
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Long id) {
        if (!sucursalServicio.existsById(id)) {
            return new ResponseEntity(new Mensaje("el ID no existe"), HttpStatus.NOT_FOUND);
        }
        sucursalServicio.eliminar(id);
        return new ResponseEntity(new Mensaje("Sucursal eliminada"), HttpStatus.OK);
    }
    
//     @GetMapping("/detail/{id}")
//    public ResponseEntity<Sucursal> getById(@PathVariable("id") Long id){
//        if(!sucursalServicio.existsById(id))
//            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
//        Sucursal sucursal = sucursalServicio.getOne(id);
//        return new ResponseEntity(sucursal, HttpStatus.OK);
//    }
}
