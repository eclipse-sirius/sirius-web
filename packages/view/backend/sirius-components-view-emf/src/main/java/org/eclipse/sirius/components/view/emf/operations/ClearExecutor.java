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
package org.eclipse.sirius.components.view.emf.operations;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.view.emf.operations.api.IClearExecutor;
import org.springframework.stereotype.Service;

/**
 * Used to clear a value from an object.
 *
 * @author sbegaudeau
 */
@Service
public class ClearExecutor implements IClearExecutor {

    @Override
    public Object eClear(EObject instance, String featureName) {
        var eStructuralFeature = instance.eClass().getEStructuralFeature(featureName);
        if (eStructuralFeature != null) {
            Object value = instance.eGet(eStructuralFeature);
            if (value != null) {
                if (value instanceof Collection collection) {
                    collection.clear();
                } else {
                    instance.eSet(eStructuralFeature, null);
                }
            }

            return instance;
        }
        return null;
    }
}
