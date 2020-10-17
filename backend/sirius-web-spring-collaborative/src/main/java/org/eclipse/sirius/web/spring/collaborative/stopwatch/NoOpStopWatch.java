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

import org.eclipse.sirius.web.services.api.monitoring.IStopWatch;

/**
 * Implementation of the stop watch which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpStopWatch implements IStopWatch {

    @Override
    public void start(String taskName) {
        // do nothing
    }

    @Override
    public void stop() {
        // do nothing
    }

    @Override
    public String prettyPrint() {
        return "StopWatch inactive"; //$NON-NLS-1$
    }

}
