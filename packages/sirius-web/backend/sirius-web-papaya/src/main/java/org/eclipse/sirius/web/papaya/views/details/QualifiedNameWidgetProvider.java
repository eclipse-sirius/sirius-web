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
package org.eclipse.sirius.web.papaya.views.details;

import static org.eclipse.sirius.web.papaya.views.details.PapayaDetailsViewPageDescriptionProvider.QUALIFIED_NAME_COLOR;

import org.eclipse.sirius.components.view.builder.generated.form.FormBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.form.LabelDescription;
import org.springframework.stereotype.Service;

/**
 * Used to provide the qualified name widget.
 *
 * @author sbegaudeau
 */
@Service
public class QualifiedNameWidgetProvider {

    public LabelDescription getQualifiedNameWidget(IColorProvider colorProvider) {
        var qualifiedNameStyleDescription = new FormBuilders().newLabelDescriptionStyle()
                .color(colorProvider.getColor(QUALIFIED_NAME_COLOR))
                .build();

        return new FormBuilders().newLabelDescription()
                .name("Qualified Name")
                .labelExpression("Qualified Name")
                .valueExpression("aql:self.qualifiedName")
                .style(qualifiedNameStyleDescription)
                .build();
    }
}
