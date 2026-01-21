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
package org.eclipse.sirius.web.application.project.services;

/**
 * Used to get representation metadata from the manifest of the exported project.
 *
 * @author tgiraudet
 */
public record RepresentationImportMetadata(
        String descriptionUri,
        String targetObjectUri,
        String lastMigrationPerformed,
        String migrationVersion
) {
}
