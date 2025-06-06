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
package org.eclipse.sirius.web.application.document.services.api;

import org.eclipse.emf.ecore.resource.ResourceSet;

import java.io.InputStream;
import java.util.Optional;

/**
 * Used to compute a sanitized content for a given resource.
 *
 * @author sbegaudeau
 */
public interface IDocumentSanitizedJsonContentProvider {

    Optional<SanitizedResult> getContent(ResourceSet resourceSet, String name, InputStream inputStream, boolean applyMigrationParticipants);
}
