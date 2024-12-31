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
package org.eclipse.sirius.web.papaya.representations.table;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.eclipse.sirius.components.papaya.Type;
import org.eclipse.sirius.components.tables.ColumnFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to indicate if a column should be filtered or not.
 *
 * @author sbegaudeau
 */
public class PapayaColumnFilterPredicate implements Predicate<ColumnFilter> {

    private final ObjectMapper objectMapper;

    private final Type type;

    private final Logger logger = LoggerFactory.getLogger(PapayaColumnFilterPredicate.class);

    public PapayaColumnFilterPredicate(ObjectMapper objectMapper, Type type) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.type = Objects.requireNonNull(type);
    }

    @Override
    public boolean test(ColumnFilter columnFilter) {
        boolean isValidColumFilterCandidate = true;
        if (columnFilter.id().equals("papaya.NamedElement#name")) {
            isValidColumFilterCandidate = this.isValidNameColumnFilterCandidate(columnFilter);
        } else if (columnFilter.id().equals("papaya.Type#nbAnnotation")) {
            isValidColumFilterCandidate = this.isValidAnnotationCountFilterCandidate(columnFilter);
        }
        return isValidColumFilterCandidate;
    }

    private boolean isValidNameColumnFilterCandidate(ColumnFilter columnFilter) {
        var isValid = true;
        try {
            String filterValue = this.objectMapper.readValue(columnFilter.value(), new TypeReference<>() { });
            isValid = this.type.getName() != null && this.type.getName().contains(filterValue);
        } catch (JsonProcessingException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return isValid;
    }

    private boolean isValidAnnotationCountFilterCandidate(ColumnFilter columnFilter) {
        var isValid = true;
        try {
            List<String> filterValues = objectMapper.readValue(columnFilter.value(), new TypeReference<>() { });
            int nbAnnotation = this.type.getAnnotations().size();
            if (filterValues.size() == 2) {
                if (filterValues.get(0) != null && !filterValues.get(0).isBlank()) {
                    try {
                        int minValue = Integer.parseInt(filterValues.get(0));
                        isValid = minValue <= nbAnnotation;
                    } catch (NumberFormatException exception) {
                        this.logger.warn(exception.getMessage(), exception);
                    }
                }
                if (filterValues.get(1) != null && !filterValues.get(1).isBlank()) {
                    try {
                        int maxValue = Integer.parseInt(filterValues.get(1));
                        isValid = isValid && maxValue >= nbAnnotation;
                    } catch (NumberFormatException exception) {
                        this.logger.warn(exception.getMessage(), exception);
                        isValid = true;
                    }
                }
            }
        } catch (JsonProcessingException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return  isValid;
    }
}
