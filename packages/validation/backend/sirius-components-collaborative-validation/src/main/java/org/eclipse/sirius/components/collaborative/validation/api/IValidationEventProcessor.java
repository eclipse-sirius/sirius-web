/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.validation.api;

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.validation.Validation;

/**
 * Interface implemented by the validation event processor.
 *
 * @author gcoutable
 */
public interface IValidationEventProcessor extends IRepresentationEventProcessor {

    /**
     * Used to update the state of the representation event processor.
     *
     * @param cause The cause which has triggered the update
     * @param validation The new version of the representation
     *
     * @technical-debt This API should not be considered stable for the moment, it is still being evaluated against the
     * various use cases of our event processors
     * @since v2026.9.0
     */
    void update(ICause cause, Validation validation);
}
