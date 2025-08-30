package com.ages.volunteersmile.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ages.volunteersmile.domain.volunteer.model.Volunteer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
@Transactional(readOnly = true)
public class VolunteerRepositoryImpl implements VolunteerRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Optional<Volunteer> findByEmail(String email) {
		TypedQuery<Volunteer> query = entityManager.createQuery(
				"SELECT v FROM Volunteer v WHERE v.email = :email AND v.deletedAt IS NULL", Volunteer.class);
		query.setParameter("email", email);
		return query.getResultStream().findFirst();
	}

	@Override
	public List<Volunteer> findAllActive() {
		return entityManager.createQuery(
				"SELECT v FROM Volunteer v WHERE v.deletedAt IS NULL", Volunteer.class)
				.getResultList();
	}

	@Override
	public Optional<Volunteer> findActiveById(UUID id) {
		TypedQuery<Volunteer> query = entityManager.createQuery(
				"SELECT v FROM Volunteer v WHERE v.id = :id AND v.deletedAt IS NULL", Volunteer.class);
		query.setParameter("id", id);
		return query.getResultStream().findFirst();
	}

	@Override
    @Transactional
    public Volunteer save(Volunteer volunteer) {
        if (volunteer.getId() == null) {
            entityManager.persist(volunteer);
            return volunteer;
        }
        return entityManager.merge(volunteer);
    }

}
