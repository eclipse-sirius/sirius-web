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

import org.eclipse.sirius.web.diagrams.ArrowStyle;

/**
 * Used to convert sirius arrow styles.
 *
 * @author nvannier
 *
 */
public class ArrowStyleConverter {

    public ArrowStyle getStyle(org.eclipse.sirius.diagram.EdgeArrows siriusStyle) {
        ArrowStyle convertedStyle;
        switch (siriusStyle) {
        case NO_DECORATION_LITERAL:
            convertedStyle = ArrowStyle.None;
            break;
        case OUTPUT_ARROW_LITERAL:
            convertedStyle = ArrowStyle.OutputArrow;
            break;
        case INPUT_ARROW_LITERAL:
            convertedStyle = ArrowStyle.InputArrow;
            break;
        case OUTPUT_CLOSED_ARROW_LITERAL:
            convertedStyle = ArrowStyle.OutputClosedArrow;
            break;
        case INPUT_CLOSED_ARROW_LITERAL:
            convertedStyle = ArrowStyle.InputClosedArrow;
            break;
        case OUTPUT_FILL_CLOSED_ARROW_LITERAL:
            convertedStyle = ArrowStyle.OutputFillClosedArrow;
            break;
        case INPUT_FILL_CLOSED_ARROW_LITERAL:
            convertedStyle = ArrowStyle.InputFillClosedArrow;
            break;
        case DIAMOND_LITERAL:
            convertedStyle = ArrowStyle.Diamond;
            break;
        case FILL_DIAMOND_LITERAL:
            convertedStyle = ArrowStyle.FillDiamond;
            break;
        case INPUT_ARROW_WITH_DIAMOND_LITERAL:
            convertedStyle = ArrowStyle.InputArrowWithDiamond;
            break;
        case INPUT_ARROW_WITH_FILL_DIAMOND_LITERAL:
            convertedStyle = ArrowStyle.InputArrowWithFillDiamond;
            break;
        default:
            convertedStyle = ArrowStyle.None;
            break;
        }
        return convertedStyle;
    }

}
