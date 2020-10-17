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

import java.text.MessageFormat;

import org.eclipse.sirius.web.services.api.monitoring.IStopWatch;
import org.springframework.util.StopWatch;

/**
 * Spring based implementation of the stop watch.
 *
 * @author sbegaudeau
 */
public class SpringStopWatch implements IStopWatch {

    private StopWatch delegate;

    public SpringStopWatch(String id) {
        this.delegate = new StopWatch(id) {
            @Override
            public String shortSummary() {
                return MessageFormat.format("StopWatch ''{0}'': running time = {1} ms", this.getId(), this.getTotalTimeMillis()); //$NON-NLS-1$
            }
        };
    }

    @Override
    public void start(String taskName) {
        this.delegate.start(taskName);
    }

    @Override
    public void stop() {
        this.delegate.stop();
    }

    @Override
    public String prettyPrint() {
        return this.delegate.prettyPrint();
    }

}
