/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.domain.emf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.sirius.components.domain.Attribute;
import org.eclipse.sirius.components.domain.DataType;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.domain.Feature;
import org.eclipse.sirius.components.domain.Relation;

/**
 * Converts a Domain into an equivalent Ecore EPackage.
 *
 * @author pcdavid
 */
public class DomainConverter {

    public static final String DOMAIN_SCHEME = "domain";

    public Stream<EPackage> convert(List<Domain> domains) {
        List<EPackage> ePackages = new ArrayList<>();

        Map<Entity, EClass> convertedTypes = new HashMap<>();
        for (Domain domain : domains) {
            EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
            ePackage.setName(domain.getName());
            ePackage.setNsPrefix(Optional.ofNullable(domain.getName()).orElse("").toLowerCase());
            ePackage.setNsURI(DOMAIN_SCHEME + "://" + domain.getName());

            // First pass to create the EClasses and EAttributes
            for (Entity entity : domain.getTypes()) {
                EClass eClass = this.convertEntity(entity);
                convertedTypes.put(entity, eClass);
                ePackage.getEClassifiers().add(eClass);
            }

            ePackages.add(ePackage);
        }

        // Second pass to convert references and resolve super-types.
        for (Domain domain : domains) {
            for (Entity entity : domain.getTypes()) {
                // Convert references
                EClass eClass = convertedTypes.get(entity);
                for (Relation relation : entity.getRelations()) {
                    EReference eReference = this.convertRelation(relation, convertedTypes);
                    eClass.getEStructuralFeatures().add(eReference);
                }
                // Resolve super-types
                for (Entity superType : entity.getSuperTypes()) {
                    if (convertedTypes.containsKey(superType)) {
                        eClass.getESuperTypes().add(convertedTypes.get(superType));
                    }
                }
            }
        }

        // Only return valid and safe EPackages
        return ePackages.stream().filter(ePackage -> Diagnostician.INSTANCE.validate(ePackage).getSeverity() < Diagnostic.ERROR);
    }

    private EClass convertEntity(Entity entity) {
        EClass eClass = EcoreFactory.eINSTANCE.createEClass();
        eClass.setName(entity.getName());
        eClass.setAbstract(entity.isAbstract());
        entity.getAttributes().forEach(attribute -> eClass.getEStructuralFeatures().add(this.convertAttribute(attribute)));
        return eClass;
    }

    private EAttribute convertAttribute(Attribute attribute) {
        EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
        eAttribute.setName(attribute.getName());
        this.convertCardinality(attribute, eAttribute);
        eAttribute.setEType(this.convertDataType(attribute.getType()));
        return eAttribute;
    }

    private EDataType convertDataType(DataType dataType) {
        final EDataType result;
        switch (dataType) {
            case BOOLEAN:
                result = EcorePackage.Literals.EBOOLEAN;
                break;
            case NUMBER:
                result = EcorePackage.Literals.EINT;
                break;
            case STRING:
                result = EcorePackage.Literals.ESTRING;
                break;
            default:
                result = EcorePackage.Literals.ESTRING;
                break;
        }
        return result;
    }

    private EReference convertRelation(Relation relation, Map<Entity, EClass> convertedTypes) {
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
