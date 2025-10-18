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
package org.eclipse.sirius.web.services.browser;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.browser.api.IModelBrowserTreeDescriptionIdProviderDelegate;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.springframework.stereotype.Service;

/**
 * Customizes the tree description to use for a specific description id to use the full Explorer tree description.
 *
 * @author pcdavid
 */
@Service
public class CustomModelBrowserTreeDescriptionIdProviderDelegate implements IModelBrowserTreeDescriptionIdProviderDelegate {
    public static final String CUSTOM_REFERENCE_WIDGET = "custom.reference.widget";

    private final IURLParser urlParser;

    public CustomModelBrowserTreeDescriptionIdProviderDelegate(IURLParser urlParser) {
        this.urlParser = Objects.requireNonNull(urlParser);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String modelBrowserId) {
        return this.hasDescriptionId(modelBrowserId, CUSTOM_REFERENCE_WIDGET);
    }

    @Override
    public String getModelBrowserTreeDescriptionId(IEditingContext editingContext, String modelBrowserId) {
        if (this.hasDescriptionId(modelBrowserId, CUSTOM_REFERENCE_WIDGET)) {
            return CustomTreeDescriptionProvider.CUSTOM_CONTAINER_DESCRIPTION_ID;
        } else {
            return null;
        }
    }

    private boolean hasDescriptionId(String modelBrowserId, String descriptionId) {
        Map<String, List<String>> parameters = this.urlParser.getParameterValues(modelBrowserId);
        String actualDescriptionId = parameters.get("descriptionId").get(0);
        return CUSTOM_REFERENCE_WIDGET.equals(actualDescriptionId);
    }

}
