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
package org.eclipse.sirius.web.application.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.graphql.api.IExceptionWrapper;
import org.eclipse.sirius.web.application.dto.PageInfoWithCount;
import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.starter.ExceptionWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import graphql.relay.DefaultConnection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Used to test the exception wrapper.
 *
 * @author sbegaudeau
 */
public class ExceptionWrapperTests {

    private final IExceptionWrapper exceptionWrapper = new ExceptionWrapper();

    @Test
    @DisplayName("Given a basic exception wrapper, when supplier are wrapped, then the supplier is really executed")
    public void givenBasicExceptionWrapperWhenWrappedThenSupplierExecuted() {
        var input = new CreateProjectInput(UUID.randomUUID(), "New Project", List.of());

        var payload = this.exceptionWrapper.wrap(() -> new SuccessPayload(input.id()), input);
        assertThat(payload.id()).isEqualTo(input.id());

        var list = this.exceptionWrapper.wrapList(List::of);
        assertThat(list).isEmpty();

        var optional = this.exceptionWrapper.wrapOptional(Optional::empty);
        assertThat(optional).isEmpty();

        var connection = this.exceptionWrapper.wrapConnection(() -> new DefaultConnection<>(List.of(), new PageInfoWithCount(null, null, false, false, 0)));
        assertThat(connection.getPageInfo().isHasPreviousPage()).isFalse();
        assertThat(connection.getPageInfo().isHasNextPage()).isFalse();

        var mono = this.exceptionWrapper.wrapMono(Mono::empty, input);
        StepVerifier.create(mono).expectComplete().verify(Duration.ofSeconds(5));

        var flux = this.exceptionWrapper.wrapFlux(Flux::empty, input);
        StepVerifier.create(flux).expectComplete().verify(Duration.ofSeconds(5));
    }
}
