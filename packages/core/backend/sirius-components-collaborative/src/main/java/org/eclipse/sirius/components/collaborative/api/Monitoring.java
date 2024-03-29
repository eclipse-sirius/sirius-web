/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.api;

/**
 * Monitoring constants.
 *
 * @author sbegaudeau
 */
public final class Monitoring {

    public static final String EVENT_HANDLER = "siriusweb_eventhandlers";
    public static final String REPRESENTATION_EVENT_PROCESSOR_REFRESH = "siriusweb_representationeventprocessor_refresh";
    public static final String TIMER_REFRESH_REPRESENTATION = "timer_refresh_representation";
    public static final String TIMER_CREATE_REPRESENATION_EVENT_PROCESSOR = "timer_create_representation_event_processor";
    public static final String TIMER_PROCESSING_INPUT = "timer_processing_input";

    public static final String NAME = "name";

    private Monitoring() {
        // Prevent instantiation
    }
}
