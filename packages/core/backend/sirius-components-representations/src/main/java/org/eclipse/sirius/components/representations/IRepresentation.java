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

import org.eclipse.sirius.components.annotations.PublicApi;

/**
 * Common interface for all the representations.
 *
 * <p>
 *     A representation is a projection of the semantic data into a specific data structure.
 *     It is rendered using both the semantic data and the {@link IRepresentationDescription description} of the
 *     representation as input.
 *     The description of the representation is used most of the time to parameterize this rendering with specific rules
 *     created by specifiers.
 * </p>
 *
 * <p>
 *     The data structure of the representation is made to fulfill the requirements of the target environment.
 *     For example, one could want to leverage some JavaScript framework to display some visualization.
 *     Said framework could thus ask for some specific data structure.
 *     The goal of the representation would be to provide a data structure as close as possible from the one expected to
 *     be able to render a part of the semantic data using this framework.
 * </p>
 *
 * <p>
 *     A representation does not have to match exactly the data structure expected by the target environment since some
 *     processing can be made later.
 * </p>
 *
 * <p>
 *     Representations are computed using a dedicated {@link BaseRenderer renderer}.
 *     It will require both semantic data and a description of the representation to perform the rendering.
 *     Among all the semantic data, a specific object will be required as the target of the representation.
 *     When we will need to refresh an existing representation, we will first have to retrieve this object using its
 *     {@link IRepresentation#getTargetObjectId() identifier}.
 *     We will also have to find the description of the representation thanks to its {@link IRepresentation#getDescriptionId() identifier}.
 *     The target of the representation will then be used as the context on which the business rules defined in the description
 *     should be executed.
 * </p>
 *
 * @author sbegaudeau
 * @since v0.1.0
 */
@PublicApi
public interface IRepresentation {
    String KIND_PREFIX = "siriusComponents://representation";

    /**
     * Provides the unique identifier of the representation.
     *
     * @return the identifier of the representation.
     */
    String getId();

    /**
     * Provides the identifier of the {@link IRepresentationDescription description} used to render this representation.
     *
     * <p>
     *     This identifier will often be used to find the description to access the various business rules defined by
     *     specifiers to refresh the representation.
     *     It can also be used to retrieve the description to access additional pieces of behavior unrelated to the rendering.
     *     One could use it to find additional behavior, such as tools, that could be executed on the representation.
     * </p>
     *
     * @return The identifier of the description
     */
    String getDescriptionId();

    /**
     * Provides a unique kind for the representation.
     *
     * <p>
     *     It is mostly used in order to know how to handle an unknown representation.
     *     For example, if you want to deserialize representations, this property would let you know the expected shape
     *     of the data structure.
     * </p>
     *
     * @return The kind of representation
     *
     * @technical-debt This method could be deleted since this information can just be derived from the name of the class.
     */
    String getKind();

    /**
     * Provides the identifier of the target of the representation.
     *
     * <p>
     *     All representations are rendered using a specific target object from the semantic data.
     *     This object is used to start executing the various business rules defined in the description of the representation.
     *     Sirius Components does not make any assumptions on this object apart from the fact that it must have a unique
     *     identity.
     * </p>
     *
     * @return The identifier of the target of the representation.
     */
    String getTargetObjectId();

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author gcoutable
     */
    class NoOp implements IRepresentation {
        @Override
        public String getId() {
            return "";
        }

        @Override
        public String getDescriptionId() {
            return "";
        }

        @Override
        public String getKind() {
            return "";
        }

        @Override
        public String getTargetObjectId() {
            return "";
        }
    }
}
