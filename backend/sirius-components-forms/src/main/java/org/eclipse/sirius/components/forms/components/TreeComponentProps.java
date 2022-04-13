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

import org.eclipse.sirius.components.forms.description.TreeDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties for the tree component.
 *
 * @author pcdavid
 */
public class TreeComponentProps implements IProps {
    private final VariableManager variableManager;

    private final TreeDescription treeDescription;

    public TreeComponentProps(VariableManager variableManager, TreeDescription treeDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.treeDescription = Objects.requireNonNull(treeDescription);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public TreeDescription getTreeDescription() {
        return this.treeDescription;
    }
}
