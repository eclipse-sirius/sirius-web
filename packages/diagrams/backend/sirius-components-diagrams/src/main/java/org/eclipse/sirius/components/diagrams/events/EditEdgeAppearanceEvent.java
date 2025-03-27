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

import org.eclipse.sirius.components.diagrams.appearancedata.EdgeAppearanceData;

/**
 * Diagram event used to transmit an updated appearance data of an edge.
 *
 * @author nvannier
 */
public class EditEdgeAppearanceEvent implements IDiagramEvent {

    private final String edgeId;

    private final EdgeAppearanceData edgeAppearanceData;

    public EditEdgeAppearanceEvent(String edgeId, EdgeAppearanceData appearanceData) {
        this.edgeId = edgeId;
        this.edgeAppearanceData = appearanceData;
    }

    public String getEdgeId() {
        return edgeId;
    }

    public EdgeAppearanceData getEdgeAppearanceData() {
        return edgeAppearanceData;
    }
}
