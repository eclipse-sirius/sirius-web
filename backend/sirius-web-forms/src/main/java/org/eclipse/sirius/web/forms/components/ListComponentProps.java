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
import org.eclipse.sirius.web.forms.description.ListDescription;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * The properties of the list component.
 *
 * @author sbegaudeau
 */
public class ListComponentProps implements IProps {
    private VariableManager variableManager;

    private ListDescription listDescription;

    public ListComponentProps(VariableManager variableManager, ListDescription listDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.listDescription = Objects.requireNonNull(listDescription);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public ListDescription getListDescription() {
        return this.listDescription;
    }
}
