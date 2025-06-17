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

package org.eclipse.sirius.components.emf.forms;

import java.util.Objects;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.sirius.components.core.api.IReadOnlyObjectPredicate;
import org.eclipse.sirius.components.emf.forms.api.IWidgetReadOnlyProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Used to indicate if a widget from the EMF form description should be read only.
 *
 * @author sbegaudeau
 */
@Service
public class WidgetReadOnlyProvider implements IWidgetReadOnlyProvider {

    private final IReadOnlyObjectPredicate readOnlyObjectPredicate;

    public WidgetReadOnlyProvider(IReadOnlyObjectPredicate readOnlyObjectPredicate) {
        this.readOnlyObjectPredicate = Objects.requireNonNull(readOnlyObjectPredicate);
    }

    @Override
    public Boolean apply(VariableManager variableManager) {
        var isChangeableFeature = variableManager.get(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE, EAttribute.class)
                .map(eAttribute -> !eAttribute.isChangeable())
                .orElse(false);

        var isReadOnly = variableManager.get(VariableManager.SELF, Object.class)
                .filter(this.readOnlyObjectPredicate)
                .isPresent();
        return isChangeableFeature || isReadOnly;
    }
}
