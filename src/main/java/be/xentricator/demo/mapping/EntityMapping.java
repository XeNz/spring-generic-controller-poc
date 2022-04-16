package be.xentricator.demo.mapping;

import be.xentricator.demo.entity.AbstractEntity;

import java.util.Map;

public class EntityMapping {
    public EntityMapping(Map<String, EntityMappingInformation> entities) {
        this.entities = entities;
    }

    private final Map<String, EntityMappingInformation> entities;

    public Map<String, EntityMappingInformation> getEntities() {
        return entities;
    }

    public Class<? extends AbstractEntity<?>> getEntityClass(String name) {
        EntityMappingInformation entityMappingInformation = entities.get(name);
        return entityMappingInformation.getEntityClass();
    }

    public Class getPrimaryKeyClass(String name) {
        EntityMappingInformation entityMappingInformation = entities.get(name);
        return entityMappingInformation.getPrimaryKeyClass();
    }

    public EntityMappingInformation getEntityInformation(String name) {
        return entities.get(name);
    }
}
