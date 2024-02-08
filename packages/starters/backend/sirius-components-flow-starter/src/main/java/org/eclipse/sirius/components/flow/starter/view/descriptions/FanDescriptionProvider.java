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
package org.eclipse.sirius.components.flow.starter.view.descriptions;

import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.FAN_SVG_ID;

import java.util.Objects;

import org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder;
import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;

/**
 * Used to create the fan node description.
 *
 * @author frouene
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class FanDescriptionProvider implements INodeDescriptionProvider {

    public static final String NAME = "Fan Node";

    private final IColorProvider colorProvider;

    private final DiagramBuilders diagramBuilderHelper = new DiagramBuilders();

    private final FlowViewBuilder flowViewBuilder = new FlowViewBuilder();

    private final SynchronizationPolicy synchronizationPolicy;

    public FanDescriptionProvider(IColorProvider colorProvider, SynchronizationPolicy synchronizationPolicy) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
        this.synchronizationPolicy = Objects.requireNonNull(synchronizationPolicy);
    }


    @Override
    public NodeDescription create() {
        return this.diagramBuilderHelper.newNodeDescription()
                .name(NAME)
                .domainType("flow::Fan")
                .semanticCandidatesExpression("feature:elements")
                .childrenLayoutStrategy(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())
                .outsideLabels(this.flowViewBuilder.getOutsideLabelDescription(this.colorProvider, "feature:speed"))
                .defaultHeightExpression("aql:self.speed/2")
                .defaultWidthExpression("aql:self.speed/2")
                .userResizable(false)
                .keepAspectRatio(true)
                .style(this.flowViewBuilder.createImageNodeStyleDescription(FAN_SVG_ID, this.colorProvider))
                .synchronizationPolicy(this.synchronizationPolicy)
                .palette(this.createNodePalette())
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        cache.getNodeDescription(NAME).ifPresent(nodeDescription -> diagramDescription.getNodeDescriptions().add(nodeDescription));
    }

    private NodePalette createNodePalette() {

        return this.diagramBuilderHelper.newNodePalette()
                .deleteTool(this.flowViewBuilder.createDeleteTool())
                .labelEditTool(this.flowViewBuilder.createLabelEditTool())
                .build();
    }

}
