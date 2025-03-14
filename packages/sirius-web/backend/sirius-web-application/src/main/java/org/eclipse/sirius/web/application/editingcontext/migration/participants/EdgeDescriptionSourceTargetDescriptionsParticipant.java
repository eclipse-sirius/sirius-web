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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.springframework.stereotype.Service;


/**
 * MigrationParticipant that update the sourceNodesDescriptions and targetNodeDescriptions of NodeDescription to sourceDescriptions and targetDescriptions.
 * And sourceNodeExpression and targetNodesExpression to sourceExpression and targetExpression.
 *
 * @author mcharfadi
 */
@Service
public class EdgeDescriptionSourceTargetDescriptionsParticipant implements IMigrationParticipant {

    private static final String PARTICIPANT_VERSION = "2025.4.0-202503171117";


    @Override
    public String getVersion() {
        return PARTICIPANT_VERSION;
    }

    @Override
    public EStructuralFeature getEStructuralFeature(EClass eClass, String eStructuralFeatureName) {
        var newFeature = eClass.getEStructuralFeature(eStructuralFeatureName);
        if (eClass.equals(DiagramPackage.eINSTANCE.getEdgeDescription()) && eStructuralFeatureName.equals("targetNodeDescriptions")) {
            newFeature = DiagramPackage.eINSTANCE.getEdgeDescription_TargetDescriptions();
        }
        if (eClass.equals(DiagramPackage.eINSTANCE.getEdgeDescription()) && eStructuralFeatureName.equals("targetNodesExpression")) {
            newFeature = DiagramPackage.eINSTANCE.getEdgeDescription_TargetExpression();
        }
        if (eClass.equals(DiagramPackage.eINSTANCE.getEdgeDescription()) && eStructuralFeatureName.equals("sourceNodeDescriptions")) {
            newFeature = DiagramPackage.eINSTANCE.getEdgeDescription_SourceDescriptions();
        }
        if (eClass.equals(DiagramPackage.eINSTANCE.getEdgeDescription()) && eStructuralFeatureName.equals("sourceNodesExpression")) {
            newFeature = DiagramPackage.eINSTANCE.getEdgeDescription_SourceExpression();
        }
        return newFeature;
    }

}
