/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.components.compatibility.diagrams;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.viewpoint.description.EAttributeCustomization;
import org.eclipse.sirius.viewpoint.description.VSMElementCustomization;

/**
 * Used to find the EAttribute customization which can be used.
 *
 * @author sbegaudeau
 */
public class EAttributeCustomizationProvider {

    private final AQLInterpreter interpreter;

    private final VariableManager variableManager;

    public EAttributeCustomizationProvider(AQLInterpreter interpreter, VariableManager variableManager) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.variableManager = Objects.requireNonNull(variableManager);
    }

    public Optional<EAttributeCustomization> getEAttributeCustomization(EObject eObject, String attributeName) {
        // @formatter:off
        Collection<Setting> settings = this.getCrossReferencer(eObject)
                .map(adapter -> adapter.getInverseReferences(eObject))
                .orElse(Collections.emptyList());

        return settings.stream()
                .map(Setting::getEObject)
                .filter(EAttributeCustomization.class::isInstance)
                .map(EAttributeCustomization.class::cast)
                .filter(eAttributeCustomization -> attributeName.equals(eAttributeCustomization.getAttributeName()))
                .filter(this::validatePredicate)
                .findFirst();
        // @formatter:on
    }

    /**
     * Used to test if the given EAttribute customization can be used by checking if the predicate defined on its parent
     * is respected.
     *
     * @param eAttributeCustomization
     *            The EAttribute customization
     * @return <code>true</code> if the EAttribute customization can be used, <code>false</code> otherwise
     */
    private boolean validatePredicate(EAttributeCustomization eAttributeCustomization) {
        if (eAttributeCustomization.eContainer() instanceof VSMElementCustomization) {
            VSMElementCustomization elementCustomization = (VSMElementCustomization) eAttributeCustomization.eContainer();
            String predicate = elementCustomization.getPredicateExpression();
            Result result = this.interpreter.evaluateExpression(this.variableManager.getVariables(), predicate);
            return result.asBoolean().orElse(false);
        }
        return false;
    }

    /**
     * Returns the cross reference adapter installed on the resource set.
     *
     * @param eObject
     *            An EObject
     * @return The cross reference adapter found or an empty optional
     */
    private Optional<ECrossReferenceAdapter> getCrossReferencer(EObject eObject) {
        // @formatter:off
        return Optional.ofNullable(eObject.eResource())
                .map(resource -> resource.getResourceSet().eAdapters())
                .orElse(new BasicEList<>())
                .stream()
                .filter(ECrossReferenceAdapter.class::isInstance)
                .map(ECrossReferenceAdapter.class::cast)
                .findFirst();
        // @formatter:on
    }
}
