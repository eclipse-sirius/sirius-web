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
package org.eclipse.sirius.web.application.editingcontext.migration.participants;

import com.google.gson.JsonObject;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.springframework.stereotype.Service;

/**
 * MigrationParticipant that updates the flexbox properties for widget description styles.
 *
 * @author lfasani
 */
@Service
public class WidgetDescriptionStyleLayoutPropertiesMigrationParticipant implements IMigrationParticipant {

    private static final String PARTICIPANT_VERSION = "2025.1.0-202411081600";

    @Override
    public String getVersion() {
        return PARTICIPANT_VERSION;
    }

    @Override
    public void postObjectLoading(JsonResource resource, EObject eObject, JsonObject jsonObject) {
        if (eObject instanceof CheckboxDescriptionStyle checkboxDescriptionStyle) {
            var optionalCheckboxDescriptionStyleData = Optional.ofNullable(jsonObject.getAsJsonObject("data"));
            optionalCheckboxDescriptionStyleData.ifPresent(checkboxDescriptionStyleData -> {
                var optionalLabelPlacement = Optional.ofNullable(checkboxDescriptionStyleData.get("labelPlacement"));
                optionalLabelPlacement.ifPresent(labelPlacement -> {
                    if (labelPlacement.getAsString().equals("start")) {
                        checkboxDescriptionStyle.setGridTemplateColumns("min-content 1fr");
                    } else if (labelPlacement.getAsString().equals("end")) {
                        checkboxDescriptionStyle.setGridTemplateColumns("min-content 1fr");
                        checkboxDescriptionStyle.setLabelGridColumn("2/3");
                        checkboxDescriptionStyle.setWidgetGridColumn("1/2");
                        checkboxDescriptionStyle.setWidgetGridRow("1/2");
                    } else if (labelPlacement.getAsString().equals("bottom")) {
                        checkboxDescriptionStyle.setLabelGridRow("2/3");
                    }
                });
            });
        }
    }
}
