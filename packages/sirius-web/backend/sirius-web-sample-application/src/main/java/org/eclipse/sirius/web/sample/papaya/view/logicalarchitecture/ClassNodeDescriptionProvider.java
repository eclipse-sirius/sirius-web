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

import java.util.Objects;

import org.eclipse.emf.common.util.EList;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.EdgeTool;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.IColorProvider;
import org.eclipse.sirius.web.sample.papaya.view.INodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewCache;

/**
 * Description of the class.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ClassNodeDescriptionProvider implements INodeDescriptionProvider {

    private final IColorProvider colorProvider;

    public ClassNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_blue"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_blue"));
        nodeStyle.setLabelColor(this.colorProvider.getColor("label_white"));
        nodeStyle.setWithHeader(false);

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("Class");
        nodeDescription.setSemanticCandidatesExpression("aql:self.types");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createListLayoutStrategyDescription());
        nodeDescription.setLabelExpression("aql:self.name");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.getChildrenDescriptions().add(this.attributesNodeDescription());
        nodeDescription.getChildrenDescriptions().add(this.operationsNodeDescription());

        var nodePalette = ViewFactory.eINSTANCE.createNodePalette();
        nodeDescription.setPalette(nodePalette);
        var newAttributeNodeTool = new PapayaToolsFactory().createNamedElement("papaya_logical_architecture::Attribute", "attributes", "Attibute");
        newAttributeNodeTool.setName("New Attribute");
        nodePalette.getNodeTools().add(newAttributeNodeTool);
        var newOperationNodeTool = new PapayaToolsFactory().createNamedElement("papaya_logical_architecture::Operation", "operations", "Operation");
        newOperationNodeTool.setName("New Operation");
        nodePalette.getNodeTools().add(newOperationNodeTool);

        var abstractNodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        abstractNodeStyle.setColor(this.colorProvider.getColor("color_green"));
        abstractNodeStyle.setBorderColor(this.colorProvider.getColor("border_green"));
        abstractNodeStyle.setLabelColor(this.colorProvider.getColor("label_white"));
        abstractNodeStyle.setWithHeader(false);

        var abstractConditionalNodeStyle = ViewFactory.eINSTANCE.createConditionalNodeStyle();
        abstractConditionalNodeStyle.setCondition("aql:self.abstract");
        abstractConditionalNodeStyle.setStyle(abstractNodeStyle);
        nodeDescription.getConditionalStyles().add(abstractConditionalNodeStyle);
        nodePalette.setLabelEditTool(new PapayaToolsFactory().editName());
        nodePalette.setDeleteTool(new PapayaToolsFactory().deleteTool());
        nodePalette.getEdgeTools().add(this.createExtendsClassEdgeTool());
        nodePalette.getEdgeTools().add(this.createImplementsInterfaceEdgeTool());

        return nodeDescription;
    }

    private EdgeTool createExtendsClassEdgeTool() {
        var extendsClassEdgeTool = ViewFactory.eINSTANCE.createEdgeTool();
        extendsClassEdgeTool.setName("Extends");
        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:semanticEdgeSource");
        var setTargetValue = ViewFactory.eINSTANCE.createSetValue();
        setTargetValue.setFeatureName("extends");
        setTargetValue.setValueExpression("aql:semanticEdgeTarget");

        changeContext.getChildren().add(setTargetValue);
        extendsClassEdgeTool.getBody().add(changeContext);
        return extendsClassEdgeTool;
    }

    private EdgeTool createImplementsInterfaceEdgeTool() {
        var implementsInterfaceEdgeTool = ViewFactory.eINSTANCE.createEdgeTool();
        implementsInterfaceEdgeTool.setName("Implements");
        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:semanticEdgeSource");
        var setTargetValue = ViewFactory.eINSTANCE.createSetValue();
        setTargetValue.setFeatureName("implements");
        setTargetValue.setValueExpression("aql:semanticEdgeTarget");

        changeContext.getChildren().add(setTargetValue);
        implementsInterfaceEdgeTool.getBody().add(changeContext);
        return implementsInterfaceEdgeTool;
    }

    private NodeDescription attributesNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_blue"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_blue"));
        nodeStyle.setLabelColor(this.colorProvider.getColor("label_white"));
        nodeStyle.setBorderRadius(0);

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("Class");
        nodeDescription.setName(nodeDescription.getName() + " - Attributes");
        nodeDescription.setSemanticCandidatesExpression("aql:self");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createListLayoutStrategyDescription());
        nodeDescription.setLabelExpression("");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.getChildrenDescriptions().add(this.attributeNodeDescription());

        var nodePalette = ViewFactory.eINSTANCE.createNodePalette();
        var newAttributeNodeTool = new PapayaToolsFactory().createNamedElement("papaya_logical_architecture::Attribute", "attributes", "Attribute");
        newAttributeNodeTool.setName("New Attribute");
        nodePalette.getNodeTools().add(newAttributeNodeTool);
        nodeDescription.setPalette(nodePalette);

        var abstractNodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        abstractNodeStyle.setColor(this.colorProvider.getColor("color_green"));
        abstractNodeStyle.setBorderColor(this.colorProvider.getColor("border_green"));
        abstractNodeStyle.setLabelColor(this.colorProvider.getColor("label_white"));
        abstractNodeStyle.setWithHeader(false);

        var abstractConditionalNodeStyle = ViewFactory.eINSTANCE.createConditionalNodeStyle();
        abstractConditionalNodeStyle.setCondition("aql:self.abstract");
        abstractConditionalNodeStyle.setStyle(abstractNodeStyle);
        nodeDescription.getConditionalStyles().add(abstractConditionalNodeStyle);

        return nodeDescription;
    }

    private NodeDescription attributeNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createIconLabelNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_blue"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_blue"));
        nodeStyle.setLabelColor(this.colorProvider.getColor("label_white"));

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("Attribute");
        nodeDescription.setSemanticCandidatesExpression("aql:self.attributes");
        nodeDescription.setLabelExpression("aql:self.name + ': ' + if self.type = null then 'void' else self.type.name endif");
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

    private NodeDescription operationsNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_blue"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_blue"));
        nodeStyle.setLabelColor(this.colorProvider.getColor("label_white"));
        nodeStyle.setBorderRadius(0);

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("Class");
        nodeDescription.setName(nodeDescription.getName() + " - Operations");
        nodeDescription.setSemanticCandidatesExpression("aql:self");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createListLayoutStrategyDescription());
        nodeDescription.setLabelExpression("");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.getChildrenDescriptions().add(this.operationNodeDescription());

        var nodePalette = ViewFactory.eINSTANCE.createNodePalette();
        var newOperationNodeTool = new PapayaToolsFactory().createNamedElement("papaya_logical_architecture::Operation", "operations", "Operation");
        newOperationNodeTool.setName("New Operation");
        nodePalette.getNodeTools().add(newOperationNodeTool);
        nodeDescription.setPalette(nodePalette);

        var abstractNodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        abstractNodeStyle.setColor(this.colorProvider.getColor("color_green"));
        abstractNodeStyle.setBorderColor(this.colorProvider.getColor("border_green"));
        abstractNodeStyle.setLabelColor(this.colorProvider.getColor("label_white"));
        abstractNodeStyle.setWithHeader(false);

        var abstractConditionalNodeStyle = ViewFactory.eINSTANCE.createConditionalNodeStyle();
        abstractConditionalNodeStyle.setCondition("aql:self.abstract");
        abstractConditionalNodeStyle.setStyle(abstractNodeStyle);
        nodeDescription.getConditionalStyles().add(abstractConditionalNodeStyle);

        return nodeDescription;
    }

    private NodeDescription operationNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createIconLabelNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_empty"));
        nodeStyle.setLabelColor(this.colorProvider.getColor("label_white"));

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("Operation");
        nodeDescription.setSemanticCandidatesExpression("aql:self.operations");
        nodeDescription.setLabelExpression("aql:self.name + '(): ' + if self.type = null then 'void' else self.type.name endif");
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, PapayaViewCache cache) {
        var classNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Class");
        var interfaceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Interface");

        EList<EdgeTool> edgeTools = classNodeDescription.getPalette().getEdgeTools();
        edgeTools.stream().filter(tool -> tool.getName().equals("Extends")).findFirst().ifPresent(extendsClassEdgeTool -> {
            extendsClassEdgeTool.getTargetElementDescriptions().add(classNodeDescription);
        });
        edgeTools.stream().filter(tool -> tool.getName().equals("Implements")).findFirst().ifPresent(extendsClassEdgeTool -> {
            extendsClassEdgeTool.getTargetElementDescriptions().add(interfaceNodeDescription);
        });
    }

}
