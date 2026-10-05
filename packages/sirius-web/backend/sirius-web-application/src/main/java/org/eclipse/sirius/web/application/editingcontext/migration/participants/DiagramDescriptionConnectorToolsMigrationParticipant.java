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
package org.eclipse.sirius.web.application.editingcontext.migration.participants;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.springframework.stereotype.Service;

/**
 * Moves connector tools from palettes and tool sections to their diagram element description.
 *
 * @author mcharfadi
 */
@Service
public class DiagramDescriptionConnectorToolsMigrationParticipant implements IMigrationParticipant {

    private static final String PARTICIPANT_VERSION = "2026.11.0-202610021500";

    private static final String DATA = "data";

    private static final String ECLASS = "eClass";

    private static final String NODE_DESCRIPTION = "diagram:NodeDescription";

    private static final String EDGE_DESCRIPTION = "diagram:EdgeDescription";

    private static final String PALETTE = "palette";

    private static final String EDGE_TOOLS = "edgeTools";

    private static final String TOOL_SECTIONS = "toolSections";

    @Override
    public String getVersion() {
        return PARTICIPANT_VERSION;
    }

    @Override
    public void preDeserialization(JsonResource resource, JsonObject jsonObject) {
        this.migrate(jsonObject);
    }

    private void migrate(JsonElement jsonElement) {
        if (jsonElement.isJsonObject()) {
            var jsonObject = jsonElement.getAsJsonObject();
            if (this.isDiagramElementDescription(jsonObject)) {
                this.moveConnectorTools(jsonObject);
            }
            for (var entry : jsonObject.entrySet()) {
                this.migrate(entry.getValue());
            }
        } else if (jsonElement.isJsonArray()) {
            for (JsonElement arrayElement : jsonElement.getAsJsonArray()) {
                this.migrate(arrayElement);
            }
        }
    }

    private boolean isDiagramElementDescription(JsonObject jsonObject) {
        if (jsonObject.has(ECLASS) && jsonObject.get(ECLASS).isJsonPrimitive()) {
            String eClass = jsonObject.get(ECLASS).getAsString();
            return NODE_DESCRIPTION.equals(eClass) || EDGE_DESCRIPTION.equals(eClass);
        }
        return false;
    }

    private void moveConnectorTools(JsonObject description) {
        JsonObject descriptionData = this.getObject(description, DATA);
        JsonObject palette = this.getObject(descriptionData, PALETTE);
        JsonObject paletteData = this.getObject(palette, DATA);
        if (descriptionData != null && paletteData != null) {
            JsonArray connectorTools = new JsonArray();
            this.moveTools(paletteData, connectorTools);

            JsonArray toolSections = this.getArray(paletteData, TOOL_SECTIONS);
            if (toolSections != null) {
                for (JsonElement toolSection : toolSections) {
                    if (toolSection.isJsonObject()) {
                        this.moveTools(this.getObject(toolSection.getAsJsonObject(), DATA), connectorTools);
                    }
                }
            }

            if (!connectorTools.isEmpty()) {
                descriptionData.add(EDGE_TOOLS, connectorTools);
            }
        }
    }

    private void moveTools(JsonObject containerData, JsonArray connectorTools) {
        JsonArray edgeTools = this.getArray(containerData, EDGE_TOOLS);
        if (edgeTools != null) {
            edgeTools.forEach(connectorTools::add);
            containerData.remove(EDGE_TOOLS);
        }
    }

    private JsonObject getObject(JsonObject jsonObject, String key) {
        if (jsonObject != null && jsonObject.has(key) && jsonObject.get(key).isJsonObject()) {
            return jsonObject.getAsJsonObject(key);
        }
        return null;
    }

    private JsonArray getArray(JsonObject jsonObject, String key) {
        if (jsonObject != null && jsonObject.has(key) && jsonObject.get(key).isJsonArray()) {
            return jsonObject.getAsJsonArray(key);
        }
        return null;
    }
}
