//package com.example.demo.Servicios;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.example.demo.Entities.Usuario;
//
//import jakarta.annotation.PostConstruct;
//
//
//@Service
//public class UsuarioServicio {
//	
//	private List<Usuario> repositorio = new ArrayList<>();
//
//
//    public Usuario add(Usuario e) {
//        repositorio.add(e);
//        return e;
//    }
//
//    public List<Usuario> findAll() {
//        return repositorio;
//    }
//
//    @PostConstruct
//    public void init() {
//        repositorio.addAll(
//                Arrays.asList(new Usuario( 1,"asd@asd.com","clave1", "nombre", "ape1", "ape2", "direccion", "provincia", "localidad", "tel", "dni"),
//                		new Usuario( 1,"asd@asd.com","clave2", "nombre", "ape1", "ape2", "direccion", "provincia", "localidad", "tel", "dni"),
//                		new Usuario( 1,"asd@asd.com","clave3", "nombre", "ape1", "ape2", "direccion", "provincia", "localidad", "tel", "dni")                        
//                        )
//                );
//    }
//
//}
