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
 * Moves the shared reference widget body to the set operation, preserving existing add and set operations.
 *
 * @author tgiraudet
 */
@Service
public class ReferenceWidgetDescriptionSetMigrationParticipant implements IMigrationParticipant {

    private static final String PARTICIPANT_VERSION = "2026.11.0-202610220925";

    private static final String ECLASS = "eClass";

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
                    .ifPresent(entry -> this.setOperation(jsonObject, entry.getKey()));
        }
    }

    private void setOperation(JsonElement element, String prefix) {
        if (element.isJsonObject()) {
            var object = element.getAsJsonObject();
            if (object.has(ECLASS) && (prefix + ":ReferenceWidgetDescription").equals(object.get(ECLASS).getAsString())) {
                var data = object.getAsJsonObject(DATA);
                if (data == null) {
                    data = new JsonObject();
                    object.add(DATA, data);
                }
                var body = data.remove("body");
                if (body == null) {
                    body = new JsonArray();
                }
                if (!data.has("set")) {
                    var set = new JsonObject();
                    set.addProperty("id", UUID.randomUUID().toString());
                    set.addProperty(ECLASS, prefix + ":ReferenceWidgetSet");
                    var setData = new JsonObject();
                    setData.add("body", body);
                    set.add(DATA, setData);
                    data.add("set", set);
                }
            }
            object.entrySet().forEach(entry -> this.setOperation(entry.getValue(), prefix));
        } else if (element.isJsonArray()) {
            element.getAsJsonArray().forEach(child -> this.setOperation(child, prefix));
        }
    }
}
