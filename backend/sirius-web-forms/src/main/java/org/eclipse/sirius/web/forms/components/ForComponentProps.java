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

import org.eclipse.sirius.web.components.IProps;
import org.eclipse.sirius.web.forms.description.ForDescription;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * The properties of the for component.
 *
 * @author sbegaudeau
 */
public class ForComponentProps implements IProps {

    private VariableManager variableManager;

    private ForDescription forDescription;

    public ForComponentProps(VariableManager variableManager, ForDescription forDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.forDescription = Objects.requireNonNull(forDescription);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public ForDescription getForDescription() {
        return this.forDescription;
    }
}
