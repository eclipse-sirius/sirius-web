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
import org.eclipse.sirius.components.forms.MultiSelect;

/**
 * Custom assertion class used to perform some tests on a multi-select widget.
 *
 * @author sbegaudeau
 */
public class MultiSelectAssert extends AbstractAssert<MultiSelectAssert, MultiSelect> {

    public MultiSelectAssert(MultiSelect multiSelect) {
        super(multiSelect, MultiSelectAssert.class);
    }

    public MultiSelectAssert hasLabel(String label) {
        assertThat(this.actual.getLabel()).isEqualTo(label);
        return this;
    }

    public MultiSelectAssert hasOption(String option) {
        assertThat(this.actual.getOptions())
                .isNotEmpty()
                .anySatisfy(multiSelectOption -> assertThat(multiSelectOption.getLabel()).isEqualTo(option));
        return this;
    }

    public MultiSelectAssert hasValue(String value) {
        assertThat(this.actual.getValues()).isNotEmpty()
                .anySatisfy(multiSelectValue -> assertThat(multiSelectValue).isEqualTo(value));
        return this;
    }
}
