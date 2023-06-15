/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.widget.reference;

import java.util.Objects;

import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the reference widget component.
 *
 * @author pcdavid
 */
public class ReferenceWidgetComponentProps implements IProps {
    private final VariableManager variableManager;

    private final ReferenceWidgetDescription referenceWidgetDescription;

    public ReferenceWidgetComponentProps(VariableManager variableManager, ReferenceWidgetDescription referenceWidgetDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.referenceWidgetDescription = Objects.requireNonNull(referenceWidgetDescription);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public ReferenceWidgetDescription getReferenceWidgetDescription() {
        return this.referenceWidgetDescription;
    }
}
