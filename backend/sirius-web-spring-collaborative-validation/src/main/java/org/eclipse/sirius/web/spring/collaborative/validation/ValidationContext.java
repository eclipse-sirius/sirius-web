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
package org.eclipse.sirius.web.spring.collaborative.validation;

import org.eclipse.sirius.web.validation.Validation;

/**
 * Used to keep the validation representation in memory.
 *
 * @author gcoutable
 */
public class ValidationContext {

    private Validation validation;

    public ValidationContext(Validation validation) {
        this.validation = validation;
    }

    public Validation getValidation() {
        return this.validation;
    }

    public void update(Validation newValidation) {
        this.validation = newValidation;
    }

}
