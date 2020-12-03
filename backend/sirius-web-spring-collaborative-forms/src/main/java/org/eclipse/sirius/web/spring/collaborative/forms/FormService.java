/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.forms;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.collaborative.forms.api.IFormService;
import org.eclipse.sirius.web.forms.AbstractWidget;
import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.persistence.repositories.IRepresentationRepository;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.eclipse.sirius.web.spring.collaborative.representations.RepresentationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Class used to manipulate forms.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Service
public class FormService implements IFormService {

    private final Logger logger = LoggerFactory.getLogger(FormService.class);

    private final IRepresentationRepository representationRepository;

    private final ObjectMapper objectMapper;

    public FormService(IRepresentationRepository representationRepository, ObjectMapper objectMapper) {
        this.representationRepository = Objects.requireNonNull(representationRepository);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public Optional<AbstractWidget> findWidget(Form form, String widgetId) {
        // @formatter:off
        Optional<AbstractWidget> optionalWidget = form.getPages().stream()
                .flatMap(page -> page.getGroups().stream())
                .flatMap(group -> group.getWidgets().stream())
                .filter(widget -> Objects.equals(widgetId, widget.getId()))
                .findFirst();
        // @formatter:on

        if (optionalWidget.isEmpty()) {
            this.logger.warn("The widget with the id {} has not been found", widgetId); //$NON-NLS-1$
        }

        return optionalWidget;
    }

    @Override
    public Optional<Form> findById(UUID formId) {
        // @formatter:off
        return this.representationRepository.findById(formId)
                .map(new RepresentationMapper(this.objectMapper)::toDTO)
                .map(RepresentationDescriptor::getRepresentation)
                .filter(Form.class::isInstance)
                .map(Form.class::cast);
        // @formatter:on
    }

}
