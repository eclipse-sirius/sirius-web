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
 * Used to create the reactor package.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ReactorFactory {

    public Package reactor() {
        var reactor = PapayaFactory.eINSTANCE.createPackage();
        reactor.setName("reactor");
        reactor.getPackages().add(this.core());

        return reactor;
    }

    private Package core() {
        var disposableInterface = PapayaFactory.eINSTANCE.createInterface();
        disposableInterface.setName("Disposable");

        var corePublisherInterface = PapayaFactory.eINSTANCE.createInterface();
        corePublisherInterface.setName("CorePublisher");

        var coreSubscriberInterface = PapayaFactory.eINSTANCE.createInterface();
        coreSubscriberInterface.setName("CoreSubscriber");

        var scannableInterface = PapayaFactory.eINSTANCE.createInterface();
        scannableInterface.setName("Scannable");

        var reactorCoreTypes = List.of(disposableInterface, corePublisherInterface, coreSubscriberInterface, scannableInterface);

        var core = PapayaFactory.eINSTANCE.createPackage();
        core.setName("core");
        core.getTypes().addAll(reactorCoreTypes);
        core.getPackages().add(this.publisher());

        return core;
    }

    private Package publisher() {
        var fluxTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        fluxTTypeParameter.setName("T");
        var fluxClass = PapayaFactory.eINSTANCE.createClass();
        fluxClass.setName("Flux");
        fluxClass.setAbstract(true);
        fluxClass.getTypeParameters().add(fluxTTypeParameter);

        var monoTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        monoTTypeParameter.setName("T");
        var monoClass = PapayaFactory.eINSTANCE.createClass();
        monoClass.setName("Mono");
        monoClass.setAbstract(true);
        monoClass.getTypeParameters().add(monoTTypeParameter);

        var emptyTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        emptyTTypeParameter.setName("T");
        var emptyInterface = PapayaFactory.eINSTANCE.createInterface();
        emptyInterface.setName("Empty");
        emptyInterface.getTypeParameters().add(emptyTTypeParameter);

        var manyTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        manyTTypeParameter.setName("T");
        var manyInterface = PapayaFactory.eINSTANCE.createInterface();
        manyInterface.setName("Many");
        manyInterface.getTypeParameters().add(manyTTypeParameter);

        var oneTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        oneTTypeParameter.setName("T");
        var oneInterface = PapayaFactory.eINSTANCE.createInterface();
        oneInterface.setName("One");
        oneInterface.getTypeParameters().add(oneTTypeParameter);

        var sinksClass = PapayaFactory.eINSTANCE.createClass();
        sinksClass.setName("Sinks");
        sinksClass.getTypes().addAll(List.of(emptyInterface, manyInterface, oneInterface));

        var reactorCorePublisherTypes = List.of(fluxClass, monoClass, sinksClass);

        var publisher = PapayaFactory.eINSTANCE.createPackage();
        publisher.setName("publisher");
        publisher.getTypes().addAll(reactorCorePublisherTypes);

        return publisher;
    }
}
