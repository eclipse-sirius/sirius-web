/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.forms.PropertiesEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.forms.api.IPropertiesDefaultDescriptionProvider;
import org.eclipse.sirius.components.emf.forms.api.IEMFFormDescriptionProvider;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.springframework.stereotype.Service;

/**
 * Used to provide the default description of the details view.
 *
 * @author sbegaudeau
 */
@Service
public class DetailsViewDescriptionProvider implements IPropertiesDefaultDescriptionProvider {

    private final IEMFFormDescriptionProvider formDescriptionProvider;

    public DetailsViewDescriptionProvider(IEMFFormDescriptionProvider formDescriptionProvider) {
        this.formDescriptionProvider = Objects.requireNonNull(formDescriptionProvider);
    }

    @Override
    public FormDescription getFormDescription() {
        return FormDescription.newFormDescription(this.formDescriptionProvider.getFormDescription())
                .id(PropertiesEventProcessorFactory.DETAILS_VIEW_ID)
                .build();
    }
}
