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
package org.eclipse.sirius.web.application.editingcontext.migration.participants;

import com.google.gson.JsonObject;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.form.WidgetFlexboxLayout;
import org.springframework.stereotype.Service;

/**
 * MigrationParticipant that updates the flexbox properties for widget description styles.
 *
 * @author lfasani
 */
@Service
public class WidgetDescriptionStyleLayoutPropertiesMigrationParticipant implements IMigrationParticipant {

    private static final String PARTICIPANT_VERSION = "2024.11.0-202410221500";

    @Override
    public String getVersion() {
        return PARTICIPANT_VERSION;
    }

    @Override
    public void postObjectLoading(EObject eObject, JsonObject jsonObject) {
        if (eObject instanceof CheckboxDescriptionStyle checkboxDescriptionStyle) {
            var optionalCheckboxDescriptionStyleData = Optional.ofNullable(jsonObject.getAsJsonObject("data"));
            optionalCheckboxDescriptionStyleData.ifPresent(checkboxDescriptionStyleData -> {
                var optionalLabelPlacement = Optional.ofNullable(checkboxDescriptionStyleData.get("labelPlacement"));
                optionalLabelPlacement.ifPresent(labelPlacement -> {
                    if (labelPlacement.getAsString().equals("start")) {
                        checkboxDescriptionStyle.setFlexDirection("row");
                    } else if (labelPlacement.getAsString().equals("end")) {
                        checkboxDescriptionStyle.setFlexDirection("row-reverse");
                        checkboxDescriptionStyle.setLabelFlex("1 1 auto");
                    } else if (labelPlacement.getAsString().equals("top")) {
                        checkboxDescriptionStyle.setFlexDirection("colum");
                    } else if (labelPlacement.getAsString().equals("bottom")) {
                        checkboxDescriptionStyle.setFlexDirection("colum-reverse");
                    }
                });
            });
        } else if (eObject instanceof WidgetFlexboxLayout widgetFlexboxLayout) {
            widgetFlexboxLayout.setFlexDirection("column");
        }
    }
}
