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

import java.util.Objects;

import org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder;
import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IEdgeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgePalette;
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.provider.DefaultToolsFactory;

/**
 * Used to create the processor to processor edge description.
 *
 * @author frouene
 */
public class ProcessorToProcessorEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    public static final String NAME = "Processor To Processor";

    private final IColorProvider colorProvider;

    private final ViewBuilders viewBuilderHelper = new ViewBuilders();

    private final DiagramBuilders diagramBuilderHelper = new DiagramBuilders();

    private final FlowViewBuilder flowViewBuilder = new FlowViewBuilder();

    public ProcessorToProcessorEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        return this.diagramBuilderHelper.newEdgeDescription()
                .name(NAME)
                .domainType("flow::DataFlow")
                .semanticCandidatesExpression("aql:self.elements.eAllContents(flow::DataFlow)")
                .targetNodesExpression("feature:target")
                .sourceNodesExpression("feature:source")
                .isDomainBasedEdge(true)
                .centerLabelExpression("aql:self.capacity")
                .style(this.diagramBuilderHelper.newEdgeStyle()
                        .lineStyle(LineStyle.SOLID)
                        .color(this.colorProvider.getColor("Flow_Gray"))
                        .targetArrowStyle(ArrowStyle.INPUT_ARROW)
                        .borderSize(0)
                        .build())
                .palette(this.createEdgePalette())
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalDataSourceToProcessorEdgeDescription = cache.getEdgeDescription(NAME);
        var optionalProcessorNodeDescription = cache.getNodeDescription(ProcessorDescriptionProvider.NAME);

        if (optionalDataSourceToProcessorEdgeDescription.isPresent() && optionalProcessorNodeDescription.isPresent()) {
            diagramDescription.getEdgeDescriptions().add(optionalDataSourceToProcessorEdgeDescription.get());
            optionalDataSourceToProcessorEdgeDescription.get().getSourceNodeDescriptions().add(optionalProcessorNodeDescription.get());
            optionalDataSourceToProcessorEdgeDescription.get().getTargetNodeDescriptions().add(optionalProcessorNodeDescription.get());
        }
    }

    private EdgePalette createEdgePalette() {

        return this.diagramBuilderHelper.newEdgePalette()
                .toolSections(new DefaultToolsFactory().createDefaultHideRevealEdgeToolSection())
                .deleteTool(this.flowViewBuilder.createDeleteTool())
                .centerLabelEditTool(this.createLabelEditTool())
                .build();
    }

    public LabelEditTool createLabelEditTool() {
        var setValueCapacity = this.viewBuilderHelper.newSetValue()
                .featureName("capacity")
                .valueExpression("aql:newLabel");

        return this.diagramBuilderHelper.newLabelEditTool()
                .name("Edit")
                .body(setValueCapacity.build())
                .build();
    }
}
