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

    public TextareaAssert hasLabel(String label) {
        assertThat(this.actual.getLabel()).isEqualTo(label);

        return this;
    }

    public TextareaAssert hasValue(String value) {
        assertThat(this.actual.getValue()).isEqualTo(value);

        return this;
    }

    public TextareaAssert hasHelp(String help) {
        assertThat(this.actual.getHelpTextProvider().get()).isEqualTo(help);

        return this;
    }

    public TextareaAssert isReadOnly() {
        assertThat(this.actual.isReadOnly())
                .withFailMessage("Expecting the textarea to be read only but was not read only instead")
                .isTrue();

        return this;
    }

    public TextareaAssert isNotReadOnly() {
        assertThat(this.actual.isReadOnly())
                .withFailMessage("Expecting the textarea not to be read only but was read only instead")
                .isFalse();

        return this;
    }

    public TextareaAssert isBold() {
        assertThat(this.actual.getStyle().isBold())
                .withFailMessage("Expecting the textarea to be bold but was not bold instead")
                .isTrue();

        return this;
    }

    public TextareaAssert isNotBold() {
        assertThat(this.actual.getStyle().isBold())
                .withFailMessage("Expecting the textarea not to be bold but was bold instead")
                .isFalse();

        return this;
    }

    public TextareaAssert isItalic() {
        assertThat(this.actual.getStyle().isItalic())
                .withFailMessage("Expecting the textarea to be italic but was not italic instead")
                .isTrue();

        return this;
    }

    public TextareaAssert isNotItalic() {
        assertThat(this.actual.getStyle().isItalic())
                .withFailMessage("Expecting the textarea not to be italic but was italic instead")
                .isFalse();

        return this;
    }
}
