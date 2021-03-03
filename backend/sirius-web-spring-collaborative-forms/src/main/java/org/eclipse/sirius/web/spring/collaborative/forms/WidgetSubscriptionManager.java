/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.forms;

import java.text.MessageFormat;

import org.eclipse.sirius.web.collaborative.forms.api.IWidgetSubscriptionManager;
import org.eclipse.sirius.web.collaborative.forms.api.dto.UpdateWidgetFocusInput;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;

/**
 * The widget subscription manager.
 *
 * @author sbegaudeau
 */
public class WidgetSubscriptionManager implements IWidgetSubscriptionManager {

    private final Logger logger = LoggerFactory.getLogger(WidgetSubscriptionManager.class);

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    @Override
    public void handle(UpdateWidgetFocusInput input) {
        // Do nothing for now
    }

    @Override
    public Flux<IPayload> getFlux(IInput input) {
        return this.sink.asFlux();
    }

    @Override
    public void dispose() {
        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {0}"; //$NON-NLS-1$
            this.logger.warn(MessageFormat.format(pattern, emitResult));
        }
    }
}
