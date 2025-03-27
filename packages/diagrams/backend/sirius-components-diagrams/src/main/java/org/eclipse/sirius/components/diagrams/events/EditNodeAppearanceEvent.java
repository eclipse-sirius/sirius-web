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
package org.eclipse.sirius.components.diagrams.events;

import org.eclipse.sirius.components.diagrams.appearancedata.NodeAppearanceData;

/**
 * Diagram event used to transmit an updated appearance data of a node.
 *
 * @author nvannier
 */
public class EditNodeAppearanceEvent implements IDiagramEvent {

    private final String nodeId;

    private final NodeAppearanceData nodeAppearanceData;

    public EditNodeAppearanceEvent(String nodeId, NodeAppearanceData appearanceData) {
        this.nodeId = nodeId;
        this.nodeAppearanceData = appearanceData;
    }

    public String getNodeId() {
        return nodeId;
    }

    public NodeAppearanceData getNodeAppearanceData() {
        return nodeAppearanceData;
    }
}
