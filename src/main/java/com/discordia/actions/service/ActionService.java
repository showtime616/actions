package com.discordia.actions.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.discordia.actions.converter.ActionDataConverter;
import com.discordia.actions.data.ActionData;
import com.discordia.actions.model.Action;
import com.discordia.actions.repository.ActionRepository;

@Service
public class ActionService {
  @Autowired
  private ActionRepository actionRepository;

  @Autowired
  private ActionDataConverter actionDataConverter;

  public Optional<ActionData> getAction(final Long id)  {
    return Optional.ofNullable(id)
            .flatMap(actionRepository::findById)
            .map(actionDataConverter::convert);
  }

  @Transactional
  public Optional<Action> takeGift(final Long actionId) {
    return Optional.ofNullable(actionId)
            .flatMap(actionRepository::findById)
            .map(Action::decrementQuantity)
            .map(action -> (Action) action);
  }
}
