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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.web.domain.Attribute;
import org.eclipse.sirius.web.domain.Domain;
import org.eclipse.sirius.web.domain.Entity;
import org.eclipse.sirius.web.domain.Relation;

/**
 * Converts a Domain into an equivalent Ecore EPackage.
 *
 * @author pcdavid
 */
public class DomainConverter {

    public EPackage convert(Domain domain) {
        EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
        ePackage.setName(domain.getName());
        ePackage.setNsPrefix(Optional.ofNullable(domain.getName()).orElse("").toLowerCase()); //$NON-NLS-1$
        ePackage.setNsURI(domain.getUri());

        Map<Entity, EClass> convertedTypes = new HashMap<>();
        for (Entity entity : domain.getTypes()) {
            EClass eClass = this.convert(entity);
            convertedTypes.put(entity, eClass);
            ePackage.getEClassifiers().add(eClass);
        }
        for (Entity entity : domain.getTypes()) {
            EClass eClass = convertedTypes.get(entity);
            for (Relation relation : entity.getRelations()) {
                EReference eReference = this.convert(relation, convertedTypes);
                eClass.getEStructuralFeatures().add(eReference);
            }
        }
        return ePackage;
    }

    private EClass convert(Entity entity) {
        EClass eClass = EcoreFactory.eINSTANCE.createEClass();
        eClass.setName(entity.getName());
        entity.getAttributes().forEach(attribute -> eClass.getEStructuralFeatures().add(this.convert(attribute)));
        return eClass;
    }

    private EAttribute convert(Attribute attribute) {
        EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
        eAttribute.setName(attribute.getName());
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
        eReference.setContainment(relation.isContainment());
        eReference.setEType(convertedTypes.get(relation.getTargetType()));
        return eReference;
    }
}
