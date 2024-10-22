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
import org.eclipse.sirius.components.forms.WidgetGridLayout;

/**
 * Custom assertion class used to perform some tests on WidgetGridLayout.
 *
 * @author lfasani
 */
public class WidgetGridLayoutAssert extends AbstractAssert<WidgetGridLayoutAssert, WidgetGridLayout> {

    public WidgetGridLayoutAssert(WidgetGridLayout widgetGridLayout) {
        super(widgetGridLayout, WidgetGridLayoutAssert.class);
    }

    public WidgetGridLayoutAssert hasGridTemplateRows(String gridTemplateRows) {
        assertThat(this.actual.gridTemplateRows()).isEqualTo(gridTemplateRows);

        return this;
    }

    public WidgetGridLayoutAssert hasGridTemplateColumns(String gridTemplateColumns) {
        assertThat(this.actual.gridTemplateColumns()).isEqualTo(gridTemplateColumns);

        return this;
    }

    public WidgetGridLayoutAssert hasLabelGridRow(String labelGridRow) {
        assertThat(this.actual.labelGridRow()).isEqualTo(labelGridRow);

        return this;
    }

    public WidgetGridLayoutAssert hasLabelGridColumn(String labelGridColumn) {
        assertThat(this.actual.labelGridColumn()).isEqualTo(labelGridColumn);

        return this;
    }

    public WidgetGridLayoutAssert hasWidgetGridRow(String widgetGridRow) {
        assertThat(this.actual.widgetGridRow()).isEqualTo(widgetGridRow);

        return this;
    }

    public WidgetGridLayoutAssert hasWidgetGridColumn(String widgetGridColumn) {
        assertThat(this.actual.widgetGridColumn()).isEqualTo(widgetGridColumn);

        return this;
    }

    public WidgetGridLayoutAssert hasGap(String gap) {
        assertThat(this.actual.gap()).isEqualTo(gap);

        return this;
    }
}

