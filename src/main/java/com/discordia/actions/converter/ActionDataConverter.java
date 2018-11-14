package com.discordia.actions.converter;

import org.springframework.stereotype.Component;

import com.discordia.actions.data.ActionData;
import com.discordia.actions.model.Action;

@Component
public class ActionDataConverter {
  public ActionData convert(final Action action) {
    final ActionData actionData = new ActionData();
    actionData.setId(action.getId());
    actionData.setName(action.getName());
    actionData.setQuantity(action.getQuantity());

    return actionData;
  }
}
