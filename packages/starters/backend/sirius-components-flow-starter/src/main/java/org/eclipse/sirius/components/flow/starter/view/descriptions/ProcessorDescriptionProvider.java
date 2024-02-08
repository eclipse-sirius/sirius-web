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

import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.CPU_HIGH_SVG_ID;
import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.CPU_LOW_SVG_ID;
import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.CPU_OVER_SVG_ID;
import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.CPU_STANDARD_SVG_ID;
import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.CPU_UNUSED_SVG_ID;
import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.DSP_HIGH_SVG_ID;
import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.DSP_LOW_SVG_ID;
import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.DSP_OVER_SVG_ID;
import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.DSP_STANDARD_SVG_ID;
import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.DSP_UNUSED_SVG_ID;
import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.ENGINE_HIGH_SVG_ID;
import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.ENGINE_LOW_SVG_ID;
import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.ENGINE_OVER_SVG_ID;
import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.ENGINE_STANDARD_SVG_ID;
import static org.eclipse.sirius.components.flow.starter.view.FlowViewBuilder.ENGINE_UNUSED_SVG_ID;

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
 * Used to create the processor node description.
 *
 * @author frouene
 */
public class ProcessorDescriptionProvider implements INodeDescriptionProvider {

    public static final String NAME = "Processor Node";

    private final IColorProvider colorProvider;

    private final DiagramBuilders diagramBuilderHelper = new DiagramBuilders();

    private final FlowViewBuilder flowViewBuilder = new FlowViewBuilder();

    private final SynchronizationPolicy synchronizationPolicy;

    public ProcessorDescriptionProvider(IColorProvider colorProvider, SynchronizationPolicy synchronizationPolicy) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
        this.synchronizationPolicy = Objects.requireNonNull(synchronizationPolicy);
    }

    @Override
    public NodeDescription create() {
        return this.diagramBuilderHelper.newNodeDescription()
                .name(NAME)
                .domainType("flow::Processor")
                .semanticCandidatesExpression("feature:elements")
                .childrenLayoutStrategy(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())
                .outsideLabels(this.flowViewBuilder.getOutsideLabelDescription(this.colorProvider, "aql:self.name"))
                .defaultHeightExpression("aql:self.capacity*15")
                .defaultWidthExpression("aql:self.capacity*15")
                .userResizable(false)
                .keepAspectRatio(true)
                .style(this.flowViewBuilder.createImageNodeStyleDescription(CPU_UNUSED_SVG_ID, this.colorProvider))
                .synchronizationPolicy(this.synchronizationPolicy)
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.oclIsKindOf(flow::Processor)  and self.name.contains('Engine') and self.oclAsType(flow::Processor).usage = flow::FlowElementUsage::unused")
                        .style(this.flowViewBuilder.createImageNodeStyleDescription(ENGINE_UNUSED_SVG_ID, this.colorProvider))
                        .build())
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.oclIsKindOf(flow::Processor)  and self.name.contains('Engine') and self.oclAsType(flow::Processor).usage = flow::FlowElementUsage::low")
                        .style(this.flowViewBuilder.createImageNodeStyleDescription(ENGINE_LOW_SVG_ID, this.colorProvider))
                        .build())
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.oclIsKindOf(flow::Processor)  and self.name.contains('Engine') and self.oclAsType(flow::Processor).usage = flow::FlowElementUsage::standard")
                        .style(this.flowViewBuilder.createImageNodeStyleDescription(ENGINE_STANDARD_SVG_ID, this.colorProvider))
                        .build())
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.oclIsKindOf(flow::Processor)  and self.name.contains('Engine') and self.oclAsType(flow::Processor).usage = flow::FlowElementUsage::high")
                        .style(this.flowViewBuilder.createImageNodeStyleDescription(ENGINE_HIGH_SVG_ID, this.colorProvider))
                        .build())
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.oclIsKindOf(flow::Processor)  and self.name.contains('Engine') and self.oclAsType(flow::Processor).usage = flow::FlowElementUsage::over")
                        .style(this.flowViewBuilder.createImageNodeStyleDescription(ENGINE_OVER_SVG_ID, this.colorProvider))
                        .build())
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.oclIsKindOf(flow::Processor)  and self.name.contains('DSP') and self.oclAsType(flow::Processor).usage = flow::FlowElementUsage::unused")
                        .style(this.flowViewBuilder.createImageNodeStyleDescription(DSP_UNUSED_SVG_ID, this.colorProvider))
                        .build())
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.oclIsKindOf(flow::Processor)  and self.name.contains('DSP') and self.oclAsType(flow::Processor).usage = flow::FlowElementUsage::low")
                        .style(this.flowViewBuilder.createImageNodeStyleDescription(DSP_LOW_SVG_ID, this.colorProvider))
                        .build())
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.oclIsKindOf(flow::Processor)  and self.name.contains('DSP') and self.oclAsType(flow::Processor).usage = flow::FlowElementUsage::standard")
                        .style(this.flowViewBuilder.createImageNodeStyleDescription(DSP_STANDARD_SVG_ID, this.colorProvider))
                        .build())
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.oclIsKindOf(flow::Processor)  and self.name.contains('DSP') and self.oclAsType(flow::Processor).usage = flow::FlowElementUsage::high")
                        .style(this.flowViewBuilder.createImageNodeStyleDescription(DSP_HIGH_SVG_ID, this.colorProvider))
                        .build())
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.oclIsKindOf(flow::Processor)  and self.name.contains('DSP') and self.oclAsType(flow::Processor).usage = flow::FlowElementUsage::over")
                        .style(this.flowViewBuilder.createImageNodeStyleDescription(DSP_OVER_SVG_ID, this.colorProvider))
                        .build())
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.oclIsKindOf(flow::Processor) and self.oclAsType(flow::Processor).usage = flow::FlowElementUsage::unused")
                        .style(this.flowViewBuilder.createImageNodeStyleDescription(CPU_UNUSED_SVG_ID, this.colorProvider))
                        .build())
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.oclIsKindOf(flow::Processor) and self.oclAsType(flow::Processor).usage = flow::FlowElementUsage::low")
                        .style(this.flowViewBuilder.createImageNodeStyleDescription(CPU_LOW_SVG_ID, this.colorProvider))
                        .build())
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.oclIsKindOf(flow::Processor) and self.oclAsType(flow::Processor).usage = flow::FlowElementUsage::standard")
                        .style(this.flowViewBuilder.createImageNodeStyleDescription(CPU_STANDARD_SVG_ID, this.colorProvider))
                        .build())
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.oclIsKindOf(flow::Processor) and self.oclAsType(flow::Processor).usage = flow::FlowElementUsage::high")
                        .style(this.flowViewBuilder.createImageNodeStyleDescription(CPU_HIGH_SVG_ID, this.colorProvider))
                        .build())
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.oclIsKindOf(flow::Processor) and self.oclAsType(flow::Processor).usage = flow::FlowElementUsage::over")
                        .style(this.flowViewBuilder.createImageNodeStyleDescription(CPU_OVER_SVG_ID, this.colorProvider))
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
