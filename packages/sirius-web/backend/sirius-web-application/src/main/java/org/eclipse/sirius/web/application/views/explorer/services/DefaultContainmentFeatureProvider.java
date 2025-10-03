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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.web.application.views.explorer.dto.ContainmentFeature;
import org.eclipse.sirius.web.application.views.explorer.services.api.IDefaultContainmentFeatureProvider;
import org.springframework.stereotype.Service;

/**
 * Provides the list of containment feature names available to contain a child in a given container based on the Ecore metamodel.
 *
 * @author Arthur Daussy
 */
@Service
public class DefaultContainmentFeatureProvider implements IDefaultContainmentFeatureProvider {

    @Override
    public List<ContainmentFeature> getContainmentFeatures(Object container, Object child) {
        if (container instanceof EObject eContainer && child instanceof EObject eChild) {
            return eContainer.eClass().getEAllContainments().stream()
                    .filter(eReference -> eReference.getEReferenceType().isInstance(eChild))
                    .map(eReference -> new ContainmentFeature(eReference.getName(), "Add in " + eReference.getName()))
                    .toList();
        }
        return List.of();
    }
}
