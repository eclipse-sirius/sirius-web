/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST and others.
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
package org.eclipse.sirius.web.tests.services.api;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLSubscriptionResult;

/**
 * Used to create a table and subscribe to it.
 *
 * @author frouene
 */
public interface IGivenCreatedTableSubscription {

    GraphQLSubscriptionResult createAndSubscribe(CreateRepresentationInput input);
}
