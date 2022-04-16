# Generic controller POC

## How to use as a consumer
 - Add your entities to the configuration below:
 - Example can be found in `be.xentricator.demo.mapping.EntityMappingConfiguration`
```java 
   @Bean
    public EntityMapping entityMapping() {
        Map<String, EntityMappingInformation> mappingInformation = Map.of(
                "book", new EntityMappingInformation(Long.class, Book.class)
        );
        return new EntityMapping(mappingInformation);
    }
```


