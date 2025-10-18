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

import org.eclipse.sirius.components.collaborative.browser.api.IDefaultModelBrowserTreeDescriptionIdProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.springframework.stereotype.Service;

/**
 * Provides the default choice of which tree description should be used for the model browser of a given reference widget.
 * The default choice only depends on whether the requested browser is on containment or reference mode.
 *
 * @author pcdavid
 * @see DefaultModelBrowsersTreeDescriptionProvider
 */
@Service
public class DefaultModelBrowserTreeDescriptionIdProvider implements IDefaultModelBrowserTreeDescriptionIdProvider {
    @Override
    public String getModelBrowserTreeDescriptionId(IEditingContext editingContext, String modelBrowserId) {
        if (modelBrowserId.startsWith(DefaultModelBrowsersTreeDescriptionProvider.MODEL_BROWSER_CONTAINER_PREFIX)) {
            return DefaultModelBrowsersTreeDescriptionProvider.CONTAINER_DESCRIPTION_ID;
        } else {
            return DefaultModelBrowsersTreeDescriptionProvider.REFERENCE_DESCRIPTION_ID;
        }
    }

}
