package com.discordia.actions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.discordia.actions.model.Action;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
}
