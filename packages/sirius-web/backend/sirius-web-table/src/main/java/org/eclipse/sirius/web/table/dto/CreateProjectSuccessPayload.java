/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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

package org.eclipse.sirius.web.table.dto;

import jakarta.validation.constraints.NotNull;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.project.dto.ProjectDTO;

import java.util.UUID;

/**
 * The input used to create projects.
 *
 * @author mcharfadi
 */
public record CreateProjectSuccessPayload(@NotNull UUID id, @NotNull ProjectDTO project) implements IPayload {
}
