/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.sample.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.core.api.variables.IOperationProvider;
import org.eclipse.sirius.components.core.api.variables.IVariableProvider;
import org.eclipse.sirius.components.core.api.variables.Operation;
import org.eclipse.sirius.components.core.api.variables.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * Used to generate the documentation of the variables available for each operation.
 *
 * @author sbegaudeau
 */
@Service
public class VariableDocumentationGenerator implements CommandLineRunner {

    private final List<IOperationProvider> operationProviders;

    private final List<IVariableProvider> variableProviders;

    private final Logger logger = LoggerFactory.getLogger(VariableDocumentationGenerator.class);

    public VariableDocumentationGenerator(List<IOperationProvider> operationProviders, List<IVariableProvider> variableProviders) {
        this.operationProviders = Objects.requireNonNull(operationProviders);
        this.variableProviders = Objects.requireNonNull(variableProviders);
    }

    @Override
    public void run(String... args) throws Exception {
        var documentation = this.operationProviders.stream()
                .map(IOperationProvider::getOperations)
                .flatMap(Collection::stream)
                .map(operation -> {
                    var variables = this.variableProviders.stream()
                            .map(variableProvider -> variableProvider.getVariables(operation.name()))
                            .flatMap(Collection::stream)
                            .toList();
                    return this.toAsciidoc(operation, variables);
                })
                .collect(Collectors.joining(System.lineSeparator()));

        this.logger.trace(documentation);
    }

    private String toAsciidoc(Operation operation, List<Variable> variables) {
        var builder = new StringBuilder();

        builder.append("== " + operation.name() + System.lineSeparator());
        builder.append(System.lineSeparator());
        builder.append(operation.documentation() + System.lineSeparator());
        builder.append(System.lineSeparator());

        variables.forEach(variable -> {
            builder.append("- `" + variable.name() + "`: " + variable.documentation() + System.lineSeparator());
        });

        builder.append(System.lineSeparator());

        return builder.toString();
    }
}
