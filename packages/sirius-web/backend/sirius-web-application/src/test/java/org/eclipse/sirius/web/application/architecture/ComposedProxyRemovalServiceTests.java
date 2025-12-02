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
package org.eclipse.sirius.web.application.architecture;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.ComposedProxyRemovalService;
import org.eclipse.sirius.web.application.editingcontext.services.api.IDefaultProxyRemovalService;
import org.eclipse.sirius.web.application.editingcontext.services.api.IProxyRemovalServiceDelegate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the default {@link org.eclipse.sirius.web.application.editingcontext.services.api.IProxyRemovalService} implementation.
 *
 * @author gdaniel
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ComposedProxyRemovalServiceTests {

    @Test
    @DisplayName("Given a proxy removal delegate, when proxies are removed, then the delegate is used")
    public void givenProxyRemovalDelegateWhenProxiesAreRemovedThenTheDelegateIsUsed() {
        IProxyRemovalServiceDelegate proxyRemovalServiceDelegate = new IProxyRemovalServiceDelegate.NoOp() {
            @Override
            public boolean canHandle(IEditingContext editingContext) {
                return true;
            }

            @Override
            public Map<EObject, Collection<EStructuralFeature.Setting>> removeUnresolvedProxies(IEditingContext editingContext) {
                EAnnotation eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
                eAnnotation.setSource("testObject");
                return Map.of(eAnnotation, List.of());
            }
        };

        IDefaultProxyRemovalService defaultProxyRemovalService = new IDefaultProxyRemovalService.NoOp();

        ComposedProxyRemovalService composedProxyRemovalService = new ComposedProxyRemovalService(List.of(proxyRemovalServiceDelegate), defaultProxyRemovalService);

        var result = composedProxyRemovalService.removeUnresolvedProxies(new IEditingContext.NoOp());
        assertThat(result).isNotNull();
        assertThat(result.keySet()).anySatisfy(key -> {
            assertThat(key).isInstanceOf(EAnnotation.class);
            EAnnotation eAnnotation = (EAnnotation) key;
            assertThat(eAnnotation.getSource()).isEqualTo("testObject");
        });
    }

    @Test
    @DisplayName("Given a proxy removal delegate that is not relevant, when proxies are removed, then the default one is used")
    public void givenProxyRemovalDelegateThatIsNotRelevantWhenProxiesAreRemovedThenTheDefaultOneIsUsed() {
        IProxyRemovalServiceDelegate proxyRemovalServiceDelegate = new IProxyRemovalServiceDelegate.NoOp() {

            @Override
            public boolean canHandle(IEditingContext editingContext) {
                return false;
            }
        };

        IDefaultProxyRemovalService defaultProxyRemovalService = new IDefaultProxyRemovalService.NoOp() {

            @Override
            public Map<EObject, Collection<EStructuralFeature.Setting>> removeUnresolvedProxies(IEditingContext editingContext) {
                EAnnotation eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
                eAnnotation.setSource("testObject");
                return Map.of(eAnnotation, List.of());
            }
        };

        ComposedProxyRemovalService composedProxyRemovalService = new ComposedProxyRemovalService(List.of(proxyRemovalServiceDelegate), defaultProxyRemovalService);
        var result = composedProxyRemovalService.removeUnresolvedProxies(new IEditingContext.NoOp());
        assertThat(result).isNotNull();
        assertThat(result.keySet()).anySatisfy(key -> {
            assertThat(key).isInstanceOf(EAnnotation.class);
            EAnnotation eAnnotation = (EAnnotation) key;
            assertThat(eAnnotation.getSource()).isEqualTo("testObject");
        });
    }
}
