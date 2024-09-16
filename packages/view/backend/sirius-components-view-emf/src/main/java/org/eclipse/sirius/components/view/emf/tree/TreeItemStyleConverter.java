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
package org.eclipse.sirius.components.view.emf.tree;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragment;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragmentStyle;
import org.eclipse.sirius.components.core.api.labels.UnderLineStyle;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.view.tree.TreeItemLabelDescription;
import org.eclipse.sirius.components.view.tree.TreeItemLabelElementDescription;
import org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription;

/**
 * Used to convert view tree DSL tree item styles in Sirius label styles {@link StyledString}.
 *
 * @author Jerome Gout
 */
public class TreeItemStyleConverter {

    private final AQLInterpreter interpreter;

    private final Map<String, Object> variables;

    public TreeItemStyleConverter(AQLInterpreter interpreter, Map<String, Object> variables) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.variables = Objects.requireNonNull(variables);
    }

    public StyledString convert(TreeItemLabelDescription tild) {
        var fragments = tild.getChildren().stream()
                .map(this::convertElement)
                .filter(Objects::nonNull)
                .toList();
        return new StyledString(fragments);
    }

    private StyledStringFragment convertElement(TreeItemLabelElementDescription element) {
        if (element instanceof TreeItemLabelFragmentDescription fragment) {
            return this.convertFragment(fragment);
        }
        return null;
    }

    private StyledStringFragment convertFragment(TreeItemLabelFragmentDescription fragment) {
        var text = this.evaluateString(fragment.getLabelExpression()).orElse("");
        var style = fragment.getStyle();
        var styleBuilder = StyledStringFragmentStyle.newDefaultStyledStringFragmentStyle();

        if (style != null) {
            String backgroundColor = this.evaluateString(style.getBackgroundColorExpression()).orElse("");
            String foregroundColor = this.evaluateString(style.getForegroundColorExpression()).orElse("");
            boolean isUnderline = this.evaluateBoolean(style.getIsUnderlineExpression()).orElse(false);
            boolean isBold = this.evaluateBoolean(style.getIsBoldExpression()).orElse(false);
            boolean isItalic = this.evaluateBoolean(style.getIsItalicExpression()).orElse(false);
            UnderLineStyle underLineStyle = UnderLineStyle.NONE;
            if (isUnderline) {
                underLineStyle = UnderLineStyle.SOLID;
            }
            styleBuilder
                    .backgroundColor(backgroundColor)
                    .foregroundColor(foregroundColor)
                    .struckOut(false)
                    .underlineStyle(underLineStyle)
                    .bold(isBold)
                    .italic(isItalic);
        }
        return new StyledStringFragment(text, styleBuilder.build());
    }

    private Optional<String> evaluateString(String expression) {
        return this.interpreter.evaluateExpression(this.variables, expression).asString();
    }

    private Optional<Boolean> evaluateBoolean(String expression) {
        return this.interpreter.evaluateExpression(this.variables, expression).asBoolean();
    }
}
