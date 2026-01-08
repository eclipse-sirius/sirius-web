/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import static org.eclipse.sirius.components.portals.tests.PortalEventPayloadConsumer.assertRefreshedPortalThat;
import static org.eclipse.sirius.components.trees.tests.TreeEventPayloadConsumer.assertRefreshedTreeThat;

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
import org.eclipse.sirius.components.collaborative.trees.dto.DeleteTreeItemInput;
import org.eclipse.sirius.components.collaborative.trees.services.api.ITreeQueryService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.portals.PortalView;
import org.eclipse.sirius.components.portals.tests.graphql.AddPortalViewMutationRunner;
import org.eclipse.sirius.components.portals.tests.graphql.LayoutPortalMutationRunner;
import org.eclipse.sirius.components.portals.tests.graphql.PortalEventSubscriptionRunner;
import org.eclipse.sirius.components.portals.tests.graphql.RemovePortalViewMutationRunner;
import org.eclipse.sirius.components.trees.tests.graphql.DeleteTreeItemMutationRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.portal.services.PortalDescriptionProvider;
import org.eclipse.sirius.web.application.views.explorer.ExplorerEventInput;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationContent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationContentRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.RepresentationCompositeIdProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.explorer.ExplorerEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.portals.GivenCreatedPortalSubscription;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
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
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private PortalEventSubscriptionRunner portalEventSubscriptionRunner;

    @Autowired
    private IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    @Autowired
    private GivenCreatedPortalSubscription givenCreatedPortalSubscription;

    @Autowired
    private IRepresentationContentRepository representationContentRepository;

    @Autowired
    private LayoutPortalMutationRunner layoutPortalMutationRunner;

    @Autowired
    private AddPortalViewMutationRunner addPortalViewMutationRunner;

    @Autowired
    private RemovePortalViewMutationRunner removePortalViewMutationRunner;

    @Autowired
    private ExplorerEventSubscriptionRunner explorerEventSubscriptionRunner;

    @Autowired
    private ITreeQueryService treeQueryService;

    @Autowired
    private DeleteTreeItemMutationRunner deleteTreeItemMutationRunner;

    @Autowired
    private IGraphQLRequestor graphQLRequestor;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a portal containing a view, when we delete the portal representation, then it is removed")
    public void givenAPortalContainingAViewWhenWeDeleteThePortalRepresentationThenItIsRemoved() {
        var input = new PortalEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());
        var flux = this.portalEventSubscriptionRunner.run(input).flux();

        var portalViewId = new AtomicReference<String>();
        var portalViewCount = new AtomicReference<Integer>();

        Consumer<Object> initialPortalConsumer = assertRefreshedPortalThat(portal -> {
            assertThat(portal.getViews()).isNotEmpty();
            portalViewId.set(portal.getViews().get(0).getId());
            portalViewCount.set(portal.getViews().size());
            assertThat(portal.getLayoutData().size()).isEqualTo(portalViewCount.get());
            assertThat(portal.getLayoutData().get(0).getPortalViewId()).isEqualTo(portalViewId.get());
        });

        Runnable removePortalView = () -> {
            var removePortalViewInput = new RemovePortalViewInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString(),
                    portalViewId.get());
            var result = this.removePortalViewMutationRunner.run(removePortalViewInput);
            String typename = JsonPath.read(result.data(), "$.data.removePortalView.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedPortalContentConsumer = assertRefreshedPortalThat(portal -> {
            assertThat(portal.getViews().size()).isEqualTo(portalViewCount.get() - 1);
            assertThat(portal.getViews()).noneMatch(portalView -> portalViewId.get().equals(portalView.getId()));
            assertThat(portal.getLayoutData().size()).isEqualTo(portal.getViews().size());
            assertThat(portal.getLayoutData()).noneMatch(portalViewLayoutData -> portalViewId.get().equals(portalViewLayoutData.getPortalViewId()));
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialPortalConsumer)
                .then(removePortalView)
                .consumeNextWith(updatedPortalContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a portal containing a view, when we ask the portal view metadata, then metadata are sent")
    public void givenAPortalContainingAViewWhenWeAskThePortalViewMetadataThenMetadataAreSent() {
        var input = new PortalEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());
        var flux = this.graphQLRequestor.subscribeToSpecification(PORTAL_VIEWS_REPRESENTATION_METADATA_PORTAL_EVENT_SUBSCRIPTION, input)
                .flux()
                .map(Object::toString);

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
    @GivenSiriusWebServer
    @DisplayName("Given a portal, when we add a representation to the portal, then the new state is sent with the new representation")
    public void givenAPortalWhenWeAddARepresentationToThePortalThenTheNewStateIsSentWithTheNewRepresentation() {
        var createRepresentationInput = new CreateRepresentationInput(
                UUID.randomUUID(),
                TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID,
                PortalDescriptionProvider.DESCRIPTION_ID,
                TestIdentifiers.EPACKAGE_OBJECT.toString(),
                SAMPLE_PORTAL
        );
        var flux = this.givenCreatedPortalSubscription.createAndSubscribe(createRepresentationInput).flux();

        var portalId = new AtomicReference<String>();

        Consumer<Object> initialPortalContentConsumer = assertRefreshedPortalThat(portal -> {
            assertThat(portal.getViews()).isEmpty();
            assertThat(portal.getLayoutData()).isEmpty();
            portalId.set(portal.getId());
        });

        Runnable addEmptyPortal = () -> {
            var addPortalViewInput = new AddPortalViewInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, portalId.get(),
                    TestIdentifiers.EPACKAGE_EMPTY_PORTAL_REPRESENTATION.toString(), 0, 0, 200, 300);
            var result = this.addPortalViewMutationRunner.run(addPortalViewInput);

            String typename = JsonPath.read(result.data(), "$.data.addPortalView.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedPortalContentConsumer = assertRefreshedPortalThat(portal -> {
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
    @GivenSiriusWebServer
    @DisplayName("Given a portal, when we add an existing representation to the portal, then an error should be returned")
    public void givenAPortalWhenWeAddAnExistingRepresentationToThePortalThenAnErrorShouldBeReturned() {
        var portalEventInput = new PortalEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());
        var flux = this.portalEventSubscriptionRunner.run(portalEventInput).flux();

        Runnable addExistingRepresentation = () -> {
            var addPortalViewInput = new AddPortalViewInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString(),
                    TestIdentifiers.EPACKAGE_EMPTY_PORTAL_REPRESENTATION.toString(), 0, 0, 200, 300);
            var result = this.addPortalViewMutationRunner.run(addPortalViewInput);

            String typename = JsonPath.read(result.data(), "$.data.addPortalView.__typename");
            assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());
        };


        StepVerifier.create(flux)
                .then(addExistingRepresentation)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an expanded explorer containing a portal with a representation, when we delete the tree item representing the representation, then the portal should be refreshed")
    public void givenExpandedExplorerContainingPortalWithRepresentationWhenWeDeleteTheTreeItemRepresentingTheRepresentationThenThePortalShouldBeRefreshed() {
        var representationIdBuilder = new RepresentationIdBuilder();
        var explorerId = representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID,
                List.of(
                        TestIdentifiers.ECORE_SAMPLE_DOCUMENT.toString(),
                        TestIdentifiers.EPACKAGE_OBJECT.toString(),
                        TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString(),
                        TestIdentifiers.EPACKAGE_EMPTY_PORTAL_REPRESENTATION.toString(),
                        TestIdentifiers.ECLASS_OBJECT.toString()
                ),
                List.of());
        var explorerEventFlux = this.explorerEventSubscriptionRunner.run(new ExplorerEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, explorerId)).flux();

        var portalEventInput = new PortalEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());
        var portalEventFlux = this.portalEventSubscriptionRunner.run(portalEventInput).flux();
        var flux = Flux.merge(explorerEventFlux, portalEventFlux);

        Consumer<Object> initialPortalContentConsumer = assertRefreshedPortalThat(portal -> {
            assertThat(portal.getId()).isEqualTo(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());
            assertThat(portal.getViews()).map(PortalView::getRepresentationId).contains(TestIdentifiers.EPACKAGE_EMPTY_PORTAL_REPRESENTATION.toString());
            assertThat(portal.getLayoutData()).isNotEmpty();
        });

        var treeInstanceId = new AtomicReference<String>();
        Consumer<Object> intialTreeConsumer = assertRefreshedTreeThat(tree -> {
            treeInstanceId.set(tree.getId());
        });

        Runnable removeExistingRepresentation = () -> {
            var input = new DeleteTreeItemInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, treeInstanceId.get(), TestIdentifiers.EPACKAGE_EMPTY_PORTAL_REPRESENTATION);
            var result = this.deleteTreeItemMutationRunner.run(input);

            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();

            String typename = JsonPath.read(result.data(), "$.data.deleteTreeItem.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedExplorerContentConsumer = assertRefreshedTreeThat(tree -> {
            var optionalTreeItem = this.treeQueryService.findTreeItem(tree, TestIdentifiers.EPACKAGE_EMPTY_PORTAL_REPRESENTATION.toString());
            assertThat(optionalTreeItem).isEmpty();
        });

        Consumer<Object> updatedPortalContentConsumer = assertRefreshedPortalThat(portal -> {
            assertThat(portal.getId()).isEqualTo(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());
            assertThat(portal.getViews()).isEmpty();
            assertThat(portal.getLayoutData()).isEmpty();
        });

        StepVerifier.create(flux)
                .consumeNextWith(intialTreeConsumer)
                .consumeNextWith(initialPortalContentConsumer)
                .then(removeExistingRepresentation)
                .consumeNextWith(updatedExplorerContentConsumer)
                .consumeNextWith(updatedPortalContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an arbitrary semantic element, when creating portal on it, then an empty portal is created")
    public void givenAnArbitrarySemanticElementWhenCreatingPortalOnItThenEmptyPortalIsCreated() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, PortalDescriptionProvider.DESCRIPTION_ID,
                TestIdentifiers.EPACKAGE_OBJECT.toString(), SAMPLE_PORTAL);
        var flux = this.givenCreatedPortalSubscription.createAndSubscribe(input).flux();

        Consumer<Object> initialPortalContentConsumer = assertRefreshedPortalThat(portal -> {
            assertThat(portal.getViews()).isEmpty();
            assertThat(portal.getLayoutData()).isEmpty();
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialPortalContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an arbitrary semantic element, when creating a portal, then we can move an existing one inside it")
    public void givenAnArbitrarySemanticElementWhenCreatingAPortalThenWeCanMoveAnExistingOneInsideIt() {
        var representationId = new AtomicReference<String>();

        Runnable addViewRunner = () -> this.addView(representationId.get(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());

        var input = new CreateRepresentationInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, PortalDescriptionProvider.DESCRIPTION_ID,
                TestIdentifiers.EPACKAGE_OBJECT.toString(), SAMPLE_PORTAL);


        var flux = this.givenCreatedPortalSubscription.createAndSubscribe(input).flux();

        Consumer<Object> initialPortalContentConsumer = assertRefreshedPortalThat(portal -> {
            representationId.set(portal.getId());
            assertThat(portal.getViews()).isEmpty();
            assertThat(portal.getLayoutData()).isEmpty();
        });

        Consumer<Object> secontPortalContentConsumer = assertRefreshedPortalThat(portal -> {
            assertThat(portal.getViews().size()).isEqualTo(1);
            assertThat(portal.getLayoutData().size()).isEqualTo(1);
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialPortalContentConsumer)
                .then(addViewRunner)
                .consumeNextWith(secontPortalContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private void addView(String representationId, String viewRepresentationId) {
        var addPortalViewMutationInput = new AddPortalViewInput(
                UUID.randomUUID(),
                TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID,
                representationId,
                viewRepresentationId,
                0,
                0,
                100,
                100
        );

        var result = this.addPortalViewMutationRunner.run(addPortalViewMutationInput);
        String typename = JsonPath.read(result.data(), "$.data.addPortalView.__typename");
        assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a portal, when reloading a previous state after changing the portal layout, then the original layout is reverted")
    public void givenAPortalWhenReloadingPreviousSateAfterLayoutChangeThenPortalLayoutReverted() {
        var portalEventInput = new PortalEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());
        var flux = this.portalEventSubscriptionRunner.run(portalEventInput).flux();

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        var subPortalViewId = "9e277e97-7f71-4bdd-99af-9eeb8bd7f2df";
        AtomicReference<RepresentationContent> initialPortalState = new AtomicReference<>(null);

        Consumer<Object> initialPortalContentConsumer = assertRefreshedPortalThat(portal -> {
            assertThat(portal.getLayoutData()).hasSize(1);

            var layoutData = portal.getLayoutData().get(0);
            assertThat(layoutData.getPortalViewId()).isEqualTo(subPortalViewId);
            assertThat(layoutData.getX()).isZero();
            assertThat(layoutData.getY()).isZero();
            assertThat(layoutData.getWidth()).isEqualTo(500);
            assertThat(layoutData.getHeight()).isEqualTo(200);

            var id = new RepresentationCompositeIdProvider().getId(UUID.fromString(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION);
            var optionalRepresentationMetadata = this.representationContentRepository.findById(id);
            assertThat(optionalRepresentationMetadata).isPresent();
            initialPortalState.set(optionalRepresentationMetadata.get());
        });

        Runnable layoutSubPortal = () -> {
            var layoutData = List.of(new PortalViewLayoutDataInput(subPortalViewId, 50, 50, 300, 300));
            var layoutInput = new LayoutPortalInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString(), layoutData);
            this.layoutPortalMutationRunner.run(layoutInput);
        };

        Consumer<Object> layoutedPortalContentConsumer = assertRefreshedPortalThat(portal -> {
            assertThat(portal.getLayoutData()).hasSize(1);

            var layoutData = portal.getLayoutData().get(0);
            assertThat(layoutData.getPortalViewId()).isEqualTo(subPortalViewId);
            assertThat(layoutData.getX()).isEqualTo(50);
            assertThat(layoutData.getY()).isEqualTo(50);
            assertThat(layoutData.getWidth()).isEqualTo(300);
            assertThat(layoutData.getHeight()).isEqualTo(300);

            var id = new RepresentationCompositeIdProvider().getId(UUID.fromString(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION);
            var optionalRepresentationMetadata = this.representationContentRepository.findById(id);
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
                    .filter(editingContextEventProcessor -> editingContextEventProcessor.getEditingContextId().equals(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID))
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
}
