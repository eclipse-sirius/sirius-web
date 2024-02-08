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
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IEdgeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgePalette;
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.LineStyle;

/**
 * Used to create the datasource to processor edge description.
 *
 * @author frouene
 */
public class DataSourceToProcessorEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    public static final String NAME = "DataSource To Processor";

    private final IColorProvider colorProvider;

    private final ViewBuilders viewBuilderHelper = new ViewBuilders();

    private final DiagramBuilders diagramBuilderHelper = new DiagramBuilders();

    private final FlowViewBuilder flowViewBuilder = new FlowViewBuilder();

    public DataSourceToProcessorEdgeDescriptionProvider(IColorProvider colorProvider) {
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
                        .lineStyle(LineStyle.DASH)
                        .color(this.colorProvider.getColor("Flow_Gray"))
                        .targetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW)
                        .build())
                .palette(this.createEdgePalette())
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalDataSourceToProcessorEdgeDescription = cache.getEdgeDescription(NAME);
        var optionalDataSourceNodeDescription = cache.getNodeDescription(DataSourceDescriptionProvider.NAME);
        var optionalProcessorNodeDescription = cache.getNodeDescription(ProcessorDescriptionProvider.NAME);

        if (optionalDataSourceToProcessorEdgeDescription.isPresent() && optionalDataSourceNodeDescription.isPresent() && optionalProcessorNodeDescription.isPresent()) {
            diagramDescription.getEdgeDescriptions().add(optionalDataSourceToProcessorEdgeDescription.get());
            optionalDataSourceToProcessorEdgeDescription.get().getSourceNodeDescriptions().add(optionalDataSourceNodeDescription.get());
            optionalDataSourceToProcessorEdgeDescription.get().getTargetNodeDescriptions().add(optionalProcessorNodeDescription.get());
        }
    }

    private EdgePalette createEdgePalette() {

        return this.diagramBuilderHelper.newEdgePalette()
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
