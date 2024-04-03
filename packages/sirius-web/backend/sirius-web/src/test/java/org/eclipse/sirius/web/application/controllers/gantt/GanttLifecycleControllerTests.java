/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.GanttRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.DeleteGanttTaskInput;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.gantt.tests.graphql.DeleteTaskMutationRunner;
import org.eclipse.sirius.components.gantt.tests.navigation.GanttNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationData;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationDataRepository;
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
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of the lifecycle of the Gantt representation.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class GanttLifecycleControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedGanttSubscription givenCreatedGanttSubscription;

    @Autowired
    private PapayaGanttDescriptionProvider papayaGanttDescriptionProvider;

    @Autowired
    private DeleteTaskMutationRunner deleteTaskMutationRunner;

    @Autowired
    private IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    @Autowired
    private IRepresentationDataRepository representationDataRepository;

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
    @DisplayName("Given a gantt representation, when reload it, then the new version is sent")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenGanttRepresentationWhenWeReloadItThenTheNewVersionIsSent() {
        var flux = this.givenSubscriptionToGantt();

        var taskName = "Improve some features of the deck";

        var ganttId = new AtomicReference<String>();
        var taskId = new AtomicReference<String>();
        var representationData = new AtomicReference<RepresentationData>();

        Consumer<GanttRefreshedEventPayload> initialGanttContentConsumer = payload -> Optional.of(payload)
                .map(GanttRefreshedEventPayload::gantt)
                .ifPresentOrElse(gantt -> {
                    ganttId.set(gantt.getId());
                    assertThat(new GanttNavigator(gantt).existTaskByName(taskName)).isTrue();

                    var task = new GanttNavigator(gantt).findTaskByName(taskName);
                    taskId.set(task.id());

                    var representationId = new UUIDParser().parse(gantt.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid identifier"));
                    this.representationDataRepository.findById(representationId)
                            .ifPresentOrElse(representationData::set, () -> fail("Missing representation data"));
                }, () -> fail("Missing gantt"));


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
                }, () -> fail("Missing gantt"));

        Runnable reloadGantt = () -> {
            this.representationDataRepository.save(representationData.get());
            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();

            Consumer<IEditingContextEventProcessor> editingContextEventProcessorConsumer = editingContextEventProcessor -> {
                editingContextEventProcessor.getRepresentationEventProcessors().stream()
                        .filter(representationEventProcessor -> representationEventProcessor.getRepresentation().getId().equals(ganttId.get()))
                        .findFirst()
                        .ifPresentOrElse(representationEventProcessor -> {
                            IInput reloadInput = UUID::randomUUID;
                            representationEventProcessor.refresh(new ChangeDescription(ChangeKind.RELOAD_REPRESENTATION, ganttId.get(), reloadInput));
                        }, () -> fail("Missing representation event processor"));
            };
            this.editingContextEventProcessorRegistry.getEditingContextEventProcessors().stream()
                    .filter(editingContextEventProcessor -> editingContextEventProcessor.getEditingContextId().equals(PapayaIdentifiers.PAPAYA_PROJECT.toString()))
                    .findFirst()
                    .ifPresentOrElse(editingContextEventProcessorConsumer, () -> fail("Missing editing context event processor"));
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialGanttContentConsumer)
                .then(deleteGanttTask)
                .consumeNextWith(deleteGanttTaskConsumer)
                .then(reloadGantt)
                .consumeNextWith(initialGanttContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

    }
}
