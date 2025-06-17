/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.form;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.springframework.stereotype.Service;

/**
 * Implementation of IFormIdProvider.
 *
 * @author pcdavid
 */
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class FormIdProvider implements IFormIdProvider {

    private final IIdentityService identityService;

    public FormIdProvider(IIdentityService identityService) {
        this.identityService = Objects.requireNonNull(identityService);
    }

    @Override
    public String getId(FormDescription formDescription) {
        String sourceId = this.getSourceIdFromElementDescription(formDescription);
        String sourceElementId = this.identityService.getId(formDescription);
        return FORM_DESCRIPTION_KIND + "&" + SOURCE_KIND + "=" + VIEW_SOURCE_KIND + "&" + SOURCE_ID + "=" + sourceId + "&" + SOURCE_ELEMENT_ID + "=" + sourceElementId;
    }
    @Override
    public String getFormElementDescriptionId(EObject widgetDescription) {
        String sourceId = this.getSourceIdFromElementDescription(widgetDescription);
        String sourceElementId = this.identityService.getId(widgetDescription);
        String kind = widgetDescription.eClass().getName();
        return FORM_ELEMENT_DESCRIPTION_PREFIX + "?" + KIND + "=" + kind + "&" +  SOURCE_KIND + "=" +  VIEW_SOURCE_KIND + "&" + SOURCE_ID + "=" + sourceId + "&" + SOURCE_ELEMENT_ID + "=" + sourceElementId;
    }

    private String getSourceIdFromElementDescription(EObject elementDescription) {
        return elementDescription.eResource().getURI().toString().split("///")[1];
    }

}
