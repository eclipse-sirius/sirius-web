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
package org.eclipse.sirius.web.application.document.services;

import org.eclipse.sirius.web.application.document.services.api.IUploadDocumentReportProvider;
import org.eclipse.sirius.web.application.document.services.api.UploadedResource;
import org.springframework.stereotype.Service;

/**
 * Default implementation that provides reports when uploading a document.
 *
 * @author gdaniel
 */
@Service
public class DefaultUploadDocumentReportProvider implements IUploadDocumentReportProvider {

    @Override
    public boolean canHandle(UploadedResource uploadedResource) {
        return uploadedResource.loadingReport() instanceof LoadingReport;
    }

    @Override
    public String createReport(UploadedResource uploadedResource) {
        String result = "";
        if (uploadedResource.loadingReport() instanceof LoadingReport loadingReport) {
            result = String.join("\n", loadingReport.content());
        }
        return result;
    }
}
