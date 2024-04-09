/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.services;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.web.application.document.services.api.IUploadDocumentReportProvider;
import org.springframework.stereotype.Service;

/**
 * Used to create test report during document upload integration tests.
 *
 * @author arichard
 */
@Service
public class TestUploadDocumentReportProvider implements IUploadDocumentReportProvider {

    @Override
    public boolean canHandle(Resource resource) {
        return true;
    }

    @Override
    public String createReport(Resource resource) {
        return "This is a test report";
    }

}
