/*
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
 */

package org.eclipse.sirius.components.emf.utils;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class inherits from org.eclipse.emf.ecore.util.EcoreUtil.Copier to leverage protected methods.
 *
 * @author lfasani
 */
public class SiriusEMFCopier extends EcoreUtil.Copier {
    /*
     * This method copies the EObject with all its attributes but not its cross references nor its content.
     * This code is partially copied from org.eclipse.emf.ecore.util.EcoreUtil.Copier.copy
     * */
    public EObject copyWithoutContent(EObject eObject) {
        if (eObject == null) {
            return null;
        } else {
            EObject copyEObject = createCopy(eObject);
            if (copyEObject != null) {
                put(eObject, copyEObject);
                EClass eClass = eObject.eClass();
                for (int i = 0, size = eClass.getFeatureCount(); i < size; ++i) {
                    EStructuralFeature eStructuralFeature = eClass.getEStructuralFeature(i);
                    if (eStructuralFeature.isChangeable() && !eStructuralFeature.isDerived()) {
                        if (eStructuralFeature instanceof EAttribute) {
                            copyAttribute((EAttribute) eStructuralFeature, eObject, copyEObject);
                        }
                    }
                }

                copyProxyURI(eObject, copyEObject);
            }

            return copyEObject;
        }
    }
}
