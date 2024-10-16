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
package org.eclipse.sirius.components.tables.tests.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.tables.SelectCell;

/**
 * Custom assertion class used to perform some tests on a select cell widget.
 *
 * @author lfasani
 */
public class SelectCellAssert extends AbstractAssert<SelectCellAssert, SelectCell> {

    public SelectCellAssert(SelectCell selectCell) {
        super(selectCell, SelectCellAssert.class);
    }

    public SelectCellAssert hasValue(String value) {
        assertThat(this.actual.getValue()).isEqualTo(value);

        return this;
    }
}
