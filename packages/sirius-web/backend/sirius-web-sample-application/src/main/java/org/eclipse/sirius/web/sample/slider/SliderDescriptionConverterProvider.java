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
package org.eclipse.sirius.web.sample.slider;

import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.view.emf.form.IWidgetConverterProvider;
import org.springframework.stereotype.Service;

/**
 * Provides the widget converter needed for the Slider widget.
 *
 * @author pcdavid
 */
@Service
public class SliderDescriptionConverterProvider implements IWidgetConverterProvider {
    @Override
    public Switch<AbstractWidgetDescription> getWidgetConverter(AQLInterpreter interpreter, IEditService editService, IObjectService objectService) {
        return new SliderDescriptionConverterSwitch(interpreter, editService);
    }
}
