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

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.charts.barchart.components.BarChartStyle;
import org.eclipse.sirius.components.charts.barchart.components.BarChartStyle.Builder;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.BarChartDescription;
import org.eclipse.sirius.components.view.BarChartDescriptionStyle;

/**
 * The style provider for the BarChart Description widget of the View DSL.
 *
 * @author fbarbin
 */
public class BarChartStyleProvider implements Function<VariableManager, BarChartStyle> {

    private final BarChartDescription viewBarChartDescription;

    private final AQLInterpreter interpreter;

    public BarChartStyleProvider(AQLInterpreter interpreter, BarChartDescription viewBarChartDescription) {
        this.viewBarChartDescription = Objects.requireNonNull(viewBarChartDescription);
        this.interpreter = Objects.requireNonNull(interpreter);
    }

    @Override
    public BarChartStyle apply(VariableManager variableManager) {
        var effectiveStyle = this.computeEffectiveStyle(variableManager);
        if (effectiveStyle != null) {
            Builder barChartStyleBuilder = BarChartStyle.newBarChartStyle();
            String barsColor = effectiveStyle.getBarsColor();
            if (barsColor != null && !barsColor.isBlank()) {
                barChartStyleBuilder.barsColor(barsColor);
            }
            this.handleLabelStyle(barChartStyleBuilder, effectiveStyle);
            return barChartStyleBuilder.build();
        }
        return null;
    }

    private void handleLabelStyle(Builder barChartStyleBuilder, BarChartDescriptionStyle effectiveStyle) {
        // @formatter:off
        barChartStyleBuilder.fontSize(effectiveStyle.getFontSize())
        .bold(effectiveStyle.isBold())
        .italic(effectiveStyle.isItalic())
        .strikeThrough(effectiveStyle.isStrikeThrough())
        .underline(effectiveStyle.isUnderline());
        // @formatter:on
    }

    private BarChartDescriptionStyle computeEffectiveStyle(VariableManager variableManager) {
        // @formatter:off
        return this.viewBarChartDescription.getConditionalStyles().stream()
                .filter(style -> this.matches(style.getCondition(), variableManager))
                .map(BarChartDescriptionStyle.class::cast)
                .findFirst()
                .orElseGet(this.viewBarChartDescription::getStyle);
        // @formatter:on
    }

    private boolean matches(String condition, VariableManager variableManager) {
        return this.interpreter.evaluateExpression(variableManager.getVariables(), condition).asBoolean().orElse(Boolean.FALSE);
    }
}
