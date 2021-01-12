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
package org.eclipse.sirius.web.spring.collaborative.forms;

import org.eclipse.sirius.web.collaborative.forms.api.IWidgetSubscriptionManager;
import org.eclipse.sirius.web.collaborative.forms.api.dto.UpdateWidgetFocusInput;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.services.api.Context;

import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;

/**
 * The widget subscription manager.
 *
 * @author sbegaudeau
 */
public class WidgetSubscriptionManager implements IWidgetSubscriptionManager {

    private final DirectProcessor<IPayload> flux = DirectProcessor.create();

    @Override
    public void handle(UpdateWidgetFocusInput input, Context context) {
        // Do nothing for now
    }

    @Override
    public Flux<IPayload> getFlux() {
        return this.flux;
    }

    @Override
    public void dispose() {
        this.flux.sink().complete();
    }
}
