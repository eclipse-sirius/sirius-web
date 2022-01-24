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

import org.eclipse.sirius.web.forms.description.GroupDescription;
import org.eclipse.sirius.web.representations.IProps;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * The properties of the group component.
 *
 * @author sbegaudeau
 */
public class GroupComponentProps implements IProps {
    private VariableManager variableManager;

    private GroupDescription groupDescription;

    public GroupComponentProps(VariableManager variableManager, GroupDescription groupDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.groupDescription = Objects.requireNonNull(groupDescription);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public GroupDescription getGroupDescription() {
        return this.groupDescription;
    }
}
