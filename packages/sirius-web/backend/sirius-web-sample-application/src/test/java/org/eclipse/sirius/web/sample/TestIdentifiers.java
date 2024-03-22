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
package org.eclipse.sirius.web.sample;

import java.util.UUID;

/**
 * Interface used to store some test identifiers.
 *
 * @author frouene
 */
public interface TestIdentifiers {

    UUID TASK_PROJECT = UUID.fromString("d39c54dd-9f51-4c35-b9b3-2095f46f9705");

    UUID TASK_PROJECT_ROOT_OBJECT = UUID.fromString("a7fca50c-84e9-426e-b7f4-41d8c064d288");

    UUID DECK_VIEW = UUID.fromString("ce574932-af3d-4a14-84b5-7d3fa020fa5e");

    UUID DECK_DAILY_REPRESENTATION = UUID.fromString("c8328e8f-137d-493f-a56d-6901b6a9d488");

    UUID GANTT_VIEW = UUID.fromString("5d7aa2fb-869b-49f8-b6e1-8d4d49be6987");

    UUID GANTT_REPRESENTATION = UUID.fromString("583ec046-ee61-4ed6-a1e3-792fe8cb5482");
}
