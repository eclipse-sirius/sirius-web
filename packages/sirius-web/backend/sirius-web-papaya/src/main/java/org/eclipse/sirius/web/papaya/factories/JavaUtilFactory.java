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
import org.springframework.stereotype.Service;

/**
 * Used to create the java.util package.
 *
 * @author sbegaudeau
 */
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class JavaUtilFactory {

    public Package javaUtil() {
        var uuidClass = PapayaFactory.eINSTANCE.createClass();
        uuidClass.setName("UUID");

        var collectionETypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        collectionETypeParameter.setName("E");
        var collectionInterface = PapayaFactory.eINSTANCE.createInterface();
        collectionInterface.setName("Collection");
        collectionInterface.getTypeParameters().add(collectionETypeParameter);

        var listETypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        listETypeParameter.setName("E");
        var listInterface = PapayaFactory.eINSTANCE.createInterface();
        listInterface.setName("List");
        listInterface.getTypeParameters().add(listETypeParameter);

        var setETypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        setETypeParameter.setName("E");
        var setInterface = PapayaFactory.eINSTANCE.createInterface();
        setInterface.setName("Set");
        setInterface.getTypeParameters().add(setETypeParameter);

        var mapKTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        mapKTypeParameter.setName("K");
        var mapVTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        mapVTypeParameter.setName("V");
        var mapInterface = PapayaFactory.eINSTANCE.createInterface();
        mapInterface.setName("Map");
        mapInterface.getTypeParameters().addAll(List.of(mapKTypeParameter, mapVTypeParameter));


        var optionalTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        optionalTTypeParameter.setName("T");
        var optionalClass = PapayaFactory.eINSTANCE.createClass();
        optionalClass.setName("Optional");
        optionalClass.getTypeParameters().add(optionalTTypeParameter);

        var javaUtilTypes = List.of(uuidClass, collectionInterface, listInterface, setInterface, mapInterface, optionalClass);
        var javaUtilPackages = List.of(this.javaUtilConcurrent(), this.javaUtilFunction(), this.javaUtilStream());

        var javaUtil = PapayaFactory.eINSTANCE.createPackage();
        javaUtil.setName("java.util");
        javaUtil.getTypes().addAll(javaUtilTypes);
        javaUtil.getPackages().addAll(javaUtilPackages);

        return javaUtil;
    }

    private Package javaUtilConcurrent() {
        var executorInterface = PapayaFactory.eINSTANCE.createInterface();
        executorInterface.setName("Executor");

        var executorServiceInterface = PapayaFactory.eINSTANCE.createInterface();
        executorServiceInterface.setName("ExecutorService");

        var futureTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        futureTTypeParameter.setName("T");
        var futureInterface = PapayaFactory.eINSTANCE.createInterface();
        futureInterface.setName("Future");
        futureInterface.getTypeParameters().add(futureTTypeParameter);

        var completionStageTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        completionStageTTypeParameter.setName("T");
        var completionStageInterface = PapayaFactory.eINSTANCE.createInterface();
        completionStageInterface.setName("CompletionStage");
        completionStageInterface.getTypeParameters().add(completionStageTTypeParameter);

        var completionFutureTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        completionFutureTTypeParameter.setName("T");
        var completableFutureInterface = PapayaFactory.eINSTANCE.createInterface();
        completableFutureInterface.setName("CompletableFuture");
        completableFutureInterface.getTypeParameters().add(completionFutureTTypeParameter);

        var javaUtilConcurrentTypes = List.of(executorInterface, executorServiceInterface, futureInterface, completionStageInterface, completableFutureInterface);

        var javaUtilConcurrent = PapayaFactory.eINSTANCE.createPackage();
        javaUtilConcurrent.setName("concurrent");
        javaUtilConcurrent.getTypes().addAll(javaUtilConcurrentTypes);

        return javaUtilConcurrent;
    }

    private Package javaUtilFunction() {
        var functionTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        functionTTypeParameter.setName("T");
        var functionRTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        functionRTypeParameter.setName("R");
        var functionInterface = PapayaFactory.eINSTANCE.createInterface();
        functionInterface.setName("Function");
        functionInterface.getTypeParameters().addAll(List.of(functionTTypeParameter, functionRTypeParameter));

        var biFunctionTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        biFunctionTTypeParameter.setName("T");
        var biFunctionUTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        biFunctionUTypeParameter.setName("U");
        var biFunctionRTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        biFunctionRTypeParameter.setName("R");
        var biFunctionInterface = PapayaFactory.eINSTANCE.createInterface();
        biFunctionInterface.setName("BiFunction");
        biFunctionInterface.getTypeParameters().addAll(List.of(biFunctionTTypeParameter, biFunctionUTypeParameter, biFunctionRTypeParameter));

        var supplierTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        supplierTTypeParameter.setName("T");
        var supplierInterface = PapayaFactory.eINSTANCE.createInterface();
        supplierInterface.setName("Supplier");
        supplierInterface.getTypeParameters().add(supplierTTypeParameter);

        var consumerTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        consumerTTypeParameter.setName("T");
        var consumerInterface = PapayaFactory.eINSTANCE.createInterface();
        consumerInterface.setName("Consumer");
        consumerInterface.getTypeParameters().add(consumerTTypeParameter);

        var predicateTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        predicateTTypeParameter.setName("T");
        var predicateInterface = PapayaFactory.eINSTANCE.createInterface();
        predicateInterface.setName("Predicate");
        predicateInterface.getTypeParameters().add(predicateTTypeParameter);

        var unaryOperatorTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        unaryOperatorTTypeParameter.setName("T");
        var unaryOperatorInterface = PapayaFactory.eINSTANCE.createInterface();
        unaryOperatorInterface.setName("UnaryOperator");
        unaryOperatorInterface.getTypeParameters().add(unaryOperatorTTypeParameter);

        var javaUtilFunctionTypes = List.of(functionInterface, biFunctionInterface, supplierInterface, consumerInterface, predicateInterface, unaryOperatorInterface);

        var javaUtilFunction = PapayaFactory.eINSTANCE.createPackage();
        javaUtilFunction.setName("function");
        javaUtilFunction.getTypes().addAll(javaUtilFunctionTypes);

        return javaUtilFunction;
    }

    private Package javaUtilStream() {
        var baseStreamTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        baseStreamTTypeParameter.setName("T");
        var baseStreamSTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        baseStreamSTypeParameter.setName("S");
        var baseStreamInterface = PapayaFactory.eINSTANCE.createInterface();
        baseStreamInterface.setName("BaseStream");
        baseStreamInterface.getTypeParameters().addAll(List.of(baseStreamTTypeParameter, baseStreamSTypeParameter));


        var streamTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        streamTTypeParameter.setName("T");
        var streamInterface = PapayaFactory.eINSTANCE.createInterface();
        streamInterface.setName("Stream");
        streamInterface.getTypeParameters().add(streamTTypeParameter);

        var javaUtilStreamTypes = List.of(baseStreamInterface, streamInterface);

        var javaUtilStream = PapayaFactory.eINSTANCE.createPackage();
        javaUtilStream.setName("stream");
        javaUtilStream.getTypes().addAll(javaUtilStreamTypes);

        return javaUtilStream;
    }
}
