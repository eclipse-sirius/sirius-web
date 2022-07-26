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

import org.eclipse.sirius.components.forms.description.LinkDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the Link component.
 *
 * @author ldelaigue
 */
public class LinkComponentProps implements IProps {
    private final VariableManager variableManager;

    private final LinkDescription linkDescription;

    public LinkComponentProps(VariableManager variableManager, LinkDescription linkDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.linkDescription = Objects.requireNonNull(linkDescription);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public LinkDescription getLinkDescription() {
        return this.linkDescription;
    }
}
