/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.representations.migration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * Used to manipulate the Json content before deserialization.
 *
 * @author mcharfadi
 */
public class RepresentationMigrationService {

    private final IEditingContext editingContext;

    private final List<IRepresentationMigrationParticipant> migrationParticipants;

    public RepresentationMigrationService(IEditingContext editingContext, List<IRepresentationMigrationParticipant> migrationParticipants, ObjectNode root) {
        this.editingContext = Objects.requireNonNull(editingContext);
        this.migrationParticipants = Objects.requireNonNull(migrationParticipants);
    }

    public void parseProperties(ObjectNode root, ObjectMapper mapper) {
        if (this.migrationParticipants.isEmpty()) {
            return;
        }

        List<String> attributes = new ArrayList<>();
        root.fieldNames().forEachRemaining(attributes::add);
        for (String attribute : attributes) {
            var currentNode = root.get(attribute);

            if (currentNode.isObject() && currentNode.isContainerNode()) {
                this.parseProperties((ObjectNode) currentNode, mapper);
            }
            if (currentNode.isArray() && currentNode.isContainerNode()) {
                for (JsonNode objNode : currentNode) {
                    if (objNode.isContainerNode()) {
                        this.parseProperties((ObjectNode) objNode, mapper);
                    }
                }
            }

            this.replaceJsonNode(root, attribute, currentNode);
        }
    }

    private void replaceJsonNode(ObjectNode root, String currentAttribute, JsonNode currentValue) {
        for (IRepresentationMigrationParticipant migrationParticipant : this.migrationParticipants) {
            migrationParticipant.replaceJsonNode(this.editingContext, root, currentAttribute, currentValue);
        }
    }

}
