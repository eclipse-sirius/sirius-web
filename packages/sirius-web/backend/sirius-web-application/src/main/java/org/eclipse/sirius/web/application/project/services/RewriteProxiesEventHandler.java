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
package org.eclipse.sirius.web.application.project.services;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.project.services.api.IRewriteProxiesResourceFilter;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * The event handler to rewrite broken proxy URIs in documents (typically after an upload where the newly created
 * documents have different ids).
 *
 * @author pcdavid
 */
@Service
public class RewriteProxiesEventHandler implements IEditingContextEventHandler {

    private final IMessageService messageService;

    private final List<IRewriteProxiesResourceFilter> rewriteProxiesResourceFilter;

    public RewriteProxiesEventHandler(IMessageService messageService, List<IRewriteProxiesResourceFilter> rewriteProxiesResourceFilter) {
        this.messageService = Objects.requireNonNull(messageService);
        this.rewriteProxiesResourceFilter = Objects.requireNonNull(rewriteProxiesResourceFilter);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof RewriteProxiesInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        IPayload payload = new ErrorPayload(input.id(), this.messageService.unexpectedError());
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        if (input instanceof RewriteProxiesInput rewriteInput && editingContext instanceof IEMFEditingContext emfEditingContext) {
            AdapterFactoryEditingDomain adapterFactoryEditingDomain = emfEditingContext.getDomain();
            int totalRewrittenCount = 0;
            var resources = adapterFactoryEditingDomain.getResourceSet().getResources().stream()
                    .filter(r -> this.rewriteProxiesResourceFilter.stream().allMatch(f -> f.shouldRewriteProxies(r)))
                    .toList();
            for (Resource resource : resources) {
                totalRewrittenCount += this.rewriteProxyURIs(resource, rewriteInput.oldDocumentIdToNewDocumentId());
            }
            if (totalRewrittenCount > 0) {
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input);
            }
            payload = new SuccessPayload(input.id());
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private int rewriteProxyURIs(Resource resource, Map<String, String> oldDocumentIdToNewDocumentId) {
        AtomicInteger rewrittenCount = new AtomicInteger();
        resource.getAllContents().forEachRemaining(eObject -> {
            eObject.eCrossReferences().forEach(target -> {
                InternalEObject internalEObject = (InternalEObject) target;
                if (internalEObject != null && internalEObject.eIsProxy()) {
                    URI proxyURI = internalEObject.eProxyURI();
                    String oldDocumentId = proxyURI.path().substring(1);
                    String newDocumentId = oldDocumentIdToNewDocumentId.get(oldDocumentId);
                    if (newDocumentId != null) {
                        String prefix = IEMFEditingContext.RESOURCE_SCHEME + ":///";
                        URI newProxyURI = URI.createURI(proxyURI.toString().replace(prefix + oldDocumentId, prefix + newDocumentId));
                        internalEObject.eSetProxyURI(newProxyURI);
                        rewrittenCount.incrementAndGet();
                    }
                }
            });
        });
        return rewrittenCount.get();
    }

}
