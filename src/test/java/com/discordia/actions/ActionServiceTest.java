package com.discordia.actions;

import com.discordia.actions.converter.ActionDataConverter;
import com.discordia.actions.data.ActionData;
import com.discordia.actions.model.Action;
import com.discordia.actions.repository.ActionRepository;
import com.discordia.actions.service.ActionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ActionServiceTest {

  @TestConfiguration
  static class ActionServiceTestContextConfiguration {
    @Bean
    public ActionService actionService() {
      return new ActionService();
    }

    @Bean
    public ActionDataConverter actionDataConverter() {
      return new ActionDataConverter();
    }
  }

  @Autowired
  private ActionService actionService;

  @Autowired
  private ActionDataConverter actionDataConverter;

  @MockBean
  private ActionRepository actionRepository;


  @Test
  public void whenGetActionWithNullId_thenOptionalEmptyShouldBeReturned() {
    final Optional<ActionData> actionOptional = actionService.getAction(null);

    assertEquals(Optional.empty(), actionOptional);
  }

  @Test
  public void whenGetActionWithExistsActionId_thenActionalDataShouldBeReturned() {
    final Long id = 1L;
    final Long quantity = 10L;
    final String name = "testAction";

    final Action action = new Action(name, Collections.emptySet(), quantity);
    action.setId(id);

    when(actionRepository.findById(id)).thenReturn(Optional.of(action));

    final Optional<ActionData> optionalActionData = actionService.getAction(id);

    assertTrue(optionalActionData.isPresent());

    final ActionData actionData = optionalActionData.get();
    assertEquals(id, actionData.getId());
    assertEquals(name, actionData.getName());
    assertEquals(quantity, actionData.getQuantity());
  }

  @Test
  public void whenGetActionWithNonExistsActionId_thenOptionalEmptyShouldBeReturned() {
    assertEquals(Optional.empty(), actionService.getAction(1L));
  }
}
