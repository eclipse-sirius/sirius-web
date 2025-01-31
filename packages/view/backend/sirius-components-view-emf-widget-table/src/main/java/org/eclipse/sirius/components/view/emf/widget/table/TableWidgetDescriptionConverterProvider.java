/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.widget.table;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.IWidgetConverterProvider;
import org.eclipse.sirius.components.view.emf.table.ITableIdProvider;
import org.springframework.stereotype.Service;

/**
 * Provides the widget converter needed for the table widget.
 *
 * @author frouene
 */
@Service
public class TableWidgetDescriptionConverterProvider implements IWidgetConverterProvider {

    private final IFormIdProvider formIdProvider;

    private final ITableIdProvider tableIdProvider;


    public TableWidgetDescriptionConverterProvider(IFormIdProvider formIdProvider, ITableIdProvider tableIdProvider) {
        this.formIdProvider = Objects.requireNonNull(formIdProvider);
        this.tableIdProvider = Objects.requireNonNull(tableIdProvider);
    }

    @Override
    public Switch<Optional<AbstractWidgetDescription>> getWidgetConverter(AQLInterpreter interpreter, IEditService editService, IObjectService objectService, IFeedbackMessageService feedbackMessageService) {
        return new TableWidgetDescriptionConverterSwitch(interpreter, objectService, this.formIdProvider, this.tableIdProvider);
    }
}
