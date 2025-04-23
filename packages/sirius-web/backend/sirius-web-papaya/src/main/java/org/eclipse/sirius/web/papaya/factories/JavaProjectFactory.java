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

import java.util.List;
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
 * Used to create the Java project.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class JavaProjectFactory implements IObjectFactory {
    @Override
    public void create(IEMFEditingContext editingContext) {
        var documentId = UUID.randomUUID();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
        var resourceMetadataAdapter = new ResourceMetadataAdapter("Java");
        resource.eAdapters().add(resourceMetadataAdapter);
        editingContext.getDomain().getResourceSet().getResources().add(resource);

        var java = this.java();
        resource.getContents().add(java);
    }

    private Project java() {
        var java = PapayaFactory.eINSTANCE.createProject();
        java.setName("Java Standard Library");
        java.getElements().add(this.javaBase());

        return java;
    }

    private Component javaBase() {
        var javaBase = PapayaFactory.eINSTANCE.createComponent();
        javaBase.setName("java.base");
        javaBase.getPackages().addAll(List.of(
                new JavaLangFactory().javaLang(),
                new JavaIoFactory().javaIo(),
                new JavaTextFactory().javaText(),
                new JavaTimeFactory().javaTime(),
                new JavaUtilFactory().javaUtil()
        ));

        return javaBase;
    }

    @Override
    public void link(IEObjectIndexer eObjectIndexer) {
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
        var temporalAdjusterInterface = eObjectIndexer.getInterface("java.time.temporal.TemporalAdjuster");
        var temporalAccessorInterface = eObjectIndexer.getInterface("java.time.temporal.TemporalAccessor");
        var temporalInterface = eObjectIndexer.getInterface("java.time.temporal.Temporal");
        var instantClass = eObjectIndexer.getClass("java.time.Instant");

        temporalInterface.getExtends().add(temporalAccessorInterface);
        instantClass.getImplements().addAll(List.of(temporalInterface, temporalAdjusterInterface, comparableInterface, serializableInterface));

        // java.util
        var iterableInterface = eObjectIndexer.getInterface("java.lang.Iterable");
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
