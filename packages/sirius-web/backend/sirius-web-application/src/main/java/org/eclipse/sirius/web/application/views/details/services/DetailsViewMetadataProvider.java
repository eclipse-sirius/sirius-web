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

import org.eclipse.sirius.components.collaborative.forms.PropertiesEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.forms.api.PropertiesConfiguration;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.forms.Form;
import org.springframework.stereotype.Service;

/**
 * Provides the metadata for the details view representation.
 *
 * @author pcdavid
 */
@Service
public class DetailsViewMetadataProvider implements IRepresentationMetadataProvider {

    @Override
    public boolean canHandle(String representationId) {
        return representationId != null && representationId.startsWith(PropertiesConfiguration.PROPERTIES_PREFIX);
    }

    @Override
    public RepresentationMetadata handle(String representationId) {
        return new RepresentationMetadata(representationId, Form.KIND, "Properties", PropertiesEventProcessorFactory.DETAILS_VIEW_ID);
    }

}
