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
package org.eclipse.sirius.web.emf.compatibility.properties;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.emf.services.EditingContext;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Utility class used to provide a label for a structural feature in the variable manager.
 *
 * @author sbegaudeau
 */
public class EStructuralFeatureLabelProvider implements Function<VariableManager, String> {

    private String featureVariableName;

    public EStructuralFeatureLabelProvider(String featureVariableName) {
        this.featureVariableName = Objects.requireNonNull(featureVariableName);
    }

    @Override
    public String apply(VariableManager variableManager) {
        Object object = variableManager.getVariables().get(VariableManager.SELF);
        Object feature = variableManager.getVariables().get(this.featureVariableName);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, EditingContext.class);

        if (object instanceof EObject && feature instanceof EStructuralFeature && optionalEditingContext.isPresent()) {
            EObject eObject = (EObject) object;
            EStructuralFeature eStructuralFeature = (EStructuralFeature) feature;
            EditingContext editingContext = optionalEditingContext.get();

            Adapter adapter = editingContext.getDomain().getAdapterFactory().adapt(eObject, IItemPropertySource.class);
            if (adapter instanceof IItemPropertySource) {
                IItemPropertySource itemPropertySource = (IItemPropertySource) adapter;
                IItemPropertyDescriptor descriptor = itemPropertySource.getPropertyDescriptor(eObject, eStructuralFeature);
                if (descriptor != null) {
                    String displayName = descriptor.getDisplayName(eStructuralFeature);
                    return displayName;
                }
            }
        }
        return ""; //$NON-NLS-1$
    }

}
