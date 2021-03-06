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

- A set of CRUD endpoints will now be exposed at /api/{entity}
    - For the `book` example above this would result in:
        - GET /api/book
        - GET /api/book/{id}
        - POST /api/book
        - PUT /api/book/{id}
        - DELETE /api/book/{id}
        

## Possible future improvements

- entity to DTO mapping
- think about generic endpoint format + possibility to override endpoints (https://stackoverflow.com/a/70127945)
- performance check 
  - currently using service locator pattern vs DI 
  - caching needed?