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

import org.assertj.core.api.Assertions;
import org.eclipse.sirius.components.tables.CheckboxCell;
import org.eclipse.sirius.components.tables.MultiSelectCell;
import org.eclipse.sirius.components.tables.SelectCell;
import org.eclipse.sirius.components.tables.TextfieldCell;

/**
 * Entry point of all the AssertJ assertions for tables.
 *
 * @author lfasani
 */
public class TablesAssertions extends Assertions {

    public static MultiSelectCellAssert assertThat(MultiSelectCell multiSelect) {
        return new MultiSelectCellAssert(multiSelect);
    }

    public static SelectCellAssert assertThat(SelectCell selectCell) {
        return new SelectCellAssert(selectCell);
    }

    public static CheckboxCellAssert assertThat(CheckboxCell checkboxCell) {
        return new CheckboxCellAssert(checkboxCell);
    }

    public static TextfieldCellAssert assertThat(TextfieldCell textfieldCell) {
        return new TextfieldCellAssert(textfieldCell);
    }
}
