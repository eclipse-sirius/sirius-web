/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.web.application.controllers.gantt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.GanttRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.ChangeGanttColumnInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.ChangeTaskCollapseStateInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.CreateGanttTaskDependencyInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.CreateGanttTaskInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.DeleteGanttTaskDependencyInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.DeleteGanttTaskInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.EditGanttTaskDetailInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.EditGanttTaskInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.TemporalType;
import org.eclipse.sirius.components.gantt.tests.graphql.ChangeColumnMutationRunner;
import org.eclipse.sirius.components.gantt.tests.graphql.ChangeTaskCollapseStateMutationRunner;
import org.eclipse.sirius.components.gantt.tests.graphql.CreateTaskDependencyMutationRunner;
import org.eclipse.sirius.components.gantt.tests.graphql.CreateTaskMutationRunner;
import org.eclipse.sirius.components.gantt.tests.graphql.DeleteTaskDependencyMutationRunner;
import org.eclipse.sirius.components.gantt.tests.graphql.DeleteTaskMutationRunner;
import org.eclipse.sirius.components.gantt.tests.graphql.EditTaskMutationRunner;
import org.eclipse.sirius.components.gantt.tests.navigation.GanttNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.gantt.PapayaGanttDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedGanttSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of the gantt representation with a papaya model.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class PapayaGanttControllerIntegrationTests extends AbstractIntegrationTests {
    private static final String MISSING_GANTT = "Missing gantt";

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedGanttSubscription givenCreatedGanttSubscription;

    @Autowired
    private PapayaGanttDescriptionProvider papayaGanttDescriptionProvider;

    @Autowired
    private CreateTaskMutationRunner createTaskMutationRunner;

    @Autowired
    private EditTaskMutationRunner editTaskMutationRunner;

    @Autowired
    private DeleteTaskMutationRunner deleteTaskMutationRunner;

    @Autowired
    private ChangeTaskCollapseStateMutationRunner changeTaskCollapseStateMutationRunner;

    @Autowired
    private ChangeColumnMutationRunner changeColumnMutationRunner;

    @Autowired
    private CreateTaskDependencyMutationRunner createTaskDependencyMutationRunner;

    @Autowired
    private DeleteTaskDependencyMutationRunner deleteTaskDependencyMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToGantt() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.papayaGanttDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.SIRIUS_WEB_PLANNING_PROJECT_OBJECT.toString(),
                "Gantt"
        );
        return this.givenCreatedGanttSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a gantt representation, when we subscribe to its event, then the representation data are received")
    public void givenGanttRepresentationWhenWeSubscribeToItsEventThenTheRepresentationDataAreReceived() {
        var flux = this.givenSubscriptionToGantt();

        Consumer<Object> initialGanttContentConsumer = payload -> Optional.of(payload)
                .filter(GanttRefreshedEventPayload.class::isInstance)
                .map(GanttRefreshedEventPayload.class::cast)
                .map(GanttRefreshedEventPayload::gantt)
                .ifPresentOrElse(gantt -> {
                    assertThat(gantt).isNotNull();
                    assertThat(gantt.tasks()).hasSize(2);
                    assertThat(gantt.tasks().get(0).subTasks()).hasSize(3);
                }, () -> fail(MISSING_GANTT));

        StepVerifier.create(flux)
                .consumeNextWith(initialGanttContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a gantt representation, when we create/edit a task, then the new task is created/edited")
    public void givenGanttRepresentationWhenWeCreateEditTaskThenTheTaskIsAsExpected() {
        var flux = this.givenSubscriptionToGantt();

        var ganttId = new AtomicReference<String>();
        var taskId = new AtomicReference<String>();
        var createdTaskId = new AtomicReference<String>();
        Consumer<Object> initialGanttContentConsumer = payload -> Optional.of(payload)
                .filter(GanttRefreshedEventPayload.class::isInstance)
                .map(GanttRefreshedEventPayload.class::cast)
                .map(GanttRefreshedEventPayload::gantt)
                .ifPresentOrElse(gantt -> {
                    ganttId.set(gantt.getId());
                    assertThat(gantt.tasks().get(0).subTasks().get(0).subTasks()).hasSize(2);

                    var task = new GanttNavigator(gantt).findTaskByName("Improve some features of the deck");
                    taskId.set(task.id());
                }, () -> fail(MISSING_GANTT));


        Runnable createGanttTask = () -> {
            var createGanttTaskInput = new CreateGanttTaskInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    ganttId.get(),
                    taskId.get()
            );
            var result = this.createTaskMutationRunner.run(createGanttTaskInput);

            String typename = JsonPath.read(result, "$.data.createGanttTask.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> createGanttTaskConsumer = payload -> Optional.of(payload)
                .filter(GanttRefreshedEventPayload.class::isInstance)
                .map(GanttRefreshedEventPayload.class::cast)
                .map(GanttRefreshedEventPayload::gantt)
                .ifPresentOrElse(gantt -> {
                    assertThat(gantt.tasks().get(0).subTasks().get(0).subTasks()).hasSize(3);
                    createdTaskId.set(gantt.tasks().get(0).subTasks().get(0).subTasks().get(2).id());
                }, () -> fail(MISSING_GANTT));

        Runnable editGanttTask = () -> {
            EditGanttTaskDetailInput editGanttTaskDetailInput = new EditGanttTaskDetailInput(null, null, "2023-12-15T09:00:00Z", null, TemporalType.DATE_TIME, 0);
            var editGanttTaskInput = new EditGanttTaskInput(UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    ganttId.get(),
                    createdTaskId.get(),
                    editGanttTaskDetailInput);
            var result = this.editTaskMutationRunner.run(editGanttTaskInput);

            String typename = JsonPath.read(result, "$.data.editGanttTask.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> editGanttTaskConsumer = payload -> Optional.of(payload)
                .filter(GanttRefreshedEventPayload.class::isInstance)
                .map(GanttRefreshedEventPayload.class::cast)
                .map(GanttRefreshedEventPayload::gantt)
                .ifPresentOrElse(gantt -> {
                    assertThat(gantt.tasks().get(0).subTasks().get(0).subTasks().get(2).detail())
                            .hasFieldOrPropertyWithValue("name", "")
                            .hasFieldOrPropertyWithValue("startTime", "2023-12-15T09:00:00Z");
                }, () -> fail(MISSING_GANTT));

        StepVerifier.create(flux)
                .consumeNextWith(initialGanttContentConsumer)
                .then(createGanttTask)
                .consumeNextWith(createGanttTaskConsumer)
                .then(editGanttTask)
                .consumeNextWith(editGanttTaskConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a gantt representation, when we delete a task, then the task is not visible anymore")
    public void givenGanttRepresentationWhenWeDeleteTaskThenTheTaskIsNotVisibleAnymore() {
        var flux = this.givenSubscriptionToGantt();

        var taskName = "Improve some features of the deck";

        var ganttId = new AtomicReference<String>();
        var taskId = new AtomicReference<String>();
        Consumer<Object> initialGanttContentConsumer = payload -> Optional.of(payload)
                .filter(GanttRefreshedEventPayload.class::isInstance)
                .map(GanttRefreshedEventPayload.class::cast)
                .map(GanttRefreshedEventPayload::gantt)
                .ifPresentOrElse(gantt -> {
                    ganttId.set(gantt.getId());
                    assertThat(new GanttNavigator(gantt).existTaskByName(taskName)).isTrue();

                    var task = new GanttNavigator(gantt).findTaskByName(taskName);
                    taskId.set(task.id());
                }, () -> fail(MISSING_GANTT));


        Runnable deleteGanttTask = () -> {
            var deleteGanttTaskInput = new DeleteGanttTaskInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    ganttId.get(),
                    taskId.get()
            );
            var result = this.deleteTaskMutationRunner.run(deleteGanttTaskInput);

            String typename = JsonPath.read(result, "$.data.deleteGanttTask.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> deleteGanttTaskConsumer = payload -> Optional.of(payload)
                .filter(GanttRefreshedEventPayload.class::isInstance)
                .map(GanttRefreshedEventPayload.class::cast)
                .map(GanttRefreshedEventPayload::gantt)
                .ifPresentOrElse(gantt -> {
                    assertThat(new GanttNavigator(gantt).existTaskByName(taskName)).isFalse();
                }, () -> fail(MISSING_GANTT));

        StepVerifier.create(flux)
                .consumeNextWith(initialGanttContentConsumer)
                .then(deleteGanttTask)
                .consumeNextWith(deleteGanttTaskConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a gantt representation, when the expand Task mutation is performed, then it succeeds")
    public void givenGanttRepresentationWhenTheExpandTaskMutationIsPerformedThenItSucceeds() {
        var flux = this.givenSubscriptionToGantt();

        var ganttId = new AtomicReference<String>();
        var taskId = new AtomicReference<String>();
        Consumer<Object> initialGanttContentConsumer = payload -> Optional.of(payload)
                .filter(GanttRefreshedEventPayload.class::isInstance)
                .map(GanttRefreshedEventPayload.class::cast)
                .map(GanttRefreshedEventPayload::gantt)
                .ifPresentOrElse(gantt -> {
                    ganttId.set(gantt.getId());
                    assertThat(gantt).isNotNull();
                    assertThat(gantt.tasks()).hasSize(2);

                    var task = new GanttNavigator(gantt).findTaskByName("2024.3.0");
                    taskId.set(task.id());
                }, () -> fail(MISSING_GANTT));


        Runnable expandTask = () -> {
            var changeTaskCollapseStateInput = new ChangeTaskCollapseStateInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    ganttId.get(),
                    taskId.get(),
                    true
            );
            var result = this.changeTaskCollapseStateMutationRunner.run(changeTaskCollapseStateInput);

            String typename = JsonPath.read(result, "$.data.changeGanttTaskCollapseState.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> collapseGanttTaskConsumer = payload -> Optional.of(payload)
                .filter(GanttRefreshedEventPayload.class::isInstance)
                .map(GanttRefreshedEventPayload.class::cast)
                .map(GanttRefreshedEventPayload::gantt)
                .ifPresentOrElse(gantt -> {
                    assertThat(new GanttNavigator(gantt).findTaskByName("2024.3.0").detail().collapsed()).isTrue();
                }, () -> fail(MISSING_GANTT));


        StepVerifier.create(flux)
                .consumeNextWith(initialGanttContentConsumer)
                .then(expandTask)
                .consumeNextWith(collapseGanttTaskConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a gantt representation, when the change column mutation is performed, then it succeeds")
    public void givenGanttRepresentationWhenChangeColumnMutationIsPerformedThenItSucceeds() {
        var flux = this.givenSubscriptionToGantt();

        var ganttRef = new AtomicReference<Gantt>();
        Consumer<Object> initialGanttContentConsumer = payload -> Optional.of(payload)
                .filter(GanttRefreshedEventPayload.class::isInstance)
                .map(GanttRefreshedEventPayload.class::cast)
                .map(GanttRefreshedEventPayload::gantt)
                .ifPresentOrElse(gantt -> {
                    ganttRef.set(gantt);
                    assertThat(gantt).isNotNull();
                    assertThat(gantt.columns()).hasSize(4);
                }, () -> fail(MISSING_GANTT));


        Runnable changeColumn = () -> {
            var columnToChange = ganttRef.get().columns().stream()
                    .filter(col -> col.id().equals("START_DATE"))
                    .findFirst().get();
            var changeColumnInput = new ChangeGanttColumnInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    ganttRef.get().getId(), columnToChange.id(), false, 50);
            var result = this.changeColumnMutationRunner.run(changeColumnInput);

            String typename = JsonPath.read(result, "$.data.changeGanttColumn.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> changeColumnConsumer = payload -> Optional.of(payload)
                .filter(GanttRefreshedEventPayload.class::isInstance)
                .map(GanttRefreshedEventPayload.class::cast)
                .map(GanttRefreshedEventPayload::gantt)
                .ifPresentOrElse(gantt -> {
                    var changedColumn = gantt.columns().stream()
                            .filter(col -> col.id().equals("START_DATE"))
                            .findFirst().get();
                    assertThat(changedColumn.isDisplayed()).isFalse();
                    assertThat(changedColumn.width()).isEqualTo(50);
                }, () -> fail(MISSING_GANTT));


        StepVerifier.create(flux)
                .consumeNextWith(initialGanttContentConsumer)
                .then(changeColumn)
                .consumeNextWith(changeColumnConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a gantt representation, when the task dependency creation and deletion mutations are invoked, then it succeeds")
    public void givenGanttWhenCreateAndDeleteTaskDependencyMutationArePerformedThenItSucceeds() {
        var flux = this.givenSubscriptionToGantt();

        var ganttRef = new AtomicReference<Gantt>();
        var sourceTaskId = new AtomicReference<String>();
        var targetTaskId = new AtomicReference<String>();
        String taskName1 = "Improve some features of the deck";
        String taskName2 = "Improve some features of the gantt";
        Consumer<Object> initialGanttContentConsumer = payload -> Optional.of(payload)
                .filter(GanttRefreshedEventPayload.class::isInstance)
                .map(GanttRefreshedEventPayload.class::cast)
                .map(GanttRefreshedEventPayload::gantt)
                .ifPresentOrElse(gantt -> {
                    ganttRef.set(gantt);
                    var task = new GanttNavigator(gantt).findTaskByName(taskName1);
                    sourceTaskId.set(task.id());
                    task = new GanttNavigator(gantt).findTaskByName(taskName2);
                    assertThat(task.taskDependencyIds()).isEmpty();
                    targetTaskId.set(task.id());
                }, () -> fail(MISSING_GANTT));


        Runnable createDependencyRunnable = () -> {
            var createGanttTaskDependencyInput = new CreateGanttTaskDependencyInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    ganttRef.get().getId(), sourceTaskId.get(), targetTaskId.get());
            var result = this.createTaskDependencyMutationRunner.run(createGanttTaskDependencyInput);

            String typename = JsonPath.read(result, "$.data.createGanttTaskDependency.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> checkDependencyConsumer = payload -> Optional.of(payload)
                .filter(GanttRefreshedEventPayload.class::isInstance)
                .map(GanttRefreshedEventPayload.class::cast)
                .map(GanttRefreshedEventPayload::gantt)
                .ifPresentOrElse(gantt -> {
                    var task = new GanttNavigator(gantt).findTaskByName(taskName2);
                    assertThat(task.taskDependencyIds()).contains(sourceTaskId.get());
                }, () -> fail(MISSING_GANTT));
        
        Runnable deleteDependencyRunnable = () -> {
            var deleteGanttTaskDependencyInput = new DeleteGanttTaskDependencyInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    ganttRef.get().getId(), sourceTaskId.get(), targetTaskId.get());
            var result = this.deleteTaskDependencyMutationRunner.run(deleteGanttTaskDependencyInput);

            String typename = JsonPath.read(result, "$.data.deleteGanttTaskDependency.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> checkDependencyAfterDeleteConsumer = payload -> Optional.of(payload)
                .filter(GanttRefreshedEventPayload.class::isInstance)
                .map(GanttRefreshedEventPayload.class::cast)
                .map(GanttRefreshedEventPayload::gantt)
                .ifPresentOrElse(gantt -> {
                    var task = new GanttNavigator(gantt).findTaskByName(taskName2);
                    assertThat(task.taskDependencyIds()).isEmpty();
                }, () -> fail(MISSING_GANTT));


        StepVerifier.create(flux)
                .consumeNextWith(initialGanttContentConsumer)
                .then(createDependencyRunnable)
                .consumeNextWith(checkDependencyConsumer)
                .then(deleteDependencyRunnable)
                .consumeNextWith(checkDependencyAfterDeleteConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
