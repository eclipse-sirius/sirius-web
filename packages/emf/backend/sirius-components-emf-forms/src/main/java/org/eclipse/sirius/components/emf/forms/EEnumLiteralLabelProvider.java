/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.emf.forms;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.forms.components.SelectComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Utility class used to provide a label for an enumeration literal in the
 * variable manager.
 *
 * @author dnikiforov
 */
public class EEnumLiteralLabelProvider implements Function<VariableManager, String> {

    private final String featureVariableName;

    public EEnumLiteralLabelProvider(String featureVariableName) {
        this.featureVariableName = Objects.requireNonNull(featureVariableName);
    }

    @Override
    public String apply(VariableManager variableManager) {
        var variables = variableManager.getVariables();
        var object = variables.get(VariableManager.SELF);
        var feature = variables.get(this.featureVariableName);
        var literal = variables.get(SelectComponent.CANDIDATE_VARIABLE);
        var optionalAdapterFactory = variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class)
                .map(IEMFEditingContext::getDomain)
                .map(AdapterFactoryEditingDomain::getAdapterFactory);
        String result = "";
        if (object instanceof EObject eObject && feature instanceof EStructuralFeature eStructuralFeature && optionalAdapterFactory.isPresent()) {
            Adapter adapter = optionalAdapterFactory.get().adapt(eObject, IItemPropertySource.class);
            if (adapter instanceof IItemPropertySource itemPropertySource) {
                IItemPropertyDescriptor descriptor = itemPropertySource.getPropertyDescriptor(eObject, eStructuralFeature);
                if (descriptor != null) {
                    result = descriptor.getLabelProvider(eObject).getText(literal);
                }
            }
        }
        if (result.isEmpty() && literal instanceof Enumerator enumerator) {
            result = enumerator.getLiteral();
        }
        return result;
    }

}
