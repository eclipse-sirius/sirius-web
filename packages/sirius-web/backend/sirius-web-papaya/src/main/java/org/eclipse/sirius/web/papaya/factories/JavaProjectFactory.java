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
import org.eclipse.sirius.components.papaya.Type;
import org.eclipse.sirius.web.papaya.factories.api.IEObjectIndexer;
import org.eclipse.sirius.web.papaya.factories.api.IObjectFactory;

/**
 * Used to create the Java project.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class JavaProjectFactory implements IObjectFactory {
    @Override
    public void create(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var documentId = UUID.randomUUID();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
        var resourceMetadataAdapter = new ResourceMetadataAdapter("Java");
        resource.eAdapters().add(resourceMetadataAdapter);
        editingContext.getDomain().getResourceSet().getResources().add(resource);

        var java = this.java();
        resource.getContents().add(java);
        eObjectIndexer.index(java);
    }

    private Project java() {
        var java = PapayaFactory.eINSTANCE.createProject();
        java.setName("Java Standard Library");
        java.getComponents().add(this.javaBase());

        return java;
    }

    private Component javaBase() {
        var javaBase = PapayaFactory.eINSTANCE.createComponent();
        javaBase.setName("java.base");
        javaBase.getPackages().addAll(List.of(
                this.javaLang(),
                this.javaIo(),
                this.javaText(),
                this.javaTime(),
                this.javaUtil()
        ));

        return javaBase;
    }

    private Package javaLang() {
        var javaLang = PapayaFactory.eINSTANCE.createPackage();
        javaLang.setName("java.lang");
        javaLang.getTypes().addAll(this.javaLangPrimitiveTypes());
        javaLang.getTypes().addAll(this.javaLangObjectTypes());

        return javaLang;
    }

    private List<Type> javaLangPrimitiveTypes() {
        var voidDataType = PapayaFactory.eINSTANCE.createDataType();
        voidDataType.setName("void");

        var byteDataType = PapayaFactory.eINSTANCE.createDataType();
        byteDataType.setName("byte");

        var shortDataType = PapayaFactory.eINSTANCE.createDataType();
        shortDataType.setName("short");

        var intDataType = PapayaFactory.eINSTANCE.createDataType();
        intDataType.setName("int");

        var longDataType = PapayaFactory.eINSTANCE.createDataType();
        longDataType.setName("long");

        var floatDataType = PapayaFactory.eINSTANCE.createDataType();
        floatDataType.setName("float");

        var doubleDataType = PapayaFactory.eINSTANCE.createDataType();
        doubleDataType.setName("double");

        var booleanDataType = PapayaFactory.eINSTANCE.createDataType();
        booleanDataType.setName("boolean");

        var charDataType = PapayaFactory.eINSTANCE.createDataType();
        charDataType.setName("char");

        return List.of(voidDataType, byteDataType, shortDataType, intDataType, longDataType, floatDataType, doubleDataType, booleanDataType, charDataType);
    }

    private List<Type> javaLangObjectTypes() {
        var objectClass = PapayaFactory.eINSTANCE.createClass();
        objectClass.setName("Object");

        var stringClass = PapayaFactory.eINSTANCE.createClass();
        stringClass.setName("String");

        var voidClass = PapayaFactory.eINSTANCE.createClass();
        voidClass.setName("Void");

        var byteClass = PapayaFactory.eINSTANCE.createClass();
        byteClass.setName("Byte");

        var shortClass = PapayaFactory.eINSTANCE.createClass();
        shortClass.setName("Short");

        var integerClass = PapayaFactory.eINSTANCE.createClass();
        integerClass.setName("Integer");

        var longClass = PapayaFactory.eINSTANCE.createClass();
        longClass.setName("Long");

        var floatClass = PapayaFactory.eINSTANCE.createClass();
        floatClass.setName("Float");

        var doubleClass = PapayaFactory.eINSTANCE.createClass();
        doubleClass.setName("Double");

        var booleanClass = PapayaFactory.eINSTANCE.createClass();
        booleanClass.setName("Boolean");

        var charClass = PapayaFactory.eINSTANCE.createClass();
        charClass.setName("Char");

        var autoCloseableInterface = PapayaFactory.eINSTANCE.createInterface();
        autoCloseableInterface.setName("AutoCloseable");

        var cloneableInterface = PapayaFactory.eINSTANCE.createInterface();
        cloneableInterface.setName("Cloneable");

        var comparableTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        comparableTTypeParameter.setName("T");
        var comparableInterface = PapayaFactory.eINSTANCE.createInterface();
        comparableInterface.setName("Comparable");
        comparableInterface.getTypeParameters().add(comparableTTypeParameter);

        return List.of(objectClass, stringClass, voidClass, byteClass, shortClass, integerClass, longClass, floatClass, doubleClass, booleanClass, charClass, autoCloseableInterface, cloneableInterface, comparableInterface);
    }

    private Package javaIo() {
        var serializableInterface = PapayaFactory.eINSTANCE.createInterface();
        serializableInterface.setName("Serializable");

        var closeableInterface = PapayaFactory.eINSTANCE.createInterface();
        closeableInterface.setName("Closeable");

        var flushableInterface = PapayaFactory.eINSTANCE.createInterface();
        flushableInterface.setName("Flushable");

        var inputStreamClass = PapayaFactory.eINSTANCE.createClass();
        inputStreamClass.setName("InputStream");
        inputStreamClass.setAbstract(true);

        var outputStreamClass = PapayaFactory.eINSTANCE.createClass();
        outputStreamClass.setName("OutputStream");
        outputStreamClass.setAbstract(true);

        var byteArrayInputStreamClass = PapayaFactory.eINSTANCE.createClass();
        byteArrayInputStreamClass.setName("ByteArrayInputStream");

        var byteArrayOutputStreamClass = PapayaFactory.eINSTANCE.createClass();
        byteArrayOutputStreamClass.setName("ByteArrayOutputStream");

        var javaIoTypes = List.of(serializableInterface, closeableInterface, flushableInterface, inputStreamClass, outputStreamClass, byteArrayInputStreamClass, byteArrayOutputStreamClass);

        var javaIo = PapayaFactory.eINSTANCE.createPackage();
        javaIo.setName("java.io");
        javaIo.getTypes().addAll(javaIoTypes);

        return javaIo;
    }

    private Package javaText() {
        var formatClass = PapayaFactory.eINSTANCE.createClass();
        formatClass.setName("Format");
        formatClass.setAbstract(true);

        var messageFormatClass = PapayaFactory.eINSTANCE.createClass();
        messageFormatClass.setName("MessageFormat");


        var javaTextTypes = List.of(formatClass, messageFormatClass);

        var javaText = PapayaFactory.eINSTANCE.createPackage();
        javaText.setName("java.text");
        javaText.getTypes().addAll(javaTextTypes);

        return javaText;
    }

    private Package javaTime() {
        var temporalAccessorInterface = PapayaFactory.eINSTANCE.createInterface();
        temporalAccessorInterface.setName("TemporalAccessor");

        var temporalAdjusterInterface = PapayaFactory.eINSTANCE.createInterface();
        temporalAdjusterInterface.setName("TemporalAdjuster");

        var temporalInterface = PapayaFactory.eINSTANCE.createInterface();
        temporalInterface.setName("Temporal");

        var instantClass = PapayaFactory.eINSTANCE.createClass();
        instantClass.setName("Instant");

        var javaTimeTypes = List.of(temporalAccessorInterface, temporalAdjusterInterface, temporalInterface, instantClass);

        var javaTime = PapayaFactory.eINSTANCE.createPackage();
        javaTime.setName("java.time");
        javaTime.getTypes().addAll(javaTimeTypes);

        return javaTime;
    }

    private Package javaUtil() {
        var uuidClass = PapayaFactory.eINSTANCE.createClass();
        uuidClass.setName("UUID");

        var iterableTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        iterableTTypeParameter.setName("T");
        var iterableInterface = PapayaFactory.eINSTANCE.createInterface();
        iterableInterface.setName("Iterable");
        iterableInterface.getTypeParameters().add(iterableTTypeParameter);

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

        var javaUtilTypes = List.of(uuidClass, iterableInterface, collectionInterface, listInterface, setInterface, mapInterface, optionalClass);
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

    @Override
    public void link(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        // java.io
        var autoCloseableInterface = eObjectIndexer.getInterface("java.lang.AutoCloseable");
        var closeableInterface = eObjectIndexer.getInterface("java.io.Closeable");
        var flushableInterface = eObjectIndexer.getInterface("java.io.Flushable");
        var inputStreamClass = eObjectIndexer.getClass("java.io.InputStream");
        var outputStreamClass = eObjectIndexer.getClass("java.io.OutputStream");
        var byteArrayInputStreamClass = eObjectIndexer.getClass("java.io.ByteArrayInputStream");
        var byteArrayOutputStreamClass = eObjectIndexer.getClass("java.io.ByteArrayOutputStream");

        closeableInterface.getExtends().add(autoCloseableInterface);
        inputStreamClass.getImplements().add(closeableInterface);
        byteArrayInputStreamClass.setExtends(inputStreamClass);
        outputStreamClass.getImplements().addAll(List.of(closeableInterface, flushableInterface));
        byteArrayOutputStreamClass.setExtends(outputStreamClass);

        // java.text
        var serializableInterface = eObjectIndexer.getInterface("java.io.Serializable");
        var cloneableInterface = eObjectIndexer.getInterface("java.lang.Cloneable");
        var formatClass = eObjectIndexer.getClass("java.text.Format");
        var messageFormatClass = eObjectIndexer.getClass("java.text.MessageFormat");

        formatClass.getImplements().addAll(List.of(serializableInterface, cloneableInterface));
        messageFormatClass.setExtends(formatClass);

        // java.time
        var comparableInterface = eObjectIndexer.getInterface("java.lang.Comparable");
        var temporalAdjusterInterface = eObjectIndexer.getInterface("java.time.TemporalAdjuster");
        var temporalAccessorInterface = eObjectIndexer.getInterface("java.time.TemporalAccessor");
        var temporalInterface = eObjectIndexer.getInterface("java.time.Temporal");
        var instantClass = eObjectIndexer.getClass("java.time.Instant");

        temporalInterface.getExtends().add(temporalAccessorInterface);
        instantClass.getImplements().addAll(List.of(temporalInterface, temporalAdjusterInterface, comparableInterface, serializableInterface));

        // java.util
        var iterableInterface = eObjectIndexer.getInterface("java.util.Iterable");
        var collectionInterface = eObjectIndexer.getInterface("java.util.Collection");
        var listInterface = eObjectIndexer.getInterface("java.util.List");
        var setInterface = eObjectIndexer.getInterface("java.util.Set");

        collectionInterface.getExtends().add(iterableInterface);
        listInterface.getExtends().add(collectionInterface);
        setInterface.getExtends().add(collectionInterface);

        var executorInterface = eObjectIndexer.getInterface("java.util.concurrent.Executor");
        var executorServiceInterface = eObjectIndexer.getInterface("java.util.concurrent.ExecutorService");
        var completionStageInterface = eObjectIndexer.getInterface("java.util.concurrent.CompletionStage");
        var futureInterface = eObjectIndexer.getInterface("java.util.concurrent.Future");
        var completableFutureInterface = eObjectIndexer.getInterface("java.util.concurrent.CompletableFuture");

        executorServiceInterface.getExtends().add(executorInterface);
        completableFutureInterface.getExtends().addAll(List.of(futureInterface, completionStageInterface));

        var functionInterface = eObjectIndexer.getInterface("java.util.function.Function");
        var unaryOperatorInterface = eObjectIndexer.getInterface("java.util.function.UnaryOperator");

        unaryOperatorInterface.getExtends().add(functionInterface);

        var baseStreamInterface = eObjectIndexer.getInterface("java.util.stream.BaseStream");
        var streamInterface = eObjectIndexer.getInterface("java.util.stream.Stream");

        streamInterface.getExtends().add(baseStreamInterface);
    }
}
