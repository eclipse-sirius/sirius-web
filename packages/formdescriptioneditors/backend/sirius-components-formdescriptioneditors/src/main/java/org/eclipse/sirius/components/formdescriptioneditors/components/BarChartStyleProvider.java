/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.formdescriptioneditors.components;

import java.util.Objects;

import org.eclipse.sirius.components.charts.barchart.components.BarChartStyle;
import org.eclipse.sirius.components.charts.barchart.components.BarChartStyle.Builder;
import org.eclipse.sirius.components.view.form.BarChartDescriptionStyle;

/**
 * The style provider for the BarChart Description widget of the View DSL. This only handles "static" or "preview"
 * styles which can be computed for "detached" widgets in the FormDescriptionEditor.
 *
 * @author arichard
 */
public class BarChartStyleProvider {

    private final BarChartDescriptionStyle viewBarChartDescriptionStyle;

    public BarChartStyleProvider(BarChartDescriptionStyle viewBarChartDescriptionStyle) {
        this.viewBarChartDescriptionStyle = Objects.requireNonNull(viewBarChartDescriptionStyle);
    }

    public BarChartStyle build() {
        Builder barChartStyleBuilder = BarChartStyle.newBarChartStyle();
        String barsColor = this.viewBarChartDescriptionStyle.getBarsColor();
        if (barsColor != null && !barsColor.isBlank()) {
            barChartStyleBuilder.barsColor(barsColor);
        }
        // @formatter:off
        barChartStyleBuilder.fontSize(this.viewBarChartDescriptionStyle.getFontSize())
            .bold(this.viewBarChartDescriptionStyle.isBold())
            .italic(this.viewBarChartDescriptionStyle.isItalic())
            .strikeThrough(this.viewBarChartDescriptionStyle.isStrikeThrough())
            .underline(this.viewBarChartDescriptionStyle.isUnderline());
        // @formatter:on
        return barChartStyleBuilder.build();
    }

}
