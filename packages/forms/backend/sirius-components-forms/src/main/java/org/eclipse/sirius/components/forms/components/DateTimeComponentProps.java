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

import org.eclipse.sirius.components.forms.description.DateTimeDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the dateTime component.
 * @author lfasani
 */
public class DateTimeComponentProps implements IProps {
    private final VariableManager variableManager;

    private final DateTimeDescription dateTimeDescription;

    public DateTimeComponentProps(VariableManager variableManager, DateTimeDescription dateTimeDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.dateTimeDescription = Objects.requireNonNull(dateTimeDescription);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public DateTimeDescription getDateTimeDescription() {
        return this.dateTimeDescription;
    }
}
