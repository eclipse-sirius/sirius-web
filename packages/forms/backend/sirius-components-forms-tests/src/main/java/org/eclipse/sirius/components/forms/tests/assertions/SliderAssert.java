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
import org.eclipse.sirius.components.forms.Slider;

/**
 * Custom assertion class used to perform some tests on a slider widget.
 *
 * @author sbegaudeau
 */
public class SliderAssert extends AbstractAssert<SliderAssert, Slider> {
    public SliderAssert(Slider slider) {
        super(slider, SliderAssert.class);
    }

    public SliderAssert hasLabel(String label) {
        assertThat(this.actual.getLabel()).isEqualTo(label);

        return this;
    }

    public SliderAssert hasValue(int value) {
        assertThat(this.actual.getCurrentValue()).isEqualTo(value);

        return this;
    }

    public SliderAssert hasHelp(String help) {
        assertThat(this.actual.getHelpTextProvider().get()).isEqualTo(help);

        return this;
    }

    public SliderAssert isReadOnly() {
        assertThat(this.actual.isReadOnly())
                .withFailMessage("Expecting the slider to be read only but was not read only instead")
                .isTrue();

        return this;
    }

    public SliderAssert isNotReadOnly() {
        assertThat(this.actual.isReadOnly())
                .withFailMessage("Expecting the slider not to be read only but was read only instead")
                .isFalse();

        return this;
    }
}
