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

import org.eclipse.sirius.web.representations.IProps;
import org.eclipse.sirius.web.validation.description.ValidationDescription;

/**
 * The properties of the diagnostic component.
 *
 * @author gcoutable
 */
public class DiagnosticComponentProps implements IProps {

    private Object diagnostic;

    private ValidationDescription validationDescription;

    public DiagnosticComponentProps(Object diagnostic, ValidationDescription validationDescription) {
        this.diagnostic = diagnostic;
        this.validationDescription = validationDescription;
    }

    public Object getDiagnostic() {
        return this.diagnostic;
    }

    public ValidationDescription getValidationDescription() {
        return this.validationDescription;
    }

}
