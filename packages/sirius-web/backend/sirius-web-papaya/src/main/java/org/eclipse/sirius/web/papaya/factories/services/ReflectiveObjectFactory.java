/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.sirius.components.papaya.Package;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.Type;
import org.eclipse.sirius.web.papaya.factories.services.api.IReflectiveObjectFactory;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

/**
 * Used to scan the classloader and create the relevant objects.
 *
 * @author sbegaudeau
 */
public class ReflectiveObjectFactory implements IReflectiveObjectFactory {
    @Override
    public void createTypes(Package papayaPackage, Predicate<String> packageFilter) {
        var configuration = new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(papayaPackage.getQualifiedName()))
                .setScanners(Scanners.TypesAnnotated, Scanners.SubTypes.filterResultsBy(name -> true));
        var reflections = new Reflections(configuration);
        var javaClasses = Stream.concat(
                    reflections.getSubTypesOf(Object.class).stream(),
                    reflections.getSubTypesOf(Record.class).stream()
                )
                .filter(javaClass -> javaClass.getPackageName().startsWith(papayaPackage.getQualifiedName()))
                .filter(javaClass -> packageFilter.test(javaClass.getPackageName()))
                .filter(javaClass -> javaClass.getEnclosingClass() == null)
                .sorted(Comparator.comparing(Class::getName))
                .toList();

        for (var javaClass: javaClasses) {
            var papayaContainingPackage = this.getOrCreateContainingPackage(papayaPackage, javaClass.getPackageName());
            if (papayaContainingPackage != null) {
                var optionalExistingPapayaType = papayaContainingPackage.getTypes().stream()
                        .filter(type -> type.getQualifiedName().equals(javaClass.getName()))
                        .findFirst();
                if (optionalExistingPapayaType.isEmpty()) {
                    this.create(javaClass).ifPresent(papayaContainingPackage.getTypes()::add);
                }
            }
        }

        papayaPackage.getPackages().forEach(subPackage -> this.createTypes(subPackage, packageFilter));
    }

    private Package getOrCreateContainingPackage(Package papayaPackage, String packageName) {
        // If it already exists, let's find it
        Package containingPackage = Stream.concat(Stream.of(papayaPackage), this.getAllOwnedPackages(papayaPackage).stream())
                .filter(existingPapayaPackage -> existingPapayaPackage.getQualifiedName().equals(packageName))
                .findFirst()
                .orElse(null);

        if (containingPackage == null) {
            // If it doesn't exist, let's find its parent, create it and return it
            var segments = Arrays.asList(packageName.split("\\."));
            if (segments.size() >= 2) {
                var parentPackageName = String.join(".", segments.subList(0, segments.size() - 1));
                var parentContainingPackage = this.getOrCreateContainingPackage(papayaPackage, parentPackageName);
                if (parentContainingPackage != null) {
                    containingPackage = PapayaFactory.eINSTANCE.createPackage();
                    containingPackage.setName(segments.get(segments.size() - 1));
                    parentContainingPackage.getPackages().add(containingPackage);
                }
            }
        }

        return containingPackage;
    }

    private List<Package> getAllOwnedPackages(Package papayaPackage) {
        List<Package> allOwnedPackages = new ArrayList<>();

        papayaPackage.getPackages().forEach(childPackage -> {
            allOwnedPackages.add(childPackage);
            allOwnedPackages.addAll(this.getAllOwnedPackages(childPackage));
        });

        return allOwnedPackages;
    }

    private Optional<Type> create(Class<?> javaClass) {
        Optional<Type> optionalType = Optional.empty();

        if (javaClass.isAnnotation()) {
            optionalType = Optional.of(PapayaFactory.eINSTANCE.createAnnotation());
        } else if (javaClass.isEnum()) {
            optionalType = Optional.of(PapayaFactory.eINSTANCE.createEnum());
        } else if (javaClass.isRecord()) {
            optionalType = Optional.of(PapayaFactory.eINSTANCE.createRecord());
        } else if (javaClass.isInterface()) {
            optionalType = Optional.of(PapayaFactory.eINSTANCE.createInterface());
        } else {
            optionalType = Optional.of(PapayaFactory.eINSTANCE.createClass());
        }

        optionalType.ifPresent(type -> {
            type.setName(javaClass.getSimpleName());

            Arrays.stream(javaClass.getDeclaredClasses())
                    .flatMap(nestedJavaClass -> this.create(nestedJavaClass).stream())
                    .forEach(type.getTypes()::add);
        });

        return optionalType;
    }
}
