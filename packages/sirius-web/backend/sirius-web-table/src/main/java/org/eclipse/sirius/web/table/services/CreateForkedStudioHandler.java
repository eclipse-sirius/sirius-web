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

package org.eclipse.sirius.web.table.services;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.tables.api.ITableContext;
import org.eclipse.sirius.components.collaborative.tables.api.ITableEventHandler;
import org.eclipse.sirius.components.collaborative.tables.api.ITableInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.web.table.dto.CreateForkedStudioInput;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

/**
 * Handler of createForkedStudio mutation.
 *
 * @author mcharfadi
 */
@Service
public class CreateForkedStudioHandler implements ITableEventHandler {

    private final ICreateForkedStudioService createForkedStudioService;

    public CreateForkedStudioHandler(ICreateForkedStudioService createForkedStudioService) {
        this.createForkedStudioService = Objects.requireNonNull(createForkedStudioService);
    }

    @Override
    public boolean canHandle(ITableInput tableInput) {
        return tableInput instanceof CreateForkedStudioInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, ITableContext tableContext, TableDescription tableDescription, ITableInput tableInput) {
        var payload = createForkedStudioService.create(tableInput, editingContext);

        payloadSink.tryEmitValue(payload);

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), tableInput);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
