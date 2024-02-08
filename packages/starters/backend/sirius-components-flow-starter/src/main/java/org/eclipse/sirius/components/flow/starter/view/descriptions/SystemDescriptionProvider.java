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
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;

/**
 * Used to create the system node description.
 *
 * @author frouene
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class SystemDescriptionProvider implements INodeDescriptionProvider {

    public static final String NAME = "System Node";

    private final IColorProvider colorProvider;

    private final ViewBuilders viewBuilderHelper = new ViewBuilders();

    private final DiagramBuilders diagramBuilderHelper = new DiagramBuilders();

    private final FlowViewBuilder flowViewBuilder = new FlowViewBuilder();

    private final SynchronizationPolicy synchronizationPolicy;

    private final boolean autoLayout;

    public SystemDescriptionProvider(IColorProvider colorProvider, SynchronizationPolicy synchronizationPolicy, boolean autoLayout) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
        this.synchronizationPolicy = Objects.requireNonNull(synchronizationPolicy);
        this.autoLayout = autoLayout;
    }

    @Override
    public NodeDescription create() {
        var nodeDescription = this.diagramBuilderHelper.newNodeDescription()
                .name(NAME)
                .domainType("flow::System")
                .semanticCandidatesExpression("feature:elements")
                .childrenLayoutStrategy(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())
                .insideLabel(this.flowViewBuilder.getInsideLabelDescription(this.colorProvider, "aql:self.name", true, true, false))
                .defaultHeightExpression("70")
                .defaultWidthExpression("150")
                .keepAspectRatio(false)
                .style(this.getRectangularNodeStyleDescription("Flow_Gray", "Flow_Black"))
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.oclAsType(flow::System).temperature>40")
                        .style(this.getRectangularNodeStyleDescription("Flow_Red", "Flow_Red"))
                        .build())
                .conditionalStyles(this.diagramBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.oclAsType(flow::System).temperature>30")
                        .style(this.getRectangularNodeStyleDescription("Flow_Orange", "Flow_Orange"))
                        .build())
                .synchronizationPolicy(this.synchronizationPolicy)
                .borderNodesDescriptions(new PowerOutputDescriptionProvider(this.colorProvider).create(),
                        new PowerInputDescriptionProvider(this.colorProvider).create());

        if (this.autoLayout) {
            nodeDescription.childrenDescriptions(this.createDescriptionNode());
        }

        return nodeDescription.build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        cache.getNodeDescription(NAME).ifPresent(nodeDescription -> {
            diagramDescription.getNodeDescriptions().add(nodeDescription);
            cache.getNodeDescription(ProcessorDescriptionProvider.NAME).ifPresent(reusedChildNode -> nodeDescription.getReusedChildNodeDescriptions().add(reusedChildNode));
            cache.getNodeDescription(FanDescriptionProvider.NAME)
                    .ifPresent(reusedChildNode -> nodeDescription.getReusedChildNodeDescriptions().add(reusedChildNode));
            cache.getNodeDescription(DataSourceDescriptionProvider.NAME)
                    .ifPresent(reusedChildNode -> nodeDescription.getReusedChildNodeDescriptions().add(reusedChildNode));
            nodeDescription.setPalette(this.createNodePalette(cache));
        });
    }

    private RectangularNodeStyleDescription getRectangularNodeStyleDescription(String borderColor, String labelColor) {
        return this.diagramBuilderHelper.newRectangularNodeStyleDescription()
                .color(this.colorProvider.getColor("Flow_LightGray"))
                .borderColor(this.colorProvider.getColor(borderColor))
                .borderRadius(0)
                .build();
    }

    private NodePalette createNodePalette(IViewDiagramElementFinder cache) {

        return this.diagramBuilderHelper.newNodePalette()
                .deleteTool(this.flowViewBuilder.createDeleteTool())
                .labelEditTool(this.flowViewBuilder.createLabelEditTool())
                .toolSections(this.createNodeToolSection(cache))
                .build();
    }

    private NodeToolSection createNodeToolSection(IViewDiagramElementFinder cache) {
        return this.diagramBuilderHelper.newNodeToolSection()
                .name("Creation Tools")
                .nodeTools(this.createNodeToolCreateFan(cache),
                        this.createNodeToolProcessor(cache),
                        this.createNodeToolCreateDataSource(cache))
                .build();
    }

    private NodeTool createNodeToolCreateFan(IViewDiagramElementFinder cache) {

        var setValueSpeed = this.viewBuilderHelper.newSetValue()
                .featureName("speed")
                .valueExpression("200");

        if (this.synchronizationPolicy.equals(SynchronizationPolicy.UNSYNCHRONIZED)) {
            cache.getNodeDescription(FanDescriptionProvider.NAME).ifPresent(dataSourceNodeDescription -> {
                var createView = this.diagramBuilderHelper.newCreateView()
                        .parentViewExpression("aql:selectedNode")
                        .semanticElementExpression("aql:newInstance")
                        .elementDescription(dataSourceNodeDescription);

                setValueSpeed.children(createView.build());
            });
        }

        var changeContextNewInstance = this.viewBuilderHelper.newChangeContext()
                .expression("aql:newInstance")
                .children(setValueSpeed.build());

        var createInstance = this.viewBuilderHelper.newCreateInstance()
                .typeName("flow::Fan")
                .referenceName("elements")
                .variableName("newInstance")
                .children(changeContextNewInstance.build());

        return this.diagramBuilderHelper.newNodeTool()
                .name("Fan")
                .iconURLsExpression("/icons/full/obj16/Fan.gif")
                .body(createInstance.build())
                .build();
    }

    private NodeTool createNodeToolProcessor(IViewDiagramElementFinder cache) {

        var setValueStatus = this.viewBuilderHelper.newSetValue()
                .featureName("status")
                .valueExpression("active");
        var setValueName = this.viewBuilderHelper.newSetValue()
                .featureName("name")
                .valueExpression("aql:'Processor' + self.eContainer().eContents()->filter(flow::Processor)->size()");

        if (this.synchronizationPolicy.equals(SynchronizationPolicy.UNSYNCHRONIZED)) {
            cache.getNodeDescription(ProcessorDescriptionProvider.NAME).ifPresent(dataSourceNodeDescription -> {
                var createView = this.diagramBuilderHelper.newCreateView()
                        .parentViewExpression("aql:selectedNode")
                        .semanticElementExpression("aql:newInstance")
                        .elementDescription(dataSourceNodeDescription);

                setValueName.children(createView.build());
            });
        }

        var changeContextNewInstance = this.viewBuilderHelper.newChangeContext()
                .expression("aql:newInstance")
                .children(setValueStatus.build(), setValueName.build());

        var createInstance = this.viewBuilderHelper.newCreateInstance()
                .typeName("flow::Processor")
                .referenceName("elements")
                .variableName("newInstance")
                .children(changeContextNewInstance.build());

        return this.diagramBuilderHelper.newNodeTool()
                .name("Processor")
                .iconURLsExpression("/icons/full/obj16/Processor_active.gif")
                .body(createInstance.build())
                .build();
    }

    private NodeTool createNodeToolCreateDataSource(IViewDiagramElementFinder cache) {

        var setValueVolume = this.viewBuilderHelper.newSetValue()
                .featureName("volume")
                .valueExpression("6");
        var setValueStatus = this.viewBuilderHelper.newSetValue()
                .featureName("status")
                .valueExpression("active");
        var setValueName = this.viewBuilderHelper.newSetValue()
                .featureName("name")
                .valueExpression("aql:'DataSource' + self.eContainer().eContents()->filter(flow::DataSource)->size()");

        if (this.synchronizationPolicy.equals(SynchronizationPolicy.UNSYNCHRONIZED)) {
            cache.getNodeDescription(DataSourceDescriptionProvider.NAME).ifPresent(dataSourceNodeDescription -> {
                var createView = this.diagramBuilderHelper.newCreateView()
                        .parentViewExpression("aql:selectedNode")
                        .semanticElementExpression("aql:newInstance")
                        .elementDescription(dataSourceNodeDescription);

                setValueName.children(createView.build());
            });
        }

        var changeContextNewInstance = this.viewBuilderHelper.newChangeContext()
                .expression("aql:newInstance")
                .children(setValueVolume.build(), setValueStatus.build(), setValueName.build());

        var createInstance = this.viewBuilderHelper.newCreateInstance()
                .typeName("flow::DataSource")
                .referenceName("elements")
                .variableName("newInstance")
                .children(changeContextNewInstance.build());

        return this.diagramBuilderHelper.newNodeTool()
                .name("Data Source")
                .iconURLsExpression("/icons/full/obj16/DataSource_active.gif")
                .body(createInstance.build())
                .build();
    }

    private NodeDescription createDescriptionNode() {
        var weightNodeDescription =
                this.diagramBuilderHelper.newNodeDescription()
                        .name("Weight Node")
                        .semanticCandidatesExpression("aql:self")
                        .insideLabel(this.flowViewBuilder.getInsideLabelDescription(this.colorProvider, "aql:'Weight: ' + self.weight"))
                        .style(this.diagramBuilderHelper.newIconLabelNodeStyleDescription().build())
                        .build();

        var temperatureNodeDescription =
                this.diagramBuilderHelper.newNodeDescription()
                        .name("Temperature Node")
                        .semanticCandidatesExpression("aql:self")
                        .insideLabel(this.flowViewBuilder.getInsideLabelDescription(this.colorProvider, "aql:'Temperature: ' + self.temperature"))
                        .style(this.diagramBuilderHelper.newIconLabelNodeStyleDescription().build())
                        .build();

        return this.diagramBuilderHelper.newNodeDescription()
                .name("Description Node")
                .domainType("flow::CompositeProcessor")
                .semanticCandidatesExpression("aql:self")
                .childrenLayoutStrategy(this.diagramBuilderHelper.newListLayoutStrategyDescription().areChildNodesDraggableExpression("aql:false").build())
                .insideLabel(this.flowViewBuilder.getInsideLabelDescription(this.colorProvider, "Description", false, true, true))
                .defaultHeightExpression("50")
                .defaultWidthExpression("120")
                .keepAspectRatio(false)
                .style(this.diagramBuilderHelper.newRectangularNodeStyleDescription()
                        .color(this.colorProvider.getColor("Flow_White"))
                        .borderColor(this.colorProvider.getColor("Flow_Black"))
                        .borderRadius(3)
                        .build())
                .synchronizationPolicy(this.synchronizationPolicy)
                .childrenDescriptions(weightNodeDescription, temperatureNodeDescription)
                .build();
    }
}
