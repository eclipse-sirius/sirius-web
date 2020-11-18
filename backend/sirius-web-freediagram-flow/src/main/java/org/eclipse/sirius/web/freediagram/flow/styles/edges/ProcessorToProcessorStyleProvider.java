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
package org.eclipse.sirius.web.freediagram.flow.styles.edges;

import org.eclipse.sirius.web.diagrams.EdgeStyle;
import org.eclipse.sirius.web.diagrams.LabelStyle;
import org.eclipse.sirius.web.freediagram.styles.DefaultStyles;
import org.eclipse.sirius.web.freediagram.styles.IEdgeStyleProvider;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Customize the flow.DataSource Edge/Label styles.
 *
 * @author hmarchadour
 *
 */
public class ProcessorToProcessorStyleProvider implements IEdgeStyleProvider {

    @Override
    public LabelStyle getLabelStyle(VariableManager variableManager) {
       // @formatter:off
       return LabelStyle.newLabelStyle()
           .color(DefaultStyles.LABEL_FONT_COLOR)
           .fontSize(8)
           .bold(DefaultStyles.LABEL_BOLD)
           .italic(DefaultStyles.LABEL_ITALIC)
           .strikeThrough(false)
           .underline(DefaultStyles.LABEL_UNDERLINE)
           .iconURL("") //$NON-NLS-1$
           .build();
       // @formatter:on
    }

    @Override
    public EdgeStyle getEdgeStyle(VariableManager variableManager) {
     // @formatter:off
        return EdgeStyle.newEdgeStyle()
                .size(DefaultStyles.EDGE_SIZE)
                .lineStyle(DefaultStyles.EDGE_LINE_STYLE)
                .sourceArrow(DefaultStyles.EDGE_SOURCE_ARROW)
                .targetArrow(DefaultStyles.EDGE_TARGET_ARROW)
                .color("rgb(177, 188, 190)") //$NON-NLS-1$
                .build();
        // @formatter:on
    }

}
