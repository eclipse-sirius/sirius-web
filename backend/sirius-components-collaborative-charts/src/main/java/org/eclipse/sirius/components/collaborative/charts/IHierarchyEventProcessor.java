/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;

/**
 * Interface implemented by the hierarchy event processor.
 *
 * @author sbegaudeau
 */
public interface IHierarchyEventProcessor extends IRepresentationEventProcessor {
    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp extends IRepresentationEventProcessor.NoOp implements IHierarchyEventProcessor {

    }
}
