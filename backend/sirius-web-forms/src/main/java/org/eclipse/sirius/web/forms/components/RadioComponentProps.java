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
import org.eclipse.sirius.web.forms.description.RadioDescription;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * The properties of the radio component.
 *
 * @author sbegaudeau
 */
public class RadioComponentProps implements IProps {
    private VariableManager variableManager;

    private RadioDescription radioDescription;

    public RadioComponentProps(VariableManager variableManager, RadioDescription radioDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.radioDescription = Objects.requireNonNull(radioDescription);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public RadioDescription getRadioDescription() {
        return this.radioDescription;
    }
}
