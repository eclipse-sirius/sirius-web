/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.web.application.representation.services;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IIdentityServiceDelegate;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.springframework.stereotype.Service;

/**
 * The identity service delegate used to compute the identity of a representation metadata.
 *
 * @author gcoutable
 */
@Service
public class RepresentationMetadataIdentityServiceDelegate implements IIdentityServiceDelegate {

    @Override
    public boolean canHandle(Object object) {
        return Optional.ofNullable(object)
                .filter(RepresentationMetadata.class::isInstance)
                .isPresent();
    }

    @Override
    public String getId(Object object) {
        return Optional.of(object)
                .filter(RepresentationMetadata.class::isInstance)
                .map(RepresentationMetadata.class::cast)
                .map(RepresentationMetadata::getRepresentationMetadataId)
                .map(UUID::toString)
                .orElse(null);
    }

    @Override
    public String getKind(Object object) {
        return Optional.of(object)
                .filter(RepresentationMetadata.class::isInstance)
                .map(RepresentationMetadata.class::cast)
                .map(RepresentationMetadata::getKind)
                .orElse(null);
    }
}
