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
package org.eclipse.sirius.components.widget.reference.dto;

import java.util.UUID;

import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;
import org.eclipse.sirius.components.forms.ClickEventKind;

/**
 * Input object for the mutation to handle the click of a reference value.
 *
 * @author frouene
 */
public record ClickReferenceValueInput(UUID id, String editingContextId, String representationId, String referenceWidgetId, String referenceValueId,
                                       ClickEventKind clickEventKind) implements IFormInput {

}
