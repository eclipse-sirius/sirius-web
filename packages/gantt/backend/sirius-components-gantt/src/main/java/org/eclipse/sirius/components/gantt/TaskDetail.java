/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import java.util.Objects;

/**
 * Detail of task concept.
 *
 * @author lfasani
 */
public record TaskDetail(String name, String description, long startDate, long endDate, int progress) {

    public TaskDetail {
        Objects.requireNonNull(name);
        Objects.requireNonNull(description);
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        Objects.requireNonNull(progress);
    }
}
