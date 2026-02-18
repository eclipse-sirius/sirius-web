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

import org.eclipse.sirius.components.core.api.IIdentityServiceDelegate;
import org.eclipse.sirius.web.application.views.viewsexplorer.domain.RepresentationDescriptionType;
import org.eclipse.sirius.web.application.views.viewsexplorer.domain.RepresentationKind;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.springframework.stereotype.Service;

/**
 * The identity service delegate used to compute the identity of "views explorer" view objects.
 *
 * @author theogiraudet
 */
@Service
public class ViewsExplorerIdentityServiceDelegate implements IIdentityServiceDelegate {
    @Override
    public boolean canHandle(Object object) {
        return object instanceof RepresentationKind || object instanceof RepresentationDescriptionType;
    }

    @Override
    public String getId(Object object) {
        String result = null;
        if (object instanceof RepresentationKind kind) {
            result = kind.name();
        }
        if (object instanceof RepresentationDescriptionType type) {
            result = type.id();
        }
        if (object instanceof RepresentationMetadata metadata) {
            result = metadata.getRepresentationMetadataId().toString();
        }
        return result;
    }

    @Override
    public String getKind(Object object) {
        return "";
    }
}
