package br.edu.ibmec.cloud.ecommerce_cloud.controller;

import br.edu.ibmec.cloud.ecommerce_cloud.model.Usuario;
import br.edu.ibmec.cloud.ecommerce_cloud.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsers() {
        List<Usuario> response = repository.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Integer id) {
        Optional<Usuario> response = this.repository.findById(id);
        return response.map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario usuario) {
        Usuario savedUsuario = repository.save(usuario);
        return new ResponseEntity<>(savedUsuario, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable Integer id, @RequestBody Usuario usuario) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        usuario.setId(id);
        Usuario updatedUsuario = repository.save(usuario);
        return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}