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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.DiagramStyleDescription;
import org.springframework.stereotype.Service;

/**
 * MigrationParticipant that add the DiagramStyle of DiagramDescription.
 *
 * @author frouene
 */
@Service
public class DiagramStyleDescriptionAddMigrationParticipant implements IMigrationParticipant {

    @Override
    public String getVersion() {
        return "2024.9.0-202456071800";
    }

    @Override
    public void postObjectLoading(EObject eObject, JsonObject jsonObject) {
        if (eObject instanceof DiagramDescription diagramDescription && diagramDescription.getStyle() == null) {
            DiagramStyleDescription diagramStyleDescription = DiagramPackage.eINSTANCE.getDiagramFactory().createDiagramStyleDescription();
            diagramDescription.setStyle(diagramStyleDescription);
        }
    }
}
