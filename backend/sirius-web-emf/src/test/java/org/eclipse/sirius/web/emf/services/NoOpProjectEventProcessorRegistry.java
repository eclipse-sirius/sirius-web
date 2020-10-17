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
package org.eclipse.sirius.web.emf.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.collaborative.api.services.IProjectEventProcessor;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventProcessorRegistry;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.dto.IInput;
import org.eclipse.sirius.web.services.api.dto.IPayload;

/**
 * Implementation of the project event processor registry which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpProjectEventProcessorRegistry implements IProjectEventProcessorRegistry {

    @Override
    public List<IProjectEventProcessor> getProjectEventProcessors() {
        return new ArrayList<>();
    }

    @Override
    public Optional<IPayload> dispatchEvent(UUID projectId, IInput input, Context context) {
        return Optional.empty();
    }

    @Override
    public Optional<IProjectEventProcessor> getOrCreateProjectEventProcessor(UUID projectId) {
        return Optional.empty();
    }

    @Override
    public void dispose(UUID projectId) {
    }

}
