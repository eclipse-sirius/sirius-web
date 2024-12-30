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

package org.eclipse.sirius.web.application.controllers.portal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.assertj.core.api.Assertions;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.portals.dto.AddPortalViewInput;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalEventInput;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.portals.dto.RemovePortalViewInput;
import org.eclipse.sirius.components.core.api.RedoInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.core.api.UndoInput;
import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.components.portals.tests.graphql.AddPortalViewMutationRunner;
import org.eclipse.sirius.components.portals.tests.graphql.PortalEventSubscriptionRunner;
import org.eclipse.sirius.components.portals.tests.graphql.RemovePortalViewMutationRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.portal.services.PortalDescriptionProvider;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationMetadataRepository;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.portals.GivenCreatedPortalSubscription;
import org.eclipse.sirius.web.tests.undoredo.RedoMutationRunner;
import org.eclipse.sirius.web.tests.undoredo.UndoMutationRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import graphql.execution.DataFetcherResult;
import reactor.test.StepVerifier;

/**
 * Integration tests of undo/redo on portal.
 *
 * @author mcharfadi
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PortalUndoRedoControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    @Autowired
    private PortalEventSubscriptionRunner portalEventSubscriptionRunner;

    @Autowired
    private GivenCreatedPortalSubscription givenCreatedPortalSubscription;

    @Autowired
    private IGivenCommittedTransaction givenCommittedTransaction;

    @Autowired
    private AddPortalViewMutationRunner addPortalViewMutationRunner;

    @Autowired
    private RemovePortalViewMutationRunner removePortalViewMutationRunner;

    @Autowired
    private IRepresentationMetadataRepository representationMetadataRepository;

    @Autowired
    private UndoMutationRunner undoMutationRunner;

    @Autowired
    private RedoMutationRunner redoMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.editingContextEventProcessorRegistry.getEditingContextEventProcessors().stream().map(IEditingContextEventProcessor::getEditingContextId)
                .forEach(this.editingContextEventProcessorRegistry::disposeEditingContextEventProcessor);
    }

    @Test
    @DisplayName("Given an arbitrary semantic element, when creating a portal, then we can move an existing one inside it then undo and redo")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenAnArbitrarySemanticElementWhenCreatingAPortalThenWeCanMoveAnExistingOneInsideItUndoAndRedo() {
        var representationId = new AtomicReference<String>();

        String mutationId = UUID.randomUUID().toString();
        Runnable addViewRunner = () -> this.addView(mutationId, representationId.get(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());

        var createRepresentationInput = new CreateRepresentationInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), PortalDescriptionProvider.DESCRIPTION_ID,
                TestIdentifiers.EPACKAGE_OBJECT.toString(), "Sample Portal");

        var flux = this.givenCreatedPortalSubscription.createAndSubscribe(createRepresentationInput);

        Runnable undoMutation = () -> {
            var input = new UndoInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), mutationId);
            var result = this.undoMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.undo.__typename");
            Assertions.assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Runnable redoMutation = () -> {
            var input = new RedoInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), mutationId);
            var result = this.redoMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.redo.__typename");
            Assertions.assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> initialPortalContentConsumer = payload -> Optional.of(payload)
                .filter(PortalRefreshedEventPayload.class::isInstance)
                .map(PortalRefreshedEventPayload.class::cast)
                .map(PortalRefreshedEventPayload::portal)
                .ifPresentOrElse(portal -> {
                    representationId.set(portal.getId());
                    assertThat(portal.getViews()).isEmpty();
                    assertThat(portal.getLayoutData()).isEmpty();
                }, () -> fail("Missing Portal"));

        Consumer<Object> secontPortalContentConsumer = payload -> Optional.of(payload)
                .filter(PortalRefreshedEventPayload.class::isInstance)
                .map(PortalRefreshedEventPayload.class::cast)
                .map(PortalRefreshedEventPayload::portal)
                .ifPresentOrElse(portal -> {
                    assertThat(portal.getViews().size()).isEqualTo(1);
                    assertThat(portal.getLayoutData().size()).isEqualTo(1);
                }, () -> fail("Missing Portal"));

        StepVerifier.create(flux)
                .consumeNextWith(initialPortalContentConsumer)
                .then(addViewRunner)
                .consumeNextWith(secontPortalContentConsumer)
                .then(undoMutation)
                .consumeNextWith(initialPortalContentConsumer)
                .then(redoMutation)
                .consumeNextWith(secontPortalContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given an arbitrary portal, when deleting a view inside a portal, then we can undo and redo the change")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenAnArbitraryPortalWhenDeletingAViewWeCanUndoRedoTheChange() {
        this.givenCommittedTransaction.commit();

        var subPortalViewId = "bbf6c9b9-ee36-32c5-aee0-f4d64efcccb7";
        var representationId = new AtomicReference<String>();

        String mutationId = UUID.randomUUID().toString();
        Runnable removeViewRunner = () -> this.removeView(mutationId, representationId.get(), subPortalViewId);

        var portalEventInput = new PortalEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());
        var flux = this.portalEventSubscriptionRunner.run(portalEventInput);

        Runnable undoMutation = () -> {
            var input = new UndoInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), mutationId);
            var result = this.undoMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.undo.__typename");
            Assertions.assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Runnable redoMutation = () -> {
            var input = new RedoInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), mutationId);
            var result = this.redoMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.redo.__typename");
            Assertions.assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> initialPortalContentConsumer = this.portalRefreshedConsumer(portal -> {
            representationId.set(portal.getId());
            assertThat(portal.getLayoutData()).hasSize(1);
            assertThat(portal.getLayoutData()).hasSize(1);
            var layoutData = portal.getLayoutData().get(0);
            assertThat(layoutData.getPortalViewId()).isEqualTo(subPortalViewId);
            assertThat(layoutData.getX()).isZero();
            assertThat(layoutData.getY()).isZero();
            assertThat(layoutData.getWidth()).isEqualTo(500);
            assertThat(layoutData.getHeight()).isEqualTo(200);
            var optionalRepresentationData = this.representationMetadataRepository.findById(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION);
            assertThat(optionalRepresentationData).isPresent();
        });

        Consumer<Object> secondPortalContentConsumer = this.portalRefreshedConsumer(portal -> {
            assertThat(portal.getLayoutData()).isEmpty();
            assertThat(portal.getViews()).isEmpty();
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialPortalContentConsumer)
                .then(removeViewRunner)
                .consumeNextWith(secondPortalContentConsumer)
                .then(undoMutation)
                .consumeNextWith(initialPortalContentConsumer)
                .then(redoMutation)
                .consumeNextWith(secondPortalContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private void addView(String mutationId, String representationId, String viewRepresentationId) {
        var addPortalViewMutationInput = new AddPortalViewInput(UUID.fromString(mutationId), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(),
                representationId, viewRepresentationId, 0, 0, 100, 100);

        String result = this.addPortalViewMutationRunner.run(addPortalViewMutationInput);
        this.givenCommittedTransaction.commit();
        String typename = JsonPath.read(result, "$.data.addPortalView.__typename");
        assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
    }

    private void removeView(String mutationId, String representationId, String portalViewId) {
        var removePortalViewInput = new RemovePortalViewInput(UUID.fromString(mutationId), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(),
                representationId, portalViewId);

        String result = this.removePortalViewMutationRunner.run(removePortalViewInput);
        this.givenCommittedTransaction.commit();
        String typename = JsonPath.read(result, "$.data.removePortalView.__typename");
        assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
    }

    private Consumer<Object> portalRefreshedConsumer(Consumer<Portal> portalConsumer) {
        return payload -> Optional.of(payload)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(PortalRefreshedEventPayload.class::isInstance)
                .map(PortalRefreshedEventPayload.class::cast)
                .map(PortalRefreshedEventPayload::portal)
                .ifPresentOrElse(portalConsumer, () -> fail("Missing Portal"));
    }
}
