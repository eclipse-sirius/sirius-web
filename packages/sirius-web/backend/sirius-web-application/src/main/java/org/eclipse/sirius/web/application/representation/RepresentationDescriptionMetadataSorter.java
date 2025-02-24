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
package org.eclipse.sirius.web.application.representation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.collaborative.api.RepresentationDescriptionMetadata;
import org.eclipse.sirius.components.emf.services.api.IRepresentationDescriptionMetadataSorter;
import org.eclipse.sirius.web.application.portal.services.PortalDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * Make sure the generic Portal representation is always shown after any other more specific representation.
 *
 * @author pcdavid
 */
@Service
public class RepresentationDescriptionMetadataSorter implements IRepresentationDescriptionMetadataSorter {

    @Override
    public List<RepresentationDescriptionMetadata> sort(List<RepresentationDescriptionMetadata> representationDescriptions) {
        ArrayList<RepresentationDescriptionMetadata> result = new ArrayList<>(representationDescriptions);
        var optionalPortalDescription = result.stream().filter(representationDescriptionMetadata -> representationDescriptionMetadata.getId().equals(PortalDescriptionProvider.DESCRIPTION_ID)).findFirst();
        if (optionalPortalDescription.isPresent()) {
            result.remove(optionalPortalDescription.get());
            result.add(optionalPortalDescription.get());
        }
        return result;
    }

}
