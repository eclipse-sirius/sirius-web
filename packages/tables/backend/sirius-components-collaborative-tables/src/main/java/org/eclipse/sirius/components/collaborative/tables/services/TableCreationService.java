/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
package org.eclipse.sirius.components.collaborative.tables.services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.components.TableComponent;
import org.eclipse.sirius.components.tables.components.TableComponentProps;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.tables.renderer.TableRenderer;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * Service used to create table representations.
 *
 * @author frouene
 */
@Service
public class TableCreationService {

    private final Timer timer;

    public TableCreationService(MeterRegistry meterRegistry) {
        this.timer = Timer.builder(Monitoring.REPRESENTATION_EVENT_PROCESSOR_REFRESH).tag(Monitoring.NAME, "table").register(meterRegistry);
    }

    public Table create(String label, Object targetObject, TableDescription tableDescription, IEditingContext editingContext) {
        return this.doRender(label, targetObject, editingContext, tableDescription);
    }

    private Table doRender(String label, Object targetObject, IEditingContext editingContext, TableDescription tableDescription) {
        long start = System.currentTimeMillis();

        VariableManager variableManager = new VariableManager();
        variableManager.put(TableDescription.LABEL, label);
        variableManager.put(VariableManager.SELF, targetObject);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);


        TableComponentProps tableComponentProps = new TableComponentProps(variableManager, tableDescription, Optional.empty(), List.of(), null);

        Element element = new Element(TableComponent.class, tableComponentProps);
        Table newTable = new TableRenderer().render(element);

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);
        return newTable;
    }

}
