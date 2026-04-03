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
 * Initialize the label size attribute of a LabelLayoutData.
 *
 * @author frouene
 */
@Service
public class DiagramLabelSizeMigrationParticipant implements IRepresentationMigrationParticipant {

    public static final String LABEL_SIZE = "size";

    public static final String LABEL_LAYOUT_DATA = "labelLayoutData";

    @Override
    public String getVersion() {
        return "2025.12.0-202511041100";
    }

    @Override
    public String getKind() {
        return "siriusComponents://representation?type=Diagram";
    }

    @Override
    public void replaceJsonNode(IEditingContext editingContext, ObjectNode root, String currentAttribute, JsonNode currentValue) {
        if (currentAttribute.equals(LABEL_LAYOUT_DATA)) {
            if (root.get(LABEL_LAYOUT_DATA) instanceof ObjectNode labelLayoutData) {
                for (JsonNode edgeLayoutDataElement : labelLayoutData) {
                    if (edgeLayoutDataElement instanceof ObjectNode elementObjectNode) {
                        elementObjectNode.putObject(LABEL_SIZE);
                    }
                }
            }
        }
    }
}
