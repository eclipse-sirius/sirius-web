/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import org.eclipse.sirius.components.forms.description.SplitButtonDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the splitButton component.
 *
 * @author mcharfadi
 */
public class SplitButtonComponentProps implements IProps {

    private final VariableManager variableManager;

    private final SplitButtonDescription splitButtonDescription;

    public SplitButtonComponentProps(VariableManager variableManager, SplitButtonDescription splitButtonDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.splitButtonDescription = Objects.requireNonNull(splitButtonDescription);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public SplitButtonDescription getSplitButtonDescription() {
        return this.splitButtonDescription;
    }

}
