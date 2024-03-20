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
import org.eclipse.sirius.components.forms.Textfield;

/**
 * Custom assertion class used to perform some tests on a textfield widget.
 *
 * @author sbegaudeau
 */
public class TextfieldAssert extends AbstractAssert<TextfieldAssert, Textfield> {

    public TextfieldAssert(Textfield textfield) {
        super(textfield, TextfieldAssert.class);
    }

    public TextfieldAssert hasLabel(String label) {
        assertThat(this.actual.getLabel()).isEqualTo(label);

        return this;
    }

    public TextfieldAssert hasValue(String value) {
        assertThat(this.actual.getValue()).isEqualTo(value);

        return this;
    }

    public TextfieldAssert hasHelp(String help) {
        assertThat(this.actual.getHelpTextProvider().get()).isEqualTo(help);

        return this;
    }

    public TextfieldAssert isReadOnly() {
        assertThat(this.actual.isReadOnly())
                .withFailMessage("Expecting the textfield to be read only but was not read only instead")
                .isTrue();

        return this;
    }

    public TextfieldAssert isNotReadOnly() {
        assertThat(this.actual.isReadOnly())
                .withFailMessage("Expecting the textfield not to be read only but was read only instead")
                .isFalse();

        return this;
    }

    public TextfieldAssert isBold() {
        assertThat(this.actual.getStyle().isBold())
                .withFailMessage("Expecting the textfield to be bold but was not bold instead")
                .isTrue();

        return this;
    }

    public TextfieldAssert isNotBold() {
        assertThat(this.actual.getStyle().isBold())
                .withFailMessage("Expecting the textfield not to be bold but was bold instead")
                .isFalse();

        return this;
    }

    public TextfieldAssert isItalic() {
        assertThat(this.actual.getStyle().isItalic())
                .withFailMessage("Expecting the textfield to be italic but was not italic instead")
                .isTrue();

        return this;
    }

    public TextfieldAssert isNotItalic() {
        assertThat(this.actual.getStyle().isItalic())
                .withFailMessage("Expecting the textfield not to be italic but was italic instead")
                .isFalse();

        return this;
    }
}
