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
package org.eclipse.sirius.web.papaya.factories.services;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.papaya.Annotation;
import org.eclipse.sirius.components.papaya.Class;
import org.eclipse.sirius.components.papaya.DataType;
import org.eclipse.sirius.components.papaya.Enum;
import org.eclipse.sirius.components.papaya.GenericType;
import org.eclipse.sirius.components.papaya.Interface;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.Record;
import org.eclipse.sirius.components.papaya.Type;
import org.eclipse.sirius.components.papaya.Visibility;
import org.eclipse.sirius.web.papaya.factories.services.api.IEObjectIndexer;
import org.eclipse.sirius.web.papaya.factories.services.api.IEObjectInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to initialize objects.
 *
 * @author sbegaudeau
 */
public class EObjectInitializer implements IEObjectInitializer {

    private final IEObjectIndexer eObjectIndexer;

    private final Logger logger = LoggerFactory.getLogger(EObjectInitializer.class);

    public EObjectInitializer(IEObjectIndexer eObjectIndexer) {
        this.eObjectIndexer = Objects.requireNonNull(eObjectIndexer);
    }

    @Override
    public void initialize(ResourceSet resourceSet) {
        var iterator = resourceSet.getAllContents();
        while (iterator.hasNext()) {
            var eObject = iterator.next();
            if (eObject instanceof Type type && !(type instanceof DataType)) {
                this.getClass(type).ifPresent(aClass -> this.initialize(aClass, type));
            }
        }
    }

    private void initialize(java.lang.Class<?> javaClass, Type type) {
        type.setVisibility(this.getVisibility(javaClass.getModifiers()));

        if (type instanceof Class aClass) {
            this.initialize(javaClass, aClass);
        } else if (type instanceof Interface anInterface) {
            this.initialize(javaClass, anInterface);
        } else if (type instanceof Record aRecord) {
            this.initialize(javaClass, aRecord);
        } else if (type instanceof Enum anEnum) {
            this.initialize(javaClass, anEnum);
        } else if (type instanceof Annotation annotation) {
            this.initialize(javaClass, annotation);
        }
    }

    private Visibility getVisibility(int modifiers) {
        Visibility visibility = Visibility.PACKAGE;
        if (Modifier.isPrivate(modifiers)) {
            visibility = Visibility.PRIVATE;
        } else if (Modifier.isProtected(modifiers)) {
            visibility = Visibility.PROTECTED;
        } else if (Modifier.isPublic(modifiers)) {
            visibility = Visibility.PUBLIC;
        }
        return visibility;
    }

    private void initialize(java.lang.Class<?> javaClass, Class aClass) {
        aClass.setAbstract(Modifier.isAbstract(javaClass.getModifiers()));
        aClass.setFinal(Modifier.isFinal(javaClass.getModifiers()));
        aClass.setStatic(Modifier.isStatic(javaClass.getModifiers()));
        aClass.setVisibility(this.getVisibility(javaClass.getModifiers()));

        if (javaClass.getSuperclass() != null) {
            var superClass = this.eObjectIndexer.getClass(javaClass.getSuperclass().getName());
            aClass.setExtends(superClass);
        }

        Arrays.stream(javaClass.getInterfaces())
                .map(javaInterface -> this.eObjectIndexer.getInterface(javaInterface.getName()))
                .filter(Objects::nonNull)
                .forEach(aClass.getImplements()::add);

        Arrays.stream(javaClass.getTypeParameters())
                .map(javaTypeParameter -> {
                    var typeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
                    typeParameter.setName(javaTypeParameter.getName());
                    return typeParameter;
                })
                .forEach(aClass.getTypeParameters()::add);

        Arrays.stream(javaClass.getDeclaredConstructors())
                .filter(javaConstructor -> !javaConstructor.isSynthetic())
                .map(javaConstructor -> {
                    var constructor = PapayaFactory.eINSTANCE.createConstructor();
                    constructor.setVisibility(this.getVisibility(javaConstructor.getModifiers()));

                    Arrays.stream(javaConstructor.getParameters())
                            .map(javaParameter -> {
                                var parameter = PapayaFactory.eINSTANCE.createParameter();
                                parameter.setName(javaParameter.getName());
                                parameter.setType(this.getGenericType(javaParameter.getType()));
                                return parameter;
                            })
                            .forEach(constructor.getParameters()::add);

                    return constructor;
                })
                .forEach(aClass.getConstructors()::add);

        Arrays.stream(javaClass.getDeclaredFields())
                .filter(javaField -> !javaField.isSynthetic())
                .map(javaField -> {
                    var attribute = PapayaFactory.eINSTANCE.createAttribute();
                    attribute.setName(javaField.getName());
                    attribute.setType(this.getGenericType(javaField.getGenericType()));
                    attribute.setVisibility(this.getVisibility(javaField.getModifiers()));
                    attribute.setFinal(Modifier.isFinal(javaField.getModifiers()));
                    attribute.setStatic(Modifier.isStatic(javaField.getModifiers()));
                    return attribute;
                })
                .forEach(aClass.getAttributes()::add);

        Arrays.stream(javaClass.getDeclaredMethods())
                .filter(javaMethod -> !javaMethod.isSynthetic())
                .filter(javaMethod -> !javaMethod.isBridge())
                .map(javaMethod -> {
                    var operation = PapayaFactory.eINSTANCE.createOperation();
                    operation.setName(javaMethod.getName());
                    operation.setType(this.getGenericType(javaMethod.getGenericReturnType()));
                    operation.setVisibility(this.getVisibility(javaMethod.getModifiers()));
                    operation.setAbstract(Modifier.isAbstract(javaMethod.getModifiers()));
                    operation.setFinal(Modifier.isFinal(javaMethod.getModifiers()));
                    operation.setStatic(Modifier.isStatic(javaMethod.getModifiers()));

                    Arrays.stream(javaMethod.getParameters())
                            .map(javaParameter -> {
                                var parameter = PapayaFactory.eINSTANCE.createParameter();
                                parameter.setName(javaParameter.getName());
                                parameter.setType(this.getGenericType(javaParameter.getType()));
                                return parameter;
                            })
                            .forEach(operation.getParameters()::add);

                    return operation;
                })
                .forEach(aClass.getOperations()::add);
    }

    private GenericType getGenericType(java.lang.reflect.Type javaType) {
        var genericType = PapayaFactory.eINSTANCE.createGenericType();

        if (javaType instanceof java.lang.Class<?> javaClass) {
            genericType.setRawType(this.getType(javaClass));
        } else if (javaType instanceof java.lang.reflect.ParameterizedType javaParameterizedType) {
            genericType.setRawType(this.getType(javaParameterizedType));
            Arrays.stream(javaParameterizedType.getActualTypeArguments())
                    .map(this::getGenericType)
                    .forEach(genericType.getTypeArguments()::add);
        }

        return genericType;
    }

    private Type getType(java.lang.reflect.ParameterizedType javaParameterizedType) {
        Type type = null;

        var javaType = javaParameterizedType.getRawType();
        if (javaType instanceof java.lang.Class<?> javaClass) {
            type = this.getType(javaClass);
        }

        return type;
    }

    private Type getType(java.lang.Class<?> javaClass) {
        var type = this.eObjectIndexer.getType(javaClass.getName());
        if (type == null) {
            type = this.eObjectIndexer.getType("java.lang.Object");
        }
        return type;
    }

    private void initialize(java.lang.Class<?> javaClass, Interface anInterface) {
        anInterface.setVisibility(this.getVisibility(javaClass.getModifiers()));

        Arrays.stream(javaClass.getInterfaces())
                .map(javaInterface -> this.eObjectIndexer.getInterface(javaInterface.getName()))
                .filter(Objects::nonNull)
                .forEach(anInterface.getExtends()::add);

        Arrays.stream(javaClass.getTypeParameters())
                .map(javaTypeParameter -> {
                    var typeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
                    typeParameter.setName(javaTypeParameter.getName());
                    return typeParameter;
                })
                .forEach(anInterface.getTypeParameters()::add);

        Arrays.stream(javaClass.getDeclaredMethods())
                .filter(javaMethod -> !javaMethod.isSynthetic())
                .filter(javaMethod -> !javaMethod.isBridge())
                .map(javaMethod -> {
                    var operation = PapayaFactory.eINSTANCE.createOperation();
                    operation.setName(javaMethod.getName());
                    operation.setType(this.getGenericType(javaMethod.getGenericReturnType()));

                    Arrays.stream(javaMethod.getParameters())
                            .map(javaParameter -> {
                                var parameter = PapayaFactory.eINSTANCE.createParameter();
                                parameter.setName(javaParameter.getName());
                                parameter.setType(this.getGenericType(javaParameter.getType()));
                                return parameter;
                            })
                            .forEach(operation.getParameters()::add);

                    return operation;
                })
                .forEach(anInterface.getOperations()::add);
    }

    private void initialize(java.lang.Class<?> javaClass, Record aRecord) {
        aRecord.setVisibility(this.getVisibility(javaClass.getModifiers()));

        Arrays.stream(javaClass.getInterfaces())
                .map(javaInterface -> this.eObjectIndexer.getInterface(javaInterface.getName()))
                .filter(Objects::nonNull)
                .forEach(aRecord.getImplements()::add);

        Arrays.stream(javaClass.getTypeParameters())
                .map(javaTypeParameter -> {
                    var typeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
                    typeParameter.setName(javaTypeParameter.getName());
                    return typeParameter;
                })
                .forEach(aRecord.getTypeParameters()::add);

        Arrays.stream(javaClass.getDeclaredFields())
                .filter(javaField -> !javaField.isSynthetic())
                .map(javaField -> {
                    var recordComponent = PapayaFactory.eINSTANCE.createRecordComponent();
                    recordComponent.setName(javaField.getName());
                    recordComponent.setType(this.getGenericType(javaField.getGenericType()));
                    return recordComponent;
                })
                .forEach(aRecord.getComponents()::add);
    }

    private void initialize(java.lang.Class<?> javaClass, Enum anEnum) {
        anEnum.setVisibility(this.getVisibility(javaClass.getModifiers()));

        Arrays.stream(javaClass.getDeclaredFields())
                .filter(javaField -> !javaField.isSynthetic())
                .map(javaField -> {
                    var enumLiteral = PapayaFactory.eINSTANCE.createEnumLiteral();
                    enumLiteral.setName(javaField.getName());
                    return enumLiteral;
                })
                .forEach(anEnum.getLiterals()::add);
    }

    private void initialize(java.lang.Class<?> javaClass, Annotation annotation) {
        annotation.setVisibility(this.getVisibility(javaClass.getModifiers()));

        Arrays.stream(javaClass.getDeclaredFields())
                .filter(javaField -> !javaField.isSynthetic())
                .map(javaField -> {
                    var annotationField = PapayaFactory.eINSTANCE.createAnnotationField();
                    annotationField.setName(javaField.getName());
                    return annotationField;
                })
                .forEach(annotation.getFields()::add);
    }
    
    private Optional<java.lang.Class<?>> getClass(Type type) {
        Optional<java.lang.Class<?>> optionalJavaClass = Optional.empty();

        try {
            optionalJavaClass = Optional.of(java.lang.Class.forName(type.getQualifiedName()));
        } catch (ClassNotFoundException exception) {
            this.logger.warn(exception.getMessage());
        }

        return optionalJavaClass;
    }
}
