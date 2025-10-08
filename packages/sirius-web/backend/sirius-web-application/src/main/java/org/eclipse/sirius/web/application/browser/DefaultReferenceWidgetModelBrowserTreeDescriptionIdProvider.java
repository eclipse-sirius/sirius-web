/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.browser;

import java.util.Objects;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.view.emf.form.api.IViewFormDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.widget.reference.api.IDefaultReferenceWidgetModelBrowserTreeDescriptionIdProvider;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Provides the default choice of which tree description should be used for the model browser of a given reference widget.
 * The default choice only depends on whether the reference is a containment reference of not.
 *
 * @author pcdavid
 * @see DefaultModelBrowsersTreeDescriptionProvider
 */
@Service
public class DefaultReferenceWidgetModelBrowserTreeDescriptionIdProvider implements IDefaultReferenceWidgetModelBrowserTreeDescriptionIdProvider {

    private final IViewFormDescriptionSearchService viewFormDescriptionSearchService;

    public DefaultReferenceWidgetModelBrowserTreeDescriptionIdProvider(IViewFormDescriptionSearchService viewFormDescriptionSearchService) {
        this.viewFormDescriptionSearchService = Objects.requireNonNull(viewFormDescriptionSearchService);
    }

    @Override
    public String getModelBrowserTreeDescriptionId(IEditingContext editingContext, String referenceWidgetDescriptionId, boolean isContainment) {
        var result = DefaultModelBrowsersTreeDescriptionProvider.REFERENCE_DESCRIPTION_ID;
        var optionalReferenceWidgetDescription = this.viewFormDescriptionSearchService.findFormElementDescriptionById(editingContext, referenceWidgetDescriptionId);
        if (optionalReferenceWidgetDescription.isPresent() && optionalReferenceWidgetDescription.get() instanceof ReferenceWidgetDescription && isContainment) {
            result = DefaultModelBrowsersTreeDescriptionProvider.CONTAINER_DESCRIPTION_ID;
        }
        return result;
    }

}
