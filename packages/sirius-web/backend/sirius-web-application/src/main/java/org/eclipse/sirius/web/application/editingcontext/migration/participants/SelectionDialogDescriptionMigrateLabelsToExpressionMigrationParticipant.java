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

import com.google.gson.JsonObject;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.springframework.stereotype.Service;

/**
 * Used to migrate `selectionMessage` and `noSelectionLabel` from existing SelectionDialogDescription to `descriptionExpression` and `noSelectionActionLabelExpression`.
 *
 * @author gcoutable
 */
@Service
public class SelectionDialogDescriptionMigrateLabelsToExpressionMigrationParticipant implements IMigrationParticipant {

    private static final String PARTICIPANT_VERSION = "2026.3.0-202603061557";

    @Override
    public String getVersion() {
        return PARTICIPANT_VERSION;
    }

    @Override
    public void postObjectLoading(JsonResource resource, EObject eObject, JsonObject jsonObject) {
        if (eObject instanceof SelectionDialogDescription selectionDialogDescription) {
            Optional.ofNullable(jsonObject.getAsJsonObject("data")).ifPresent(selectionDialogDescriptionData -> {
                Optional.ofNullable(selectionDialogDescriptionData.get("selectionMessage")).ifPresent(selectionMessageJson -> {
                    var selectionMessage = selectionMessageJson.getAsString();
                    if (!selectionMessage.isBlank()) {
                        selectionDialogDescription.setDescriptionExpression(selectionMessage);
                    }
                });
                Optional.ofNullable(selectionDialogDescriptionData.get("noSelectionLabel")).ifPresent(noSelectionLabelJson -> {
                    var noSelectionLabel = noSelectionLabelJson.getAsString();
                    if (!noSelectionLabel.isBlank()) {
                        selectionDialogDescription.setNoSelectionActionLabelExpression(noSelectionLabel);
                    }
                });
            });
        }
    }
}
