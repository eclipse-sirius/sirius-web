/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import org.springframework.stereotype.Service;

/**
 * Remove the attributes source and target anchor relative positions from diagram representation and in its content.
 *
 * @author gcoutable
 */
@Service
public class DiagramAnchorsAttributeMigrationParticipant implements IRepresentationMigrationParticipant {

    public static final String SOURCE_ANCHOR_RELATIVE_POSITION = "sourceAnchorRelativePosition";

    public static final String TARGET_ANCHOR_RELATIVE_POSITION = "targetAnchorRelativePosition";

    @Override
    public String getVersion() {
        return "2024.11.0-202409231051";
    }

    @Override
    public String getKind() {
        return "siriusComponents://representation?type=Diagram";
    }

    @Override
    public void replaceJsonNode(ObjectNode root, String currentAttribute, JsonNode currentValue) {
        if (currentAttribute.equals(SOURCE_ANCHOR_RELATIVE_POSITION)) {
            root.remove(SOURCE_ANCHOR_RELATIVE_POSITION);
        }
        if (currentAttribute.equals(TARGET_ANCHOR_RELATIVE_POSITION)) {
            root.remove(TARGET_ANCHOR_RELATIVE_POSITION);
        }
    }
}
