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
package org.eclipse.sirius.web.application.document.services.api;

import java.util.Optional;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * Used to export a document using a specific media type.
 *
 * @author sbegaudeau
 */
public interface IDocumentExporter {
    boolean canHandle(Resource resource, String mediaType);

    Optional<byte[]> getBytes(Resource resource, String mediaType);
}
