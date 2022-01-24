/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.validation.components;

import java.util.Optional;

import org.eclipse.sirius.web.representations.IProps;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.validation.Validation;
import org.eclipse.sirius.web.validation.description.ValidationDescription;

/**
 * The properties of the validation component.
 *
 * @author gcoutable
 */
public class ValidationComponentProps implements IProps {

    private final VariableManager variableManager;

    private final ValidationDescription validationDescription;

    private Optional<Validation> optionalPreviousValidation;

    public ValidationComponentProps(VariableManager variableManager, ValidationDescription validationDescription, Optional<Validation> optionalPreviousValidation) {
        this.variableManager = variableManager;
        this.validationDescription = validationDescription;
        this.optionalPreviousValidation = optionalPreviousValidation;
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public ValidationDescription getValidationDescription() {
        return this.validationDescription;
    }

    public Optional<Validation> getPreviousValidation() {
        return this.optionalPreviousValidation;
    }

}
