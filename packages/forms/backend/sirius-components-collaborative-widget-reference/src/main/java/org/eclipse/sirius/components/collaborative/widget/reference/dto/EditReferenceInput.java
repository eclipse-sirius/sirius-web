/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;

/**
 * Input object for the mutation to edit the content of a multi-valued reference.
 *
 * @author pcdavid
 */
public record EditReferenceInput(UUID id, String editingContextId, String representationId, String referenceWidgetId, List<String> newValueIds) implements IFormInput {

}
