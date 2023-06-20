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
package org.eclipse.sirius.components.view.diagram.provider;

import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.diagram.DeleteTool;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramPalette;
import org.eclipse.sirius.components.view.diagram.EdgePalette;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.SourceEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool;

/**
 * Factory to create tool descriptions which invoke the default/canonical behaviors.
 *
 * @author pcdavid
 */
public class DefaultToolsFactory {

    public DiagramPalette createDefaultDiagramPalette() {
        DiagramPalette palette = DiagramFactory.eINSTANCE.createDiagramPalette();
        return palette;
    }

    public NodePalette createDefaultNodePalette() {
        NodePalette palette = DiagramFactory.eINSTANCE.createNodePalette();
        palette.setDeleteTool(this.createDefaultDeleteTool());
        palette.setLabelEditTool(this.createDefaultLabelEditTool());
        return palette;
    }

    public EdgePalette createDefaultEdgePalette() {
        EdgePalette palette = DiagramFactory.eINSTANCE.createEdgePalette();
        palette.setDeleteTool(this.createDefaultDeleteTool());
        palette.setCenterLabelEditTool(this.createDefaultLabelEditTool());
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
}
