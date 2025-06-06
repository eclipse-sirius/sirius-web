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
package org.eclipse.sirius.components.interpreter;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.runtime.EvaluationResult;
import org.eclipse.acceleo.query.runtime.ICompletionResult;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.IQueryCompletionEngine;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IQueryEvaluationEngine;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.QueryCompletion;
import org.eclipse.acceleo.query.runtime.QueryEvaluation;
import org.eclipse.acceleo.query.runtime.QueryParsing;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.runtime.impl.EPackageProvider;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An AQL interpreter used for tests.
 *
 * @author sbegaudeau
 */
public class AQLInterpreter {

    /**
     * The prefix used by AQL expressions.
     */
    private static final String AQL_PREFIX = "aql:";

    private final Logger logger = LoggerFactory.getLogger(AQLInterpreter.class);

    /**
     * The cache of the expressions parsed.
     */
    private LoadingCache<String, AstResult> parsedExpressions;

    /**
     * The query environment.
     */
    private IQueryEnvironment queryEnvironment;

    /**
     * The constructor.
     *
     * @param classes
     *            classes for java service that can called by AQLInterpreter
     * @param ePackages
     *            Additional meta-models. A typical use case will be to register semantic meta-models so that reference
     *            to classes, such as {@code <semanticMM>::<AClass>}, can be interpreted.
     */
    public AQLInterpreter(List<Class<?>> classes, List<EPackage> ePackages) {
        this(classes, List.of(), ePackages);
    }

    /**
     * The constructor.
     *
     * @param classes
     *            classes for java service that can called by AQLInterpreter
     * @param instances
     *            instances for java service that can called by AQLInterpreter
     * @param ePackages
     *            Additional meta-models. A typical use case will be to register semantic meta-models so that reference
     *            to classes, such as {@code <semanticMM>::<AClass>}, can be interpreted.
     */
    public AQLInterpreter(List<Class<?>> classes, List<Object> instances, List<EPackage> ePackages) {
        this.queryEnvironment = Query.newEnvironmentWithDefaultServices(new SimpleCrossReferenceProvider());
        this.queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
        this.queryEnvironment.registerCustomClassMapping(EcorePackage.eINSTANCE.getEStringToStringMapEntry(), EStringToStringMapEntryImpl.class);

        for (Class<?> aClass : classes) {
            var services = ServiceUtils.getServices(this.queryEnvironment, aClass);
            ServiceUtils.registerServices(this.queryEnvironment, services);
        }
        for (Object instance : instances) {
            var services = ServiceUtils.getServices(this.queryEnvironment, instance);
            ServiceUtils.registerServices(this.queryEnvironment, services);
        }

        ePackages.stream().filter(this::isValidEPackage).forEach(ePackage -> {
            String nsURI = ePackage.getNsURI();
            long start = System.nanoTime();
            boolean cacheHit = this.registerEPackage(ePackage);

            Duration duration = Duration.ofNanos(System.nanoTime() - start);
            this.logger.atDebug()
                .setMessage("Registered {} in {}ms ({})")
                .addArgument(nsURI)
                .addArgument(duration.toMillis())
                .addArgument(() -> {
                    if (cacheHit) {
                        return "cache hit";
                    }
                    return "cache miss";
                })
                .log();
        });

        this.initExpressionsCache();
    }

    private boolean registerEPackage(EPackage ePackage) {
        boolean cacheHit = false;

        if (this.queryEnvironment.getEPackageProvider() instanceof EPackageProvider ePackageProvider) {
            EPackage registeredEPackage = ePackageProvider.registerPackage(ePackage);
            if (registeredEPackage != null) {
                var optionalCachedServices = ePackage.eAdapters().stream()
                        .filter(AQLServicesAdapter.class::isInstance)
                        .map(AQLServicesAdapter.class::cast)
                        .map(AQLServicesAdapter::getServices)
                        .findFirst();
                if (optionalCachedServices.isPresent()) {
                    cacheHit = true;
                    ServiceUtils.registerServices(this.queryEnvironment, optionalCachedServices.get());
                } else {
                    var services = ServiceUtils.getServices(registeredEPackage);
                    var cache = new AQLServicesAdapter(services);
                    registeredEPackage.eAdapters().add(cache);
                    ServiceUtils.registerServices(this.queryEnvironment, services);
                }
            }
        }

        return cacheHit;
    }

    private boolean isValidEPackage(EPackage ePackage) {
        return ePackage != null && ePackage.getName() != null && ePackage.getNsURI() != null;
    }

    /**
     * Initializes the cache of the expressions.
     */
    private void initExpressionsCache() {
        IQueryBuilderEngine builder = QueryParsing.newBuilder();
        int maxCacheSize = 500;
        this.parsedExpressions = CacheBuilder.newBuilder().maximumSize(maxCacheSize).build(CacheLoader.from(builder::build));
    }

    public Result evaluateExpression(Map<String, Object> variables, String expressionBody) {
        String expression = new ExpressionConverter().convertExpression(expressionBody);
        if (expression.startsWith(AQL_PREFIX)) {
            expression = expression.substring(AQL_PREFIX.length());
        }

        try {
            long start = System.currentTimeMillis();

            AstResult build = this.parsedExpressions.get(expression);
            IQueryEvaluationEngine evaluationEngine = QueryEvaluation.newEngine(this.queryEnvironment);
            EvaluationResult evalResult = evaluationEngine.eval(build, variables);

            BasicDiagnostic diagnostic = new BasicDiagnostic();
            if (Diagnostic.OK != build.getDiagnostic().getSeverity()) {
                diagnostic.merge(build.getDiagnostic());
            }
            if (Diagnostic.OK != evalResult.getDiagnostic().getSeverity()) {
                diagnostic.merge(evalResult.getDiagnostic());
            }

            this.log(expressionBody, diagnostic);

            long end = System.currentTimeMillis();
            if (end - start > 200) {
                this.logger.atDebug()
                        .setMessage("{}ms to execute the expression {}")
                        .addArgument(end - start)
                        .addArgument(expressionBody)
                        .log();
            }

            return new Result(Optional.ofNullable(evalResult.getResult()), Status.getStatus(diagnostic.getSeverity()));
        } catch (ExecutionException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return new Result(Optional.empty(), Status.ERROR);
    }

    private void log(String expression, Diagnostic diagnostic) {
        if (diagnostic.getMessage() != null) {
            if (Diagnostic.INFO == diagnostic.getSeverity()) {
                this.logger.info("An info has occurred with the expression '{}': {}", expression, diagnostic.getMessage());
            } else if (Diagnostic.WARNING == diagnostic.getSeverity()) {
                this.logger.warn("A warning has occurred with the expression '{}': {}", expression, diagnostic.getMessage());
            } else if (Diagnostic.ERROR == diagnostic.getSeverity() || Diagnostic.CANCEL == diagnostic.getSeverity()) {
                this.logger.warn("An error has occurred with the expression '{}': {}", expression, diagnostic.getMessage());
            }
        }

        diagnostic.getChildren().forEach(childDiagnostic -> this.log(expression, childDiagnostic));
    }

    public ICompletionResult getProposals(String expression, int offset) {
        IQueryCompletionEngine engine = QueryCompletion.newEngine(this.queryEnvironment);
        Map<String, Set<IType>> variableTypes = new LinkedHashMap<>();
        final Set<IType> potentialTypes = new LinkedHashSet<>(1);
        potentialTypes.add(new EClassifierType(this.queryEnvironment, EcorePackage.Literals.EOBJECT));
        variableTypes.put("self", potentialTypes);

        return engine.getCompletion(expression, offset, variableTypes);
    }
}
