/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.sample.papaya.view.classdiagram;

import java.util.Objects;

import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.EdgeTool;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodePalette;
import org.eclipse.sirius.components.view.SynchronizationPolicy;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;


/**
 * Used to create the class node description.
 *
 * @author sbegaudeau
 */
public class CDClassNodeDescriptionProvider implements INodeDescriptionProvider {
    public static final String NAME = "CD Node Class";

    private final IColorProvider colorProvider;

    private final ViewBuilders viewBuilderHelper = new ViewBuilders();

    public CDClassNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var builder = new PapayaViewBuilder();
        var domainType = builder.domainType(builder.entity("Class"));

        var nodeDescription = this.viewBuilderHelper.newNodeDescription()
                .name(NAME)
                .domainType(domainType)
                .semanticCandidatesExpression("aql:self.types")
                .childrenLayoutStrategy(ViewFactory.eINSTANCE.createListLayoutStrategyDescription())
                .labelExpression("aql:self.name")
                .style(this.viewBuilderHelper.newRectangularNodeStyleDescription()
                        .color(this.colorProvider.getColor("color_blue"))
                        .borderColor(this.colorProvider.getColor("border_blue"))
                        .labelColor(this.colorProvider.getColor("label_white"))
                        .withHeader(false)
                        .build())
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)
                .conditionalStyles(this.viewBuilderHelper.newConditionalNodeStyle()
                        .condition("aql:self.abstract")
                        .style(this.viewBuilderHelper.newRectangularNodeStyleDescription()
                                .color(this.colorProvider.getColor("color_green"))
                                .borderColor(this.colorProvider.getColor("border_green"))
                                .labelColor(this.colorProvider.getColor("label_white"))
                                .withHeader(false)
                                .build())
                        .build())
                .build();

        return nodeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var classNodeDescription = cache.getNodeDescription(NAME);
        var packageNodeDescription = cache.getNodeDescription(CDPackageNodeDescriptionProvider.NAME);
        if (classNodeDescription.isPresent() && packageNodeDescription.isPresent()) {
            packageNodeDescription.get().getChildrenDescriptions().add(classNodeDescription.get());
            classNodeDescription.get().setPalette(this.palette(cache));
        }
    }

    private NodePalette palette(IViewDiagramElementFinder cache) {
        var optionalClassNodeDescription = cache.getNodeDescription(NAME);
        var optionalInterfaceNodeDescription = cache.getNodeDescription(CDInterfaceNodeDescriptionProvider.NAME);

        var nodePaletteBuilder = this.viewBuilderHelper.newNodePalette();
        if (optionalClassNodeDescription.isPresent() && optionalInterfaceNodeDescription.isPresent()) {
            var nodePalette = nodePaletteBuilder.edgeTools(this.createExtendsClassEdgeTool(optionalClassNodeDescription.get()),
                    this.createImplementsInterfaceEdgeTool(optionalInterfaceNodeDescription.get()))
                .labelEditTool(new PapayaToolsFactory().editName())
                .deleteTool(new PapayaToolsFactory().deleteTool())
                .build();
            optionalClassNodeDescription.get().setPalette(nodePalette);
            return nodePalette;
        }
        return nodePaletteBuilder.build();
    }

    private EdgeTool createExtendsClassEdgeTool(NodeDescription classNodeDescription) {
        return this.viewBuilderHelper.newEdgeTool()
                .name("Extends Class")
                .targetElementDescriptions(classNodeDescription)
                .body(this.viewBuilderHelper.newChangeContext()
                    .expression("aql:semanticEdgeSource")
                    .children(this.viewBuilderHelper.newSetValue()
                        .featureName("extends")
                        .valueExpression("aql:semanticEdgeTarget")
                        .build())
                    .build())
                .build();
    }

    private EdgeTool createImplementsInterfaceEdgeTool(NodeDescription interfaceNodeDescription) {
        return this.viewBuilderHelper.newEdgeTool()
                .name("Implements")
                .targetElementDescriptions(interfaceNodeDescription)
                .body(this.viewBuilderHelper.newChangeContext()
                        .expression("aql:semanticEdgeSource")
                        .children(this.viewBuilderHelper.newSetValue()
                            .featureName("implements")
                            .valueExpression("aql:semanticEdgeTarget")
                            .build())
                        .build())
                .build();
    }
}
