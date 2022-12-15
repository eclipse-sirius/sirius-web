/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.compatibility.diagrams;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.viewpoint.description.ColorDescription;
import org.eclipse.sirius.viewpoint.description.ComputedColor;
import org.eclipse.sirius.viewpoint.description.FixedColor;

/**
 * Provides a unified color format from a Sirius {@link ColorDescription}.
 *
 * @author fbarbin
 */
public class ColorDescriptionConverter {

    private static final String DEFAULT_COLOR = "#000000";

    private final AQLInterpreter interpreter;

    private final Map<String, Object> variables;

    public ColorDescriptionConverter(AQLInterpreter interpreter, Map<String, Object> variables) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.variables = Objects.requireNonNull(variables);
    }

    public String convert(ColorDescription colorDescription) {
        String value = DEFAULT_COLOR;
        if (colorDescription instanceof FixedColor) {
            FixedColor fixedColor = (FixedColor) colorDescription;
            value = this.toHex(fixedColor.getRed(), fixedColor.getGreen(), fixedColor.getBlue());
        } else if (colorDescription instanceof ComputedColor) {
            ComputedColor computedColor = (ComputedColor) colorDescription;
            int red = this.interpreter.evaluateExpression(this.variables, computedColor.getRed()).asInt().orElse(0);
            int green = this.interpreter.evaluateExpression(this.variables, computedColor.getGreen()).asInt().orElse(0);
            int blue = this.interpreter.evaluateExpression(this.variables, computedColor.getBlue()).asInt().orElse(0);

            value = this.toHex(red, green, blue);
        }
        return value;
    }

    private String toHex(int r, int g, int b) {
        return String.format("#%02x%02x%02x", r, g, b);
    }
}
