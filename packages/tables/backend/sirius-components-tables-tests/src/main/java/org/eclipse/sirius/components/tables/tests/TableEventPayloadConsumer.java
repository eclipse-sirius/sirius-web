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
package org.eclipse.sirius.components.tables.tests;

import static org.assertj.core.api.Assertions.fail;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.tables.TableColumnFilterPayload;
import org.eclipse.sirius.components.collaborative.tables.TableColumnSortPayload;
import org.eclipse.sirius.components.collaborative.tables.TableGlobalFilterValuePayload;
import org.eclipse.sirius.components.collaborative.tables.TableRefreshedEventPayload;
import org.eclipse.sirius.components.tables.ColumnFilter;
import org.eclipse.sirius.components.tables.ColumnSort;
import org.eclipse.sirius.components.tables.Table;

/**
 * Used to handle table event payloads.
 *
 * @author sbegaudeau
 */
public class TableEventPayloadConsumer {
    public static Consumer<Object> assertRefreshedTableThat(Consumer<Table> consumer) {
        return object -> Optional.of(object)
                .filter(TableRefreshedEventPayload.class::isInstance)
                .map(TableRefreshedEventPayload.class::cast)
                .map(TableRefreshedEventPayload::table)
                .ifPresentOrElse(consumer, () -> fail("Missing table"));
    }

    public static Consumer<Object> assertRefreshedGlobalFilterThat(Consumer<String> consumer) {
        return object -> Optional.of(object)
                .filter(TableGlobalFilterValuePayload.class::isInstance)
                .map(TableGlobalFilterValuePayload.class::cast)
                .map(TableGlobalFilterValuePayload::globalFilterValue)
                .ifPresentOrElse(consumer, () -> fail("Missing global filter"));
    }

    public static Consumer<Object> assertRefreshedColumnFilterThat(Consumer<List<ColumnFilter>> consumer) {
        return object -> Optional.of(object)
                .filter(TableColumnFilterPayload.class::isInstance)
                .map(TableColumnFilterPayload.class::cast)
                .map(TableColumnFilterPayload::columnFilters)
                .ifPresentOrElse(consumer, () -> fail("Missing filters"));
    }

    public static Consumer<Object> assertRefreshedColumnSortThat(Consumer<List<ColumnSort>> consumer) {
        return object -> Optional.of(object)
                .filter(TableColumnSortPayload.class::isInstance)
                .map(TableColumnSortPayload.class::cast)
                .map(TableColumnSortPayload::columnSort)
                .ifPresentOrElse(consumer, () -> fail("Missing sort"));
    }
}
