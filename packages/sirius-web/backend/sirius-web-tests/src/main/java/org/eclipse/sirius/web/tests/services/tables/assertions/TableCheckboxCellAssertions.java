/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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

import org.eclipse.sirius.web.application.table.customcells.CheckboxCell;

/**
 * Entry point of all the AssertJ assertions for tables checkbox cell.
 *
 * @author Jerome Gout
 */
public class TableCheckboxCellAssertions {

    public static CheckboxCellAssert assertThat(CheckboxCell checkboxCell) {
        return new CheckboxCellAssert(checkboxCell);
    }

}
