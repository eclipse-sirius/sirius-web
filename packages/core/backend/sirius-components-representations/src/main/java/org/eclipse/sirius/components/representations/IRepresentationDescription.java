/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
package org.eclipse.sirius.components.representations;

import java.util.function.Predicate;

import org.eclipse.sirius.components.annotations.PublicApi;

/**
 * Common interface for all the {@link IRepresentation representation} descriptions.
 *
 * <p>
 *     The description of a representation is used to define how a representation will be rendered.
 *     They are thus used to distinguish the various types of representations that can be created in a given environment.
 * </p>
 *
 * <p>
 *     Most of the time, a representation description is configurable by a specifier.
 *     It allows them to define a specific behavior for the rendering of a representation.
 *     This capability gives the specifier a way to create representations respecting some business logic.
 *     When they are configurable, they are almost always parameterized thanks to the concepts used by the semantic data.
 *     Thanks to this, one specifier can build business rules ensuring that all elements of a specific type are using some
 *     specific style for example.
 * </p>
 *
 * @author sbegaudeau
 * @since v0.1.0
 */
@PublicApi
public interface IRepresentationDescription {

    /**
     * Variable used to access the type of the current semantic element.
     *
     * <p>
     *     It is used when we are trying to determine if we can create a new instance of representation using this description.
     * </p>
     *
     * @technical-debt This code should be deleted. It is a remnant of a time when we did not have access to the current
     * semantic element but instead had only its type. Newer variables are available to have access to the same data and
     * much more.
     * @see <a href="https://github.com/eclipse-sirius/sirius-web/issues/4721">Github Issue</a>
     */
    String CLASS = "class";

    /**
     * Provides the unique identifier of the representation description.
     *
     * <p>
     *     This identifier should not change over time.
     *     Sirius Components will look for the description matching this identifier to perform various operations.
     *     If it cannot be found anymore then we will not be able to interact with the representation.
     * </p>
     *
     * @return the identifier of the representation description
     */
    String getId();

    /**
     * Provides the label which can be displayed to end users to let them select the type of representation to create.
     *
     * @return The label of the representation description
     */
    String getLabel();

    /**
     * Provides a predicate used to find out if a representation can be created from this description.
     *
     * <p>
     *     This predicate will use some variables to let specifiers create a precondition for the creation of a representation.
     *     The following variables will be available to compute this precondition:
     * </p>
     *
     * <ul>
     *     <li><strong>self</strong> - The semantic element which will be used as the target of the representation</li>
     *     <li><strong>class</strong> - The type of the semantic element. This variable will be removed in the future since
     *     it can be derived from self</li>
     * </ul>
     *
     * @return The predicate to compute if a representation can be created from this description
     */
    Predicate<VariableManager> getCanCreatePredicate();
}
