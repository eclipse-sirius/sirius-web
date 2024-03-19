/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.forms.tests.assertions;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.forms.Form;

/**
 * Custom assertion class used to performs some tests on a form.
 *
 * @author lfasani
 */
public class FormAssert extends AbstractAssert<FormAssert, Form> {

    public FormAssert(Form form) {
        super(form, FormAssert.class);
    }
}
