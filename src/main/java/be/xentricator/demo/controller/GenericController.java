package be.xentricator.demo.controller;

import be.xentricator.demo.entity.AbstractEntity;
import be.xentricator.demo.mapping.EntityMapping;
import be.xentricator.demo.repository.GenericRepositoryAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class GenericController {
    private final GenericRepositoryAdapter genericRepositoryAdapter;
    private final EntityMapping entityMapping;
    private final ObjectMapper objectMapper;



    @GetMapping("api/{entity}")
    public ResponseEntity<?> getAll(@PathVariable("entity") String entityName) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Class<? extends AbstractEntity<?>> entityClass = entityMapping.getEntityClass(entityName);
        Class primaryKeyClass = entityMapping.getPrimaryKeyClass(entityName);
        Constructor<? extends AbstractEntity<?>> constructor = entityClass.getConstructor();
        AbstractEntity instance = constructor.newInstance((Object[]) null);
        Iterable all = genericRepositoryAdapter.findAll(instance, primaryKeyClass);
        return ResponseEntity.ok(all);
    }

    @GetMapping("api/{entity}/{id}")
    public ResponseEntity<?> getById(@PathVariable("entity") String entityName, @PathVariable("id") Long id) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Class<? extends AbstractEntity<?>> entityClass = entityMapping.getEntityClass(entityName);
        Class primaryKeyClass = entityMapping.getPrimaryKeyClass(entityName);
        Constructor<? extends AbstractEntity<?>> constructor = entityClass.getConstructor();
        AbstractEntity instance = constructor.newInstance((Object[]) null);
        instance.setId(id);
        Optional<? extends AbstractEntity> byId = genericRepositoryAdapter.findById(instance, primaryKeyClass);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(byId.get());
    }

    @PutMapping("api/{entity}/{id}")
    public ResponseEntity<?> update(@PathVariable("entity") String entityName, @RequestBody Object entityDto, @PathVariable("id") Long id) {
        Class<? extends AbstractEntity<?>> entityClass = entityMapping.getEntityClass(entityName);
        Class primaryKeyClass = entityMapping.getPrimaryKeyClass(entityName);

        AbstractEntity entityInstance = objectMapper.convertValue(entityDto, entityClass);
        entityInstance.setId(id);
        AbstractEntity created = genericRepositoryAdapter.save(entityInstance, primaryKeyClass);
        return ResponseEntity.ok().body(created);
    }

    @PostMapping("api/{entity}")
    public ResponseEntity<?> create(@PathVariable("entity") String entityName, @RequestBody Object entityDto) {
        Class<? extends AbstractEntity<?>> entityClass = entityMapping.getEntityClass(entityName);
        Class primaryKeyClass = entityMapping.getPrimaryKeyClass(entityName);

        AbstractEntity<?> entityInstance = objectMapper.convertValue(entityDto, entityClass);
        AbstractEntity created = genericRepositoryAdapter.save(entityInstance, primaryKeyClass);
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        URI location = builder.path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created((location)).body(created);
    }

    @DeleteMapping("api/{entity}/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("entity") String entityName, @PathVariable("id") Long Id) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Class<? extends AbstractEntity<?>> entityClass = entityMapping.getEntityClass(entityName);
        Class primaryKeyClass = entityMapping.getPrimaryKeyClass(entityName);
        Constructor<? extends AbstractEntity<?>> constructor = entityClass.getConstructor();
        AbstractEntity instance = constructor.newInstance((Object[]) null);
        instance.setId(Id);
        genericRepositoryAdapter.delete(instance, primaryKeyClass);
        return ResponseEntity.noContent().build();
    }
}
