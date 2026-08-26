/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

/**
 * Factory to create tool descriptions for studio's View instances, which invoke the default/canonical behaviors.
 *
 * @author pcdavid
 */
public class StudioDefaultToolsFactory {

    public DiagramPalette createDefaultDiagramPalette() {
        return DiagramFactory.eINSTANCE.createDiagramPalette();
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

    public LabelEditTool createDefaultLabelEditTool() {
        return this.createLabelEditTool("Edit Label");
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
        nodeToolSection.setName("Show/Hide");
        nodeToolSection.getNodeTools().add(this.createDefaultHideNodeTool());
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

    public EdgeToolSection createDefaultHideRevealEdgeToolSection() {
        EdgeToolSection edgeToolSection = DiagramFactory.eINSTANCE.createEdgeToolSection();
        edgeToolSection.setName("Show/Hide");
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
