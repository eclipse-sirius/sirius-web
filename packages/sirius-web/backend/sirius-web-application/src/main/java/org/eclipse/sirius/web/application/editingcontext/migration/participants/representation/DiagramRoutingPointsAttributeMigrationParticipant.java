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
 * Remove the attribute routing points from diagram representation and in its content.
 *
 * @author gcoutable
 */
@Service
public class DiagramRoutingPointsAttributeMigrationParticipant implements IRepresentationMigrationParticipant {

    public static final String ROUTING_POINTS = "routingPoints";

    @Override
    public String getVersion() {
        return "2024.11.0-202409231053";
    }

    @Override
    public String getKind() {
        return "siriusComponents://representation?type=Diagram";
    }

    @Override
    public void replaceJsonNode(ObjectNode root, String currentAttribute, JsonNode currentValue) {
        if (currentAttribute.equals(ROUTING_POINTS)) {
            root.remove(ROUTING_POINTS);
        }
    }
}
