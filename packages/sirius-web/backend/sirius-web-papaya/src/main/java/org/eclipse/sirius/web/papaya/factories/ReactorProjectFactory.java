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
package org.eclipse.sirius.web.papaya.factories;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.papaya.Component;
import org.eclipse.sirius.components.papaya.Package;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.web.papaya.factories.api.IEObjectIndexer;
import org.eclipse.sirius.web.papaya.factories.api.IObjectFactory;

/**
 * Used to create the Reactor project.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ReactorProjectFactory implements IObjectFactory {
    @Override
    public void create(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var documentId = UUID.randomUUID();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
        var resourceMetadataAdapter = new ResourceMetadataAdapter("Reactor");
        resource.eAdapters().add(resourceMetadataAdapter);
        editingContext.getDomain().getResourceSet().getResources().add(resource);

        var projectReactor = this.projectReactor();
        resource.getContents().add(projectReactor);
        eObjectIndexer.index(projectReactor);
    }

    private Project projectReactor() {
        var projectReactor = PapayaFactory.eINSTANCE.createProject();
        projectReactor.setName("Project Reactor");
        projectReactor.getComponents().add(this.reactorCore());

        return projectReactor;
    }

    private Component reactorCore() {
        var reactorCore = PapayaFactory.eINSTANCE.createComponent();
        reactorCore.setName("reactor-core");
        reactorCore.getPackages().add(this.reactor());

        return reactorCore;
    }

    private Package reactor() {
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

    @Override
    public void link(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var publisherInterface = eObjectIndexer.getInterface("org.reactivestreams.Publisher");
        var subscriberInterface = eObjectIndexer.getInterface("org.reactivestreams.Subscriber");
        var corePublisherInterface = eObjectIndexer.getInterface("reactor.core.CorePublisher");
        var coreSubscriberInterface = eObjectIndexer.getInterface("reactor.core.CoreSubscriber");
        var scannableInterface = eObjectIndexer.getInterface("reactor.core.Scannable");
        var monoClass = eObjectIndexer.getClass("reactor.core.publisher.Mono");
        var fluxClass = eObjectIndexer.getClass("reactor.core.publisher.Flux");
        var emptyInterface = eObjectIndexer.getInterface("reactor.core.publisher.Sinks$Empty");
        var manyInterface = eObjectIndexer.getInterface("reactor.core.publisher.Sinks$Many");
        var oneInterface = eObjectIndexer.getInterface("reactor.core.publisher.Sinks$One");

        corePublisherInterface.getExtends().add(publisherInterface);
        coreSubscriberInterface.getExtends().add(subscriberInterface);
        monoClass.getImplements().add(corePublisherInterface);
        fluxClass.getImplements().add(corePublisherInterface);
        emptyInterface.getExtends().add(scannableInterface);
        oneInterface.getExtends().add(emptyInterface);
        manyInterface.getExtends().add(scannableInterface);

        var reactorCoreComponent = eObjectIndexer.getComponent("reactor-core");
        var reactiveStreamsComponent = eObjectIndexer.getComponent("reactive-streams");
        reactorCoreComponent.getDependencies().add(reactiveStreamsComponent);
    }
}
