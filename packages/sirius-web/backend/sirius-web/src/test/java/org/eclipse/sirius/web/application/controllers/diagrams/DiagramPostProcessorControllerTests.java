/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramPostProcessor;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramLayoutDataInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.NodeLayoutDataInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.PinDiagramElementInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;
import org.eclipse.sirius.components.diagrams.tests.graphql.LayoutDiagramMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.PinDiagramElementMutationRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.studio.services.representations.api.IDomainDiagramDescriptionProvider;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.services.diagrams.DiagramPostProcessorProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests to test the usage of {@link IDiagramPostProcessor}.
 *
 * @author lfasani
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class DiagramPostProcessorControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private IDomainDiagramDescriptionProvider domainDiagramDescriptionProvider;

    @Autowired
    private LayoutDiagramMutationRunner layoutDiagramMutationRunner;

    @Autowired
    private PinDiagramElementMutationRunner pinDiagramElementMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram and a provided IDiagramPostProcessor, when a diagram is opened or refreshed, then the diagram is updated by the IDiagramPostProcessor")
    public void givenDiagramWhenItIsOpenedOrMdifiedThenDiagramIsUpdatedWithDiagramPostProcessor() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, this.domainDiagramDescriptionProvider.getDescriptionId(), StudioIdentifiers.DOMAIN_OBJECT.toString(),
                DiagramPostProcessorProvider.DIAGRAM_WITH_POST_PROCESSOR_NAME);
        var flux = this.givenCreatedDiagramSubscription.createAndSubscribe(input);

        var diagramId = new AtomicReference<String>();
        var currentRevisionId = new AtomicReference<UUID>();
        var nodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(diagramPayload -> {
                    currentRevisionId.set(diagramPayload.id());
                    return diagramPayload.diagram();
                })
                .ifPresentOrElse(diagram -> {
                    diagramId.set(diagram.getId());
                    nodeId.set(diagram.getNodes().get(0).getId());
                    assertThat(diagram.getNodes().get(0).getInsideLabel().getText()).isEqualTo(DiagramPostProcessorProvider.TEXT_AFTER_REFRESH);
                }, () -> fail("Missing diagram"));

        Runnable initialDiagramLayout = () -> {
            var humanNodeLayout = new NodeLayoutDataInput(nodeId.get(), new Position(0., 0.), new Size(50, 50), false, false, List.of(), new Size(0, 0));
            var layoutData = new DiagramLayoutDataInput(List.of(humanNodeLayout), List.of(), List.of());
            var layoutInput = new LayoutDiagramInput(currentRevisionId.get(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, diagramId.get(), "refresh", layoutData);
            this.layoutDiagramMutationRunner.run(layoutInput);
        };

        Consumer<Object> afterLayoutDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getNodes().get(0).getInsideLabel().getText()).isEqualTo(DiagramPostProcessorProvider.TEXT_AFTER_ARRANGE_ALL);
        });

        Runnable pinDiagramNode = () -> {
            // We use any IDiagramInput so that the diagram is refreshed
            PinDiagramElementInput pinDiagramElementInput = new PinDiagramElementInput(currentRevisionId.get(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, diagramId.get(), Set.of(), true);
            this.pinDiagramElementMutationRunner.run(pinDiagramElementInput);
        };

        Consumer<Object> nextDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getNodes().get(0).getInsideLabel().getText()).isEqualTo(DiagramPostProcessorProvider.TEXT_AFTER_REFRESH);
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(initialDiagramLayout)
                .consumeNextWith(nextDiagramContentConsumer)
                .then(pinDiagramNode)
                .consumeNextWith(nextDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
