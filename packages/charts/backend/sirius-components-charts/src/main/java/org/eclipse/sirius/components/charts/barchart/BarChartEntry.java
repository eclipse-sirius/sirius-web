/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.charts.barchart;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * Represent the bar-chart key-value entry.
 *
 * @author fbarbin
 */
public class BarChartEntry {

    private final String key;

    private final Number value;

    public BarChartEntry(String key, Number value) {
        this.key = Objects.requireNonNull(key);
        this.value = Objects.requireNonNull(value);
    }

    public String getKey() {
        return this.key;
    }

    public Number getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'key: {1}, value: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.key, this.value);
    }

}
