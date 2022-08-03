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
package org.eclipse.sirius.components.collaborative.diagrams.api;

/**
 * Attributes of the SVG that will be used to build the SVG.
 *
 * @author lfasani
 */
public enum SVGAttribute {
    WIDTH("width"), //$NON-NLS-1$
    HEIGHT("height"), //$NON-NLS-1$
    LABELWIDTH("labelWidth"), //$NON-NLS-1$
    LABELHEIGHT("labelHeight"), //$NON-NLS-1$
    COLOR("color"), //$NON-NLS-1$
    BORDERCOLOR("borderColor"), //$NON-NLS-1$
    BORDERSTYLE("borderStyle"), //$NON-NLS-1$
    BORDERSIZE("borderSize"); //$NON-NLS-1$

    private String label;

    SVGAttribute(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
