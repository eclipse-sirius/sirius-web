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
import org.eclipse.sirius.components.forms.Select;

/**
 * Custom assertion class used to perform some tests on a select widget.
 *
 * @author sbegaudeau
 */
public class SelectAssert extends AbstractAssert<SelectAssert, Select> {

    public SelectAssert(Select select) {
        super(select, SelectAssert.class);
    }

    public SelectAssert hasLabel(String label) {
        assertThat(this.actual.getLabel()).isEqualTo(label);
        return this;
    }

    public SelectAssert hasOption(String option) {
        assertThat(this.actual.getOptions())
                .isNotEmpty()
                .anySatisfy(selectOption -> assertThat(selectOption.getLabel()).isEqualTo(option));
        return this;
    }

    public SelectAssert hasValue(String value) {
        assertThat(this.actual.getValue()).isEqualTo(value);
        return this;
    }
}
