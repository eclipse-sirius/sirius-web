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
import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.EditLabelInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.EditLabelSuccessPayload;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.tests.graphql.EditLabelMutationRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.diagram.EllipseNodeStyle;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.CustomNodesDiagramDescriptionProvider;
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
 * Integration tests of the interpreted node style.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class InterpretedNodeStyleControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private CustomNodesDiagramDescriptionProvider customNodesDiagramDescriptionProvider;

    @Autowired
    private EditLabelMutationRunner editLabelMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a node with an expression , when nodes is rendered, then the style is computed properly")
    public void givenViewBasedDiagramWhenNodesAreUsingCustomImagesThenTheStyleIsComputedProperly() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), this.customNodesDiagramDescriptionProvider.getRepresentationDescriptionId(), PapayaIdentifiers.PROJECT_OBJECT.toString(), "Diagram");
        var flux = this.givenCreatedDiagramSubscription.createAndSubscribe(input);

        var diagramId = new AtomicReference<String>();
        var firstNodeId = new AtomicReference<String>();
        var firstNodeLabelId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getNodes())
                    .isNotEmpty()
                    .allSatisfy(node -> {
                        assertThat(node.getStyle()).isInstanceOf(EllipseNodeStyle.class);
                        assertThat(((EllipseNodeStyle) node.getStyle()).getOpacity()).isEqualTo("1");
                    });
            diagramId.set(diagram.getId());
            firstNodeId.set(diagram.getNodes().get(0).getId());
            firstNodeLabelId.set(diagram.getNodes().get(0).getInsideLabel().getId());
        });

        Runnable editLabel = () -> {
            var inputEditLabel = new EditLabelInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), firstNodeLabelId.get(), "faded-element");
            var result = this.editLabelMutationRunner.run(inputEditLabel);

            String typename = JsonPath.read(result, "$.data.editLabel.__typename");
            assertThat(typename).isEqualTo(EditLabelSuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentMatcher = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getNodes().stream().filter(node -> node.getId().equals(firstNodeId.get())))
                    .isNotEmpty()
                    .hasSize(1)
                    .allSatisfy(node -> {
                        assertThat(node.getStyle()).isInstanceOf(EllipseNodeStyle.class);
                        assertThat(((EllipseNodeStyle) node.getStyle()).getOpacity()).isEqualTo("0.2");
                    });
            assertThat(diagram.getNodes().stream().filter(node -> !node.getId().equals(firstNodeId.get())))
                    .isNotEmpty()
                    .allSatisfy(node -> {
                        assertThat(node.getStyle()).isInstanceOf(EllipseNodeStyle.class);
                        assertThat(((EllipseNodeStyle) node.getStyle()).getOpacity()).isEqualTo("1");
                    });
        });


        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(editLabel)
                .consumeNextWith(updatedDiagramContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
