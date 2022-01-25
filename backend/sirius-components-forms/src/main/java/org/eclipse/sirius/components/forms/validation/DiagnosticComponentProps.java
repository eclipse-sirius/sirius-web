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
package org.eclipse.sirius.components.forms.validation;

import java.util.Objects;

import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the diagnostic component for forms.
 *
 * @author gcoutable
 */
public class DiagnosticComponentProps implements IProps {

    private final AbstractWidgetDescription widgetDescription;

    private final VariableManager variableManager;

    public DiagnosticComponentProps(AbstractWidgetDescription widgetDescription, VariableManager variableManager) {
        this.widgetDescription = Objects.requireNonNull(widgetDescription);
        this.variableManager = Objects.requireNonNull(variableManager);
    }

    public AbstractWidgetDescription getWidgetDescription() {
        return this.widgetDescription;
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

}
