package be.xentricator.demo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;
import org.springframework.data.repository.CrudRepository;

@Configuration
@RequiredArgsConstructor
public class RepositoryUtils implements ApplicationContextAware {

    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public <T, PK> CrudRepository<T, PK> getRepositoryFor(Class<T> clazz, Class<PK> primaryKeyClazz) {
        ResolvableType type = ResolvableType.forClassWithGenerics(CrudRepository.class, clazz, primaryKeyClazz);
        return (CrudRepository<T, PK>) applicationContext.getBeanProvider(type).getObject();
    }
}
