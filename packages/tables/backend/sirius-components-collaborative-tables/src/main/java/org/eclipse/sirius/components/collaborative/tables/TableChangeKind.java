/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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

/**
 * Description of the changes performed by table event handlers.
 *
 * @author frouene
 */
public final class TableChangeKind {

    public static final String TABLE_LAYOUT_CHANGE = "TABLE_LAYOUT_CHANGE";

    public static final String TABLE_EVENTS_PARAM = "TABLE_EVENTS";

    public static final String TABLE_GLOBAL_FILTER_VALUE_CHANGE = "TABLE_GLOBAL_FILTER_VALUE_CHANGE";

    public static final String TABLE_EVENT_PARAM = "TABLE_EVENT";

    public static final String GLOBAL_FILTER_NEW_VALUE_PARAM = "GLOBAL_FILTER_NEW_VALUE";

    private TableChangeKind() {
        // Prevent instantiation
    }
}
