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

import com.google.gson.JsonObject;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.springframework.stereotype.Service;

/**
 * MigrationParticipant that add a default 'Delete from diagram' quick access tool for unsynchronized node.
 *
 * @author frouene
 */
@Service
public class NodePaletteDeleteFromDiagramMigrationParticipant implements IMigrationParticipant {

    private static final String PARTICIPANT_VERSION = "2025.6.0-202505221730";

    @Override
    public String getVersion() {
        return PARTICIPANT_VERSION;
    }

    @Override
    public void postObjectLoading(EObject eObject, JsonObject jsonObject) {
        if (eObject instanceof NodeDescription nodeDescription) {
            if (SynchronizationPolicy.UNSYNCHRONIZED.equals(nodeDescription.getSynchronizationPolicy())) {
                var nodePalette = nodeDescription.getPalette();
                if (nodePalette != null) {
                    var quickAccessDeleteFromDiagramTool = DiagramFactory.eINSTANCE.createNodeTool();
                    quickAccessDeleteFromDiagramTool.setName("Delete from diagram");
                    quickAccessDeleteFromDiagramTool.setIconURLsExpression("/diagram-images/graphicalDelete.svg");
                    quickAccessDeleteFromDiagramTool.getBody().add(DiagramFactory.eINSTANCE.createDeleteView());
                    nodePalette.getQuickAccessTools().add(quickAccessDeleteFromDiagramTool);
                }
            }
        }
    }
}
