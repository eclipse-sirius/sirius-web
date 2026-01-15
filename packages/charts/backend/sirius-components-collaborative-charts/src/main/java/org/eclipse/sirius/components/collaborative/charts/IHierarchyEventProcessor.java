/*******************************************************************************
 * Copyright (c) 2022, 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.charts;

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.core.api.IInput;

/**
 * Interface implemented by the hierarchy event processor.
 *
 * @author sbegaudeau
 */
public interface IHierarchyEventProcessor extends IRepresentationEventProcessor {

    /**
     * Used to update the content of the representation event processor.
     *
     * @param input The input which has caused the update
     * @param hierarchy The new version of the representation
     *
     * @technical-debt This API should not be considered stable for the moment, it is still being evaluated against the
     * various use cases of our event processors
     */
    void update(IInput input, Hierarchy hierarchy);
}
