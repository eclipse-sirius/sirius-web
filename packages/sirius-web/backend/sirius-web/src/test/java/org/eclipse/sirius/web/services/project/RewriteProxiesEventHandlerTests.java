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
package org.eclipse.sirius.web.services.project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import fr.obeo.dsl.designer.sample.flow.DataFlow;
import fr.obeo.dsl.designer.sample.flow.FlowPackage;
import fr.obeo.dsl.designer.sample.flow.FlowTarget;
import fr.obeo.dsl.designer.sample.flow.util.FlowAdapterFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.project.services.RewriteProxiesEventHandler;
import org.eclipse.sirius.web.application.project.services.RewriteProxiesInput;
import org.eclipse.sirius.web.application.project.services.api.IRewriteProxiesResourceFilter;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Tests for {@link RewriteProxiesEventHandler}.
 *
 * @author arichard
 */
public class RewriteProxiesEventHandlerTests {

    private static final String FLOW1_OLD = "8633c890-2b5f-4d03-8dc1-38df596a2030";

    private static final String FLOW2_OLD = "ef81fea6-808e-4976-98e8-b6b366034838";

    private static final String FLOW1_NEW = "49f22f54-ccbc-4581-ad8f-e2eda2862dee";

    private static final String FLOW2_NEW = "c2adc096-32af-4314-816f-2b41c8d3fa91";

    @Test
    void testRewriteProxies() {
        IMessageService messageService = new IMessageService.NoOp();

        List<IRewriteProxiesResourceFilter> rewriteProxiesResourceFilters = new ArrayList<>();
        rewriteProxiesResourceFilters.add(r -> true);

        RewriteProxiesEventHandler handler = new RewriteProxiesEventHandler(messageService, rewriteProxiesResourceFilters);
        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

        IEMFEditingContext editingContext = this.createEditingContext();

        this.createResource(editingContext.getDomain().getResourceSet(), FLOW1_NEW, this.documentFlow1());
        this.createResource(editingContext.getDomain().getResourceSet(), FLOW2_NEW, this.documentFlow2());

        Map<String, String> oldDocumentIdToNewDocumentId = new HashMap<>();
        oldDocumentIdToNewDocumentId.put(FLOW1_OLD, FLOW1_NEW);
        oldDocumentIdToNewDocumentId.put(FLOW2_OLD, FLOW2_NEW);

        RewriteProxiesInput input = new RewriteProxiesInput(UUID.randomUUID(), editingContext.getId(), oldDocumentIdToNewDocumentId);

        assertThat(handler.canHandle(editingContext, input)).isTrue();

        handler.handle(payloadSink, changeDescriptionSink, editingContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(SuccessPayload.class);

        ResourceSet resourceSet = editingContext.getDomain().getResourceSet();
        Resource flow1 = resourceSet.getResource(new JSONResourceFactory().createResourceURI(FLOW1_NEW), false);
        EObject eObject = flow1.getEObject("52e2b37b-4492-4f78-90b2-3607b375b7ce");
        if (eObject instanceof fr.obeo.dsl.designer.sample.flow.System newSystem) {
            EList<DataFlow> outgoingFlows = newSystem.getOutgoingFlows();
            assertFalse(outgoingFlows.isEmpty());
            DataFlow dataFlow = outgoingFlows.get(0);
            FlowTarget target = dataFlow.getTarget();
            assertNotNull(target);
            assertFalse(target.eIsProxy());
        }
    }


    @Test
    void testRewriteProxiesWithFilteredResources() {
        IMessageService messageService = new IMessageService.NoOp();

        List<IRewriteProxiesResourceFilter> rewriteProxiesResourceFilters = new ArrayList<>();
        rewriteProxiesResourceFilters.add(r -> false);

        RewriteProxiesEventHandler handler = new RewriteProxiesEventHandler(messageService, rewriteProxiesResourceFilters);
        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

        IEMFEditingContext editingContext = this.createEditingContext();

        this.createResource(editingContext.getDomain().getResourceSet(), FLOW1_NEW, this.documentFlow1());
        this.createResource(editingContext.getDomain().getResourceSet(), FLOW2_NEW, this.documentFlow2());

        Map<String, String> oldDocumentIdToNewDocumentId = new HashMap<>();
        oldDocumentIdToNewDocumentId.put(FLOW1_OLD, FLOW1_NEW);
        oldDocumentIdToNewDocumentId.put(FLOW2_OLD, FLOW2_NEW);

        RewriteProxiesInput input = new RewriteProxiesInput(UUID.randomUUID(), editingContext.getId(), oldDocumentIdToNewDocumentId);

        assertThat(handler.canHandle(editingContext, input)).isTrue();

        handler.handle(payloadSink, changeDescriptionSink, editingContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.NOTHING);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(SuccessPayload.class);

        ResourceSet resourceSet = editingContext.getDomain().getResourceSet();
        Resource flow1 = resourceSet.getResource(new JSONResourceFactory().createResourceURI(FLOW1_NEW), false);
        EObject eObject = flow1.getEObject("52e2b37b-4492-4f78-90b2-3607b375b7ce");
        if (eObject instanceof fr.obeo.dsl.designer.sample.flow.System newSystem) {
            EList<DataFlow> outgoingFlows = newSystem.getOutgoingFlows();
            assertFalse(outgoingFlows.isEmpty());
            DataFlow dataFlow = outgoingFlows.get(0);
            FlowTarget target = dataFlow.getTarget();
            assertNotNull(target);
            assertTrue(target.eIsProxy());
        }
    }

    private IEMFEditingContext createEditingContext() {
        var editingContextId = UUID.randomUUID().toString();
        AdapterFactoryEditingDomain editingDomain = this.create();

        return new IEMFEditingContext() {
            @Override
            public String getId() {
                return editingContextId;
            }

            @Override
            public AdapterFactoryEditingDomain getDomain() {
                return editingDomain;
            }
        };
    }

    private AdapterFactoryEditingDomain create() {
        ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory();
        composedAdapterFactory.addAdapterFactory(new FlowAdapterFactory());
        composedAdapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

        EPackage.Registry ePackageRegistry = new EPackageRegistryImpl();
        ePackageRegistry.put(FlowPackage.eINSTANCE.getNsURI(), FlowPackage.eINSTANCE);

        AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(composedAdapterFactory, new BasicCommandStack());

        ResourceSet resourceSet = editingDomain.getResourceSet();

        resourceSet.setPackageRegistry(ePackageRegistry);
        resourceSet.eAdapters().add(new ECrossReferenceAdapter());

        return editingDomain;
    }

    private Resource createResource(ResourceSet resourceSet, String documentName, String content) {
        JSONResourceFactory jsonResourceFactory = new JSONResourceFactory();
        JsonResource resource = jsonResourceFactory.createResourceFromPath(documentName);
        resourceSet.getResources().add(resource);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        try {
            resource.load(inputStream, null);
        } catch (IOException e) {
            fail(e);
        }
        return resource;
    }

    private String documentFlow1() {
        return """
            {
              "json": { "version": "1.0", "encoding": "utf-8" },
              "ns": { "flow": "http://www.obeo.fr/dsl/designer/sample/flow" },
              "content": [
                {
                  "id": "52e2b37b-4492-4f78-90b2-3607b375b7ce",
                  "eClass": "flow:System",
                  "data": {
                    "outgoingFlows": [
                      {
                        "id": "23e19e59-012c-4ff3-98f5-f166af4a7c72",
                        "eClass": "flow:DataFlow",
                        "data": {
                          "usage": "low",
                          "target": "flow:CompositeProcessor ef81fea6-808e-4976-98e8-b6b366034838#//@elements.0"
                        }
                      }
                    ],
                    "name": "NewSystem",
                    "elements": [
                      {
                        "id": "936ac74f-3d54-4059-84e5-9f8767d6db3d",
                        "eClass": "flow:CompositeProcessor",
                        "data": { "name": "CompositeProcessor1" }
                      }
                    ]
                  }
                }
              ]
            }
            """;
    }

    private String documentFlow2() {
        return """
            {
              "json": { "version": "1.0", "encoding": "utf-8" },
              "ns": { "flow": "http://www.obeo.fr/dsl/designer/sample/flow" },
              "content": [
                {
                  "id": "55c68677-2d08-4bb8-a694-abeb4469570b",
                  "eClass": "flow:System",
                  "data": {
                    "name": "NewSystem2",
                    "elements": [
                      {
                        "id": "3f8def68-b0a3-43d9-8eb9-a8dce77e6855",
                        "eClass": "flow:CompositeProcessor",
                        "data": {
                          "name": "CompositeProcessor2",
                          "incomingFlows": [
                            "flow:DataFlow 8633c890-2b5f-4d03-8dc1-38df596a2030#//@outgoingFlows.0"
                          ]
                        }
                      }
                    ]
                  }
                }
              ]
            }
            """;
    }
}
