/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import org.eclipse.sirius.components.collaborative.gantt.dto.input.CreateGanttTaskInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.DeleteGanttTaskInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.tests.graphql.ChangeColumnMutationRunner;
import org.eclipse.sirius.components.gantt.tests.graphql.ChangeTaskCollapseStateMutationRunner;
import org.eclipse.sirius.components.gantt.tests.graphql.CreateTaskMutationRunner;
import org.eclipse.sirius.components.gantt.tests.graphql.DeleteTaskMutationRunner;
import org.eclipse.sirius.components.gantt.tests.navigation.GanttNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.api.IGivenCreatedGanttSubscription;
import org.eclipse.sirius.web.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.services.gantt.PapayaGanttDescriptionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
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
    private DeleteTaskMutationRunner deleteTaskMutationRunner;

    @Autowired
    private ChangeTaskCollapseStateMutationRunner changeTaskCollapseStateMutationRunner;

    @Autowired
    private ChangeColumnMutationRunner changeColumnMutationRunner;


    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<GanttRefreshedEventPayload> givenSubscriptionToGantt() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                this.papayaGanttDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.SIRIUS_WEB_PLANNING_PROJECT_OBJECT.toString(),
                "Gantt"
        );
        return this.givenCreatedGanttSubscription.createAndSubscribe(input);
    }

    @Test
    @DisplayName("Given a gantt representation, when we subscribe to its event, then the representation data are received")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenGanttRepresentationWhenWeSubscribeToItsEventThenTheRepresentationDataAreReceived() {
        var flux = this.givenSubscriptionToGantt();

        Consumer<GanttRefreshedEventPayload> initialGanttContentConsumer = payload -> Optional.of(payload)
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
    @DisplayName("Given a gantt representation, when we create a task, then the new task is visible")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenGanttRepresentationWhenWeCreateTaskThenTheNewTaskIsVisible() {
        var flux = this.givenSubscriptionToGantt();

        var ganttId = new AtomicReference<String>();
        var taskId = new AtomicReference<String>();
        Consumer<GanttRefreshedEventPayload> initialGanttContentConsumer = payload -> Optional.of(payload)
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
                    PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                    ganttId.get(),
                    taskId.get()
            );
            var result = this.createTaskMutationRunner.run(createGanttTaskInput);

            String typename = JsonPath.read(result, "$.data.createGanttTask.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<GanttRefreshedEventPayload> createGanttTaskConsumer = payload -> Optional.of(payload)
                .map(GanttRefreshedEventPayload::gantt)
                .ifPresentOrElse(gantt -> {
                    assertThat(gantt.tasks().get(0).subTasks().get(0).subTasks()).hasSize(3);
                }, () -> fail(MISSING_GANTT));

        StepVerifier.create(flux)
                .consumeNextWith(initialGanttContentConsumer)
                .then(createGanttTask)
                .consumeNextWith(createGanttTaskConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a gantt representation, when we delete a task, then the task is not visible anymore")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenGanttRepresentationWhenWeDeleteTaskThenTheTaskIsNotVisibleAnymore() {
        var flux = this.givenSubscriptionToGantt();

        var taskName = "Improve some features of the deck";

        var ganttId = new AtomicReference<String>();
        var taskId = new AtomicReference<String>();
        Consumer<GanttRefreshedEventPayload> initialGanttContentConsumer = payload -> Optional.of(payload)
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
                    PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                    ganttId.get(),
                    taskId.get()
            );
            var result = this.deleteTaskMutationRunner.run(deleteGanttTaskInput);

            String typename = JsonPath.read(result, "$.data.deleteGanttTask.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<GanttRefreshedEventPayload> deleteGanttTaskConsumer = payload -> Optional.of(payload)
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
    @DisplayName("Given a gantt representation, when the expand Task mutation is performed then it succeeds")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenGanttRepresentationWhenTheExpandTaskMutationIsPerformedThenItSucceeds() {
        var flux = this.givenSubscriptionToGantt();

        var ganttId = new AtomicReference<String>();
        var taskId = new AtomicReference<String>();
        Consumer<GanttRefreshedEventPayload> initialGanttContentConsumer = payload -> Optional.of(payload)
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
                    PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                    ganttId.get(),
                    taskId.get(),
                    true
            );
            var result = this.changeTaskCollapseStateMutationRunner.run(changeTaskCollapseStateInput);

            String typename = JsonPath.read(result, "$.data.changeGanttTaskCollapseState.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<GanttRefreshedEventPayload> collapseGanttTaskConsumer = payload -> Optional.of(payload)
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
    @DisplayName("Given a gantt representation, when the change column mutation is performed, then it succeeds")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenGanttRepresentationWhenChangeColumnMutationIsPerformedThenItSucceeds() {

        var flux = this.givenSubscriptionToGantt();

        var ganttRef = new AtomicReference<Gantt>();
        Consumer<GanttRefreshedEventPayload> initialGanttContentConsumer = payload -> Optional.of(payload)
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
                    PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                    ganttRef.get().getId(), columnToChange.id(), false, 50);
            var result = this.changeColumnMutationRunner.run(changeColumnInput);

            String typename = JsonPath.read(result, "$.data.changeGanttColumn.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<GanttRefreshedEventPayload> changeColumnConsumer = payload -> Optional.of(payload)
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
}
