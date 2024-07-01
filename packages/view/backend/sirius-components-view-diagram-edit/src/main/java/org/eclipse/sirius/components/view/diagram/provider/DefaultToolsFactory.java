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
package org.eclipse.sirius.components.view.diagram.provider;

import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.diagram.DeleteTool;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramPalette;
import org.eclipse.sirius.components.view.diagram.EdgePalette;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.EdgeToolSection;
import org.eclipse.sirius.components.view.diagram.InsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelStyle;
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.OutsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.OutsideLabelStyle;
import org.eclipse.sirius.components.view.diagram.SourceEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool;

/**
 * Factory to create tool descriptions which invoke the default/canonical behaviors.
 *
 * @author pcdavid
 */
public class DefaultToolsFactory {

    private static final String HAS_CHILDREN_EXPRESSION = "aql:selectedNode.getChildNodes()->notEmpty() or selectedNode.getBorderNodes()->notEmpty()";

    private static final String HAS_HIDDEN_CHILDREN_EXPRESSION = "aql:selectedNode.getChildNodes()->union(selectedNode.getBorderNodes())->select(n | n.isHidden())->notEmpty()";

    public DiagramPalette createDefaultDiagramPalette() {
        DiagramPalette palette = DiagramFactory.eINSTANCE.createDiagramPalette();
        return palette;
    }

    public NodePalette createDefaultNodePalette() {
        NodePalette palette = DiagramFactory.eINSTANCE.createNodePalette();
        palette.setDeleteTool(this.createDefaultDeleteTool());
        palette.setLabelEditTool(this.createDefaultLabelEditTool());
        palette.getToolSections().add(this.createDefaultHideRevealNodeToolSection());
        return palette;
    }

    public EdgePalette createDefaultEdgePalette() {
        EdgePalette palette = DiagramFactory.eINSTANCE.createEdgePalette();
        palette.setDeleteTool(this.createDefaultDeleteTool());
        palette.setCenterLabelEditTool(this.createDefaultLabelEditTool());
        palette.getToolSections().add(this.createDefaultHideRevealEdgeToolSection());
        return palette;
    }

    public NodeTool createDefaultNodeCreationTool() {
        NodeTool newNodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        newNodeTool.setName("Create Node");
        return newNodeTool;
    }

    public EdgeTool createDefaultEdgeTool() {
        EdgeTool newEdgeTool = DiagramFactory.eINSTANCE.createEdgeTool();
        newEdgeTool.setName("Create Edge");
        return newEdgeTool;
    }

    public SourceEdgeEndReconnectionTool createDefaultSourceEdgeReconnectionTool() {
        SourceEdgeEndReconnectionTool sourceReconnectionTool = DiagramFactory.eINSTANCE.createSourceEdgeEndReconnectionTool();
        sourceReconnectionTool.setName("Reconnect Edge Source");
        ChangeContext reconnectSourceInitialOperation = ViewFactory.eINSTANCE.createChangeContext();
        reconnectSourceInitialOperation.setExpression("aql:edgeSemanticElement");
        SetValue reconnectSourceSetValue = ViewFactory.eINSTANCE.createSetValue();
        reconnectSourceInitialOperation.getChildren().add(reconnectSourceSetValue);
        sourceReconnectionTool.getBody().add(reconnectSourceInitialOperation);
        return sourceReconnectionTool;
    }

    public TargetEdgeEndReconnectionTool createDefaultTargetEdgeReconnectionTool() {
        TargetEdgeEndReconnectionTool targetReconnectionTool = DiagramFactory.eINSTANCE.createTargetEdgeEndReconnectionTool();
        targetReconnectionTool.setName("Reconnect Edge Target");
        ChangeContext reconnectTargetInitialOperation = ViewFactory.eINSTANCE.createChangeContext();
        reconnectTargetInitialOperation.setExpression("aql:edgeSemanticElement");
        SetValue reconnectTargetSetValue = ViewFactory.eINSTANCE.createSetValue();
        reconnectTargetInitialOperation.getChildren().add(reconnectTargetSetValue);
        targetReconnectionTool.getBody().add(reconnectTargetInitialOperation);
        return targetReconnectionTool;
    }

    public LabelEditTool createDefaultLabelEditTool() {
        return this.createLabelEditTool("Edit Label");
    }

    public LabelEditTool createDefaultBeginLabelEditTool() {
        return this.createLabelEditTool("Edit Begin Label");
    }

    public LabelEditTool createDefaultCenterLabelEditTool() {
        return this.createLabelEditTool("Edit Center Label");
    }

    public LabelEditTool createDefaultEndLabelEditTool() {
        return this.createLabelEditTool("Edit End Label");
    }

    private LabelEditTool createLabelEditTool(String name) {
        LabelEditTool newLabelEditTool = DiagramFactory.eINSTANCE.createLabelEditTool();
        newLabelEditTool.setName(name);
        ChangeContext body = ViewFactory.eINSTANCE.createChangeContext();
        body.setExpression("aql:self.defaultEditLabel(newLabel)");
        newLabelEditTool.getBody().add(body);
        return newLabelEditTool;
    }

    public DeleteTool createDefaultDeleteTool() {
        DeleteTool newDeleteTool = DiagramFactory.eINSTANCE.createDeleteTool();
        newDeleteTool.setName("Delete");
        ChangeContext body = ViewFactory.eINSTANCE.createChangeContext();
        body.setExpression("aql:self.defaultDelete()");
        newDeleteTool.getBody().add(body);
        return newDeleteTool;
    }

    public InsideLabelDescription createDefaultInsideLabelDescription() {
        InsideLabelDescription insideLabelDescription = DiagramFactory.eINSTANCE.createInsideLabelDescription();
        InsideLabelStyle style = DiagramFactory.eINSTANCE.createInsideLabelStyle();
        style.setBorderSize(0);
        insideLabelDescription.setStyle(style);
        return insideLabelDescription;
    }

    public OutsideLabelDescription createDefaultOutsideLabelDescription() {
        OutsideLabelDescription outsideLabelDescription = DiagramFactory.eINSTANCE.createOutsideLabelDescription();
        OutsideLabelStyle outsideLabelStyle = DiagramFactory.eINSTANCE.createOutsideLabelStyle();
        outsideLabelStyle.setBorderSize(0);
        outsideLabelDescription.setStyle(outsideLabelStyle);
        return outsideLabelDescription;
    }

    public NodeToolSection createDefaultHideRevealNodeToolSection() {
        NodeToolSection nodeToolSection = DiagramFactory.eINSTANCE.createNodeToolSection();
        nodeToolSection.setName("Hide/Show Tool Section");
        nodeToolSection.getNodeTools().add(this.createDefaultHideNodeTool());
        nodeToolSection.getNodeTools().add(this.createDefaultHideAllChildrenNodeTool());
        nodeToolSection.getNodeTools().add(this.createDefaultRevealAllChildrenNodeTool());
        nodeToolSection.getNodeTools().add(this.createDefaultResetAllChildrenVisibilityModifiersNodeTool());
        nodeToolSection.getNodeTools().add(this.createDefaultRevealChildrenWithValueNodeTool());
        return nodeToolSection;
    }

    public NodeTool createDefaultHideNodeTool() {
        NodeTool newNodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        newNodeTool.setName("Hide");
        ChangeContext body = ViewFactory.eINSTANCE.createChangeContext();
        body.setExpression("aql:diagramServices.hide(Sequence{selectedNode})");
        newNodeTool.getBody().add(body);
        newNodeTool.setIconURLsExpression("aql:'/icons/full/obj16/HideTool.svg'");
        return newNodeTool;
    }

    public NodeTool createDefaultHideAllChildrenNodeTool() {
        NodeTool newNodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        newNodeTool.setName("Hide all content");
        ChangeContext body = ViewFactory.eINSTANCE.createChangeContext();
        body.setExpression("aql:diagramServices.hide(selectedNode.getChildNodes()->union(selectedNode.getBorderNodes()))");
        newNodeTool.getBody().add(body);
        newNodeTool.setIconURLsExpression("aql:'/icons/full/obj16/HideTool.svg'");
        newNodeTool.setPreconditionExpression(HAS_CHILDREN_EXPRESSION);
        return newNodeTool;
    }

    public NodeTool createDefaultRevealAllChildrenNodeTool() {
        NodeTool newNodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        newNodeTool.setName("Show all content");
        ChangeContext body = ViewFactory.eINSTANCE.createChangeContext();
        body.setExpression("aql:diagramServices.reveal(selectedNode.getChildNodes()->union(selectedNode.getBorderNodes()))");
        newNodeTool.getBody().add(body);
        newNodeTool.setIconURLsExpression("aql:'/icons/full/obj16/ShowTool.svg'");
        newNodeTool.setPreconditionExpression(HAS_HIDDEN_CHILDREN_EXPRESSION);
        return newNodeTool;
    }

    public NodeTool createDefaultResetAllChildrenVisibilityModifiersNodeTool() {
        NodeTool newNodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        newNodeTool.setName("Reset content");
        ChangeContext body = ViewFactory.eINSTANCE.createChangeContext();
        body.setExpression("aql:diagramServices.resetViewModifiers(selectedNode.getChildNodes()->union(selectedNode.getBorderNodes()))");
        newNodeTool.getBody().add(body);
        newNodeTool.setIconURLsExpression("aql:'/icons/full/obj16/ShowTool.svg'");
        newNodeTool.setPreconditionExpression(HAS_CHILDREN_EXPRESSION);
        return newNodeTool;
    }

    public NodeTool createDefaultRevealChildrenWithValueNodeTool() {
        NodeTool newNodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        newNodeTool.setName("Show valued content");
        ChangeContext body = ViewFactory.eINSTANCE.createChangeContext();
        body.setExpression("aql:diagramServices.reveal(selectedNode.getChildNodes()->union(selectedNode.getBorderNodes())->select(n | n.getChildNodes()->notEmpty() or n.getBorderNodes()->notEmpty()))");
        newNodeTool.getBody().add(body);
        newNodeTool.setIconURLsExpression("aql:'/icons/full/obj16/ShowTool.svg'");
        newNodeTool.setPreconditionExpression(HAS_HIDDEN_CHILDREN_EXPRESSION);
        return newNodeTool;
    }

    public EdgeToolSection createDefaultHideRevealEdgeToolSection() {
        EdgeToolSection edgeToolSection = DiagramFactory.eINSTANCE.createEdgeToolSection();
        edgeToolSection.setName("Hide/Show Tool Section");
        edgeToolSection.getNodeTools().add(this.createDefaultHideEdgeTool());
        return edgeToolSection;
    }

    public NodeTool createDefaultHideEdgeTool() {
        NodeTool newNodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        newNodeTool.setName("Hide");
        ChangeContext body = ViewFactory.eINSTANCE.createChangeContext();
        body.setExpression("aql:diagramServices.hide(Sequence{selectedEdge})");
        newNodeTool.getBody().add(body);
        newNodeTool.setIconURLsExpression("aql:'/icons/full/obj16/HideTool.svg'");
        return newNodeTool;
    }
}
