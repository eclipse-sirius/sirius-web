/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
 * Initialize the HandleLayoutData attribute of a EdgeLayoutData.
 *
 * @author mcharfadi
 */
@Service
public class EdgeHandleLayoutDataMigrationParticipant implements IRepresentationMigrationParticipant {

    public static final String EDGE_ANCHOR_LAYOUT_DATA = "edgeAnchorLayoutData";

    public static final String EDGE_LAYOUT_DATA = "edgeLayoutData";

    @Override
    public String getVersion() {
        return "2025.6.0-202506011650";
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
                        elementObjectNode.putArray(EDGE_ANCHOR_LAYOUT_DATA);
                    }
                }
            }
        }
    }
}
