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
package org.eclipse.sirius.web.application.document.dto;

import jakarta.validation.constraints.NotNull;
import org.eclipse.sirius.components.core.api.IPayload;

import java.util.Map;
import java.util.UUID;

/**
 * The payload of the upload document mutation.
 *
 * @author sbegaudeau
 *
 * @technical-debt The record component idMapping is not exposed to the frontend, yet it exists because the project
 * import mechanism is relying on the editing context event processor and thus the upload document event handler.
 * We should probably update the way the import mechanism is working.
 */
public record UploadDocumentSuccessPayload(@NotNull UUID id, @NotNull DocumentDTO document, String report, Map<String, String> idMapping) implements IPayload {
}
