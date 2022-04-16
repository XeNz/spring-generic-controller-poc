package be.xentricator.demo.mapping;

import be.xentricator.demo.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EntityMappingInformation {
    private Class primaryKeyClass;
    private Class<? extends AbstractEntity<?>> entityClass;
}
