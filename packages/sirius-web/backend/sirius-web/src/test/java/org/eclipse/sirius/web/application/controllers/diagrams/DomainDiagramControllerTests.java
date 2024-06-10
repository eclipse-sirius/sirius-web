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
package org.eclipse.sirius.web.application.controllers.diagrams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
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
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.NodeLayoutDataInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;
import org.eclipse.sirius.components.diagrams.tests.graphql.LayoutDiagramMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.studio.services.representations.api.IDomainDiagramDescriptionProvider;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationData;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationDataRepository;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the domain diagram.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DomainDiagramControllerTests extends AbstractIntegrationTests {

    private static final String MISSING_DIAGRAM = "Missing diagram";

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private IDomainDiagramDescriptionProvider domainDiagramDescriptionProvider;

    @Autowired
    private IRepresentationDataRepository representationDataRepository;

    @Autowired
    private LayoutDiagramMutationRunner layoutDiagramMutationRunner;

    @Autowired
    private IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @DisplayName("Given a domain diagram on a studio, when it is opened, then entities are visible")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDomainDiagramOnStudioWhenItIsOpenedThenEntitiesAreVisible() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(), this.domainDiagramDescriptionProvider.getDescriptionId(), StudioIdentifiers.DOMAIN_OBJECT.toString(), "Domain");
        var flux = this.givenCreatedDiagramSubscription.createAndSubscribe(input);

        Consumer<DiagramRefreshedEventPayload> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    var rootNode = new DiagramNavigator(diagram).nodeWithLabel("Root").getNode();
                    var namedElementNode = new DiagramNavigator(diagram).nodeWithLabel("NamedElement").getNode();
                    var humanNode = new DiagramNavigator(diagram).nodeWithLabel("Human").getNode();

                    var nodes = List.of(rootNode, namedElementNode, humanNode);
                    assertThat(nodes)
                            .isNotEmpty()
                            .allSatisfy(node -> assertThat(node).isNotNull().extracting(Node::getChildrenLayoutStrategy).isInstanceOf(ListLayoutStrategy.class));
                }, () -> fail(MISSING_DIAGRAM));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a domain diagram on a studio, when moving a node and then reloading the previously saved state, then the node is back to its initial position")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDomainDiagramOnStudioWhenMovingNodeAndThenReloadingPreviousStateThenNodeBackToInitialPosition() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(), this.domainDiagramDescriptionProvider.getDescriptionId(), StudioIdentifiers.DOMAIN_OBJECT.toString(), "Domain");
        var flux = this.givenCreatedDiagramSubscription.createAndSubscribe(input);

        var initialPosition = new Position(50.0, 50.0);
        var initialSize = new Size(100, 100);
        var modifiedPosition = new Position(100.0, 100.0);
        var modifiedSize = new Size(200, 200);

        var diagramId = new AtomicReference<String>();
        var currentRevisionId = new AtomicReference<UUID>();
        var humanNodeId  = new AtomicReference<String>();
        var initialDiagramData = new AtomicReference<RepresentationData>(null);

        Consumer<DiagramRefreshedEventPayload> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .map(diagramPayload -> {
                    currentRevisionId.set(diagramPayload.id());
                    return payload.diagram();
                })
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());
                    var humanNode = new DiagramNavigator(diagram).nodeWithLabel("Human").getNode();
                    humanNodeId.set(humanNode.getId());
                }, () -> fail(MISSING_DIAGRAM));

        Runnable initialDiagramLayout = () -> {
            var humanNodeLayout = new NodeLayoutDataInput(humanNodeId.get(), initialPosition, initialSize, true);
            var layoutData = new DiagramLayoutDataInput(List.of(humanNodeLayout));
            var layoutInput = new LayoutDiagramInput(currentRevisionId.get(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(), diagramId.get(), layoutData);
            this.layoutDiagramMutationRunner.run(layoutInput);
        };

        Consumer<DiagramRefreshedEventPayload> initialDiagramLayoutConsumer = payload -> Optional.of(payload)
                .map(diagramPayload -> {
                    currentRevisionId.set(diagramPayload.id());
                    return payload.diagram();
                })
                .ifPresentOrElse(diagram -> {
                    var humanNodeLayout = diagram.getLayoutData().nodeLayoutData().get(humanNodeId.get());
                    assertThat(humanNodeLayout).isNotNull();
                    assertThat(humanNodeLayout.position()).isEqualTo(initialPosition);
                    assertThat(humanNodeLayout.size()).isEqualTo(initialSize);
                    this.representationDataRepository.findById(UUID.fromString(diagramId.get())).ifPresent(initialDiagramData::set);
                }, () -> fail(MISSING_DIAGRAM));

        Runnable modifyDiagramLayout = () -> {
            var humanNodeLayout = new NodeLayoutDataInput(humanNodeId.get(), modifiedPosition, modifiedSize, true);
            var layoutData = new DiagramLayoutDataInput(List.of(humanNodeLayout));
            var layoutInput = new LayoutDiagramInput(currentRevisionId.get(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(), diagramId.get(), layoutData);
            this.layoutDiagramMutationRunner.run(layoutInput);
        };

        Consumer<DiagramRefreshedEventPayload> modifiedDiagramLayoutConsumer = payload -> Optional.of(payload)
                .map(diagramPayload -> {
                    currentRevisionId.set(diagramPayload.id());
                    return payload.diagram();
                })
                .ifPresentOrElse(diagram -> {
                    var humanNodeLayout = diagram.getLayoutData().nodeLayoutData().get(humanNodeId.get());
                    assertThat(humanNodeLayout).isNotNull();
                    assertThat(humanNodeLayout.position()).isEqualTo(modifiedPosition);
                    assertThat(humanNodeLayout.size()).isEqualTo(modifiedSize);

                    var modifiedDiagramData = this.representationDataRepository.findById(UUID.fromString(diagramId.get())).get();
                    assertThat(modifiedDiagramData).isNotEqualTo(initialDiagramData.get());
                    assertThat(modifiedDiagramData.getContent()).isNotEqualTo(initialDiagramData.get().getContent());
                }, () -> fail(MISSING_DIAGRAM));

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(initialDiagramLayout)
                .consumeNextWith(initialDiagramLayoutConsumer)
                .then(modifyDiagramLayout)
                .consumeNextWith(modifiedDiagramLayoutConsumer)
                .then(() -> this.reload(diagramId.get(), initialDiagramData.get()))
                .consumeNextWith(initialDiagramLayoutConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private void reload(String diagramId, RepresentationData representationData) {
        this.representationDataRepository.save(representationData);
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
                .filter(editingContextEventProcessor -> editingContextEventProcessor.getEditingContextId().equals(StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString()))
                .findFirst()
                .ifPresentOrElse(editingContextEventProcessorConsumer, () -> fail("Missing editing context event processor"));
    }
}
