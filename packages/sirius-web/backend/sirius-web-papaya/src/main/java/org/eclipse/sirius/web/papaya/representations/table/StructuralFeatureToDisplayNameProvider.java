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
package org.eclipse.sirius.web.papaya.representations.table;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Used to compute all the relevant features of an object with their display name.
 *
 * @author sbegaudeau
 */
public class StructuralFeatureToDisplayNameProvider {

    private final DisplayNameProvider displayNameProvider;

    public StructuralFeatureToDisplayNameProvider(DisplayNameProvider displayNameProvider) {
        this.displayNameProvider = Objects.requireNonNull(displayNameProvider);
    }

    public Map<EStructuralFeature, String> getColumnsStructuralFeaturesDisplayName(EObject eObject, EClass eClass) {
        Map<EStructuralFeature, String> featureToDisplayName = new LinkedHashMap<>();
        EList<EStructuralFeature> eAllStructuralFeatures = eClass.getEAllStructuralFeatures();
        for (EStructuralFeature eStructuralFeature : eAllStructuralFeatures) {
            if (eStructuralFeature instanceof EAttribute && !eStructuralFeature.isMany() && !eStructuralFeature.isDerived()) {
                featureToDisplayName.put(eStructuralFeature, this.displayNameProvider.getDisplayName(eObject, eStructuralFeature));
            } else if (eStructuralFeature instanceof EReference ref && !eStructuralFeature.isDerived() && !ref.isContainment()) {
                featureToDisplayName.put(eStructuralFeature, this.displayNameProvider.getDisplayName(eObject, eStructuralFeature));
            }
        }
        return featureToDisplayName;
    }
}
