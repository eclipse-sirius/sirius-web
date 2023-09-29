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
package org.eclipse.sirius.components.collaborative.widget.reference;

import java.util.Objects;

import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
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

    private final IFormIdProvider formIdProvider;

    public ReferenceWidgetDescriptionConverterProvider(ComposedAdapterFactory composedAdapterFactory, IEMFKindService emfKindService, IFormIdProvider formIdProvider) {
        this.composedAdapterFactory = composedAdapterFactory;
        this.formIdProvider = Objects.requireNonNull(formIdProvider);
    }

    @Override
    public Switch<AbstractWidgetDescription> getWidgetConverter(AQLInterpreter interpreter, IEditService editService, IObjectService objectService, IFeedbackMessageService feedbackMessageService) {
        return new ReferenceWidgetDescriptionConverterSwitch(interpreter, objectService, editService, feedbackMessageService, this.composedAdapterFactory, this.formIdProvider);
    }
}
