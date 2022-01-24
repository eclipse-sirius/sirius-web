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
package org.eclipse.sirius.web.forms.components;

import java.util.Objects;

import org.eclipse.sirius.web.forms.description.IfDescription;
import org.eclipse.sirius.web.representations.IProps;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * The properties of the if component.
 *
 * @author sbegaudeau
 */
public class IfComponentProps implements IProps {
    private VariableManager variableManager;

    private IfDescription ifDescription;

    public IfComponentProps(VariableManager variableManager, IfDescription ifDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.ifDescription = Objects.requireNonNull(ifDescription);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public IfDescription getIfDescription() {
        return this.ifDescription;
    }
}
