/***********************************************************************************************
 * Copyright (c) 2019, 2020 Obeo. All Rights Reserved.
 * This software and the attached documentation are the exclusive ownership
 * of its authors and was conceded to the profit of Obeo SARL.
 * This software and the attached documentation are protected under the rights
 * of intellectual ownership, including the section "Titre II  Droits des auteurs (Articles L121-1 L123-12)"
 * By installing this software, you acknowledge being aware of this rights and
 * accept them, and as a consequence you must:
 * - be in possession of a valid license of use conceded by Obeo only.
 * - agree that you have read, understood, and will comply with the license terms and conditions.
 * - agree not to do anything that could conflict with intellectual ownership owned by Obeo or its beneficiaries
 * or the authors of this software
 *
 * Should you not agree with these terms, you must stop to use this software and give it back to its legitimate owner.
 ***********************************************************************************************/
package org.eclipse.sirius.web.freediagram.styles;

import org.eclipse.sirius.web.diagrams.ArrowStyle;
import org.eclipse.sirius.web.diagrams.LineStyle;

/**
 *
 * Style constants that can be used as default value in a free diagram description.
 *
 * @author sdrapeau
 *
 */
public final class DefaultStyles {

    public static final String WHITE_COLOR = "#FFFFFF"; //$NON-NLS-1$

    public static final String BLACK_COLOR = "#000000"; //$NON-NLS-1$

    public static final int NODE_BORDER_SIZE = 1;

    public static final LineStyle NODE_BORDER_STYLE = LineStyle.Solid;

    public static final String NODE_BACKGROUND_COLOR = WHITE_COLOR;

    public static final String NODE_BORDER_COLOR = BLACK_COLOR;

    public static final String LABEL_FONT_COLOR = BLACK_COLOR;

    public static final int LABEL_FONT_SIZE = 12;

    public static final boolean LABEL_BOLD = false;

    public static final boolean LABEL_ITALIC = false;

    public static final boolean LABEL_UNDERLINE = false;

    public static final int EDGE_SIZE = 1;

    public static final String EDGE_COLOR = BLACK_COLOR;

    public static final LineStyle EDGE_LINE_STYLE = LineStyle.Solid;

    public static final ArrowStyle EDGE_SOURCE_ARROW = ArrowStyle.None;

    public static final ArrowStyle EDGE_TARGET_ARROW = ArrowStyle.InputArrow;

    private DefaultStyles() {
        // Nada
    }

}
