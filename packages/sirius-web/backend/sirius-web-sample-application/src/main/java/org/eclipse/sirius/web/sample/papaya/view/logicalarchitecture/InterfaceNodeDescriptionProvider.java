/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture;

import java.util.Objects;

import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.web.sample.papaya.view.PapayaToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;

/**
 * Description of the interface.
 *
 * @author sbegaudeau
 */
public class InterfaceNodeDescriptionProvider implements INodeDescriptionProvider {

    private final IColorProvider colorProvider;

    public InterfaceNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var nodeStyle = DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_blue_3"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_blue_2"));

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("Interface");
        nodeDescription.setSemanticCandidatesExpression("aql:self.types");
        nodeDescription.setChildrenLayoutStrategy(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription());
        nodeDescription.setInsideLabel(new PapayaViewBuilder().createInsideLabelDescriptionWithHeader("aql:self.name", this.colorProvider.getColor("label_white"), true));
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.getChildrenDescriptions().add(this.operationNodeDescription());

        var nodePalette = DiagramFactory.eINSTANCE.createNodePalette();
        nodeDescription.setPalette(nodePalette);
        nodePalette.setLabelEditTool(new PapayaToolsFactory().editName());
        nodePalette.setDeleteTool(new PapayaToolsFactory().deleteTool());
        var newOperationNodeTool = new PapayaToolsFactory().createNamedElement("papaya_logical_architecture::Operation", "operations", "Operation");
        newOperationNodeTool.setName("New Operation");
        nodePalette.getNodeTools().add(newOperationNodeTool);

        var extendsInterfaceEdgeTool = DiagramFactory.eINSTANCE.createEdgeTool();
        extendsInterfaceEdgeTool.setName("Extends");
        extendsInterfaceEdgeTool.getTargetElementDescriptions().add(nodeDescription);
        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:semanticEdgeSource");
        var setTargetValue = ViewFactory.eINSTANCE.createSetValue();
        setTargetValue.setFeatureName("extends");
        setTargetValue.setValueExpression("aql:semanticEdgeTarget");

        changeContext.getChildren().add(setTargetValue);
        extendsInterfaceEdgeTool.getBody().add(changeContext);
        nodePalette.getEdgeTools().add(extendsInterfaceEdgeTool);

        return nodeDescription;
    }

    private NodeDescription operationNodeDescription() {
        var nodeStyle = DiagramFactory.eINSTANCE.createIconLabelNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_blue_3"));

        var builder = new PapayaViewBuilder();
        var nodeDescription = builder.createNodeDescription("Operation");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.setSemanticCandidatesExpression("aql:self.operations");
        nodeDescription.setInsideLabel(new PapayaViewBuilder().createInsideLabelDescription("aql:self.name + '(): ' + if self.type = null then 'void' else self.type.name endif", this.colorProvider.getColor("label_white")));

        var nodePalette = DiagramFactory.eINSTANCE.createNodePalette();
        nodeDescription.setPalette(nodePalette);

        nodePalette.setLabelEditTool(new PapayaToolsFactory().editName());
        nodePalette.setDeleteTool(new PapayaToolsFactory().deleteTool());

        return nodeDescription;
    }

}
