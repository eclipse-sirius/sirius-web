/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.view.emf.form;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.charts.piechart.components.PieChartStyle;
import org.eclipse.sirius.components.charts.piechart.components.PieChartStyle.Builder;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.PieChartDescription;
import org.eclipse.sirius.components.view.PieChartDescriptionStyle;

/**
 * The style provider for the PieChart Description widget of the View DSL.
 *
 * @author fbarbin
 */
public class PieChartStyleProvider implements Function<VariableManager, PieChartStyle> {

    private final PieChartDescription viewPieChartDescription;

    private final AQLInterpreter interpreter;

    public PieChartStyleProvider(AQLInterpreter interpreter, PieChartDescription viewPieChartDescription) {
        this.viewPieChartDescription = Objects.requireNonNull(viewPieChartDescription);
        this.interpreter = Objects.requireNonNull(interpreter);
    }

    @Override
    public PieChartStyle apply(VariableManager variableManager) {
        var effectiveStyle = this.computeEffectiveStyle(variableManager);
        if (effectiveStyle != null) {
            Builder pieChartStyleBuilder = PieChartStyle.newPieChartStyle();
            String colors = effectiveStyle.getColors();
            String strokeColor = effectiveStyle.getStrokeColor();
            String strokeWidth = effectiveStyle.getStrokeWidth();
            this.handleColors(variableManager, pieChartStyleBuilder, colors);
            this.handleStrokeColor(pieChartStyleBuilder, strokeColor);
            this.handleStrokeWidth(pieChartStyleBuilder, strokeWidth);
            this.handleLabelStyle(pieChartStyleBuilder, effectiveStyle);
            return pieChartStyleBuilder.build();
        }
        return null;
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

    private void handleStrokeWidth(Builder pieChartStyleBuilder, String strokeWidth) {
        if (strokeWidth != null && !strokeWidth.isBlank()) {
            try {
                int width = Integer.parseInt(strokeWidth);
                pieChartStyleBuilder.strokeWidth(width);
            } catch (NullPointerException | NumberFormatException e) {
                // unexpected value.
            }
        }
    }

    private void handleStrokeColor(Builder pieChartStyleBuilder, String strokeColor) {
        if (strokeColor != null && !strokeColor.isBlank()) {
            pieChartStyleBuilder.strokeColor(strokeColor);
        }
    }

    private void handleColors(VariableManager variableManager, Builder pieChartStyleBuilder, String colors) {
        if (colors != null && !colors.isBlank()) {
            // @formatter:off
            List<Object> colorsObjects = this.interpreter.evaluateExpression(variableManager.getVariables(), colors).asObjects()
                    .orElse(List.of());
            List<String> colorsValues = colorsObjects.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .collect(Collectors.toList());
            // @formatter:on
            pieChartStyleBuilder.colors(colorsValues);
        }
    }

    private PieChartDescriptionStyle computeEffectiveStyle(VariableManager variableManager) {
        // @formatter:off
        return this.viewPieChartDescription.getConditionalStyles().stream()
                .filter(style -> this.matches(style.getCondition(), variableManager))
                .map(PieChartDescriptionStyle.class::cast)
                .findFirst()
                .orElseGet(this.viewPieChartDescription::getStyle);
        // @formatter:on
    }

    private boolean matches(String condition, VariableManager variableManager) {
        return this.interpreter.evaluateExpression(variableManager.getVariables(), condition).asBoolean().orElse(Boolean.FALSE);
    }
}
