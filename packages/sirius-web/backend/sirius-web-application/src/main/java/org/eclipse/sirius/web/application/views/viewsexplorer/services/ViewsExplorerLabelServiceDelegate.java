/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.views.viewsexplorer.services;

import java.util.List;

import org.eclipse.sirius.components.core.api.ILabelServiceDelegate;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragment;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragmentStyle;
import org.eclipse.sirius.web.application.views.viewsexplorer.domain.RepresentationDescriptionType;
import org.eclipse.sirius.web.application.views.viewsexplorer.domain.RepresentationKind;
import org.springframework.stereotype.Service;

/**
 * Label service delegate for "views explorer" view objects.
 *
 * @author tgiraudet
 */
@Service
public class ViewsExplorerLabelServiceDelegate implements ILabelServiceDelegate {

    @Override
    public boolean canHandle(Object object) {
        return object instanceof RepresentationKind || object instanceof RepresentationDescriptionType;
    }

    @Override
    public StyledString getStyledLabel(Object object) {
        var result = StyledString.of("");
        if (object instanceof RepresentationKind kind) {
            String name = kind.name();
            String size = String.valueOf(kind.representationDescriptionTypes().stream().mapToLong(descType -> descType.representationsMetadata().size()).sum());
            result = getColoredLabel(name, size);
        } else if (object instanceof RepresentationDescriptionType descriptionType) {
            String name = descriptionType.descriptions().getLabel();
            String size = String.valueOf(descriptionType.representationsMetadata().size());
            result = getColoredLabel(name, size);
        }
        return result;
    }

    @Override
    public List<String> getImagePaths(Object object) {
        return List.of();
    }

    private StyledString getColoredLabel(String label, String size) {
        return new StyledString(List.of(
                new StyledStringFragment("%s (%s)".formatted(label.toUpperCase(), size), StyledStringFragmentStyle.newDefaultStyledStringFragmentStyle()
                        .foregroundColor("#261E588A")
                        .build())
        ));
    }
}
