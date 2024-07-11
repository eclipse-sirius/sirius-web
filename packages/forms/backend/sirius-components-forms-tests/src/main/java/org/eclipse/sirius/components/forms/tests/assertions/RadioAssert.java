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
import org.eclipse.sirius.components.forms.Radio;
import org.eclipse.sirius.components.forms.RadioOption;

/**
 * Custom assertion class used to perform some tests on a radio widget.
 *
 * @author sbegaudeau
 */
public class RadioAssert extends AbstractAssert<RadioAssert, Radio> {

    public RadioAssert(Radio radio) {
        super(radio, RadioAssert.class);
    }

    public RadioAssert hasLabel(String label) {
        assertThat(this.actual.getLabel()).isEqualTo(label);

        return this;
    }

    public RadioAssert hasValueWithLabel(String selectedRadioOptionValue) {
        assertThat(this.actual.getOptions())
                .filteredOn(RadioOption::isSelected)
                .first()
                .extracting(RadioOption::getLabel).isEqualTo(selectedRadioOptionValue);

        return this;
    }

    public RadioAssert hasHelp(String help) {
        assertThat(this.actual.getHelpTextProvider().get()).isEqualTo(help);

        return this;
    }

    public RadioAssert isReadOnly() {
        assertThat(this.actual.isReadOnly())
                .withFailMessage("Expecting the radio to be read only but was not read only instead")
                .isTrue();

        return this;
    }

    public RadioAssert isNotReadOnly() {
        assertThat(this.actual.isReadOnly())
                .withFailMessage("Expecting the radio not to be read only but was read only instead")
                .isFalse();

        return this;
    }
}
