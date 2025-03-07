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
package org.eclipse.sirius.web.papaya.factories;

import java.util.List;

import org.eclipse.sirius.components.papaya.Package;
import org.eclipse.sirius.components.papaya.PapayaFactory;

/**
 * Used to create the reactive streams package.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ReactiveStreamsFactory {

    public Package orgReactiveStreams() {
        var orgReactiveStreams = PapayaFactory.eINSTANCE.createPackage();
        orgReactiveStreams.setName("org.reactivestreams");

        var processorTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        processorTTypeParameter.setName("T");
        var processorInterface = PapayaFactory.eINSTANCE.createInterface();
        processorInterface.setName("Processor");
        processorInterface.getTypeParameters().add(processorTTypeParameter);

        var publisherTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        publisherTTypeParameter.setName("T");
        var publisherInterface = PapayaFactory.eINSTANCE.createInterface();
        publisherInterface.setName("Publisher");
        publisherInterface.getTypeParameters().add(publisherTTypeParameter);

        var subscriberTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        subscriberTTypeParameter.setName("T");
        var subscriberInterface = PapayaFactory.eINSTANCE.createInterface();
        subscriberInterface.setName("Subscriber");
        subscriberInterface.getTypeParameters().add(subscriberTTypeParameter);

        var subscriptionInterface = PapayaFactory.eINSTANCE.createInterface();
        subscriptionInterface.setName("Subscription");

        var reactiveStreamsTypes = List.of(processorInterface, publisherInterface, subscriberInterface, subscriptionInterface);
        orgReactiveStreams.getTypes().addAll(reactiveStreamsTypes);

        return orgReactiveStreams;
    }
}
