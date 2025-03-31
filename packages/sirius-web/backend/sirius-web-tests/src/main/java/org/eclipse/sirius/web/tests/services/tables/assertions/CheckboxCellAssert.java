/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.tests.services.tables.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.web.application.table.customcells.CheckboxCell;

/**
 * Custom assertion class used to perform some tests on a checkbox Cell widget.
 *
 * @author lfasani
 */
public class CheckboxCellAssert extends AbstractAssert<CheckboxCellAssert, CheckboxCell> {

    public CheckboxCellAssert(CheckboxCell checkboxCell) {
        super(checkboxCell, CheckboxCellAssert.class);
    }

    public CheckboxCellAssert hasValue(boolean value) {
        assertThat(this.actual.isValue()).isEqualTo(value);

        return this;
    }
}
