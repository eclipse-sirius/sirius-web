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
package org.eclipse.sirius.web.application.object.services;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.web.application.object.services.api.IObjectExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Used to export objects as CSV resources.
 *
 * @author gdaniel
 */
@Service
public class CsvObjectExporter implements IObjectExporter {

    private final Logger logger = LoggerFactory.getLogger(CsvObjectExporter.class);

    private final IIdentityService identityService;

    private final ILabelService labelService;

    public CsvObjectExporter(IIdentityService identityService, ILabelService labelService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
    }


    @Override
    public boolean canHandle(List<Object> objects, String mediaType) {
        return "text/csv".equals(mediaType);
    }

    @Override
    public Optional<byte[]> getBytes(List<Object> objects, String mediaType) {
        Optional<byte[]> result = Optional.empty();
        CsvMapper csvMapper = new CsvMapper();
        try (StringWriter stringWriter = new StringWriter()) {
            var writer = csvMapper.writer()
                    .writeValues(stringWriter);
            writer.write(List.of("id", "type", "label"));
            for (Object object : objects) {
                writer.write(List.of(this.identityService.getId(object), this.getType(object), this.labelService.getStyledLabel(object).toString()));
            }
            writer.close();
            result = Optional.of(stringWriter.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return result;
    }

    private String getType(Object object) {
        final String result;
        if (object == null) {
            result = "null";
        } else if (object instanceof EObject eObject) {
            result = eObject.eClass().getName();
        } else {
            result = object.getClass().getSimpleName();
        }
        return result;
    }

}
