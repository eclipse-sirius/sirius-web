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
package org.eclipse.sirius.components.collaborative.portals;

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;

/**
 * Interface implemented by the portal event processor.
 *
 * @author pcdavid
 */
public interface IPortalEventProcessor extends IRepresentationEventProcessor {

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp extends IRepresentationEventProcessor.NoOp implements IPortalEventProcessor {

    }
}
