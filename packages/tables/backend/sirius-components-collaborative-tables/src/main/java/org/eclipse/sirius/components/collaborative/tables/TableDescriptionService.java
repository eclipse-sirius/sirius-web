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
package org.eclipse.sirius.components.collaborative.tables;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.tables.api.ITableDescriptionService;
import org.eclipse.sirius.components.tables.descriptions.LineDescription;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link ITableDescriptionService}.
 *
 * @author arichard
 */
@Service
public class TableDescriptionService implements ITableDescriptionService {

    @Override
    public Optional<LineDescription> findLineDescriptionById(TableDescription tableDescription, UUID lineDescriptionId) {
        return this.findLineDescription(lineDesc -> Objects.equals(lineDesc.getId(), lineDescriptionId), tableDescription.getLineDescriptions());
    }

    private Optional<LineDescription> findLineDescription(Predicate<LineDescription> condition, List<LineDescription> candidates) {
        Optional<LineDescription> result = Optional.empty();
        for (LineDescription lineDescription : candidates) {
            if (condition.test(lineDescription)) {
                result = Optional.of(lineDescription);
            }
            if (result.isPresent()) {
                break;
            }
        }
        return result;
    }

}
