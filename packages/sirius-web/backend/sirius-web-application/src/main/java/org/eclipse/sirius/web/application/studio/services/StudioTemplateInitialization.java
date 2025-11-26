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
package org.eclipse.sirius.web.application.studio.services;

import java.util.UUID;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.events.ICause;

import jakarta.validation.constraints.NotNull;

/**
 * Used to indicate that semantic data have been updated because of a studio template.
 *
 * @author sbegaudeau
 */
public record StudioTemplateInitialization(
        @NotNull UUID id,
        @NotNull IEMFEditingContext editingContext,
        @NotNull Resource domainResource,
        @NotNull ICause causedBy) implements ICause {
}
