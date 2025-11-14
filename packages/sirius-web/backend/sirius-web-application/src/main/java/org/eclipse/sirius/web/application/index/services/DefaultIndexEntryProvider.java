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
package org.eclipse.sirius.web.application.index.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.web.application.index.services.api.IDefaultIndexEntryProvider;
import org.eclipse.sirius.web.application.index.services.api.IIndexEntry;
import org.springframework.stereotype.Service;

/**
 * The default implementation that creates an index entry for an object.
 *
 * @author gdaniel
 */
@Service
public class DefaultIndexEntryProvider implements IDefaultIndexEntryProvider {

    private final IIdentityService identityService;

    private final ILabelService labelService;

    public DefaultIndexEntryProvider(IIdentityService identityService, ILabelService labelService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
    }

    @Override
    public Optional<IIndexEntry> getIndexEntry(IEditingContext editingContext, Object object) {
        Optional<IIndexEntry> result = Optional.empty();
        if (object instanceof EObject eObject) {
            Map<String, Object> settings = new HashMap<>();
            EClass eClass = eObject.eClass();
            for (EStructuralFeature eStructuralFeature : eClass.getEAllStructuralFeatures()) {
                if (eStructuralFeature.isMany()) {
                    List<?> featureValues = (List) eObject.eGet(eStructuralFeature);
                    if (eStructuralFeature instanceof EAttribute) {
                        settings.put(eStructuralFeature.getName(), featureValues.stream().map(Object::toString).toList());
                    } else if (eStructuralFeature instanceof EReference) {
                        settings.put(eStructuralFeature.getName(), featureValues.stream().map(this.identityService::getId).toList());
                    }
                } else {
                    Object featureValue = eObject.eGet(eStructuralFeature);
                    if (eStructuralFeature instanceof EAttribute && featureValue != null) {
                        settings.put(eStructuralFeature.getName(), featureValue.toString());
                    } else if (eStructuralFeature instanceof EReference) {
                        String featureValueId = this.identityService.getId(featureValue);
                        if (featureValueId != null) {
                            settings.put(eStructuralFeature.getName(), featureValueId);
                        }
                    }
                }
            }

            String objectId = this.identityService.getId(eObject);
            String type = eClass.getEPackage().getName() + ":" + eClass.getName();
            String label = this.labelService.getStyledLabel(eObject).toString();
            List<String> iconURLs = this.labelService.getImagePaths(eObject);

            result = Optional.of(new EMFIndexEntry(editingContext.getId(), objectId, type, label, iconURLs, settings));
        }
        return result;
    }
}
