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
 * Description of the class.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ClassNodeDescriptionProvider implements INodeDescriptionProvider {

    @Override
    public NodeDescription create() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#1976D2");
        nodeStyle.setBorderColor("#0d47a1");
        nodeStyle.setLabelColor("white");
        nodeStyle.setWithHeader(false);

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("Class");
        nodeDescription.setSemanticCandidatesExpression("aql:self.types");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createListLayoutStrategyDescription());
        nodeDescription.setLabelExpression("aql:self.name");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.getChildrenDescriptions().add(this.attributesNodeDescription());
        nodeDescription.getChildrenDescriptions().add(this.operationsNodeDescription());

        var newClassNodeTool = new PapayaToolsFactory().createNamedElement("papaya_logical_architecture::Class", "types", "Class");
        newClassNodeTool.setName("New Class");
        nodeDescription.getNodeTools().add(newClassNodeTool);

        var abstractNodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        abstractNodeStyle.setColor("#00796B");
        abstractNodeStyle.setBorderColor("#004D40");
        abstractNodeStyle.setLabelColor("white");
        abstractNodeStyle.setWithHeader(false);

        var abstractConditionalNodeStyle = ViewFactory.eINSTANCE.createConditionalNodeStyle();
        abstractConditionalNodeStyle.setCondition("aql:self.abstract");
        abstractConditionalNodeStyle.setStyle(abstractNodeStyle);
        nodeDescription.getConditionalStyles().add(abstractConditionalNodeStyle);
        nodeDescription.setLabelEditTool(new PapayaToolsFactory().editName());
        nodeDescription.setDeleteTool(new PapayaToolsFactory().deleteTool());

        return nodeDescription;
    }

    private NodeDescription attributesNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#1976D2");
        nodeStyle.setBorderColor("#0d47a1");
        nodeStyle.setLabelColor("white");
        nodeStyle.setBorderRadius(0);

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("Class");
        nodeDescription.setName(nodeDescription.getName() + " - Attributes");
        nodeDescription.setSemanticCandidatesExpression("aql:self");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createListLayoutStrategyDescription());
        nodeDescription.setLabelExpression("");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.getChildrenDescriptions().add(this.attributeNodeDescription());

        var abstractNodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        abstractNodeStyle.setColor("#00796B");
        abstractNodeStyle.setBorderColor("#004D40");
        abstractNodeStyle.setLabelColor("white");
        abstractNodeStyle.setWithHeader(false);

        var abstractConditionalNodeStyle = ViewFactory.eINSTANCE.createConditionalNodeStyle();
        abstractConditionalNodeStyle.setCondition("aql:self.abstract");
        abstractConditionalNodeStyle.setStyle(abstractNodeStyle);
        nodeDescription.getConditionalStyles().add(abstractConditionalNodeStyle);

        return nodeDescription;
    }

    private NodeDescription attributeNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createIconLabelNodeStyleDescription();
        nodeStyle.setColor("");
        nodeStyle.setBorderColor("");
        nodeStyle.setLabelColor("white");

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("Attribute");
        nodeDescription.setSemanticCandidatesExpression("aql:self.attributes");
        nodeDescription.setLabelExpression("aql:self.name + ': ' + if self.type = null then 'void' else self.type.name endif");
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

    private NodeDescription operationsNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#1976D2");
        nodeStyle.setBorderColor("#0d47a1");
        nodeStyle.setLabelColor("white");
        nodeStyle.setBorderRadius(0);

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("Class");
        nodeDescription.setName(nodeDescription.getName() + " - Operations");
        nodeDescription.setSemanticCandidatesExpression("aql:self");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createListLayoutStrategyDescription());
        nodeDescription.setLabelExpression("");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.getChildrenDescriptions().add(this.operationNodeDescription());

        var abstractNodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        abstractNodeStyle.setColor("#00796B");
        abstractNodeStyle.setBorderColor("#004D40");
        abstractNodeStyle.setLabelColor("white");
        abstractNodeStyle.setWithHeader(false);

        var abstractConditionalNodeStyle = ViewFactory.eINSTANCE.createConditionalNodeStyle();
        abstractConditionalNodeStyle.setCondition("aql:self.abstract");
        abstractConditionalNodeStyle.setStyle(abstractNodeStyle);
        nodeDescription.getConditionalStyles().add(abstractConditionalNodeStyle);

        return nodeDescription;
    }

    private NodeDescription operationNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createIconLabelNodeStyleDescription();
        nodeStyle.setColor("");
        nodeStyle.setBorderColor("");
        nodeStyle.setLabelColor("white");

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("Operation");
        nodeDescription.setSemanticCandidatesExpression("aql:self.operations");
        nodeDescription.setLabelExpression("aql:self.name + '(): ' + if self.type = null then 'void' else self.type.name endif");
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

}
