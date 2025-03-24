/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.widget.reference;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.IWidgetConverterProvider;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.springframework.stereotype.Service;

/**
 * Provides the widget converter needed for the reference widget.
 *
 * @author pcdavid
 */
@Service
public class ReferenceWidgetDescriptionConverterProvider implements IWidgetConverterProvider {

    private final ComposedAdapterFactory composedAdapterFactory;

    private final IObjectService objectService;

    private final IOperationExecutor operationExecutor;

    private final IFeedbackMessageService feedbackMessageService;

    private final IFormIdProvider formIdProvider;

    private final IEMFKindService emfKindService;

    public ReferenceWidgetDescriptionConverterProvider(ComposedAdapterFactory composedAdapterFactory, IObjectService objectService, IOperationExecutor operationExecutor, IFeedbackMessageService feedbackMessageService, IEMFKindService emfKindService, IFormIdProvider formIdProvider) {
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.objectService = Objects.requireNonNull(objectService);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.formIdProvider = Objects.requireNonNull(formIdProvider);
        this.emfKindService = Objects.requireNonNull(emfKindService);
    }

    @Override
    public Switch<Optional<AbstractWidgetDescription>> getWidgetConverter(AQLInterpreter interpreter) {
        return new ReferenceWidgetDescriptionConverterSwitch(interpreter, this.objectService, this.operationExecutor, this.emfKindService, feedbackMessageService, this.composedAdapterFactory, this.formIdProvider);
    }
}
