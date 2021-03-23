/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.emf.view;

import org.eclipse.sirius.web.diagrams.ArrowStyle;
import org.eclipse.sirius.web.diagrams.EdgeStyle;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.LineStyle;
import org.eclipse.sirius.web.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.web.diagrams.description.LabelStyleDescription;

/**
 * Factory to create the basic style descriptions used by {@link ViewConverter}.
 *
 * @author pcdavid
 */
public final class StylesFactory {
    public LabelStyleDescription createLabelStyleDescription(String color) {
        // @formatter:off
        return LabelStyleDescription.newLabelStyleDescription()
                                    .colorProvider(variableManager -> color)
                                    .fontSizeProvider(variableManager -> 16)
                                    .boldProvider(variableManager -> false)
                                    .italicProvider(variableManager -> false)
                                    .underlineProvider(variableManager -> false)
                                    .strikeThroughProvider(variableManager -> false)
                                    .iconURLProvider(variableManager -> "") //$NON-NLS-1$
                                    .build();
        // @formatter:on
    }

    public EdgeStyle createEdgeStyle(String color) {
        // @formatter:off
        return EdgeStyle.newEdgeStyle()
                        .color(color)
                        .lineStyle(LineStyle.Solid)
                        .size(1)
                        .sourceArrow(ArrowStyle.None)
                        .targetArrow(ArrowStyle.InputArrow)
                        .build();
        // @formatter:on
    }

    public INodeStyle createNodeStyle(String color) {
        // @formatter:off
        return RectangularNodeStyle.newRectangularNodeStyle()
                                   .color(color)
                                   .borderColor("rgb(0, 0, 0)") //$NON-NLS-1$
                                   .borderSize(1)
                                   .borderStyle(LineStyle.Solid)
                                   .build();
        // @formatter:on
    }
}
