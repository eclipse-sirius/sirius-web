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
package org.eclipse.sirius.web.application.controllers.diagrams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;

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
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramLayoutDataInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LabelLayoutDataInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.NodeLayoutDataInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;
import org.eclipse.sirius.components.diagrams.tests.graphql.LayoutDiagramMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.studio.services.representations.api.IDomainDiagramDescriptionProvider;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationContent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationContentRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationMetadataRepository;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the domain diagram.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DomainDiagramControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private IDomainDiagramDescriptionProvider domainDiagramDescriptionProvider;

    @Autowired
    private IRepresentationMetadataRepository representationMetadataRepository;

    @Autowired
    private IRepresentationContentRepository representationContentRepository;

    @Autowired
    private LayoutDiagramMutationRunner layoutDiagramMutationRunner;

    @Autowired
    private IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a domain diagram on a studio, when it is opened, then entities are visible")
    public void givenDomainDiagramOnStudioWhenItIsOpenedThenEntitiesAreVisible() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, this.domainDiagramDescriptionProvider.getDescriptionId(), StudioIdentifiers.DOMAIN_OBJECT.toString(), "Domain");
        var flux = this.givenCreatedDiagramSubscription.createAndSubscribe(input);

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            var rootNode = new DiagramNavigator(diagram).nodeWithLabel("Root").getNode();
            var namedElementNode = new DiagramNavigator(diagram).nodeWithLabel("NamedElement").getNode();
            var humanNode = new DiagramNavigator(diagram).nodeWithLabel("Human").getNode();

            var nodes = List.of(rootNode, namedElementNode, humanNode);
            assertThat(nodes)
                    .isNotEmpty()
                    .allSatisfy(node -> assertThat(node).isNotNull().extracting(n -> n.getStyle().getChildrenLayoutStrategy()).isInstanceOf(ListLayoutStrategy.class));
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a domain diagram on a studio, when moving a node and then reloading the previously saved state, then the node is back to its initial position")
    public void givenDomainDiagramOnStudioWhenMovingNodeAndThenReloadingPreviousStateThenNodeBackToInitialPosition() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, this.domainDiagramDescriptionProvider.getDescriptionId(), StudioIdentifiers.DOMAIN_OBJECT.toString(), "Domain");
        var flux = this.givenCreatedDiagramSubscription.createAndSubscribe(input);

        var initialPosition = new Position(50.0, 50.0);
        var initialSize = new Size(100, 100);
        var modifiedPosition = new Position(100.0, 100.0);
        var modifiedSize = new Size(200, 200);

        var diagramId = new AtomicReference<String>();
        var currentRevisionId = new AtomicReference<UUID>();

        var humanNodeId = new AtomicReference<String>();
        var initialDiagramMetadata = new AtomicReference<RepresentationMetadata>(null);
        var initialDiagramContent = new AtomicReference<RepresentationContent>(null);

        Consumer<Object> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(diagramPayload -> {
                    currentRevisionId.set(diagramPayload.id());
                    return diagramPayload.diagram();
                })
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());
                    var humanNode = new DiagramNavigator(diagram).nodeWithLabel("Human").getNode();
                    humanNodeId.set(humanNode.getId());
                }, () -> fail("Missing diagram"));

        Runnable initialDiagramLayout = () -> {
            var humanNodeLayout = new NodeLayoutDataInput(humanNodeId.get(), initialPosition, initialSize, true, List.of());
            var layoutData = new DiagramLayoutDataInput(List.of(humanNodeLayout), List.of(), List.of());
            var layoutInput = new LayoutDiagramInput(currentRevisionId.get(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, diagramId.get(), "refresh", layoutData);
            this.layoutDiagramMutationRunner.run(layoutInput);
        };

        Consumer<Object> initialDiagramLayoutConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(diagramPayload -> {
                    currentRevisionId.set(diagramPayload.id());
                    return diagramPayload.diagram();
                })
                .ifPresentOrElse(diagram -> {
                    var humanNodeLayout = diagram.getLayoutData().nodeLayoutData().get(humanNodeId.get());
                    assertThat(humanNodeLayout).isNotNull();
                    assertThat(humanNodeLayout.position()).isEqualTo(initialPosition);
                    assertThat(humanNodeLayout.size()).isEqualTo(initialSize);
                    var optionalRepresentationMetadata = this.representationMetadataRepository.findById(UUID.fromString(diagramId.get()));
                    if (optionalRepresentationMetadata.isPresent()) {
                        var representationMetadata = optionalRepresentationMetadata.get();
                        initialDiagramMetadata.set(representationMetadata);
                        this.representationContentRepository.findById(representationMetadata.getId()).ifPresent(initialDiagramContent::set);
                    }
                }, () -> fail("Missing diagram"));

        Runnable modifyDiagramLayout = () -> {
            var humanNodeLayout = new NodeLayoutDataInput(humanNodeId.get(), modifiedPosition, modifiedSize, true, List.of());
            var layoutData = new DiagramLayoutDataInput(List.of(humanNodeLayout), List.of(), List.of());
            var layoutInput = new LayoutDiagramInput(currentRevisionId.get(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, diagramId.get(), "refresh", layoutData);
            this.layoutDiagramMutationRunner.run(layoutInput);
        };

        Consumer<Object> modifiedDiagramLayoutConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(diagramPayload -> {
                    currentRevisionId.set(diagramPayload.id());
                    return diagramPayload.diagram();
                })
                .ifPresentOrElse(diagram -> {
                    var humanNodeLayout = diagram.getLayoutData().nodeLayoutData().get(humanNodeId.get());
                    assertThat(humanNodeLayout).isNotNull();
                    assertThat(humanNodeLayout.position()).isEqualTo(modifiedPosition);
                    assertThat(humanNodeLayout.size()).isEqualTo(modifiedSize);

                    var modifiedDiagramMetadata = this.representationMetadataRepository.findById(UUID.fromString(diagramId.get())).get();
                    var modifiedDiagramContent = this.representationContentRepository.findById(modifiedDiagramMetadata.getId()).get();
                    assertThat(modifiedDiagramContent.getContent()).isNotEqualTo(initialDiagramContent.get().getContent());
                }, () -> fail("Missing diagram"));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(initialDiagramLayout)
                .consumeNextWith(initialDiagramLayoutConsumer)
                .then(modifyDiagramLayout)
                .consumeNextWith(modifiedDiagramLayoutConsumer)
                .then(() -> this.reload(diagramId.get(), initialDiagramMetadata.get(), initialDiagramContent.get()))
                .consumeNextWith(initialDiagramLayoutConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a domain diagram on a studio, when moving a label and then reloading the previously saved state, then the label is back to its initial position")
    public void givenDomainDiagramOnStudioWhenMovingLabelAndThenReloadingPreviousStateThenLabelBackToInitialPosition() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, this.domainDiagramDescriptionProvider.getDescriptionId(), StudioIdentifiers.DOMAIN_OBJECT.toString(), "Domain");
        var flux = this.givenCreatedDiagramSubscription.createAndSubscribe(input);

        var initialPosition = new Position(50.0, 50.0);
        var modifiedPosition = new Position(100.0, 100.0);

        var diagramId = new AtomicReference<String>();
        var currentRevisionId = new AtomicReference<UUID>();

        var humansEdgeCenterLabelId = new AtomicReference<String>();
        var initialDiagramMetadata = new AtomicReference<RepresentationMetadata>(null);
        var initialDiagramContent = new AtomicReference<RepresentationContent>(null);

        Consumer<Object> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(diagramPayload -> {
                    currentRevisionId.set(diagramPayload.id());
                    return diagramPayload.diagram();
                })
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());
                    var humansEdgeCenterLabel = new DiagramNavigator(diagram).edgeWithLabel("humans [0..*]").findCenterLabel();
                    humansEdgeCenterLabelId.set(humansEdgeCenterLabel.id());
                }, () -> fail("Missing diagram"));

        Runnable initialDiagramLayout = () -> {
            var humansLabelLayout = new LabelLayoutDataInput(humansEdgeCenterLabelId.get(), initialPosition);
            var layoutData = new DiagramLayoutDataInput(List.of(), List.of(), List.of(humansLabelLayout));
            var layoutInput = new LayoutDiagramInput(currentRevisionId.get(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, diagramId.get(), "refresh", layoutData);
            this.layoutDiagramMutationRunner.run(layoutInput);
        };

        Consumer<Object> initialDiagramLayoutConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(diagramPayload -> {
                    currentRevisionId.set(diagramPayload.id());
                    return diagramPayload.diagram();
                })
                .ifPresentOrElse(diagram -> {
                    var humansLabelLayout = diagram.getLayoutData().labelLayoutData().get(humansEdgeCenterLabelId.get());
                    assertThat(humansLabelLayout).isNotNull();
                    assertThat(humansLabelLayout.position()).isEqualTo(initialPosition);
                    var optionalRepresentationMetadata = this.representationMetadataRepository.findById(UUID.fromString(diagramId.get()));
                    if (optionalRepresentationMetadata.isPresent()) {
                        var representationMetadata = optionalRepresentationMetadata.get();
                        initialDiagramMetadata.set(representationMetadata);
                        this.representationContentRepository.findById(representationMetadata.getId()).ifPresent(initialDiagramContent::set);
                    }
                }, () -> fail("Missing diagram"));

        Runnable modifyDiagramLayout = () -> {
            var humansLabelLayout = new LabelLayoutDataInput(humansEdgeCenterLabelId.get(), modifiedPosition);
            var layoutData = new DiagramLayoutDataInput(List.of(), List.of(), List.of(humansLabelLayout));
            var layoutInput = new LayoutDiagramInput(currentRevisionId.get(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, diagramId.get(), "refresh", layoutData);
            this.layoutDiagramMutationRunner.run(layoutInput);
        };

        Consumer<Object> modifiedDiagramLayoutConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(diagramPayload -> {
                    currentRevisionId.set(diagramPayload.id());
                    return diagramPayload.diagram();
                })
                .ifPresentOrElse(diagram -> {
                    var humansLabelLayout = diagram.getLayoutData().labelLayoutData().get(humansEdgeCenterLabelId.get());
                    assertThat(humansLabelLayout).isNotNull();
                    assertThat(humansLabelLayout.position()).isEqualTo(modifiedPosition);

                    var modifiedDiagramMetadata = this.representationMetadataRepository.findById(UUID.fromString(diagramId.get())).get();
                    var modifiedDiagramContent = this.representationContentRepository.findById(modifiedDiagramMetadata.getId()).get();
                    assertThat(modifiedDiagramContent.getContent()).isNotEqualTo(initialDiagramContent.get().getContent());
                }, () -> fail("Missing diagram"));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(initialDiagramLayout)
                .consumeNextWith(initialDiagramLayoutConsumer)
                .then(modifyDiagramLayout)
                .consumeNextWith(modifiedDiagramLayoutConsumer)
                .then(() -> this.reload(diagramId.get(), initialDiagramMetadata.get(), initialDiagramContent.get()))
                .consumeNextWith(initialDiagramLayoutConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private void reload(String diagramId, RepresentationMetadata representationMetadata, RepresentationContent representationContent) {
        this.representationMetadataRepository.save(representationMetadata);
        this.representationContentRepository.save(representationContent);
        TestTransaction.flagForCommit();
        TestTransaction.end();

        Consumer<IEditingContextEventProcessor> editingContextEventProcessorConsumer = editingContextEventProcessor -> {
            editingContextEventProcessor.getRepresentationEventProcessors().stream()
                    .filter(representationEventProcessor -> representationEventProcessor.getRepresentation().getId().equals(diagramId))
                    .findFirst()
                    .ifPresentOrElse(representationEventProcessor -> {
                        IInput reloadInput = UUID::randomUUID;
                        representationEventProcessor.refresh(new ChangeDescription(ChangeKind.RELOAD_REPRESENTATION, diagramId, reloadInput));
                    }, () -> fail("Missing representation event processor"));
        };
        this.editingContextEventProcessorRegistry.getEditingContextEventProcessors().stream()
                .filter(editingContextEventProcessor -> editingContextEventProcessor.getEditingContextId().equals(StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID))
                .findFirst()
                .ifPresentOrElse(editingContextEventProcessorConsumer, () -> fail("Missing editing context event processor"));
    }
}
