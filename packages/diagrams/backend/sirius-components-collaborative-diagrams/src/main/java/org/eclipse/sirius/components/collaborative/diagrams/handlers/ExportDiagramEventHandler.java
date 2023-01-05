/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.handlers;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ExportRepresentationInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ExportRepresentationPayload;
import org.eclipse.sirius.components.collaborative.diagrams.export.api.ISVGDiagramExportService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to export a representation.
 *
 * @author rpage
 */
@Service
public class ExportDiagramEventHandler implements IDiagramEventHandler {
    private final ISVGDiagramExportService exportService;

    public ExportDiagramEventHandler(ISVGDiagramExportService exportService) {
        this.exportService = Objects.requireNonNull(exportService);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof ExportRepresentationInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        String svgExport = this.exportService.export(diagramContext.getDiagram());
        IPayload payload = new ExportRepresentationPayload(diagramInput.id(), diagramContext.getDiagram().getLabel() + ".svg", svgExport);
        payloadSink.tryEmitValue(payload);
    }
}
