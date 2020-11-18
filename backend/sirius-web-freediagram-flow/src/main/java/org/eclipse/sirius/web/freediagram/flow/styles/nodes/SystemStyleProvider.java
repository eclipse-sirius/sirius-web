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
package org.eclipse.sirius.web.freediagram.flow.styles.nodes;

import java.util.Objects;

import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.NodeType;
import org.eclipse.sirius.web.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.web.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.web.freediagram.services.FreeDiagramService;
import org.eclipse.sirius.web.freediagram.styles.DefaultStyles;
import org.eclipse.sirius.web.freediagram.styles.INodeStyleProvider;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Customize the flow.System Node/Label styles.
 *
 * @author hmarchadour
 */
public class SystemStyleProvider implements INodeStyleProvider {

    private final FreeDiagramService freeDiagramService;

    public SystemStyleProvider(FreeDiagramService freeDiagramService) {
        this.freeDiagramService = Objects.requireNonNull(freeDiagramService);
    }

    @Override
    public String getNodeType(VariableManager variableManager) {
        return NodeType.NODE_RECTANGLE;
    }

    @Override
    public INodeStyle getNodeStyle(VariableManager variableManager) {
        // @formatter:off
        return RectangularNodeStyle.newRectangularNodeStyle()
            .color("rgb(240, 240, 240)") //$NON-NLS-1$
            .borderColor("rgb(177, 188, 190)") //$NON-NLS-1$
            .borderSize(DefaultStyles.NODE_BORDER_SIZE)
            .borderStyle(DefaultStyles.NODE_BORDER_STYLE)
            .build();
        // @formatter:on
    }

    @Override
    public LabelStyleDescription getLabelStyleDescription() {
    // @formatter:off
       return LabelStyleDescription.newLabelStyleDescription()
               .colorProvider(vManager -> "rgb(0, 43, 60)") //$NON-NLS-1$
               .fontSizeProvider(vManager -> 10)
               .boldProvider(vManager -> true)
               .italicProvider(vManager -> DefaultStyles.LABEL_ITALIC)
               .underlineProvider(vManager -> DefaultStyles.LABEL_UNDERLINE)
               .strikeThroughProvider(vManager -> false)
               .iconURLProvider(this.freeDiagramService::getIcon)
               .build();
       // @formatter:on
    }

}
