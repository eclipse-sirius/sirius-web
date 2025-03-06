/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import java.util.UUID;

import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.papaya.Component;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.web.papaya.factories.services.api.IEObjectIndexer;
import org.eclipse.sirius.web.papaya.factories.services.api.IObjectFactory;

/**
 * Used to create the Reactor project.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ReactorProjectFactory implements IObjectFactory {
    @Override
    public void create(IEMFEditingContext editingContext) {
        var documentId = UUID.randomUUID();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
        var resourceMetadataAdapter = new ResourceMetadataAdapter("Reactor");
        resource.eAdapters().add(resourceMetadataAdapter);
        editingContext.getDomain().getResourceSet().getResources().add(resource);

        var projectReactor = this.projectReactor();
        resource.getContents().add(projectReactor);
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
        reactorCore.getPackages().add(new ReactorFactory().reactor());

        return reactorCore;
    }



    @Override
    public void link(IEObjectIndexer eObjectIndexer) {
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
