/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.forms.components;

import java.util.Objects;

import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the toolbar action component.
 *
 * @author arichard
 */
public class ToolbarActionComponentProps implements IProps {
    private final VariableManager variableManager;

    private final ButtonDescription buttonDescription;

    public ToolbarActionComponentProps(VariableManager variableManager, ButtonDescription buttonDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.buttonDescription = Objects.requireNonNull(buttonDescription);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public ButtonDescription getButtonDescription() {
        return this.buttonDescription;
    }
}
