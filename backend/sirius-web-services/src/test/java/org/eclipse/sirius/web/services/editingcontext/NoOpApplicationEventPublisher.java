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
package org.eclipse.sirius.web.services.editingcontext;

import org.springframework.context.ApplicationEventPublisher;

/**
 * Implementation of the application event publisher which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpApplicationEventPublisher implements ApplicationEventPublisher {

    @Override
    public void publishEvent(Object event) {
    }

}
