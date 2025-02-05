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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.web.application.object.services.api.IEObjectRestDeserializer;
import org.springframework.stereotype.Service;

/**
 * Used to deserialize EObjects for the REST API.
 *
 * @author sbegaudeau
 */
@Service
public class EObjectRestDeserializer implements IEObjectRestDeserializer {

    private static final String ID_PROPERTY = "@id";

    private final IObjectSearchService objectSearchService;

    public EObjectRestDeserializer(IObjectSearchService objectSearchService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
    }

    @Override
    public void fromMap(IEditingContext editingContext, Map<String, Object> serializedEObject, EObject eObject, Map<EObject, Map<String, Object>> emptyNewObjects, Set<EObject> newObjectsAttached) {
        serializedEObject.entrySet().forEach(eStructuralFeatureEntry -> {
            this.deserialize(editingContext, eObject, eStructuralFeatureEntry, emptyNewObjects, newObjectsAttached);
        });
    }

    private void deserialize(IEditingContext editingContext, EObject eObject, Map.Entry<String, Object> eStructuralFeatureEntry, Map<EObject, Map<String, Object>> emptyNewObjects, Set<EObject> newObjectsAttached) {
        var eStructuralFeature = eObject.eClass().getEStructuralFeature(eStructuralFeatureEntry.getKey());
        if (eStructuralFeature != null && !eStructuralFeature.isTransient()) {
            if (eStructuralFeature instanceof EAttribute eAttribute && !eAttribute.isMany()) {
                this.deserializeMonovaluedEAttribute(eObject, eAttribute, eStructuralFeatureEntry);
            } else if (eStructuralFeature instanceof EAttribute eAttribute && eAttribute.isMany()) {
                this.deserializeMultivaluedEAttribute(eObject, eAttribute, eStructuralFeatureEntry);
            } else if (eStructuralFeature instanceof EReference eReference && !eReference.isMany()) {
                this.deserializeMonovaluedEReference(editingContext, eObject, eReference, eStructuralFeatureEntry, emptyNewObjects, newObjectsAttached);
            } else if (eStructuralFeature instanceof EReference eReference && eReference.isMany()) {
                this.deserializeMultivaluedEReference(editingContext, eObject, eReference, eStructuralFeatureEntry, emptyNewObjects, newObjectsAttached);
            }
        }
    }

    private void deserializeMonovaluedEAttribute(EObject eObject, EAttribute eAttribute, Map.Entry<String, Object> eStructuralFeatureEntry) {
        eObject.eSet(eAttribute, EcoreUtil.createFromString(eAttribute.getEAttributeType(), eStructuralFeatureEntry.getValue().toString()));
    }

    private void deserializeMultivaluedEAttribute(EObject eObject, EAttribute eAttribute, Map.Entry<String, Object> eStructuralFeatureEntry) {
        eObject.eSet(eAttribute, EcoreUtil.createFromString(eAttribute.getEAttributeType(), eStructuralFeatureEntry.getValue().toString()));
    }

    private void deserializeMonovaluedEReference(IEditingContext editingContext, EObject eObject, EReference eReference, Map.Entry<String, Object> eStructuralFeatureEntry, Map<EObject, Map<String, Object>> emptyNewObjects, Set<EObject> newObjectsAttached) {
        var value = eStructuralFeatureEntry.getValue();
        if (value instanceof Map<?, ?> map) {
            var elementId = Optional.ofNullable(map.get(ID_PROPERTY))
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .orElse("");

            var element = this.objectSearchService.getObject(editingContext, elementId);
            if (element.isPresent()) {
                eObject.eSet(eReference, element.get());
            } else {
                emptyNewObjects.entrySet().stream()
                        .filter(newObjEntry -> {
                            Map<String, Object> features = newObjEntry.getValue();
                            var rawId = features.get(ID_PROPERTY);
                            return rawId instanceof String id && Objects.equals(id, elementId);
                        })
                        .findFirst()
                        .ifPresent(newObjEntry -> {
                            eObject.eSet(eReference, newObjEntry.getKey());
                            if (eReference.isContainment()) {
                                newObjectsAttached.add(newObjEntry.getKey());
                            }
                        });
            }
        }
    }

    private void deserializeMultivaluedEReference(IEditingContext editingContext, EObject eObject, EReference eReference, Map.Entry<String, Object> eStructuralFeatureEntry, Map<EObject, Map<String, Object>> emptyNewObjects, Set<EObject> newObjectsAttached) {
        var value = eStructuralFeatureEntry.getValue();
        if (value instanceof List<?> valueIdentities) {
            valueIdentities.stream()
                    .filter(Map.class::isInstance)
                    .map(Map.class::cast)
                    .map(map -> map.get(ID_PROPERTY))
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .forEach(elementId -> {
                        var element = this.objectSearchService.getObject(editingContext, elementId);
                        if (element.isPresent()) {
                            var existingValues = eObject.eGet(eReference);
                            if (existingValues instanceof List<?> existingValuesAsList) {
                                ((List<Object>) existingValuesAsList).add(element.get());
                            }
                        } else {
                            emptyNewObjects.entrySet().stream()
                                    .filter(newObjEntry -> {
                                        var features = newObjEntry.getValue();
                                        var rawId = features.get(ID_PROPERTY);
                                        return rawId instanceof String id && Objects.equals(id, elementId);
                                    })
                                    .findFirst()
                                    .ifPresent(newObjEntry -> {
                                        var existingValues = eObject.eGet(eReference);
                                        if (existingValues instanceof List<?> existingValuesAsList) {
                                            ((List<Object>) existingValuesAsList).add(newObjEntry.getKey());
                                        }
                                        if (eReference.isContainment()) {
                                            newObjectsAttached.add(newObjEntry.getKey());
                                        }
                                    });
                        }
                    });
        }
    }
}
