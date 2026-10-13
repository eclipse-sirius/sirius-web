/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.widget.reference.api;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;

/**
 * Handles set operations on reference widgets.
 *
 * @author tgiraudet
 * @since 2026.11.0
 */
public interface IReferenceWidgetSetElementHandler {

    boolean canHandle(FormDescription formDescription);

    IStatus set(IEditingContext editingContext, FormDescription formDescription, ReferenceWidget referenceWidget, String newValueId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author tgiraudet
     */
    class NoOp implements IReferenceWidgetSetElementHandler {

        @Override
        public boolean canHandle(FormDescription formDescription) {
            return false;
        }

        @Override
        public IStatus set(IEditingContext editingContext, FormDescription formDescription, ReferenceWidget referenceWidget, String newValueId) {
            return new Failure("");
        }
    }
}
