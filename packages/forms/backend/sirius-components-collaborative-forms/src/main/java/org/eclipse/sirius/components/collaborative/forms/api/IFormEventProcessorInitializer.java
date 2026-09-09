/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms.api;

import java.util.Optional;

import org.eclipse.sirius.components.collaborative.forms.FormContext;
import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * Used to initialize the form representation for an event processor.
 *
 * @author sbegaudeau
 */
public interface IFormEventProcessorInitializer {

    Optional<FormContext> getRefreshedRepresentation(IEditingContext editingContext, String representationId);
}
