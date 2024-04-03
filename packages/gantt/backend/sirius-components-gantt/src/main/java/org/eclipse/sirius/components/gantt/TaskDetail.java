/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.gantt;

import java.time.Instant;
import java.util.Objects;

/**
 * Detail of task concept.
 *
 * @author lfasani
 */
public record TaskDetail(String name, String description, Instant startTime, Instant endTime, int progress, boolean computeStartEndDynamically, boolean collapsed) {

    public TaskDetail {
        Objects.requireNonNull(name);
        Objects.requireNonNull(description);
    }
}
