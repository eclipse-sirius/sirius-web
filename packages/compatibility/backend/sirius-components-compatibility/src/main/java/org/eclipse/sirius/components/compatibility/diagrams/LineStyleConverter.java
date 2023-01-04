/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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

import org.eclipse.sirius.components.diagrams.LineStyle;

/**
 * Used to convert Sirius line style to Sirius Web line style.
 *
 * @author wpiers
 */
public class LineStyleConverter {

    public LineStyle getStyle(org.eclipse.sirius.diagram.LineStyle siriusStyle) {
        LineStyle res = LineStyle.Solid;
        switch (siriusStyle) {
            case DASH_LITERAL:
                res = LineStyle.Dash;
                break;
            case DASH_DOT_LITERAL:
                res = LineStyle.Dash_Dot;
                break;
            case DOT_LITERAL:
                res = LineStyle.Dot;
                break;
            default:
                break;
        }
        return res;
    }

}
