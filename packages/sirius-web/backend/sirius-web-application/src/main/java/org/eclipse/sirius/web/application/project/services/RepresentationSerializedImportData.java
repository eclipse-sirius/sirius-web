/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.project.services;

import org.eclipse.sirius.components.representations.IRepresentation;

import java.util.UUID;

/**
 * Used to get representation data from the exported project.
 *
 * @author jmallet
 */
public record RepresentationSerializedImportData(
        UUID id,
        String projectId,
        String descriptionId,
        String targetObjectId,
        String label,
        String kind,
        IRepresentation representation
) {
}
