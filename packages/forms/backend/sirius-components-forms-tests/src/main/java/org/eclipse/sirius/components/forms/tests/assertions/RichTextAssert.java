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

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.forms.RichText;

/**
 * Custom assertion class used to perform some tests on a rich text widget.
 *
 * @author sbegaudeau
 */
public class RichTextAssert extends AbstractAssert<RichTextAssert, RichText> {

    public RichTextAssert(RichText richText) {
        super(richText, RichTextAssert.class);
    }

    public RichTextAssert hasValue(String value) {
        assertThat(this.actual.getValue()).isEqualTo(value);
        return this;
    }
}
