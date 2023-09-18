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
package org.eclipse.sirius.components.widget.reference;

import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.view.emf.form.IWidgetConverterProvider;
import org.springframework.stereotype.Service;

/**
 * Provides the widget converter needed for the reference widget.
 *
 * @author pcdavid
 */
@Service
public class ReferenceWidgetDescriptionConverterProvider implements IWidgetConverterProvider {

    private final ComposedAdapterFactory composedAdapterFactory;

    private final IEMFKindService emfKindService;

    public ReferenceWidgetDescriptionConverterProvider(ComposedAdapterFactory composedAdapterFactory, IEMFKindService emfKindService) {
        this.composedAdapterFactory = composedAdapterFactory;
        this.emfKindService = emfKindService;
    }

    @Override
    public Switch<AbstractWidgetDescription> getWidgetConverter(AQLInterpreter interpreter, IEditService editService, IObjectService objectService, IFeedbackMessageService feedbackMessageService) {
        return new ReferenceWidgetDescriptionConverterSwitch(interpreter, objectService, editService, this.emfKindService, feedbackMessageService, this.composedAdapterFactory);
    }
}
