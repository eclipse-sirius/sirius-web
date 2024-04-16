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
import org.eclipse.sirius.components.forms.DateTime;

/**
 * Custom assertion class used to perform some tests on a dateTime widget.
 *
 * @author lfasani
 */
public class DateTimeAssert extends AbstractAssert<DateTimeAssert, DateTime> {
    public DateTimeAssert(DateTime dateTime) {
        super(dateTime, DateTimeAssert.class);
    }

    public DateTimeAssert hasLabel(String label) {
        assertThat(this.actual.getLabel()).isEqualTo(label);

        return this;
    }

    public DateTimeAssert hasValue(String value) {
        assertThat(this.actual.getStringValue()).isEqualTo(value);

        return this;
    }

    public DateTimeAssert hasHelp(String help) {
        assertThat(this.actual.getHelpTextProvider().get()).isEqualTo(help);

        return this;
    }

    public DateTimeAssert isReadOnly() {
        assertThat(this.actual.isReadOnly())
                .withFailMessage("Expecting the dateTime to be read only but was not read only instead")
                .isTrue();

        return this;
    }

    public DateTimeAssert isNotReadOnly() {
        assertThat(this.actual.isReadOnly())
                .withFailMessage("Expecting the dateTime not to be read only but was read only instead")
                .isFalse();

        return this;
    }
}
