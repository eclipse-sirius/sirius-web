/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
 * MigrationParticipant that move the childrenLayoutStrategy from NodeDescription to NodeStyleDescription.
 *
 * @author frouene
 */
@Service
public class NodeDescriptionLayoutStrategyMigrationParticipant implements IMigrationParticipant {

    private static final String PARTICIPANT_VERSION = "2025.6.0-202505281000";

    @Override
    public String getVersion() {
        return PARTICIPANT_VERSION;
    }

    @Override
    public void preDeserialization(JsonResource resource, JsonObject jsonObject) {
        this.moveLayoutStrategies(jsonObject);
    }

    public void moveLayoutStrategies(JsonObject jsonObject) {
        for (String key : jsonObject.keySet()) {
            JsonElement value = jsonObject.get(key);

            if (value.isJsonObject()) {
                if (key.equals("childrenLayoutStrategy")) {
                    jsonObject.remove("childrenLayoutStrategy");

                    JsonObject style = jsonObject.getAsJsonObject("style");
                    if (style != null) {
                        JsonObject styleData = style.getAsJsonObject("data");
                        if (styleData != null) {
                            styleData.add("childrenLayoutStrategy", value);
                        }
                    }
                    JsonArray conditionalStyleArray = jsonObject.getAsJsonArray("conditionalStyles");
                    if (conditionalStyleArray != null) {
                        for (JsonElement conditionalStyle : conditionalStyleArray) {
                            JsonObject conditionalStyleData = conditionalStyle.getAsJsonObject().getAsJsonObject("data");
                            if (conditionalStyleData != null) {
                                JsonObject conditionalStyleStyleData = conditionalStyleData.getAsJsonObject("style");
                                if (conditionalStyleStyleData != null) {
                                    JsonObject styleData = conditionalStyleStyleData.getAsJsonObject("data");
                                    if (styleData != null) {
                                        styleData.add("childrenLayoutStrategy", value);
                                    }
                                }
                            }
                        }
                    }
                    if (jsonObject.getAsJsonObject().has("childrenDescriptions")) {
                        for (JsonElement arrayElement : jsonObject.getAsJsonObject().getAsJsonArray("childrenDescriptions")) {
                            if (arrayElement.isJsonObject()) {
                                this.moveLayoutStrategies(arrayElement.getAsJsonObject());
                            }
                        }
                    }
                    break;
                }
                this.moveLayoutStrategies(value.getAsJsonObject());
            } else if (value.isJsonArray()) {
                JsonArray jsonArray = value.getAsJsonArray();
                for (JsonElement arrayElement : jsonArray) {
                    if (arrayElement.isJsonObject()) {
                        this.moveLayoutStrategies(arrayElement.getAsJsonObject());
                    }
                }
            }
        }
    }

}
