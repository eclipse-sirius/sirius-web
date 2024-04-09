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
package org.eclipse.sirius.web.application.document.dto;

import java.util.UUID;

import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.graphql.api.UploadFile;

import jakarta.validation.constraints.NotNull;

/**
 * The input object for the upload document mutation.
 *
 * @author hmarchadour
 */
public record UploadDocumentInput(@NotNull UUID id, @NotNull String editingContextId, @NotNull UploadFile file) implements IInput {
}
