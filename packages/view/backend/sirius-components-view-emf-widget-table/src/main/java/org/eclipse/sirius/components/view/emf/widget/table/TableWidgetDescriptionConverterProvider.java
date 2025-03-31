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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.IWidgetConverterProvider;
import org.eclipse.sirius.components.view.emf.table.ITableIdProvider;
import org.eclipse.sirius.components.view.emf.table.api.ICustomCellConverter;
import org.springframework.stereotype.Service;

/**
 * Provides the widget converter needed for the table widget.
 *
 * @author frouene
 */
@Service
public class TableWidgetDescriptionConverterProvider implements IWidgetConverterProvider {

    private final IFormIdProvider formIdProvider;

    private final IObjectService objectService;

    private final ITableIdProvider tableIdProvider;

    private final List<ICustomCellConverter> customCellConverters;

    public TableWidgetDescriptionConverterProvider(IFormIdProvider formIdProvider, IObjectService objectService, ITableIdProvider tableIdProvider, List<ICustomCellConverter> customCellConverters) {
        this.formIdProvider = Objects.requireNonNull(formIdProvider);
        this.objectService = Objects.requireNonNull(objectService);
        this.tableIdProvider = Objects.requireNonNull(tableIdProvider);
        this.customCellConverters = Objects.requireNonNull(customCellConverters);
    }

    @Override
    public Switch<Optional<AbstractWidgetDescription>> getWidgetConverter(AQLInterpreter interpreter) {
        return new TableWidgetDescriptionConverterSwitch(interpreter, this.objectService, this.formIdProvider, this.tableIdProvider, this.customCellConverters);
    }
}
