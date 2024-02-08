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
package org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.exchange;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.web.sample.papaya.view.PapayaToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;


/**
 * Description of the component.
 *
 * @author mcharfadi
 */
public class CEDComponentNodeProvider implements INodeDescriptionProvider {

    private final IColorProvider colorProvider;

    private final ViewBuilders viewBuilderHelper = new ViewBuilders();

    private final DiagramBuilders diagramBuilderHelper = new DiagramBuilders();

    public CEDComponentNodeProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var nodeStyle = DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_blue_4"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_blue_4"));

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("Component");
        nodeDescription.setSemanticCandidatesExpression("aql:self.components");
        nodeDescription.setChildrenLayoutStrategy(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        nodeDescription.setStyle(nodeStyle);

        var nodePalette = DiagramFactory.eINSTANCE.createNodePalette();
        nodeDescription.setPalette(nodePalette);

        var newComponentNodeTool = new PapayaToolsFactory().createNamedElement("papaya_logical_architecture::Component", "components", "Component");
        newComponentNodeTool.setName("New Component");
        nodePalette.getNodeTools().add(newComponentNodeTool);

        var newComponentPortNodeTool = new PapayaToolsFactory().createNamedElement("papaya_logical_architecture::ComponentPort", "ports", "Port");
        newComponentPortNodeTool.setName("New ComponentPort");
        nodePalette.getNodeTools().add(newComponentPortNodeTool);
        nodePalette.getEdgeTools().add(this.createComponentExchangeEdgeTool(nodeDescription));
        nodePalette.setLabelEditTool(new PapayaToolsFactory().editName());
        nodePalette.setDeleteTool(new PapayaToolsFactory().deleteTool());

        return nodeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalComponentNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Component");
        optionalComponentNodeDescription.ifPresent(nodeDescription -> {
                diagramDescription.getNodeDescriptions().add(nodeDescription);
                var optionalComponentPortNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::ComponentPort");
                optionalComponentPortNodeDescription.ifPresent(componentPortDescription -> nodeDescription.getBorderNodesDescriptions().add(componentPortDescription));
                nodeDescription.getReusedChildNodeDescriptions().add(nodeDescription);
            }
        );
    }

    private EdgeTool createComponentExchangeEdgeTool(NodeDescription nodeDescription) {
        return this.diagramBuilderHelper.newEdgeTool()
            .name("Create Component Exchange")
            .targetElementDescriptions(nodeDescription)
            .body(this.viewBuilderHelper.newChangeContext()
                .expression("aql:semanticEdgeSource.createComponentExchange(semanticEdgeTarget)")
                .build())
            .build();
    }

}
