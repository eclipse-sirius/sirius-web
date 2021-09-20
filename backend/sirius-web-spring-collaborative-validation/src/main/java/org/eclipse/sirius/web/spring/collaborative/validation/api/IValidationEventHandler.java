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

import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.validation.Validation;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Interface of all the validation event handlers.
 *
 * @author gcoutable
 */
public interface IValidationEventHandler {
    boolean canHandle(IValidationInput validationInput);

    void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, Validation validation, IValidationInput validationInput);
}
