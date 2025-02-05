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
package org.eclipse.sirius.web.application.object.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.web.application.dto.Identified;
import org.eclipse.sirius.web.application.object.services.api.IEObjectRestSerializer;
import org.springframework.stereotype.Service;

/**
 * Used to serialize EObjects for the REST API.
 *
 * @author sbegaudeau
 */
@Service
public class EObjectRestSerializer implements IEObjectRestSerializer {

    private static final String ID_PROPERTY = "@id";

    private final IIdentityService identityService;

    public EObjectRestSerializer(IIdentityService identityService) {
        this.identityService = Objects.requireNonNull(identityService);
    }

    @Override
    public Map<String, Object> toMap(EObject eObject) {
        Map<String, Object> serializedEObject = new LinkedHashMap<>();
        var id = this.identityService.getId(eObject);
        if (id != null) {
            serializedEObject.put(ID_PROPERTY, id);
        }
        serializedEObject.put("@type", eObject.eClass().getName());
        eObject.eClass().getEAllStructuralFeatures().stream()
                .filter(eStructuralFeature -> !eStructuralFeature.isTransient())
                .forEach(eStructuralFeature -> this.serializeEStructuralFeature(eObject, eStructuralFeature, serializedEObject));
        return serializedEObject;
    }

    private void serializeEStructuralFeature(EObject eObject, EStructuralFeature eStructuralFeature, Map<String, Object> serializedEObject) {
        var featureValue = eObject.eGet(eStructuralFeature);
        if (featureValue != null && eStructuralFeature instanceof EAttribute eAttribute && !eAttribute.isMany()) {
            var value = EcoreUtil.convertToString(eAttribute.getEAttributeType(), featureValue);
            if (value != null && !value.isEmpty()) {
                serializedEObject.put(eStructuralFeature.getName(), value);
            }
        } else if (featureValue != null && eStructuralFeature instanceof EReference eReference && !eReference.isMany()) {
            var featureValueId = this.identityService.getId(featureValue);
            if (featureValueId != null) {
                serializedEObject.put(eStructuralFeature.getName(), new Identified(UUID.fromString(featureValueId)));
            }
        } else if (eStructuralFeature.isMany() && featureValue instanceof List<?> featureListValue && !featureListValue.isEmpty()) {
            var referenceIds = new ArrayList<>();
            featureListValue.forEach(i -> {
                var featureValueId = this.identityService.getId(i);
                if (featureValueId != null) {
                    referenceIds.add(new Identified(UUID.fromString(featureValueId)));
                }
            });
            serializedEObject.put(eStructuralFeature.getName(), referenceIds);
        }
    }
}
