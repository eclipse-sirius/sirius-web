/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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

import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the textfield component.
 *
 * @author sbegaudeau
 */
public class TextfieldComponentProps implements IProps {
    private final VariableManager variableManager;

    private final TextfieldDescription textfieldDescription;

    public TextfieldComponentProps(VariableManager variableManager, TextfieldDescription textfieldDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.textfieldDescription = Objects.requireNonNull(textfieldDescription);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public TextfieldDescription getTextfieldDescription() {
        return this.textfieldDescription;
    }
}
