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
import org.eclipse.sirius.components.view.builder.generated.SelectionDialogTreeDescriptionBuilder;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;
import org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.springframework.stereotype.Service;

/**
 * MigrationParticipant that create a new SelectionDialogTreeDescription in the SelectionDialogDescription to replace the former SelectionDialogDescription#selectionCandidatesExpression.
 * SelectionDialogDescription#selectionCandidatesExpression value is moved toward SelectionDialogDescription#selectionDialogTreeDescription#elementsExpression
 *
 * @author fbarbin
 */
@Service
public class SelectionDialogDescriptionSelectionCandidatesExpressionMigrationParticipant implements IMigrationParticipant {

    private static final String PARTICIPANT_VERSION = "2024.9.0-202408261126";

    @Override
    public String getVersion() {
        return PARTICIPANT_VERSION;
    }

    @Override
    public void postObjectLoading(JsonResource resource, EObject eObject, JsonObject jsonObject) {
        if (eObject instanceof SelectionDialogDescription selectionDialogDescription) {
            var optionalSelectionDialogDescriptionData = Optional.ofNullable(jsonObject.getAsJsonObject("data"));
            optionalSelectionDialogDescriptionData.ifPresent(selectionDialogDescriptionData -> {
                var optionalSelectionCandidatesExpression = Optional.ofNullable(selectionDialogDescriptionData.get("selectionCandidatesExpression"));
                optionalSelectionCandidatesExpression.ifPresent(selectionCandidatesExpression -> {
                    SelectionDialogTreeDescription selectionDialogTreeDescription = new SelectionDialogTreeDescriptionBuilder()
                            .elementsExpression(selectionCandidatesExpression.getAsString())
                            .build();
                    selectionDialogDescription.setSelectionDialogTreeDescription(selectionDialogTreeDescription);
                });
            });
        }
    }
}
