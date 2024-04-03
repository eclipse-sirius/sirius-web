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

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.portals.dto.LayoutPortalInput;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalEventInput;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalViewLayoutDataInput;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.components.portals.tests.graphql.PortalEventSubscriptionRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.portal.services.PortalDescriptionProvider;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationData;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationDataRepository;
import org.eclipse.sirius.web.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.services.portals.GivenCreatedPortalSubscription;
import org.eclipse.sirius.web.services.portals.LayoutPortalMutationRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import graphql.execution.DataFetcherResult;
import reactor.test.StepVerifier;

/**
 * Integration tests of the portal controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PortalControllerIntegrationTests extends AbstractIntegrationTests {

    private static final String SAMPLE_PORTAL = "Sample Portal";

    @Autowired
    private PortalEventSubscriptionRunner portalEventSubscriptionRunner;

    @Autowired
    private IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    @Autowired
    private GivenCreatedPortalSubscription givenCreatedPortalSubscription;

    @Autowired
    private IGivenCommittedTransaction givenCommittedTransaction;

    @Autowired
    private IRepresentationDataRepository representationDataRepository;

    @Autowired
    private LayoutPortalMutationRunner layoutPortalMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.editingContextEventProcessorRegistry.getEditingContextEventProcessors().stream().map(IEditingContextEventProcessor::getEditingContextId)
                .forEach(this.editingContextEventProcessorRegistry::disposeEditingContextEventProcessor);
    }

    @Test
    @DisplayName("Given a portal, when we subscribe to portal events, then the current state of the portal is sent")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenPortalWhenWeSubscribeToPortalEventsThenCurrentStateOfThePortalIsSent() {
        var input = new PortalEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());
        var flux = this.portalEventSubscriptionRunner.run(input);

        Predicate<Object> portalRefreshedEventPayloadMatcher = object -> Optional.of(object).filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(PortalRefreshedEventPayload.class::isInstance)
                .isPresent();

        StepVerifier.create(flux)
                    .expectNextMatches(portalRefreshedEventPayloadMatcher)
                    .thenCancel()
                    .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given an arbitrary semantic element, when creating portal on it, then an empty portal is created")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenAnArbitrarySemanticElementWhenCreatingPortalOnItThenEmptyPortalIsCreated() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), PortalDescriptionProvider.DESCRIPTION_ID,
                TestIdentifiers.EPACKAGE_OBJECT.toString(), SAMPLE_PORTAL);
        var flux = this.givenCreatedPortalSubscription.createAndSubscribe(input);

        Consumer<PortalRefreshedEventPayload> initialPortalContentConsumer = payload -> Optional.of(payload).map(PortalRefreshedEventPayload::portal).ifPresentOrElse(portal -> {
            assertThat(portal.getLabel()).isEqualTo(SAMPLE_PORTAL);
            assertThat(portal.getViews()).isEmpty();
            assertThat(portal.getLayoutData()).isEmpty();
        }, () -> fail("Missing portal"));

        StepVerifier.create(flux)
                    .consumeNextWith(initialPortalContentConsumer)
                    .thenCancel()
                    .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a portal, when reloading a previous state after changing the portal layout, then the original layout is reverted")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenAPortalWhenReloadingPreviousSateAfterLayoutChangeThenPortalLayoutReverted() {
        this.givenCommittedTransaction.commit();

        var portalEventInput = new PortalEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());
        var flux = this.portalEventSubscriptionRunner.run(portalEventInput);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        var subPortalViewId = "9e277e97-7f71-4bdd-99af-9eeb8bd7f2df";
        AtomicReference<RepresentationData> initialPortalState = new AtomicReference<>(null);
        AtomicReference<RepresentationData> layoutedPortalState = new AtomicReference<>(null);

        Consumer<Object> initialPortalContentConsumer = this.portalRefreshedConsumer(portal -> {
            assertThat(portal.getLayoutData()).hasSize(1);
            var layoutData = portal.getLayoutData().get(0);
            assertThat(layoutData.getPortalViewId()).isEqualTo(subPortalViewId);
            assertThat(layoutData.getX()).isZero();
            assertThat(layoutData.getY()).isZero();
            assertThat(layoutData.getWidth()).isEqualTo(500);
            assertThat(layoutData.getHeight()).isEqualTo(200);
            var optionalRepresentationData = this.representationDataRepository.findById(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION);
            assertThat(optionalRepresentationData).isPresent();
            initialPortalState.set(optionalRepresentationData.get());
        });

        Runnable layoutSubPortal = () -> {
            var layoutData = List.of(new PortalViewLayoutDataInput(subPortalViewId, 50, 50, 300, 300));
            var layoutInput = new LayoutPortalInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString(), layoutData);
            this.layoutPortalMutationRunner.run(layoutInput);
        };

        Consumer<Object> layoutedPortalContentConsumer = this.portalRefreshedConsumer(portal -> {
            assertThat(portal.getLayoutData()).hasSize(1);
            var layoutData = portal.getLayoutData().get(0);
            assertThat(layoutData.getPortalViewId()).isEqualTo(subPortalViewId);
            assertThat(layoutData.getX()).isEqualTo(50);
            assertThat(layoutData.getY()).isEqualTo(50);
            assertThat(layoutData.getWidth()).isEqualTo(300);
            assertThat(layoutData.getHeight()).isEqualTo(300);
            var optionalRepresentationData = this.representationDataRepository.findById(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION);
            assertThat(optionalRepresentationData).isPresent();
            layoutedPortalState.set(optionalRepresentationData.get());
        });

        Runnable reloadInitialPortalState = () -> {
            this.representationDataRepository.save(initialPortalState.get());
            TestTransaction.flagForCommit();
            TestTransaction.end();

            Consumer<IEditingContextEventProcessor> editingContextEventProcessorConsumer = editingContextEventProcessor -> {
                editingContextEventProcessor.getRepresentationEventProcessors().stream()
                        .filter(representationEventProcessor -> representationEventProcessor.getRepresentation().getId().equals(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString()))
                        .findFirst()
                        .ifPresentOrElse(representationEventProcessor -> {
                            IInput reloadInput = UUID::randomUUID;
                            representationEventProcessor.refresh(new ChangeDescription(ChangeKind.RELOAD_REPRESENTATION, TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString(), reloadInput));
                        }, () -> fail("Missing representation event processor"));
            };
            this.editingContextEventProcessorRegistry.getEditingContextEventProcessors().stream()
                    .filter(editingContextEventProcessor -> editingContextEventProcessor.getEditingContextId().equals(TestIdentifiers.ECORE_SAMPLE_PROJECT.toString()))
                    .findFirst()
                    .ifPresentOrElse(editingContextEventProcessorConsumer, () -> fail("Missing editing context event processor"));
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialPortalContentConsumer)
                .then(layoutSubPortal)
                .consumeNextWith(layoutedPortalContentConsumer)
                .then(reloadInitialPortalState)
                .consumeNextWith(initialPortalContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private Consumer<Object> portalRefreshedConsumer(Consumer<Portal> portalConsumer) {
        return payload -> Optional.of(payload)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(PortalRefreshedEventPayload.class::isInstance)
                .map(PortalRefreshedEventPayload.class::cast)
                .map(PortalRefreshedEventPayload::portal)
                .ifPresentOrElse(portalConsumer, () -> fail("Missing portal"));
    }
}
