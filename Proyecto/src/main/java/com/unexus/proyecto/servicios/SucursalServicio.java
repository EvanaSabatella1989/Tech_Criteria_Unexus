package com.unexus.proyecto.servicios;

import com.unexus.proyecto.entidades.Sucursal;
import com.unexus.proyecto.excepciones.MiExcepcion;
import com.unexus.proyecto.repositorios.SucursalRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@Transactional
public class SucursalServicio {

    @Autowired
    SucursalRepositorio sucursalRespositorio;

    @Transactional
    public void crearSucursal(String provincia, String ciudad, String direccion) throws MiExcepcion {

        validar(provincia, ciudad, direccion);

        Sucursal sucursal = new Sucursal();

        sucursal.setProvincia(provincia);
        sucursal.setCiudad(ciudad);
        sucursal.setDireccion(direccion);

        sucursalRespositorio.save(sucursal);
    }

    public List<Sucursal> listarSucursales() {

        List<Sucursal> sucursales = new ArrayList();

        sucursales = sucursalRespositorio.findAll();

        return sucursales;
    }

    @Transactional
    public void modificarSucursal(Long id, String provincia, String ciudad, String direccion) throws MiExcepcion {

        validar(provincia, ciudad, direccion);

        Optional<Sucursal> respuesta = sucursalRespositorio.findById(id);

        if (respuesta.isPresent()) {

            Sucursal sucursal = respuesta.get();

            sucursal.setProvincia(provincia);
            sucursal.setCiudad(ciudad);
            sucursal.setDireccion(direccion);

            sucursalRespositorio.save(sucursal);
        }
    }
    
    public Sucursal getOne(Long id) {
        return sucursalRespositorio.getOne(id);
    }
    
        public boolean existsById(Long id){
        return sucursalRespositorio.existsById(id);
    }

        public void eliminar(Long id){
        sucursalRespositorio.deleteById(id);
    }    
        
    private void validar(String provincia, String ciudad, String direccion) throws MiExcepcion {

        if (provincia.isEmpty() || provincia == null) {
            throw new MiExcepcion("el nombre de la provincia no puede ser nula o estar vacia");
        }

        if (ciudad.isEmpty() || ciudad == null) {
            throw new MiExcepcion("el nombre de la ciudad no puede ser nula o estar vacia");
        }

        if (direccion.isEmpty() || direccion == null) {
            throw new MiExcepcion("la direccion no puede estar vacia");
        }
    }
    
//    public List<Sucursal> list(){
//        return sucursalRespositorio.findAll();
//    }
//    
//    public Optional<Sucursal> getOne(Long id){
//        return sucursalRespositorio.findById(id);
//    }
//    
//    
//    public void save(Sucursal sucursal){
//        sucursalRespositorio.save(sucursal);
//    }
//    

//    

    
}
