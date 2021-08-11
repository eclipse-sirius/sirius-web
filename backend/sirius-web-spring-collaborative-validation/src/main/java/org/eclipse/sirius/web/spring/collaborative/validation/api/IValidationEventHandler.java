/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.validation.api;

import org.eclipse.sirius.web.spring.collaborative.api.EventHandlerResponse;
import org.eclipse.sirius.web.validation.Validation;

/**
 * Interface of all the validation event handlers.
 *
 * @author gcoutable
 */
public interface IValidationEventHandler {
    boolean canHandle(IValidationInput validationInput);

    EventHandlerResponse handle(Validation validation, IValidationInput validationInput);
}
