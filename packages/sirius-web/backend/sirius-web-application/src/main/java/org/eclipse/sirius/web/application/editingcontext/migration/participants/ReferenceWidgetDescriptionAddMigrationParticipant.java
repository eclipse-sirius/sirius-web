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

import java.util.UUID;

import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.view.widget.reference.ReferencePackage;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.springframework.stereotype.Service;

/**
 * Adds an add operation to existing reference widget descriptions, preserving their default or custom behavior.
 *
 * @author tgiraudet
 */
@Service
public class ReferenceWidgetDescriptionAddMigrationParticipant implements IMigrationParticipant {

    private static final String PARTICIPANT_VERSION = "2026.11.0-202610211729";

    private static final String ECLASS = "eClass";

    private static final String ID = "id";

    private static final String BODY = "body";

    private static final String DATA = "data";

    @Override
    public String getVersion() {
        return PARTICIPANT_VERSION;
    }

    @Override
    public void preDeserialization(JsonResource resource, JsonObject jsonObject) {
        var namespaces = jsonObject.getAsJsonObject("ns");
        if (namespaces != null) {
            namespaces.entrySet().stream()
                    .filter(entry -> ReferencePackage.eNS_URI.equals(entry.getValue().getAsString()))
                    .findFirst()
                    .ifPresent(entry -> this.addOperation(jsonObject, entry.getKey()));
        }
    }

    private void addOperation(JsonElement element, String prefix) {
        if (element.isJsonObject()) {
            var object = element.getAsJsonObject();
            if (object.has(ECLASS) && (prefix + ":ReferenceWidgetDescription").equals(object.get(ECLASS).getAsString())) {
                var data = object.getAsJsonObject(DATA);
                if (data == null) {
                    data = new JsonObject();
                    object.add(DATA, data);
                }
                if (!data.has("add")) {
                    var add = new JsonObject();
                    add.addProperty(ID, UUID.randomUUID().toString());
                    add.addProperty(ECLASS, prefix + ":ReferenceWidgetAdd");
                    var addData = new JsonObject();
                    JsonElement body = new JsonArray();
                    if (data.has(BODY)) {
                        body = data.get(BODY).deepCopy();
                    }
                    this.regenerateIds(body);
                    addData.add(BODY, body);
                    add.add(DATA, addData);
                    data.add("add", add);
                }
            }
            object.entrySet().forEach(entry -> this.addOperation(entry.getValue(), prefix));
        } else if (element.isJsonArray()) {
            element.getAsJsonArray().forEach(child -> this.addOperation(child, prefix));
        }
    }

    private void regenerateIds(JsonElement element) {
        if (element.isJsonObject()) {
            var object = element.getAsJsonObject();
            if (object.has(ECLASS) && object.has(ID)) {
                object.addProperty(ID, UUID.randomUUID().toString());
            }
            object.entrySet().forEach(entry -> this.regenerateIds(entry.getValue()));
        } else if (element.isJsonArray()) {
            element.getAsJsonArray().forEach(this::regenerateIds);
        }
    }
}
