/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.widget.reference.dto;

import java.util.UUID;

import org.eclipse.sirius.components.core.api.IInput;

/**
 * Input object for the query to retrieve the child creation descriptions.
 *
 * @author frouene
 */
public record ReferenceWidgetChildCreationDescriptionsInput(UUID id, String editingContextId, String containerId, String referenceKind, String descriptionId) implements IInput {

}
