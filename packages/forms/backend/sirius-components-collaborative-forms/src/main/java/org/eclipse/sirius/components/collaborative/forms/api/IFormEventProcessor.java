/*******************************************************************************
 * Copyright (c) 2019, 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms.api;

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.forms.FormContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.forms.Form;

/**
 * Interface implemented by the form event processor.
 *
 * @author sbegaudeau
 */
public interface IFormEventProcessor extends IRepresentationEventProcessor {

    FormContext getFormContext();

    /**
     * Used to update the state of the representation event processor.
     *
     * @param cause The cause which has triggered the update
     * @param form The new version of the representation
     *
     * @technical-debt This API should not be considered stable for the moment, it is still being evaluated against the
     * various use cases of our event processors
     * @since v2026.9.0
     */
    void update(ICause cause, Form form);
}
