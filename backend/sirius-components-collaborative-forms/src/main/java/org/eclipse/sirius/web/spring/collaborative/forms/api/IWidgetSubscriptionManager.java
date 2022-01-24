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
package org.eclipse.sirius.web.spring.collaborative.forms.api;

import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.spring.collaborative.forms.dto.UpdateWidgetFocusInput;

import reactor.core.publisher.Flux;

/**
 * Service used to manager the subscriptions of a widget.
 *
 * @author sbegaudeau
 */
public interface IWidgetSubscriptionManager {

    void handle(UpdateWidgetFocusInput input);

    Flux<IPayload> getFlux(IInput input);

    void dispose();

}
