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
package org.eclipse.sirius.components.charts.piechart;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.charts.IChart;

/**
 * Root concept of the pie-chart representation.
 *
 * @author fbarbin
 */
public class PieChart implements IChart {
    public static final String KIND = "PieChart"; //$NON-NLS-1$

    private String id;

    private String descriptionId;

    private String label;

    private String kind;

    private List<PieChartEntry> entries;

    public PieChart() {
        // Used by Jackson
    }

    public PieChart(String id, String descriptionId, String label, List<PieChartEntry> entries) {
        this.id = Objects.requireNonNull(id);
        this.descriptionId = Objects.requireNonNull(descriptionId);
        this.label = Objects.requireNonNull(label);
        this.kind = KIND;
        this.entries = new ArrayList<>(Objects.requireNonNull(entries));
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getDescriptionId() {
        return this.descriptionId;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getKind() {
        return this.kind;
    }

    public List<PieChartEntry> getEntries() {
        return this.entries;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, descriptionId: {2}, label: {3}, kind: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.descriptionId, this.label, this.kind);
    }

}
