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

package org.eclipse.sirius.components.formdescriptioneditors.widget.reference;

import org.eclipse.sirius.components.formdescriptioneditors.IFormElementDescriptionInitializer;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.widget.reference.ReferenceFactory;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Services initializing a reference widget description for the form description editors.
 *
 * @author tgiraudet
 */
@Service
public class ReferenceFormElementDescriptionInitializer implements IFormElementDescriptionInitializer {

    @Override
    public void initialize(FormElementDescription formElementDescription) {
        if (formElementDescription instanceof ReferenceWidgetDescription referenceWidgetDescription) {
            referenceWidgetDescription.setClearButton(ReferenceFactory.eINSTANCE.createReferenceWidgetClearButtonDescription());
            referenceWidgetDescription.setCreateButton(ReferenceFactory.eINSTANCE.createReferenceWidgetCreateButtonDescription());
        }
    }
}
