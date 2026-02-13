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
package org.eclipse.sirius.web.application.controllers.documents;

import org.eclipse.sirius.web.application.document.services.api.IUploadDocumentReportProvider;
import org.eclipse.sirius.web.application.document.services.api.UploadedResource;
import org.eclipse.sirius.web.application.editingcontext.services.EditingContextAdapter;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.springframework.stereotype.Service;

/**
 * Used to test the generated report for the upload of a resource.
 *
 * @author sbegaudeau
 */
@Service
public class TestUploadDocumentReportProvider implements IUploadDocumentReportProvider {

    public static final String TEST_REPORT = "Test Report";

    @Override
    public boolean canHandle(UploadedResource uploadedResource) {
        return uploadedResource.resource().getResourceSet().eAdapters().stream()
                .filter(EditingContextAdapter.class::isInstance)
                .map(EditingContextAdapter.class::cast)
                .anyMatch(editingContextAdapter -> editingContextAdapter.getEditingContextId().equals(StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString()));
    }

    @Override
    public String createReport(UploadedResource uploadedResource) {
        return TEST_REPORT;
    }
}
