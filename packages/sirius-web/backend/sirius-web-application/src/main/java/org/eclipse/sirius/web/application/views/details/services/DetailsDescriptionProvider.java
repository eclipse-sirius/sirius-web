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
package org.eclipse.sirius.web.application.views.details.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.forms.PropertiesEventProcessorFactory;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.emf.forms.api.IEMFFormDescriptionProvider;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.springframework.stereotype.Service;

/**
 * Provides the Details form description to make sure it is registered in the editing context.
 *
 * @author pcdavid
 */
@Service
public class DetailsDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    private final IEMFFormDescriptionProvider formDescriptionProvider;

    public DetailsDescriptionProvider(IEMFFormDescriptionProvider formDescriptionProvider) {
        this.formDescriptionProvider = Objects.requireNonNull(formDescriptionProvider);
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        var detailsDescription  = FormDescription.newFormDescription(this.formDescriptionProvider.getFormDescription())
                .id(PropertiesEventProcessorFactory.DETAILS_VIEW_ID)
                .build();
        return List.of(detailsDescription);
    }
}
