/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.editingcontext.migration.participants.representation;


import org.eclipse.sirius.components.collaborative.representations.migration.IRepresentationMigrationParticipant;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.springframework.stereotype.Service;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ObjectNode;

/**
 * Initialize the relativePositionBendingPoints attribute of a EdgeLayoutData.
 *
 * @author frouene
 */
@Service
public class EdgeHandleLayoutDataRelativePositionBendingPointsMigrationParticipant implements IRepresentationMigrationParticipant {

    public static final String RELATIVE_POSITION_BENDING_POINTS = "relativePositionBendingPoints";

    public static final String EDGE_LAYOUT_DATA = "edgeLayoutData";

    @Override
    public String getVersion() {
        return "2026.5.0-202604141050";
    }

    @Override
    public String getKind() {
        return "siriusComponents://representation?type=Diagram";
    }

    @Override
    public void replaceJsonNode(IEditingContext editingContext, ObjectNode root, String currentAttribute, JsonNode currentValue) {
        if (currentAttribute.equals(EDGE_LAYOUT_DATA)) {
            if (root.get(EDGE_LAYOUT_DATA) instanceof ObjectNode edgeLayoutData) {
                for (JsonNode edgeLayoutDataElement : edgeLayoutData) {
                    if (edgeLayoutDataElement instanceof ObjectNode elementObjectNode) {
                        elementObjectNode.putArray(RELATIVE_POSITION_BENDING_POINTS);
                    }
                }
            }
        }
    }
}
