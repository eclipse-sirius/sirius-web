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
package org.eclipse.sirius.components.flow.starter.view.descriptions;

import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.POWER_OUTPUT_SVG_ID;

import java.util.Objects;

import org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder;
import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.diagram.UserResizableDirection;
import org.eclipse.sirius.components.view.diagram.provider.DefaultToolsFactory;

/**
 * Used to create the powerOutput node description.
 *
 * @author frouene
 */
public class PowerOutputDescriptionProvider implements INodeDescriptionProvider {

    public static final String NAME = "PowerOutput Node";

    private final IColorProvider colorProvider;

    private final DiagramBuilders diagramBuilderHelper = new DiagramBuilders();

    private final FlowViewBuilder flowViewBuilder = new FlowViewBuilder();

    public PowerOutputDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        return this.diagramBuilderHelper.newNodeDescription()
                .name(NAME)
                .domainType("flow::PowerOutput")
                .semanticCandidatesExpression("feature:powerOutputs")
                .defaultHeightExpression("50")
                .defaultWidthExpression("50")
                .userResizable(UserResizableDirection.NONE)
                .keepAspectRatio(true)
                .style(this.flowViewBuilder.createImageNodeStyleDescription(POWER_OUTPUT_SVG_ID, this.colorProvider))
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        cache.getNodeDescription(NAME).ifPresent(nodeDescription -> {
            cache.getNodeDescription(SystemDescriptionProvider.NAME).ifPresent(systemNodeDescription -> {
                systemNodeDescription.getBorderNodesDescriptions().add(nodeDescription);
            });

            nodeDescription.setPalette(this.createNodePalette(cache));
        });
    }

    private NodePalette createNodePalette(IViewDiagramElementFinder cache) {
        return this.diagramBuilderHelper.newNodePalette()
                .edgeTools(this.createEdgeToolPowerLink(cache))
                .toolSections(
                        new DefaultToolsFactory().createDefaultHideRevealNodeToolSection()
                )
                .deleteTool(this.flowViewBuilder.createDeleteTool())
                .build();
    }

    private EdgeTool createEdgeToolPowerLink(IViewDiagramElementFinder cache) {
        var setPowerLink = new ViewBuilders().newChangeContext()
                .expression("aql:newPowerLink")
                .children(
                        new ViewBuilders().newSetValue()
                                .featureName("source")
                                .valueExpression("aql:semanticEdgeSource")
                                .build(),
                        new ViewBuilders().newSetValue()
                                .featureName("target")
                                .valueExpression("aql:semanticEdgeTarget")
                                .build()
                )
                .build();

        var powerInputNodeDescription = cache.getNodeDescription(PowerInputDescriptionProvider.NAME).orElse(null);
        return this.diagramBuilderHelper.newEdgeTool()
                .name("Power Link")
                .targetElementDescriptions(powerInputNodeDescription)
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("var:semanticEdgeSource")
                                .children(
                                        new ViewBuilders().newCreateInstance()
                                                .typeName("flow::PowerLink")
                                                .referenceName("links")
                                                .variableName("newPowerLink")
                                                .children(setPowerLink)
                                                .build()
                                )
                                .build()
                )
                .build();
    }
}
