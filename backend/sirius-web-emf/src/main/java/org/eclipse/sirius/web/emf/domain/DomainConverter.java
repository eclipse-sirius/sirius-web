/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.emf.domain;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.sirius.web.domain.Attribute;
import org.eclipse.sirius.web.domain.Domain;
import org.eclipse.sirius.web.domain.DomainFactory;
import org.eclipse.sirius.web.domain.Entity;
import org.eclipse.sirius.web.domain.Feature;
import org.eclipse.sirius.web.domain.Relation;

/**
 * Converts a Domain into an equivalent Ecore EPackage.
 *
 * @author pcdavid
 */
public class DomainConverter {

    public Optional<EPackage> convert(Domain domain) {
        EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
        ePackage.setName(domain.getName());
        ePackage.setNsPrefix(Optional.ofNullable(domain.getName()).orElse("").toLowerCase()); //$NON-NLS-1$
        ePackage.setNsURI(domain.getUri());

        Map<Entity, EClass> convertedTypes = new HashMap<>();
        // We need to make multiple passes to handle inheritance, as an Entity can only be converted
        // once all its super types have been. We could use a topological sort to iterate in the correct
        // order but it seems simpler to simply make multiple passes until all types are converted.
        Deque<Entity> leftToConvert = new ArrayDeque<>(domain.getTypes());
        Entity sentinel = DomainFactory.eINSTANCE.createEntity();
        leftToConvert.addLast(sentinel);
        // The worst case happens when we can only find and convert a single type on each pass.
        // If we need more passes than that, it means at least one pass did not find any type that
        // has all its parents already converted, and thus there is an inheritance loop.
        int maxPasses = domain.getTypes().size();
        int nbPasses = 0;
        while (leftToConvert.size() > 1 && nbPasses <= maxPasses) {
            Entity candidate = leftToConvert.pop();
            if (candidate == sentinel) {
                // We've hit the end of the queue, i.e. finished a single pass
                nbPasses++;
                leftToConvert.addLast(sentinel);
            } else if (Optional.ofNullable(candidate.getSuperType()).stream().allMatch(convertedTypes::containsKey)) {
                // candidate can be converted if all its super types have already been
                EClass eClass = this.convert(candidate, convertedTypes);
                convertedTypes.put(candidate, eClass);
                ePackage.getEClassifiers().add(eClass);
            } else {
                // Try again in the next pass if we have converted all its super-types
                leftToConvert.addLast(candidate);
            }
        }
        if (leftToConvert.isEmpty()) {
            for (Entity entity : domain.getTypes()) {
                EClass eClass = convertedTypes.get(entity);
                for (Relation relation : entity.getRelations()) {
                    EReference eReference = this.convert(relation, convertedTypes);
                    eClass.getEStructuralFeatures().add(eReference);
                }
            }
            Diagnostic diagnostic = Diagnostician.INSTANCE.validate(ePackage);
            if (diagnostic.getSeverity() < Diagnostic.ERROR) {
                return Optional.of(ePackage);
            }
        }
        return Optional.empty();
    }

    private EClass convert(Entity entity, Map<Entity, EClass> convertedTypes) {
        EClass eClass = EcoreFactory.eINSTANCE.createEClass();
        eClass.setName(entity.getName());
        entity.getAttributes().forEach(attribute -> eClass.getEStructuralFeatures().add(this.convert(attribute)));
        if (entity.getSuperType() != null && convertedTypes.containsKey(entity.getSuperType())) {
            eClass.getESuperTypes().add(convertedTypes.get(entity.getSuperType()));
        }
        return eClass;
    }

    private EAttribute convert(Attribute attribute) {
        EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
        eAttribute.setName(attribute.getName());
        this.convertCardinality(attribute, eAttribute);
        switch (attribute.getType()) {
        case BOOLEAN:
            eAttribute.setEType(EcorePackage.Literals.EBOOLEAN);
            break;
        case NUMBER:
            eAttribute.setEType(EcorePackage.Literals.EINT);
            break;
        case STRING:
            eAttribute.setEType(EcorePackage.Literals.ESTRING);
            break;
        default:
            break;
        }
        return eAttribute;
    }

    private EReference convert(Relation relation, Map<Entity, EClass> convertedTypes) {
        EReference eReference = EcoreFactory.eINSTANCE.createEReference();
        eReference.setName(relation.getName());
        this.convertCardinality(relation, eReference);
        eReference.setContainment(relation.isContainment());
        eReference.setEType(convertedTypes.get(relation.getTargetType()));
        return eReference;
    }

    private void convertCardinality(Feature feature, EStructuralFeature eStructuralFeature) {
        if (feature.isOptional()) {
            eStructuralFeature.setLowerBound(0);
        } else {
            eStructuralFeature.setLowerBound(1);
        }
        if (feature.isMany()) {
            eStructuralFeature.setUpperBound(-1);
        } else {
            eStructuralFeature.setUpperBound(1);
        }
    }

}
