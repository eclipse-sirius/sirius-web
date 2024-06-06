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

import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.POWER_INPUT_SVG_ID;

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
import org.eclipse.sirius.components.view.diagram.UserResizableDirection;
import org.eclipse.sirius.components.view.diagram.provider.DefaultToolsFactory;

/**
 * Used to create the powerInput node description.
 *
 * @author frouene
 */
public class PowerInputDescriptionProvider implements INodeDescriptionProvider {

    public static final String NAME = "PowerInput Node";

    private final IColorProvider colorProvider;

    private final DiagramBuilders diagramBuilderHelper = new DiagramBuilders();

    private final FlowViewBuilder flowViewBuilder = new FlowViewBuilder();

    public PowerInputDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }


    @Override
    public NodeDescription create() {

        return this.diagramBuilderHelper.newNodeDescription()
                .name(NAME)
                .domainType("flow::PowerInput")
                .semanticCandidatesExpression("feature:powerInputs")
                .childrenLayoutStrategy(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())
                .defaultHeightExpression("50")
                .defaultWidthExpression("50")
                .userResizable(UserResizableDirection.NONE)
                .keepAspectRatio(true)
                .style(this.flowViewBuilder.createImageNodeStyleDescription(POWER_INPUT_SVG_ID, this.colorProvider))
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .palette(this.createNodePalette())
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        cache.getNodeDescription(NAME).ifPresent(nodeDescription -> diagramDescription.getNodeDescriptions().add(nodeDescription));
    }

    private NodePalette createNodePalette() {

        return this.diagramBuilderHelper.newNodePalette()
                .toolSections(new DefaultToolsFactory().createDefaultHideRevealNodeToolSection())
                .deleteTool(this.flowViewBuilder.createDeleteTool())
                .build();
    }

}
