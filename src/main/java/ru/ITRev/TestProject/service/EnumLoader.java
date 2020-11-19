package ru.ITRev.TestProject.service;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.stereotype.Component;
import ru.ITRev.TestProject.MappedEnum;
import ru.ITRev.TestProject.model.Dictionary;
import ru.ITRev.TestProject.repository.FormatFileRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class EnumLoader {

    @PersistenceContext
    EntityManager entityManager;

    @PostConstruct
    public void init() {
        try {
            updateEnumsIds(FormatFileRepository.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEnumsIds(Class<? extends JpaRepository> repository) throws Exception {
        JpaRepository jpaRepository = concreteJpaRepository(repository);
        Set<BeanDefinition> annotateClasses = getAnnotateMappedEnumClassSet();
        for (BeanDefinition annotateClass : annotateClasses) {
            Class<?> clss = Class.forName(annotateClass.getBeanClassName());
            if (Dictionary.class.isAssignableFrom(clss)) {
                MappedEnum mappedEnum = clss.getAnnotation(MappedEnum.class);
                Class<? extends Enum> enumClass = mappedEnum.enumClass();
                Type actualRepositoryTypeArgument = ((ParameterizedType) repository.getGenericInterfaces()[0]).getActualTypeArguments()[0];
                if (clss.isAssignableFrom((Class<?>) actualRepositoryTypeArgument)) {
                    List<Dictionary> entityValueList = new ArrayList<>(jpaRepository.findAll());
                    setEnumIds(enumClass, entityValueList);
                }
            } else {
                //ToDo доработать ошибку
                throw new Exception("");
            }
        }
    }

    private <T extends JpaRepository> T concreteJpaRepository(Class<? extends T> repositoryInterface) {
        RepositoryFactorySupport repositoryFactorySupport = new JpaRepositoryFactory(entityManager);
        return repositoryFactorySupport.getRepository(repositoryInterface);
    }

    private Set<BeanDefinition> getAnnotateMappedEnumClassSet() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(MappedEnum.class));
        //ToDo вынести в константы
        return scanner.findCandidateComponents("ru.ITRev.TestProject.model");
    }

    private void setEnumIds(Class<? extends Enum> enumClass, List<Dictionary> entityValueList) throws Exception {
        Enum[] constants = enumClass.getEnumConstants();
        for (Enum constant : constants) {
            Dictionary foundSD = entityValueList.stream().filter(x -> x.getValue().equalsIgnoreCase(constant.name())).findFirst().orElse(null);
            if (foundSD != null) {
                setIdToEnumField(constant, foundSD.getId());
            } else {
                //ToDo доработать ошибку
                throw new Exception("");
            }
        }
    }

    private void setIdToEnumField(Enum constant, Integer id) throws Exception {
        Field idField;
        if (id != null) {
            try {
                idField = constant.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(constant, id);
            } catch (Exception ex) {
                //ToDo доработать ошибку
                throw new Exception("");
            }
        }

    }
}
