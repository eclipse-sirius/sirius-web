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
package org.eclipse.sirius.components.forms.tests.assertions;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.forms.Textarea;

/**
 * Custom assertion class used to perform some tests on a textarea widget.
 *
 * @author sbegaudeau
 */
public class TextareaAssert extends AbstractAssert<TextareaAssert, Textarea> {

    public TextareaAssert(Textarea textarea) {
        super(textarea, TextareaAssert.class);
    }
}
