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

    private static final String PARTICIPANT_VERSION = "2025.6.0-202506011000";
    private static final String CHILDREN_LAYOUT_STRATEGY = "childrenLayoutStrategy";
    private static final String DATA = "data";
    private static final String STYLE = "style";
    private static final String CONDITIONAL_STYLES = "conditionalStyles";
    private static final String CHILDREN_DESCRIPTIONS = "childrenDescriptions";

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
                if (key.equals(CHILDREN_LAYOUT_STRATEGY)) {
                    this.handleChildrenLayoutStrategy(jsonObject, value);
                    break;
                }
                this.moveLayoutStrategies(value.getAsJsonObject());
            } else if (value.isJsonArray()) {
                for (JsonElement arrayElement : value.getAsJsonArray()) {
                    if (arrayElement.isJsonObject()) {
                        this.moveLayoutStrategies(arrayElement.getAsJsonObject());
                    }
                }
            }
        }
    }

    private void handleChildrenLayoutStrategy(JsonObject jsonObject, JsonElement value) {
        if (this.addLayoutStrategyToStyle(jsonObject, value)) {
            jsonObject.remove(CHILDREN_LAYOUT_STRATEGY);
        }
        this.addLayoutStrategyToConditionalStyles(jsonObject, value);
        this.addLayoutStrategyToChildrenDescriptions(jsonObject);

    }

    private boolean addLayoutStrategyToStyle(JsonObject jsonObject, JsonElement value) {
        JsonObject style = jsonObject.getAsJsonObject(STYLE);
        if (style != null) {
            JsonObject styleData = style.getAsJsonObject(DATA);
            if (styleData != null) {
                styleData.add(CHILDREN_LAYOUT_STRATEGY, value);
                return true;
            }
        }
        return false;
    }

    private void addLayoutStrategyToConditionalStyles(JsonObject jsonObject, JsonElement value) {
        JsonArray conditionalStyleArray = jsonObject.getAsJsonArray(CONDITIONAL_STYLES);
        if (conditionalStyleArray != null) {
            for (JsonElement conditionalStyle : conditionalStyleArray) {
                JsonObject conditionalStyleData = conditionalStyle.getAsJsonObject().getAsJsonObject(DATA);
                if (conditionalStyleData != null) {
                    JsonObject conditionalStyleStyleData = conditionalStyleData.getAsJsonObject(STYLE);
                    if (conditionalStyleStyleData != null) {
                        JsonObject styleData = conditionalStyleStyleData.getAsJsonObject(DATA);
                        if (styleData != null) {
                            styleData.add(CHILDREN_LAYOUT_STRATEGY, value);
                        }
                    }
                }
            }
        }
    }

    private void addLayoutStrategyToChildrenDescriptions(JsonObject jsonObject) {
        if (jsonObject.getAsJsonObject().has(CHILDREN_DESCRIPTIONS)) {
            for (JsonElement arrayElement : jsonObject.getAsJsonObject().getAsJsonArray(CHILDREN_DESCRIPTIONS)) {
                if (arrayElement.isJsonObject()) {
                    this.moveLayoutStrategies(arrayElement.getAsJsonObject());
                }
            }
        }
    }
}
