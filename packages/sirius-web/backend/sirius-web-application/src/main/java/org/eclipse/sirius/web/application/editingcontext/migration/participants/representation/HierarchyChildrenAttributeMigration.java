/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ObjectNode;

import org.eclipse.sirius.components.collaborative.representations.migration.IRepresentationMigrationParticipant;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.springframework.stereotype.Service;

/**
 * RepresentationMigrationParticipant that update the attribute children to childNodes from hierarchy representation.
 *
 * @author mcharfadi
 */
@Service
public class HierarchyChildrenAttributeMigration implements IRepresentationMigrationParticipant {
    @Override
    public String getVersion() {
        return "2024.4.3-202405130907";
    }

    @Override
    public String getKind() {
        return "siriusComponents://representation?type=TreeMap";
    }

    @Override
    public void replaceJsonNode(IEditingContext editingContext, ObjectNode root, String currentAttribute, JsonNode currentValue) {
        if (currentAttribute.equals("children")) {
            root.set("childNodes", currentValue);
            root.remove("children");
        }
    }
}
