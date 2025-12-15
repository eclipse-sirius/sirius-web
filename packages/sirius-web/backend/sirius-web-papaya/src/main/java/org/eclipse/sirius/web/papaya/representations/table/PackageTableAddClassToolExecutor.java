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
package org.eclipse.sirius.web.papaya.representations.table;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.tables.api.IToolMenuEntryExecutor;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.papaya.Package;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.springframework.stereotype.Service;

/**
 * Executor for 'add-class' tool entry.
 *
 * @author frouene
 */
@Service
public class PackageTableAddClassToolExecutor implements IToolMenuEntryExecutor {

    private final IEditService editService;

    private final IObjectSearchService objectSearchService;

    public PackageTableAddClassToolExecutor(IEditService editService, IObjectSearchService objectSearchService) {
        this.editService = Objects.requireNonNull(editService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
    }

    @Override
    public boolean canExecute(IEditingContext editingContext, TableDescription tableDescription, Table table, String menuEntryId) {
        return menuEntryId.equals(PackageTableToolMenuEntriesProvider.ADD_CLASS_TOOL_ENTRY);
    }

    @Override
    public IStatus execute(IEditingContext editingContext, TableDescription tableDescription, Table table, String menuEntryId) {
        this.objectSearchService.getObject(editingContext, table.getTargetObjectId())
                .filter(Package.class::isInstance)
                .map(Package.class::cast)
                .ifPresent(aPackage -> {
                    var aClass = PapayaFactory.eINSTANCE.createClass();
                    aPackage.getTypes().add(aClass);
                });
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of());
    }
}
