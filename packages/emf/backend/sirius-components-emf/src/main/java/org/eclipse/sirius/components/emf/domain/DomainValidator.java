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
package org.eclipse.sirius.components.emf.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EcoreValidator;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainPackage;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.domain.Feature;
import org.eclipse.sirius.components.domain.NamedElement;

/**
 * The validator for Domain.
 *
 * @author gcoutable
 */
public class DomainValidator implements EValidator {

    public static final String INVALID_NAME_ERROR_MESSAGE = "The name %1$s is not well-formed."; //$NON-NLS-1$

    public static final String ENTITY_DISTINCT_NAME_ERROR_MESSAGE = "Two entities cannot have the same name in the same domain"; //$NON-NLS-1$

    public static final String FEATURE_DISTINCT_NAME_ERROR_MESSAGE = "Two features cannot have the same name in the same entity"; //$NON-NLS-1$

    public static final String SIRIUS_COMPONENTS_EMF_PACKAGE = "org.eclipse.sirius.components.emf"; //$NON-NLS-1$

    @Override
    public boolean validate(EDataType eDataType, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    @Override
    public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean isValid = true;
        if (eObject instanceof NamedElement) {
            isValid = this.nameIsWellFormedValidate((NamedElement) eObject, diagnostics) && isValid;
        }
        if (eObject instanceof Entity) {
            Entity entity = (Entity) eObject;
            isValid = this.nameIsNotUsedByOtherEntity(entity, diagnostics) && isValid;
        }
        if (eObject instanceof Feature) {
            Feature feature = (Feature) eObject;
            isValid = this.nameIsUniqueAmongOtherFeatures(feature, diagnostics) && isValid;
        }
        return isValid;
    }

    private boolean nameIsWellFormedValidate(NamedElement namedElement, DiagnosticChain diagnostics) {
        boolean isValid = EcoreValidator.isWellFormedJavaIdentifier(namedElement.getName());

        if (!isValid && diagnostics != null) {
         // @formatter:off
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.WARNING,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    String.format(INVALID_NAME_ERROR_MESSAGE, namedElement.getName()),
                    new Object [] {
                            namedElement,
                            DomainPackage.Literals.NAMED_ELEMENT__NAME,
                    });
            // @formatter:on

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean nameIsNotUsedByOtherEntity(Entity entity, DiagnosticChain diagnostics) {
        boolean isValid = true;
        EObject eContainer = entity.eContainer();
        if (eContainer instanceof Domain) {
            Domain domain = (Domain) eContainer;
            // @formatter:off
            isValid = domain.getTypes().stream()
                .filter(domainEntity -> !domainEntity.equals(entity))
                .map(Entity::getName)
                .noneMatch(entity.getName()::equalsIgnoreCase);
            // @formatter:on
        }

        if (!isValid && diagnostics != null) {
            // @formatter:off
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    ENTITY_DISTINCT_NAME_ERROR_MESSAGE,
                    new Object [] {
                            entity,
                            DomainPackage.Literals.NAMED_ELEMENT__NAME,
                    });
            // @formatter:on

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean nameIsUniqueAmongOtherFeatures(Feature feature, DiagnosticChain diagnostics) {
        boolean isValid = true;
        EObject eContainer = feature.eContainer();
        if (eContainer instanceof Entity) {
            Entity entity = (Entity) eContainer;
            List<Feature> features = new ArrayList<>();
            features.addAll(entity.getAttributes());
            features.addAll(entity.getRelations());
            isValid = features.stream().filter(f -> Objects.equals(f.getName(), feature.getName())).count() == 1;
        }
        if (!isValid && diagnostics != null) {
            // @formatter:off
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    FEATURE_DISTINCT_NAME_ERROR_MESSAGE,
                    new Object [] {
                            feature,
                            DomainPackage.Literals.NAMED_ELEMENT__NAME,
                    });
            // @formatter:on

            diagnostics.add(basicDiagnostic);
        }
        return isValid;
    }

    @Override
    public boolean validate(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

}
