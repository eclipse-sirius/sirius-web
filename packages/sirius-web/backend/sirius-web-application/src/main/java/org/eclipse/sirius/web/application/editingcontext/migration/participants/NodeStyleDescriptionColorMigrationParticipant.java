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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.customnodes.CustomnodesPackage;
import org.springframework.stereotype.Service;

/**
 * MigrationParticipant that update the Color of NodeStyleDescription with Background.
 *
 * @author frouene
 */
@Service
public class NodeStyleDescriptionColorMigrationParticipant implements IMigrationParticipant {

    public static final String COLOR_FEATURE_NAME = "color";
    private static final String PARTICIPANT_VERSION = "2024.5.0-202405061500";

    @Override
    public String getVersion() {
        return PARTICIPANT_VERSION;
    }

    @Override
    public EStructuralFeature getEStructuralFeature(EClass eClass, String eStructuralFeatureName) {
        var newFeature = eClass.getEStructuralFeature(eStructuralFeatureName);
        if (eClass.getName().equals("RectangularNodeStyleDescription") && eStructuralFeatureName.equals(COLOR_FEATURE_NAME)) {
            newFeature = DiagramPackage.eINSTANCE.getRectangularNodeStyleDescription_Background();
        }
        if (eClass.getName().equals("IconLabelNodeStyleDescription") && eStructuralFeatureName.equals(COLOR_FEATURE_NAME)) {
            newFeature = DiagramPackage.eINSTANCE.getIconLabelNodeStyleDescription_Background();
        }
        if (eClass.getName().equals("EllipseNodeStyleDescription") && eStructuralFeatureName.equals(COLOR_FEATURE_NAME)) {
            newFeature = CustomnodesPackage.eINSTANCE.getEllipseNodeStyleDescription_Background();
        }
        return newFeature;
    }
}
