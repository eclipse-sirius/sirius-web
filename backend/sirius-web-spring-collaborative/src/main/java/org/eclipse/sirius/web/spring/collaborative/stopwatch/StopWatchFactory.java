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
package org.eclipse.sirius.web.spring.collaborative.stopwatch;

import java.util.Arrays;
import java.util.Objects;

import org.eclipse.sirius.web.services.api.monitoring.IStopWatch;
import org.eclipse.sirius.web.services.api.monitoring.IStopWatchFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Used to create stop watches independently from Spring. It will return a stop watch which does nothing if we are not
 * in development mode.
 *
 * @author sbegaudeau
 */
@Service
public class StopWatchFactory implements IStopWatchFactory {

    private final Environment environment;

    public StopWatchFactory(Environment environment) {
        this.environment = Objects.requireNonNull(environment);
    }

    @Override
    public IStopWatch createStopWatch(String id) {
        if (Arrays.asList(this.environment.getActiveProfiles()).contains("dev")) { //$NON-NLS-1$
            return new SpringStopWatch(id);
        }
        return new NoOpStopWatch();
    }

}
