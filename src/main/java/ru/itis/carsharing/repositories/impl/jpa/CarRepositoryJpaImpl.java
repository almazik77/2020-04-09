package ru.itis.carsharing.repositories.impl.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.carsharing.models.Car;
import ru.itis.carsharing.repositories.CarRepository;
import ru.itis.carsharing.repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
public class CarRepositoryJpaImpl implements CarRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public Optional<Car> find(Long id) {
        return Optional.of(entityManager.find(Car.class, id));
    }

    @Override
    @Transactional
    public List<Car> findAll() {
        return entityManager.createQuery("select c from Car c", Car.class).getResultList();
    }

    @Transactional
    @Override
    public void update(Car model) {
        entityManager.merge(model);
    }

    @Transactional
    @Override
    public void save(Car entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        entityManager.remove(find(id));
    }

    @Override
    @Transactional
    public List<Car> findByOwnerId(Long id) {
        TypedQuery<Car> query = entityManager.createQuery("select c from Car c where c.owner = :owner", Car.class);
        query.setParameter("owner", userRepository.find(id));
        return query.getResultList();
    }
}
