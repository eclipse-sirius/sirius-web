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
 * RepresentationMigrationParticipant that remove the attribute userResizable from diagram representation.
 *
 * @author frouene
 */
@Service
public class DiagramUserResizableAttributeMigrationParticipant implements IRepresentationMigrationParticipant {

    public static final String USER_RESIZABLE = "userResizable";

    @Override
    public String getVersion() {
        return "2024.5.4-202407040900";
    }

    @Override
    public String getKind() {
        return "siriusComponents://representation?type=Diagram";
    }

    @Override
    public void replaceJsonNode(ObjectNode root, String currentAttribute, JsonNode currentValue) {
        if (currentAttribute.equals(USER_RESIZABLE)) {
            root.remove(USER_RESIZABLE);
        }
    }
}
