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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;

/**
 * Object to represent a representation description type for the Views Explorer view.
 *
 * @author theogiraudet
 */
public record RepresentationDescriptionType(String id, IRepresentationDescription descriptions, List<RepresentationMetadata> representationsMetadata) {

    public RepresentationDescriptionType {
        Objects.requireNonNull(id);
        Objects.requireNonNull(descriptions);
        Objects.requireNonNull(representationsMetadata);
    }
}
