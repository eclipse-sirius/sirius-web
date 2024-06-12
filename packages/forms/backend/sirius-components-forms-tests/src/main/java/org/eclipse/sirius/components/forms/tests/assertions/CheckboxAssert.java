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
import org.eclipse.sirius.components.forms.Checkbox;

/**
 * Custom assertion class used to perform some tests on a checkbox widget.
 *
 * @author sbegaudeau
 */
public class CheckboxAssert extends AbstractAssert<CheckboxAssert, Checkbox> {

    public CheckboxAssert(Checkbox checkbox) {
        super(checkbox, CheckboxAssert.class);
    }

    public CheckboxAssert hasLabel(String label) {
        assertThat(this.actual.getLabel()).isEqualTo(label);

        return this;
    }

    public CheckboxAssert hasValue(boolean value) {
        assertThat(this.actual.isValue()).isEqualTo(value);

        return this;
    }

    public CheckboxAssert hasHelp(String help) {
        assertThat(this.actual.getHelpTextProvider().get()).isEqualTo(help);

        return this;
    }

    public CheckboxAssert isReadOnly() {
        assertThat(this.actual.isReadOnly())
                .withFailMessage("Expecting the checkbox to be read only but was not read only instead")
                .isTrue();

        return this;
    }

    public CheckboxAssert isNotReadOnly() {
        assertThat(this.actual.isReadOnly())
                .withFailMessage("Expecting the checkbox not to be read only but was read only instead")
                .isFalse();

        return this;
    }

}
