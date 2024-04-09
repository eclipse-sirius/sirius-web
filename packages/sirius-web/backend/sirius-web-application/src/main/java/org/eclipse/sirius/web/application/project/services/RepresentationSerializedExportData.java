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
package org.eclipse.sirius.web.application.project.services;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.UUID;

/**
 * Used to persist representation data in the exported project.
 *
 * @author jmallet
 */
public record RepresentationSerializedExportData(
        UUID id,
        UUID projectId,
        String descriptionId,
        String targetObjectId,
        String label,
        String kind,
        ObjectNode representation
) {
}
