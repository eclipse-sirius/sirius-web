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

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.springframework.stereotype.Service;

/**
 * Moves each connector tool's body to a node tool in its new palette.
 *
 * @author mcharfadi
 */
@Service
public class EdgeToolPaletteMigrationParticipant implements IMigrationParticipant {

    private static final String PARTICIPANT_VERSION = "2026.11.0-202610071000";

    private static final String DATA = "data";

    private static final String ECLASS = "eClass";

    private static final String ID = "id";

    private static final String NAME = "name";

    private static final String PRECONDITION_EXPRESSION = "preconditionExpression";

    private static final String ICON_URLS_EXPRESSION = "iconURLsExpression";

    private static final String ELEMENTS_TO_SELECT_EXPRESSION = "elementsToSelectExpression";

    private static final String DIALOG_DESCRIPTION = "dialogDescription";

    private static final String BODY = "body";

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
            var object = jsonElement.getAsJsonObject();
            if (this.isDiagramElementDescription(object)) {
                var data = this.getObject(object, DATA);
                var edgeTools = this.getArray(data, "edgeTools");
                if (edgeTools != null) {
                    edgeTools.forEach(this::addPalette);
                }
            }
            for (var entry : object.entrySet()) {
                this.migrate(entry.getValue());
            }
        } else if (jsonElement.isJsonArray()) {
            jsonElement.getAsJsonArray().forEach(this::migrate);
        }
    }

    private boolean isDiagramElementDescription(JsonObject object) {
        if (object.has(ECLASS) && object.get(ECLASS).isJsonPrimitive()) {
            String eClass = object.get(ECLASS).getAsString();
            return "diagram:NodeDescription".equals(eClass) || "diagram:EdgeDescription".equals(eClass);
        }
        return false;
    }

    private void addPalette(JsonElement connectorTool) {
        if (connectorTool.isJsonObject()) {
            var edgeTool = connectorTool.getAsJsonObject();
            if (this.isIdentifiedEdgeTool(edgeTool)) {
                var edgeToolData = this.getObject(edgeTool, DATA);
                if (edgeToolData != null) {
                    var nodeToolData = new JsonObject();
                    if (edgeToolData.has(NAME)) {
                        nodeToolData.add(NAME, edgeToolData.get(NAME));
                    }
                    if (edgeToolData.has(PRECONDITION_EXPRESSION)) {
                        nodeToolData.add(PRECONDITION_EXPRESSION, edgeToolData.get(PRECONDITION_EXPRESSION));
                    }
                    if (edgeToolData.has(ICON_URLS_EXPRESSION)) {
                        nodeToolData.add(ICON_URLS_EXPRESSION, edgeToolData.get(ICON_URLS_EXPRESSION));
                    }
                    if (edgeToolData.has(ELEMENTS_TO_SELECT_EXPRESSION)) {
                        nodeToolData.add(ELEMENTS_TO_SELECT_EXPRESSION, edgeToolData.get(ELEMENTS_TO_SELECT_EXPRESSION));
                    }
                    if (edgeToolData.has(DIALOG_DESCRIPTION)) {
                        nodeToolData.add(DIALOG_DESCRIPTION, edgeToolData.remove(DIALOG_DESCRIPTION));
                    }
                    if (edgeToolData.has(BODY)) {
                        nodeToolData.add(BODY, edgeToolData.remove(BODY));
                    }

                    String edgeToolId = edgeTool.get(ID).getAsString();
                    var nodeTool = new JsonObject();
                    nodeTool.addProperty(ID, this.childId(edgeToolId, "nodeTool"));
                    nodeTool.addProperty(ECLASS, "diagram:NodeTool");
                    nodeTool.add(DATA, nodeToolData);

                    var paletteData = new JsonObject();
                    var nodeTools = new JsonArray();
                    nodeTools.add(nodeTool);
                    paletteData.add("nodeTools", nodeTools);

                    var palette = new JsonObject();
                    palette.addProperty(ID, this.childId(edgeToolId, "palette"));
                    palette.addProperty(ECLASS, "diagram:NodePalette");
                    palette.add(DATA, paletteData);
                    edgeToolData.add("palette", palette);
                }
            }
        }
    }

    private boolean isIdentifiedEdgeTool(JsonObject edgeTool) {
        if (!edgeTool.has(ECLASS) || !edgeTool.get(ECLASS).isJsonPrimitive() || !"diagram:EdgeTool".equals(edgeTool.get(ECLASS).getAsString())) {
            return false;
        }
        return edgeTool.has(ID) && edgeTool.get(ID).isJsonPrimitive();
    }

    private String childId(String edgeToolId, String suffix) {
        return UUID.nameUUIDFromBytes((edgeToolId + ':' + suffix).getBytes(StandardCharsets.UTF_8)).toString();
    }

    private JsonObject getObject(JsonObject object, String key) {
        if (object != null && object.has(key) && object.get(key).isJsonObject()) {
            return object.getAsJsonObject(key);
        }
        return null;
    }

    private JsonArray getArray(JsonObject object, String key) {
        if (object != null && object.has(key) && object.get(key).isJsonArray()) {
            return object.getAsJsonArray(key);
        }
        return null;
    }
}
