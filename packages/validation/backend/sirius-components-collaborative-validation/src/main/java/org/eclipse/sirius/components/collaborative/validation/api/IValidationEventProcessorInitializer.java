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
package org.eclipse.sirius.components.collaborative.validation.api;

import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.validation.Validation;

/**
 * Used to perform the initial refresh of the validation representation for its event processor.
 *
 * @author sbegaudeau
 * @since v2026.9.0
 */
public interface IValidationEventProcessorInitializer {

    Optional<Validation> getRefreshedRepresentation(IEditingContext editingContext, String representationId);
}
