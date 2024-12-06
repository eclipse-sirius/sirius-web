/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
import org.eclipse.sirius.components.view.tree.TreePackage;
import org.springframework.stereotype.Service;

/**
 * MigrationParticipant that update the iconURLExpression of a TreeDescription to treeItemIconExpression.
 *
 * @author mcharfadi
 */
@Service
public class TreeDescriptionIconURLExpressionMigrationParticipant implements IMigrationParticipant {

    public static final String FEATURE_TO_MIGRATE = "iconURLExpression";
    private static final String PARTICIPANT_VERSION = "2024.11.1-202412041104";

    @Override
    public String getVersion() {
        return PARTICIPANT_VERSION;
    }

    @Override
    public EStructuralFeature getEStructuralFeature(EClass eClass, String eStructuralFeatureName) {
        var newFeature = eClass.getEStructuralFeature(eStructuralFeatureName);
        if (eClass.getName().equals("TreeDescription") && eStructuralFeatureName.equals(FEATURE_TO_MIGRATE)) {
            newFeature = TreePackage.eINSTANCE.getTreeDescription_TreeItemIconExpression();
        }
        return newFeature;
    }
}
