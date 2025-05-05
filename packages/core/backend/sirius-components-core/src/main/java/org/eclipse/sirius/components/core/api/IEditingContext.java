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
package org.eclipse.sirius.components.core.api;

import org.eclipse.sirius.components.annotations.PublicApi;

/**
 * The concept of editing context exists to provide an entry point, which can be used by Sirius Components, to read and
 * update semantic data.
 *
 * <p>
 *     The core parts of Sirius Components will not make any assumptions on the implementation of the editing context.
 *     It will instead rely on a set of core service interfaces to read and update semantic data accessible thanks to
 *     the editing context.
 *     Sirius Components will not manipulate the editing context directly but it will instead use a set of core services
 *     which will be familiar with the implementation of the editing context to access the semantic data.
 *     Those services will be given the editing context in order to perform the required operations.
 *     A developer integrating Sirius Components in their application will be able to provide custom implementations of
 *     those services tightly integrated with some implementation of the editing context.
 * </p>
 *
 * <p>
 *     The only strict requirement for an editing context is the presence of a unique identifier.
 *     This identifier is necessary to be given to the frontend, to let the frontend know in which context it is working.
 *     The frontend will then give it back to the backend when it will ask to perform some operation.
 * </p>
 *
 * <p>
 *     The editing context can be implemented in a way that will contain the semantic data directly but it is not a
 *     requirement from Sirius Components.
 *     Since this approach is quite popular, one could think about the editing context as the state of the application.
 *     One could also provide an editing context which would be an empty shell containing only the URL of a remote datastore
 *     for example.
 *     In order to let Sirius Components read some data from such an editing context, one would need a dedicated implementation
 *     of the {@link IObjectSearchServiceDelegate} to retrieve lazily the real data from the remote datastore.
 * </p>
 *
 * {@snippet id="lazy-loading" lang="java" :
 * public RemoteObjectSearchServiceDelegate implements IObjectSearchServiceDelegate {
 *     @Override
 *     public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
 *         return Optional.of(editingContext)
 *                        .filter(IRemoteEditingContext.class::isInstance)
 *                        .map(IRemoteEditingContext.class::cast)
 *                        .map(IRemoteEditingContext::getRemoteURL)
 *                        .flatMap(url -> this.getRemoteObject(url, objectId));
 *     }
 *
 *     private Optional<Object> getRemoteObject(String url, String objectId) {
 *         return ...;
 *     }
 * }
 * }
 *
 * <p>
 *     Using such an approach to retrieve, one by one, every object would offer better performance in terms of memory
 *     but it could be very costly in time.
 *     You can also mix both approaches to create an implementation of the editing context which would contain most of
 *     the semantic data but which could also be used to perform requests to a remote datastore for additional data.
 * </p>
 *
 * <p>
 *     In a client / server environment, loading the semantic data from scratch for every request could be too time consuming.
 *     While end users are interacting with the application, an editing context can thus stay in memory for a very long
 *     time (think several hours or even a couple of days).
 *     It may not be unusual for an application based on Sirius Components to have dozens of editing contexts loaded
 *     simultaneously.
 *     As a result, the memory consumption of the editing context can be a concern for sizable applications with a lot of
 *     users.
 * </p>
 *
 * @author sbegaudeau
 * @since v0.1.2
 */
@PublicApi
public interface IEditingContext {

    /**
     * The name of the variable used to store and retrieve the editing context from a variable manager.
     */
    String EDITING_CONTEXT = "editingContext";

    /**
     * Provides a unique identifier of the editing context.
     *
     * <p>
     *     This identifier will be massively used across the application in order to define in which context an operation
     *     is being performed.
     *     As such, the frontend will heavily rely on this identifier.
     * </p>
     *
     * <p>
     *     One can use a random identifier like a random UUID or a meaningful identifier like an URI containing some
     *     specific content.
     *     Sirius Components will ask for the creation of an editing context from this unique identifier.
     *     It thus has to be meaningful enough to find how to load the semantic data from it.
     * </p>
     *
     * @return The identifier of the editing context.
     */
    String getId();

    /**
     * Gives the opportunity to clean up things when the editing context is no longer needed.
     *
     * <p>
     *     Since an editing context can be in memory for a long time and since it can be used to load remote data, one
     *     could keep a communication channel opened or some listener attached during this time.
     *     This method can thus be used to close any communication channel opened or remove any listener.
     * </p>
     */
    default void dispose() {
        // Do nothing
    }

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IEditingContext {

        @Override
        public String getId() {
            return "";
        }

    }
}
