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
package org.eclipse.sirius.web.application.project.services.api;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.application.project.services.RepresentationImportData;

import java.util.Map;

/**
 * Service used to update imported representation.
 *
 * @author mcharfadi
 */
public interface IRepresentationImporterUpdateService {

    boolean canHandle(String editingContextId, RepresentationImportData representationImportData);

    void handle(Map<String, String> semanticElementsIdMappings, ICause cause, String editingContextId, String newRepresentationId, RepresentationImportData representationImportData);
}
