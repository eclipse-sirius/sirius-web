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
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.portals.dto.AddPortalViewInput;
import org.eclipse.sirius.components.collaborative.portals.dto.LayoutPortalInput;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalEventInput;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalViewLayoutDataInput;
import org.eclipse.sirius.components.collaborative.portals.dto.RemovePortalViewInput;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.components.portals.tests.graphql.AddPortalViewMutationRunner;
import org.eclipse.sirius.components.portals.tests.graphql.LayoutPortalMutationRunner;
import org.eclipse.sirius.components.portals.tests.graphql.PortalEventSubscriptionRunner;
import org.eclipse.sirius.components.portals.tests.graphql.RemovePortalViewMutationRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.portal.services.PortalDescriptionProvider;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationContent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationContentRepository;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.portals.GivenCreatedPortalSubscription;
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
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class PortalControllerIntegrationTests extends AbstractIntegrationTests {

    private static final String SAMPLE_PORTAL = "Sample Portal";

    private static final String PORTAL_VIEWS_REPRESENTATION_METADATA_PORTAL_EVENT_SUBSCRIPTION = """
            subscription portalEvent($input: PortalEventInput!) {
              portalEvent(input: $input) {
                __typename
                ... on PortalRefreshedEventPayload {
                  portal {
                    views {
                      representationMetadata {
                        label
                      }
                    }
                  }
                }
              }
            }
            """;

    @Autowired
    private PortalEventSubscriptionRunner portalEventSubscriptionRunner;

    @Autowired
    private IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    @Autowired
    private GivenCreatedPortalSubscription givenCreatedPortalSubscription;

    @Autowired
    private IGivenCommittedTransaction givenCommittedTransaction;

    @Autowired
    private IRepresentationContentRepository representationContentRepository;

    @Autowired
    private LayoutPortalMutationRunner layoutPortalMutationRunner;

    @Autowired
    private AddPortalViewMutationRunner addPortalViewMutationRunner;

    @Autowired
    private RemovePortalViewMutationRunner removePortalViewMutationRunner;

    @Autowired
    private IGraphQLRequestor graphQLRequestor;

    @BeforeEach
    public void beforeEach() {
        this.editingContextEventProcessorRegistry.getEditingContextEventProcessors().stream().map(IEditingContextEventProcessor::getEditingContextId)
                .forEach(this.editingContextEventProcessorRegistry::disposeEditingContextEventProcessor);
    }

    @Test
    @DisplayName("Given a portal containing a view, when we delete the portal representation, then it is removed")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void removePortalViewFromPortal() {
        var input = new PortalEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());
        var flux = this.portalEventSubscriptionRunner.run(input);

        var portalViewId = new AtomicReference<String>();
        var portalViewCount = new AtomicReference<Integer>();

        Consumer<Object> portalRefreshedEventPayloadConsumer = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(PortalRefreshedEventPayload.class::isInstance)
                .map(PortalRefreshedEventPayload.class::cast)
                .map(PortalRefreshedEventPayload::portal)
                .ifPresentOrElse(portal -> {
                    assertThat(portal.getViews()).isNotEmpty();
                    portalViewId.set(portal.getViews().get(0).getId());
                    portalViewCount.set(portal.getViews().size());
                    assertThat(portal.getLayoutData().size()).isEqualTo(portalViewCount.get());
                    assertThat(portal.getLayoutData().get(0).getPortalViewId()).isEqualTo(portalViewId.get());
                }, () -> fail("Missing portal"));

        Runnable removePortalView = () -> {
            var removePortalViewInput = new RemovePortalViewInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString(),
                    portalViewId.get());
            var result = this.removePortalViewMutationRunner.run(removePortalViewInput);
            String typename = JsonPath.read(result, "$.data.removePortalView.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedPortalContentConsumer = payload -> Optional.of(payload)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(PortalRefreshedEventPayload.class::isInstance)
                .map(PortalRefreshedEventPayload.class::cast)
                .map(PortalRefreshedEventPayload::portal)
                .ifPresentOrElse(portal -> {
                    assertThat(portal.getViews().size()).isEqualTo(portalViewCount.get() - 1);
                    assertThat(portal.getViews()).noneMatch(portalView -> portalViewId.get().equals(portalView.getId()));
                    assertThat(portal.getLayoutData().size()).isEqualTo(portal.getViews().size());
                    assertThat(portal.getLayoutData()).noneMatch(portalViewLayoutData -> portalViewId.get().equals(portalViewLayoutData.getPortalViewId()));
                }, () -> fail("Portal should have been updated"));

        StepVerifier.create(flux)
                .consumeNextWith(portalRefreshedEventPayloadConsumer)
                .then(removePortalView)
                .consumeNextWith(updatedPortalContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a portal containing a view, when we ask the portal view metadata, metadata are sent")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void getMetadataOfPortalView() {
        var input = new PortalEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());
        var flux = this.graphQLRequestor.subscribeToSpecification(PORTAL_VIEWS_REPRESENTATION_METADATA_PORTAL_EVENT_SUBSCRIPTION, input);

        Consumer<String> portalRefreshedEventPayloadConsumer = payload -> Optional.of(payload)
                .ifPresentOrElse(body -> {
                    var typename = JsonPath.read(body, "$.data.portalEvent.__typename");
                    assertThat(typename).isEqualTo(PortalRefreshedEventPayload.class.getSimpleName());

                    List<String> portalViewsLabel = JsonPath.read(body, "$.data.portalEvent.portal.views[*].representationMetadata.label");
                    assertThat(portalViewsLabel).isNotEmpty();
                }, () -> fail("Missing portal"));

        StepVerifier.create(flux)
                .consumeNextWith(portalRefreshedEventPayloadConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

    }

    @Test
    @DisplayName("Given a portal, when we add a representation to the portal, then the new state is sent with the new representation")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void addRepresentationToNewCreatedPortal() {
        var createRepresentationInput = new CreateRepresentationInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), PortalDescriptionProvider.DESCRIPTION_ID,
                TestIdentifiers.EPACKAGE_OBJECT.toString(), SAMPLE_PORTAL);
        var flux = this.givenCreatedPortalSubscription.createAndSubscribe(createRepresentationInput);

        var portalId = new AtomicReference<String>();

        Consumer<Object> initialPortalContentConsumer = payload -> Optional.of(payload)
                .filter(PortalRefreshedEventPayload.class::isInstance)
                .map(PortalRefreshedEventPayload.class::cast)
                .map(PortalRefreshedEventPayload::portal)
                .ifPresentOrElse(portal -> {
                    assertThat(portal.getLabel()).isEqualTo(SAMPLE_PORTAL);
                    assertThat(portal.getViews()).isEmpty();
                    assertThat(portal.getLayoutData()).isEmpty();
                    portalId.set(portal.getId());
                }, () -> fail("Missing portal"));

        Runnable addEmptyPortal = () -> {
            var addPortalViewInput = new AddPortalViewInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), portalId.get(),
                    TestIdentifiers.EPACKAGE_EMPTY_PORTAL_REPRESENTATION.toString(), 0, 0, 200, 300);
            var result = this.addPortalViewMutationRunner.run(addPortalViewInput);

            String typename = JsonPath.read(result, "$.data.addPortalView.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedPortalContentConsumer = payload -> Optional.of(payload)
                .filter(PortalRefreshedEventPayload.class::isInstance)
                .map(PortalRefreshedEventPayload.class::cast)
                .map(PortalRefreshedEventPayload::portal)
                .ifPresent(portal -> {
                    assertThat(portal.getViews()).isNotEmpty();
                    var portalView = portal.getViews().get(0);
                    assertThat(portalView.getRepresentationId()).isEqualTo(TestIdentifiers.EPACKAGE_EMPTY_PORTAL_REPRESENTATION.toString());
                    assertThat(portal.getLayoutData()).isNotEmpty();
                    var portalViewLayoutData = portal.getLayoutData().get(0);
                    assertThat(portalViewLayoutData.getX()).isEqualTo(0);
                    assertThat(portalViewLayoutData.getY()).isEqualTo(0);
                    assertThat(portalViewLayoutData.getWidth()).isEqualTo(200);
                    assertThat(portalViewLayoutData.getHeight()).isEqualTo(300);
                });

        StepVerifier.create(flux)
                .consumeNextWith(initialPortalContentConsumer)
                .then(addEmptyPortal)
                .consumeNextWith(updatedPortalContentConsumer)
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

        Consumer<Object> initialPortalContentConsumer = payload -> Optional.of(payload)
                .filter(PortalRefreshedEventPayload.class::isInstance)
                .map(PortalRefreshedEventPayload.class::cast)
                .map(PortalRefreshedEventPayload::portal)
                .ifPresentOrElse(portal -> {
                    assertThat(portal.getLabel()).isEqualTo("Sample Portal");
                    assertThat(portal.getViews()).isEmpty();
                    assertThat(portal.getLayoutData()).isEmpty();
                }, () -> fail("Missing Portal"));

        StepVerifier.create(flux)
                .consumeNextWith(initialPortalContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given an arbitrary semantic element, when creating a portal, then we can move an existing one inside it")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenAnArbitrarySemanticElementWhenCreatingAPortalThenWeCanMoveAnExistingOneInsideIt() {
        var representationId = new AtomicReference<String>();

        Runnable addViewRunner = () -> this.addView(representationId.get(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());

        var input = new CreateRepresentationInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), PortalDescriptionProvider.DESCRIPTION_ID,
                TestIdentifiers.EPACKAGE_OBJECT.toString(), SAMPLE_PORTAL);


        var flux = this.givenCreatedPortalSubscription.createAndSubscribe(input);

        Consumer<Object> initialPortalContentConsumer = payload -> Optional.of(payload)
                .filter(PortalRefreshedEventPayload.class::isInstance)
                .map(PortalRefreshedEventPayload.class::cast)
                .map(PortalRefreshedEventPayload::portal)
                .ifPresentOrElse(portal -> {
                    representationId.set(portal.getId());
                    assertThat(portal.getLabel()).isEqualTo("Sample Portal");
                    assertThat(portal.getViews()).isEmpty();
                    assertThat(portal.getLayoutData()).isEmpty();
                }, () -> fail("Missing Portal"));

        Consumer<Object> secontPortalContentConsumer = payload -> Optional.of(payload)
                .filter(PortalRefreshedEventPayload.class::isInstance)
                .map(PortalRefreshedEventPayload.class::cast)
                .map(PortalRefreshedEventPayload::portal)
                .ifPresentOrElse(portal -> {
                    assertThat(portal.getLabel()).isEqualTo("Sample Portal");
                    assertThat(portal.getViews().size()).isEqualTo(1);
                    assertThat(portal.getLayoutData().size()).isEqualTo(1);
                }, () -> fail("Missing Portal"));

        StepVerifier.create(flux)
                .consumeNextWith(initialPortalContentConsumer)
                .then(addViewRunner)
                .consumeNextWith(secontPortalContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private void addView(String representationId, String viewRepresentationId) {
        var addPortalViewMutationInput = new AddPortalViewInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(),
                representationId, viewRepresentationId, 0, 0, 100, 100);

        String result = this.addPortalViewMutationRunner.run(addPortalViewMutationInput);
        this.givenCommittedTransaction.commit();
        String typename = JsonPath.read(result, "$.data.addPortalView.__typename");
        assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
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
        AtomicReference<RepresentationContent> initialPortalState = new AtomicReference<>(null);

        Consumer<Object> initialPortalContentConsumer = this.portalRefreshedConsumer(portal -> {
            assertThat(portal.getLayoutData()).hasSize(1);
            var layoutData = portal.getLayoutData().get(0);
            assertThat(layoutData.getPortalViewId()).isEqualTo(subPortalViewId);
            assertThat(layoutData.getX()).isZero();
            assertThat(layoutData.getY()).isZero();
            assertThat(layoutData.getWidth()).isEqualTo(500);
            assertThat(layoutData.getHeight()).isEqualTo(200);
            var optionalRepresentationMetadata = this.representationContentRepository.findContentByRepresentationMetadataId(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION);
            assertThat(optionalRepresentationMetadata).isPresent();
            initialPortalState.set(optionalRepresentationMetadata.get());
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
            var optionalRepresentationMetadata = this.representationContentRepository.findContentByRepresentationMetadataId(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION);
            assertThat(optionalRepresentationMetadata).isPresent();
        });

        Runnable reloadInitialPortalState = () -> {
            this.representationContentRepository.save(initialPortalState.get());
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
                .ifPresentOrElse(portalConsumer, () -> fail("Missing Portal"));
    }
}
