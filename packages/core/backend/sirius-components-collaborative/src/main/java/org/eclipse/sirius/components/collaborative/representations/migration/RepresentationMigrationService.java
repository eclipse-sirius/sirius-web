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
package org.eclipse.sirius.components.collaborative.representations.migration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Used to manipulate the Json content before deserializerion.
 *
 * @author mcharfadi
 */
public class RepresentationMigrationService {

    private final RepresentationMigrationData representationMigrationData;

    private Optional<String> optionalRepresentationKind = Optional.empty();

    private final List<IRepresentationMigrationParticipant> migrationParticipants;

    public RepresentationMigrationService(List<IRepresentationMigrationParticipant> migrationParticipants, RepresentationMigrationData representationMigrationData, ObjectNode root) {
        this.optionalRepresentationKind = Optional.ofNullable(root.get("kind"))
                .map(JsonNode::asText);

        this.migrationParticipants = Objects.requireNonNull(migrationParticipants);
        this.representationMigrationData = Objects.requireNonNull(representationMigrationData);
    }

    private boolean isCandidateVersion(IRepresentationMigrationParticipant migrationParticipant) {
        return this.optionalRepresentationKind.filter(representationKind -> migrationParticipant.getVersion().compareTo(this.representationMigrationData.migrationVersion()) > 0 && migrationParticipant.getKind().equals(representationKind)).isPresent();
    }

    public void parseProperties(ObjectNode root, ObjectMapper mapper) {
        List<String> attributes = new ArrayList<>();
        root.fieldNames().forEachRemaining(attributes::add);
        for (String attribute : attributes) {
            var currentNode = root.get(attribute);

            if (currentNode.isObject() && currentNode.isContainerNode()) {
                parseProperties((ObjectNode) currentNode, mapper);
            }
            if (currentNode.isArray() && currentNode.isContainerNode()) {
                for (JsonNode objNode : currentNode) {
                    if (objNode.isContainerNode()) {
                        parseProperties((ObjectNode) objNode, mapper);
                    }
                }
            }

            replaceJsonNode(root, attribute, currentNode);
        }
    }

    private void replaceJsonNode(ObjectNode root, String currentAttribute, JsonNode currentValue) {
        var migrationParticipantsCandidate = migrationParticipants.stream()
                .filter(this::isCandidateVersion)
                .sorted(Comparator.comparing(IRepresentationMigrationParticipant::getVersion))
                .toList();

        for (IRepresentationMigrationParticipant migrationParticipant : migrationParticipantsCandidate) {
            migrationParticipant.replaceJsonNode(root, currentAttribute, currentValue);
        }
    }

}
