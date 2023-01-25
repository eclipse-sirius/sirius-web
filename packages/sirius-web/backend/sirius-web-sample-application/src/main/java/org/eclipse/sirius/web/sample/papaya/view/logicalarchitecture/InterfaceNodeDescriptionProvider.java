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
package org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture;

import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.INodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;

/**
 * Description of the interface.
 *
 * @author sbegaudeau
 */
public class InterfaceNodeDescriptionProvider implements INodeDescriptionProvider {

    @Override
    public NodeDescription create() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#3f51b5");
        nodeStyle.setBorderColor("#1a237e");
        nodeStyle.setLabelColor("white");
        nodeStyle.setWithHeader(true);

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("Interface");
        nodeDescription.setSemanticCandidatesExpression("aql:self.types");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createListLayoutStrategyDescription());
        nodeDescription.setLabelExpression("aql:self.name");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.getChildrenDescriptions().add(this.operationNodeDescription());

        var nodePalette = ViewFactory.eINSTANCE.createNodePalette();
        nodeDescription.setPalette(nodePalette);
        nodePalette.setLabelEditTool(new PapayaToolsFactory().editName());
        nodePalette.setDeleteTool(new PapayaToolsFactory().deleteTool());
        var newOperationNodeTool = new PapayaToolsFactory().createNamedElement("papaya_logical_architecture::Operation", "operations", "Operation");
        newOperationNodeTool.setName("New Operation");
        nodePalette.getNodeTools().add(newOperationNodeTool);

        var extendsInterfaceEdgeTool = ViewFactory.eINSTANCE.createEdgeTool();
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
        var nodeStyle = ViewFactory.eINSTANCE.createIconLabelNodeStyleDescription();
        nodeStyle.setColor("#3f51b5");
        nodeStyle.setLabelColor("white");

        var builder = new PapayaViewBuilder();
        var nodeDescription = builder.createNodeDescription("Operation");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.setSemanticCandidatesExpression("aql:self.operations");
        nodeDescription.setLabelExpression("aql:self.name + '(): ' + if self.type = null then 'void' else self.type.name endif");

        var nodePalette = ViewFactory.eINSTANCE.createNodePalette();
        nodeDescription.setPalette(nodePalette);

        nodePalette.setLabelEditTool(new PapayaToolsFactory().editName());
        nodePalette.setDeleteTool(new PapayaToolsFactory().deleteTool());

        return nodeDescription;
    }

}
