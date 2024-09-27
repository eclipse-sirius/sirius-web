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

import java.util.List;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.tables.MultiSelectCell;

/**
 * Custom assertion class used to perform some tests on a multi select cell widget.
 *
 * @author lfasani
 */
public class MultiSelectCellAssert extends AbstractAssert<MultiSelectCellAssert, MultiSelectCell> {

    public MultiSelectCellAssert(MultiSelectCell multiSelectCell) {
        super(multiSelectCell, MultiSelectCellAssert.class);
    }

    public MultiSelectCellAssert hasValues(List<String> values) {
        assertThat(this.actual.getValues()).isEqualTo(values);

        return this;
    }
}
