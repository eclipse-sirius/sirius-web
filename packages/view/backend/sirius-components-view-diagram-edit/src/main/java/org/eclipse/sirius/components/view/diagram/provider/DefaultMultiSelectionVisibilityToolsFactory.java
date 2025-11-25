/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;

/**
 * Factory to create multi selection tool to handle visibility of nodes and their children.
 *
 * @author mcharfadi
 */
public class DefaultMultiSelectionVisibilityToolsFactory {

    public NodeToolSection createDefaultHideRevealNodeToolSection() {
        NodeToolSection nodeToolSection = DiagramFactory.eINSTANCE.createNodeToolSection();
        nodeToolSection.setName("Show/Hide");
        nodeToolSection.getNodeTools().add(this.createDefaultHideNodeTool());
        nodeToolSection.getNodeTools().add(this.createDefaultHideAllChildrenNodeTool());
        nodeToolSection.getNodeTools().add(this.createDefaultRevealAllChildrenNodeTool());
        return nodeToolSection;
    }

    public NodeTool createDefaultHideNodeTool() {
        NodeTool newNodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        newNodeTool.setName("Hide");
        ChangeContext body = ViewFactory.eINSTANCE.createChangeContext();
        body.setExpression("aql:diagramServices.hide(selectedNodes)");
        newNodeTool.getBody().add(body);
        newNodeTool.setIconURLsExpression("aql:'/icons/full/obj16/HideTool.svg'");
        return newNodeTool;
    }

    public NodeTool createDefaultHideAllChildrenNodeTool() {
        NodeTool newNodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        newNodeTool.setName("Hide all content");
        ChangeContext body = ViewFactory.eINSTANCE.createChangeContext();
        body.setExpression("aql:diagramServices.hide(selectedNodes.getChildNodes()->union(selectedNodes.getBorderNodes()))");
        newNodeTool.getBody().add(body);
        newNodeTool.setIconURLsExpression("aql:'/icons/full/obj16/HideTool.svg'");
        newNodeTool.setPreconditionExpression("aql:selectedNodes.getChildNodes()->notEmpty() or selectedNodes.getBorderNodes()->notEmpty()");
        return newNodeTool;
    }

    public NodeTool createDefaultRevealAllChildrenNodeTool() {
        NodeTool newNodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        newNodeTool.setName("Show all content");
        ChangeContext body = ViewFactory.eINSTANCE.createChangeContext();
        body.setExpression("aql:diagramServices.reveal(selectedNodes.getChildNodes()->union(selectedNodes.getBorderNodes()))");
        newNodeTool.getBody().add(body);
        newNodeTool.setIconURLsExpression("aql:'/icons/full/obj16/ShowTool.svg'");
        newNodeTool.setPreconditionExpression("aql:selectedNodes.getChildNodes()->union(selectedNodes.getBorderNodes())->select(n | n.isHidden())->notEmpty()");
        return newNodeTool;
    }

}
