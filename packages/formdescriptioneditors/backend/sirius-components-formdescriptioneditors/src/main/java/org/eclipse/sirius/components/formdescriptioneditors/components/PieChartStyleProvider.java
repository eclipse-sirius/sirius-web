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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.charts.piechart.components.PieChartStyle;
import org.eclipse.sirius.components.charts.piechart.components.PieChartStyle.Builder;
import org.eclipse.sirius.components.view.PieChartDescriptionStyle;

/**
 * The style provider for the PieChart Description widget of the View DSL. This only handles "static" or "preview"
 * styles which can be computed for "detached" widgets in the FormDescriptionEditor.
 *
 * @author arichard
 */
public class PieChartStyleProvider {

    private final PieChartDescriptionStyle viewPieChartDescriptionStyle;

    public PieChartStyleProvider(PieChartDescriptionStyle viewPieChartDescriptionStyle) {
        this.viewPieChartDescriptionStyle = Objects.requireNonNull(viewPieChartDescriptionStyle);
    }

    public PieChartStyle build() {
        Builder pieChartStyleBuilder = PieChartStyle.newPieChartStyle();
        String strokeColor = this.viewPieChartDescriptionStyle.getStrokeColor();
        int strokeWidth = this.viewPieChartDescriptionStyle.getStrokeWidth();
        pieChartStyleBuilder.colors(List.of());
        this.handleStrokeColor(pieChartStyleBuilder, strokeColor);
        pieChartStyleBuilder.strokeWidth(strokeWidth);
        this.handleLabelStyle(pieChartStyleBuilder, this.viewPieChartDescriptionStyle);
        return pieChartStyleBuilder.build();
    }

    private void handleLabelStyle(Builder pieChartStyleBuilder, PieChartDescriptionStyle effectiveStyle) {
        // @formatter:off
        pieChartStyleBuilder.fontSize(effectiveStyle.getFontSize())
            .bold(effectiveStyle.isBold())
            .italic(effectiveStyle.isItalic())
            .strikeThrough(effectiveStyle.isStrikeThrough())
            .underline(effectiveStyle.isUnderline());
        // @formatter:on
    }

    private void handleStrokeColor(Builder pieChartStyleBuilder, String strokeColor) {
        if (strokeColor != null && !strokeColor.isBlank()) {
            pieChartStyleBuilder.strokeColor(strokeColor);
        }
    }
}
