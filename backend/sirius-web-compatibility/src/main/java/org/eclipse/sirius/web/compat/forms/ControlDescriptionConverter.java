/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.compat.forms;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.properties.ControlDescription;
import org.eclipse.sirius.properties.DynamicMappingForDescription;
import org.eclipse.sirius.properties.WidgetDescription;
import org.eclipse.sirius.web.compat.api.IIdentifierProvider;
import org.eclipse.sirius.web.compat.api.IModelOperationHandlerSwitchProvider;
import org.eclipse.sirius.web.forms.description.AbstractControlDescription;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.services.api.objects.IObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to convert a Sirius ControlDescription to an Sirius Web ControlDescription.
 *
 * @author fbarbin
 */
public class ControlDescriptionConverter {

    private final Logger logger = LoggerFactory.getLogger(ControlDescriptionConverter.class);

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IIdentifierProvider identifierProvider;

    private final IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider;

    public ControlDescriptionConverter(AQLInterpreter interpreter, IObjectService objectService, IIdentifierProvider identifierProvider,
            IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.modelOperationHandlerSwitchProvider = Objects.requireNonNull(modelOperationHandlerSwitchProvider);
    }

    public Optional<AbstractControlDescription> convert(ControlDescription controlDescription) {
        Optional<AbstractControlDescription> optionalControlDescription = Optional.empty();
        if (controlDescription instanceof WidgetDescription) {
            optionalControlDescription = this.convertWidget((WidgetDescription) controlDescription);
        } else if (controlDescription instanceof DynamicMappingForDescription) {
            optionalControlDescription = this.convertFor((DynamicMappingForDescription) controlDescription);
        } else {
            this.logger.error("The provided type {} is not yet handled", controlDescription.getClass().getName()); //$NON-NLS-1$
        }
        return optionalControlDescription;
    }

    private Optional<AbstractControlDescription> convertWidget(WidgetDescription controlDescription) {
        return new WidgetDescriptionConverter(this.interpreter, this.objectService, this.identifierProvider, this.modelOperationHandlerSwitchProvider).convert(controlDescription)
                .map(AbstractControlDescription.class::cast);
    }

    private Optional<AbstractControlDescription> convertFor(DynamicMappingForDescription forDescription) {
        return Optional.of(new ForDescriptionConverter(this.interpreter, this.objectService, this.identifierProvider, this.modelOperationHandlerSwitchProvider).convert(forDescription));
    }

}
