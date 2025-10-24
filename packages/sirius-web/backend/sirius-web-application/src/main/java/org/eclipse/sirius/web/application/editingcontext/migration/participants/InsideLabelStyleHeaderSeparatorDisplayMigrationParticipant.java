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
import org.eclipse.sirius.components.view.diagram.HeaderSeparatorDisplayMode;
import org.eclipse.sirius.components.view.diagram.InsideLabelStyle;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.springframework.stereotype.Service;

/**
 * MigrationParticipant that update the showIcon (to ShowIconExpression) of NodeLabelStyle.
 *
 * @author arichard
 */
@Service
public class InsideLabelStyleHeaderSeparatorDisplayMigrationParticipant implements IMigrationParticipant {

    private static final String PARTICIPANT_VERSION = "2024.11.0-202409121500";

    @Override
    public String getVersion() {
        return PARTICIPANT_VERSION;
    }

    @Override
    public void postObjectLoading(JsonResource resource, EObject eObject, JsonObject jsonObject) {
        if (eObject instanceof InsideLabelStyle insideLabelStyle) {
            var optionalInsideLabelStyle = Optional.ofNullable(jsonObject.getAsJsonObject("data"));
            optionalInsideLabelStyle.ifPresent(insideLabelStyleData -> {
                var optionalDisplayHeaderSeparator = Optional.ofNullable(insideLabelStyleData.get("displayHeaderSeparator"));
                optionalDisplayHeaderSeparator.ifPresent(displayHeaderSeparator -> {
                    if (displayHeaderSeparator.getAsBoolean()) {
                        insideLabelStyle.setHeaderSeparatorDisplayMode(HeaderSeparatorDisplayMode.IF_CHILDREN);
                    } else {
                        insideLabelStyle.setHeaderSeparatorDisplayMode(HeaderSeparatorDisplayMode.NEVER);
                    }
                });
            });
        }
    }
}
