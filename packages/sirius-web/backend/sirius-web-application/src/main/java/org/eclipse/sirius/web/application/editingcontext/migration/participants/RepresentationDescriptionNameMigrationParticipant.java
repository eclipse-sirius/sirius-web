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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.springframework.stereotype.Service;

/**
 * Migrates :
 * <ul>
 *     <li>the RepresentationDescription#name toward RepresentationDescription#id.</li>
 *     <li>the DiagramElementDescription#name toward DiagramElementDescription#id.</li>
 *     <li>the EdgeStyle eClass to EdgeStyleDescription</li>
 * </ul>
 *
 * @author fbarbin
 */
@Service
public class RepresentationDescriptionNameMigrationParticipant implements IMigrationParticipant {


    private static final String PARTICIPANT_VERSION = "2026.3.0-202601201200";
    private static final String NAME = "name";
    private static final String EDGE_STYLE = "EdgeStyle";

    @Override
    public String getVersion() {
        return PARTICIPANT_VERSION;
    }

    @Override
    public EStructuralFeature getEStructuralFeature(EClass eClass, String eStructuralFeatureName) {
        var feature = IMigrationParticipant.super.getEStructuralFeature(eClass, eStructuralFeatureName);
        if (ViewPackage.eINSTANCE.getRepresentationDescription().isSuperTypeOf(eClass) && NAME.equals(eStructuralFeatureName)) {
            feature = eClass.getEStructuralFeature(ViewPackage.REPRESENTATION_DESCRIPTION__ID);
        } else if (DiagramPackage.eINSTANCE.getDiagramElementDescription().isSuperTypeOf(eClass) && NAME.equals(eStructuralFeatureName)) {
            feature = eClass.getEStructuralFeature(DiagramPackage.DIAGRAM_ELEMENT_DESCRIPTION__ID);
        }
        return feature;
    }

    @Override
    public EClassifier getEClassifier(EPackage ePackage, String typeName) {
        if (DiagramPackage.eINSTANCE.equals(ePackage) && EDGE_STYLE.equals(typeName)) {
            return DiagramPackage.eINSTANCE.getEdgeStyleDescription();
        }
        return IMigrationParticipant.super.getEClassifier(ePackage, typeName);
    }
}
