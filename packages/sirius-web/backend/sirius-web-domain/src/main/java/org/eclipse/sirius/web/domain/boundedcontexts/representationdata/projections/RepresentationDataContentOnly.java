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
package org.eclipse.sirius.web.domain.boundedcontexts.representationdata.projections;

/**
 * Projection used to retrieve only the content and its associated data from the representation data.
 *
 * @author sbegaudeau
 */
public record RepresentationDataContentOnly(
        String kind,
        String content,
        String lastMigrationPerformed,
        String migrationVersion) {
}
