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
package org.eclipse.sirius.web.compat.forms;

import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.web.forms.components.GroupComponent;
import org.eclipse.sirius.web.forms.components.WidgetIdCounter;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * The provider of the id of the widget created.
 *
 * @author sbegaudeau
 */
public class WidgetIdProvider implements Function<VariableManager, String> {

    @Override
    public String apply(VariableManager variableManager) {
        // @formatter:off
        var optionalEObject = Optional.of(variableManager.getVariables().get(VariableManager.SELF))
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);

        Optional<WidgetIdCounter> optionalCounter = Optional.of(variableManager.getVariables().get(GroupComponent.WIDGET_ID_PROVIDER_COUNTER))
                .filter(WidgetIdCounter.class::isInstance)
                .map(WidgetIdCounter.class::cast);
        // @formatter:on

        if (optionalCounter.isPresent() && optionalEObject.isPresent()) {
            WidgetIdCounter counter = optionalCounter.get();
            EObject eObject = optionalEObject.get();
            String id = EcoreUtil.getURI(eObject).toString() + "#" + counter.getCounter(); //$NON-NLS-1$
            counter.increment();
            return id;
        }
        return ""; //$NON-NLS-1$
    }

}
