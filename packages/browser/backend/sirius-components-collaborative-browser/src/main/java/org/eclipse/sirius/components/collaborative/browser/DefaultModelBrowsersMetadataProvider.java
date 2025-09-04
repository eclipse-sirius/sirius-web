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
package org.eclipse.sirius.components.collaborative.browser;

import java.util.List;

import org.eclipse.sirius.components.collaborative.browser.api.IModelBrowserMetadataProvider;
import org.eclipse.sirius.components.collaborative.browser.api.ModelBrowserMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.springframework.stereotype.Service;

/**
 * Declares the default 'container' and 'reference' model browsers for all editing contexts.
 *
 * @author pcdavid
 */
@Service
public class DefaultModelBrowsersMetadataProvider implements IModelBrowserMetadataProvider {

    @Override
    public List<ModelBrowserMetadata> getModelBrowsersMetadata(IEditingContext editingContext) {
        return List.of(
                DefaultModelBrowsersTreeDescriptionProvider.CONTAINER_METADATA,
                DefaultModelBrowsersTreeDescriptionProvider.REFERENCE_METADATA
        );
    }

}
