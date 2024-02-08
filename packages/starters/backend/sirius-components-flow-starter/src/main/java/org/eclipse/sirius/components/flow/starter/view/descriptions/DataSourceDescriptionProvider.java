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

import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.ANTENNA_SVG_ID;
import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.CAMERA_SVG_ID;
import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.RADAR_SVG_ID;
import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.SENSOR_SVG_ID;

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
 * Used to create the DataSource node description.
 *
 * @author frouene
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DataSourceDescriptionProvider implements INodeDescriptionProvider {

    public static final String NAME = "DataSource Node";

    private final IColorProvider colorProvider;

    private final DiagramBuilders diagramBuilderHelper = new DiagramBuilders();

    private final FlowViewBuilder flowViewBuilder = new FlowViewBuilder();

    private final SynchronizationPolicy synchronizationPolicy;

    public DataSourceDescriptionProvider(IColorProvider colorProvider, SynchronizationPolicy synchronizationPolicy) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
        this.synchronizationPolicy = Objects.requireNonNull(synchronizationPolicy);
    }


    @Override
    public NodeDescription create() {
        return this.diagramBuilderHelper.newNodeDescription()
                .name(NAME)
                .domainType("flow::DataSource")
                .semanticCandidatesExpression("feature:elements")
                .childrenLayoutStrategy(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())
                .outsideLabels(this.flowViewBuilder.getOutsideLabelDescription(this.colorProvider, "aql:self.name"))
                .defaultHeightExpression("aql:self.volume*15")
                .defaultWidthExpression("aql:self.computeDataSourceHeight()")
                .userResizable(false)
                .keepAspectRatio(true)
                .style(this.flowViewBuilder.createImageNodeStyleDescription(SENSOR_SVG_ID, this.colorProvider))
                .synchronizationPolicy(this.synchronizationPolicy)
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.name.contains('Camera')")
                        .style(this.flowViewBuilder.createImageNodeStyleDescription(CAMERA_SVG_ID, this.colorProvider))
                        .build())
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.name.contains('Radar')")
                        .style(this.flowViewBuilder.createImageNodeStyleDescription(RADAR_SVG_ID, this.colorProvider))
                        .build())
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.name.contains('Wifi')")
                        .style(this.flowViewBuilder.createImageNodeStyleDescription(ANTENNA_SVG_ID, this.colorProvider))
                        .build())
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        cache.getNodeDescription(NAME).ifPresent(nodeDescription -> {
            diagramDescription.getNodeDescriptions().add(nodeDescription);
            cache.getNodeDescription(ProcessorDescriptionProvider.NAME).ifPresent(processorNodeDescription -> nodeDescription.setPalette(this.createNodePalette(processorNodeDescription)));
        });
    }

    private NodePalette createNodePalette(NodeDescription processorNodeDescription) {

        return this.diagramBuilderHelper.newNodePalette()
                .deleteTool(this.flowViewBuilder.createDeleteTool())
                .labelEditTool(this.flowViewBuilder.createLabelEditTool())
                .edgeTools(this.flowViewBuilder.createEdgeToProcessorTool(processorNodeDescription))
                .build();
    }


}
