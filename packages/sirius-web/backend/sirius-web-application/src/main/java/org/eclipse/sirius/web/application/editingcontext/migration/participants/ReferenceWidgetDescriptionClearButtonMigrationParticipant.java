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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.view.widget.reference.ReferenceFactory;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.springframework.stereotype.Service;

/**
 * Adds a clear button to existing reference widget descriptions.
 *
 * @author tgiraudet
 */
@Service
public class ReferenceWidgetDescriptionClearButtonMigrationParticipant implements IMigrationParticipant {

    private static final String PARTICIPANT_VERSION = "2026.9.2-202609211503";

    @Override
    public String getVersion() {
        return PARTICIPANT_VERSION;
    }

    @Override
    public void postObjectLoading(JsonResource resource, EObject eObject, JsonObject jsonObject) {
        if (eObject instanceof ReferenceWidgetDescription referenceWidgetDescription && referenceWidgetDescription.getClearButton() == null) {
            referenceWidgetDescription.setClearButton(ReferenceFactory.eINSTANCE.createReferenceWidgetClearButtonDescription());
        }
    }
}
