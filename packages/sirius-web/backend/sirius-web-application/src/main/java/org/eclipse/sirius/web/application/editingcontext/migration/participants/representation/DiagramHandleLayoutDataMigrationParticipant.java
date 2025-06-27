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
package org.eclipse.sirius.web.application.editingcontext.migration.participants.representation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.eclipse.sirius.components.collaborative.representations.migration.IRepresentationMigrationParticipant;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.springframework.stereotype.Service;

/**
 * Initialize the HandleLayoutData attribute of a NodeLayoutData.
 *
 * @author mcharfadi
 */
@Service
public class DiagramHandleLayoutDataMigrationParticipant implements IRepresentationMigrationParticipant {

    public static final String HANDLES_LAYOUT_DATA = "handleLayoutData";

    public static final String NODE_LAYOUT_DATA = "nodeLayoutData";

    @Override
    public String getVersion() {
        return "2025.4.0-202504011650";
    }

    @Override
    public String getKind() {
        return "siriusComponents://representation?type=Diagram";
    }

    @Override
    public void replaceJsonNode(IEditingContext editingContext, ObjectNode root, String currentAttribute, JsonNode currentValue) {
        if (currentAttribute.equals(NODE_LAYOUT_DATA)) {
            if (root.get(NODE_LAYOUT_DATA) instanceof ObjectNode nodeLayoutData) {
                nodeLayoutData.elements().forEachRemaining(node -> ((ObjectNode) node).putArray(HANDLES_LAYOUT_DATA));
            }
        }
    }
}
