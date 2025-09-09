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
package org.eclipse.sirius.web.application.project.dto;

import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;

import jakarta.validation.constraints.NotNull;

/**
 * Input used to init the content of a Project.
 *
 * @param id
 *         an Id
 * @param causedBy
 *         the source cause of this initialization
 * @param projectContent
 *         the project content used to initialize a project
 */
public record InitProjectContentInput(@NotNull UUID id, @NotNull ICause causedBy, @NotNull IProjectBinaryContent projectContent) implements ICause {

}
