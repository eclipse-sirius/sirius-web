/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.forms.api;

import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Interface of all the form event handlers.
 *
 * @author sbegaudeau
 */
public interface IFormEventHandler {
    boolean canHandle(IFormInput formInput);

    void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, Form form, IFormInput formInput);
}
