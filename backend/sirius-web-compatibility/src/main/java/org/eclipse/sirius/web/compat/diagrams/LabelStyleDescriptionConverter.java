/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.compat.diagrams;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.viewpoint.FontFormat;
import org.eclipse.sirius.web.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.objects.IObjectService;

/**
 * This class is used to convert a Sirius LabelStyleDescription to an Sirius Web LabelStyleDescription.
 *
 * @author hmarchadour
 */
public class LabelStyleDescriptionConverter {
    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    public LabelStyleDescriptionConverter(AQLInterpreter interpreter, IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.interpreter = Objects.requireNonNull(interpreter);
    }

    public LabelStyleDescription convert(org.eclipse.sirius.viewpoint.description.style.BasicLabelStyleDescription labelStyleDescription) {
        Objects.requireNonNull(labelStyleDescription);
        List<FontFormat> fontFormats = labelStyleDescription.getLabelFormat();

        Function<VariableManager, String> iconURLProvider = (variableManager) -> {
            String iconURL = ""; //$NON-NLS-1$
            if (labelStyleDescription.isShowIcon()) {
                // @formatter:off
                iconURL = variableManager.get(VariableManager.SELF, Object.class)
                    .map(this.objectService::getImagePath)
                    .orElse(""); //$NON-NLS-1$
                // @formatter:on
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

        Function<VariableManager, String> colorProvider = variableManager -> {
            return new ColorDescriptionConverter(this.interpreter, variableManager).convert(labelStyleDescription.getLabelColor());
        };

        // @formatter:off
        return LabelStyleDescription.newLabelStyleDescription()
                .colorProvider(colorProvider)
                .fontSizeProvider(fontSizeProvider)
                .boldProvider(variableManager -> fontFormats.contains(FontFormat.BOLD_LITERAL))
                .italicProvider(variableManager -> fontFormats.contains(FontFormat.ITALIC_LITERAL))
                .underlineProvider(variableManager -> fontFormats.contains(FontFormat.UNDERLINE_LITERAL))
                .strikeThroughProvider(variableManager -> fontFormats.contains(FontFormat.STRIKE_THROUGH_LITERAL))
                .iconURLProvider(iconURLProvider)
                .build();
        // @formatter:on
    }
}
