/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.viewpoint.FontFormat;

/**
 * This class is used to convert a Sirius LabelStyleDescription to a Sirius Web LabelStyleDescription.
 *
 * @author hmarchadour
 */
public class LabelStyleDescriptionConverter {

    private static final String DEFAULT_COLOR = "transparent";

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    public LabelStyleDescriptionConverter(AQLInterpreter interpreter, IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.interpreter = Objects.requireNonNull(interpreter);
    }

    public LabelStyleDescription convert(org.eclipse.sirius.viewpoint.description.style.BasicLabelStyleDescription labelStyleDescription) {
        List<FontFormat> fontFormats = labelStyleDescription.getLabelFormat();

        Function<VariableManager, List<String>> iconURLProvider = (variableManager) -> {
            List<String> iconURL = List.of();
            if (labelStyleDescription.isShowIcon()) {
                String iconPath = labelStyleDescription.getIconPath();
                if (iconPath != null && !iconPath.isEmpty()) {
                    int indexOfSecondSlash = iconPath.indexOf('/', 1);
                    if (indexOfSecondSlash != -1) {
                        iconURL = List.of(iconPath.substring(indexOfSecondSlash));
                    }
                } else {
                    iconURL = variableManager.get(VariableManager.SELF, Object.class)
                            .map(this.objectService::getImagePath)
                            .orElse(List.of());
                }
            }
            return iconURL;
        };

        Function<VariableManager, Integer> fontSizeProvider = (variableManager) -> {
            int labelSize = labelStyleDescription.getLabelSize();
            if (labelSize != 0) {
                return labelSize;
            }
            return 16;
        };

        Function<VariableManager, String> colorProvider = variableManager -> new ColorDescriptionConverter(this.interpreter, variableManager.getVariables()).convert(labelStyleDescription.getLabelColor());

        return LabelStyleDescription.newLabelStyleDescription()
                .colorProvider(colorProvider)
                .fontSizeProvider(fontSizeProvider)
                .boldProvider(variableManager -> fontFormats.contains(FontFormat.BOLD_LITERAL))
                .italicProvider(variableManager -> fontFormats.contains(FontFormat.ITALIC_LITERAL))
                .underlineProvider(variableManager -> fontFormats.contains(FontFormat.UNDERLINE_LITERAL))
                .strikeThroughProvider(variableManager -> fontFormats.contains(FontFormat.STRIKE_THROUGH_LITERAL))
                .iconURLProvider(iconURLProvider)
                .backgroundProvider(variableManager -> DEFAULT_COLOR)
                .borderColorProvider(variableManager -> DEFAULT_COLOR)
                .borderRadiusProvider(variableManager -> 0)
                .borderSizeProvider(variableManager -> 0)
                .borderStyleProvider(variableManager -> LineStyle.Solid)
                .build();
    }
}
