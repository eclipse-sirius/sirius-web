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
package org.eclipse.sirius.components.emf.migration;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.emf.migration.api.MigrationData;
import org.eclipse.sirius.emfjson.resource.JsonResource;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Specialized BasicExtendedMetaData.
 *
 * @author mcharfadi
 */
public class MigrationService extends BasicExtendedMetaData implements JsonResource.IJsonResourceProcessor {
    private MigrationData documentMigrationData;

    private final List<IMigrationParticipant> migrationParticipants;

    public MigrationService(List<IMigrationParticipant> migrationParticipants) {
        this.migrationParticipants = Objects.requireNonNull(migrationParticipants);
        this.documentMigrationData = new MigrationData("none", "0");
    }

    private boolean isCandidateVersion(IMigrationParticipant migrationParticipant) {
        return migrationParticipant.getVersion().compareTo(this.documentMigrationData.migrationVersion()) > 0;
    }

    public MigrationData getMostRecentParticipantMigrationData() {
        var migrationParticipantsCandidate = migrationParticipants.stream()
                .filter(this::isCandidateVersion)
                .sorted(Comparator.comparing(IMigrationParticipant::getVersion))
                .sorted(Collections.reverseOrder())
                .map(migrationParticipant -> new MigrationData("none", migrationParticipant.getVersion()))
                .findFirst();

        return migrationParticipantsCandidate.orElse(this.documentMigrationData);
    }

    @Override
    public EStructuralFeature getElement(EClass eClass, String namespace, String eStructuralFeatureName) {
        EStructuralFeature structuralFeature = eClass.getEStructuralFeature(eStructuralFeatureName);
        var migrationParticipantsCandidate = migrationParticipants.stream()
                .filter(this::isCandidateVersion)
                .sorted(Comparator.comparing(IMigrationParticipant::getVersion))
                .toList();
        for (IMigrationParticipant migrationParticipant : migrationParticipantsCandidate) {
            EStructuralFeature newStructuralFeature = migrationParticipant.getEStructuralFeature(eClass, eStructuralFeatureName);
            if (newStructuralFeature != null) {
                structuralFeature = newStructuralFeature;
            }
        }
        return structuralFeature;
    }

    @Override
    public EClassifier getType(EPackage ePackage, String typeName) {
        EClassifier eClassifier = ePackage.getEClassifier(typeName);
        var migrationParticipantsCandidate = migrationParticipants.stream()
                .filter(this::isCandidateVersion)
                .sorted(Comparator.comparing(IMigrationParticipant::getVersion))
                .toList();
        for (IMigrationParticipant migrationParticipant : migrationParticipantsCandidate) {
            EClassifier newEClassifier = migrationParticipant.getEClassifier(ePackage, typeName);
            if (newEClassifier != null) {
                eClassifier = newEClassifier;
            }
        }
        return eClassifier;
    }

    @Override
    public EPackage getPackage(String nsURI) {
        EPackage ePackage = super.getPackage(nsURI);
        var migrationParticipantsCandidate = migrationParticipants.stream()
                .filter(this::isCandidateVersion)
                .sorted(Comparator.comparing(IMigrationParticipant::getVersion))
                .toList();
        for (IMigrationParticipant migrationParticipant : migrationParticipantsCandidate) {
            EPackage newEPackage = migrationParticipant.getPackage(nsURI);
            if (newEPackage != null) {
                ePackage = newEPackage;
            }
        }
        return ePackage;
    }

    @Override
    public void preDeserialization(JsonResource resource, JsonObject jsonObject) {
        this.setMigrationDataFromDocumentContent(jsonObject);
        this.setResourceMetadataAdapterMigrationData(resource, this.documentMigrationData);
        var migrationParticipantsCandidates = migrationParticipants.stream()
                .filter(this::isCandidateVersion)
                .sorted(Comparator.comparing(IMigrationParticipant::getVersion))
                .toList();
        for (IMigrationParticipant migrationParticipant : migrationParticipantsCandidates) {
            migrationParticipant.preDeserialization(resource, jsonObject);
            setResourceMetadataAdapterMigrationData(resource, new MigrationData(migrationParticipant.getClass().getSimpleName(), migrationParticipant.getVersion()));
        }
    }

    @Override
    public void postSerialization(JsonResource resource, JsonObject jsonObject) {
        getOptionalResourceMetadataAdapter(resource).ifPresent(resourceMetadataAdapter -> {
            if (resourceMetadataAdapter.getMigrationData() != null) {
                var rootMigration = new Gson().toJsonTree(resourceMetadataAdapter.getMigrationData(), MigrationData.class);
                if (rootMigration != null) {
                    jsonObject.add(MigrationData.JSON_OBJECT_ROOT, rootMigration);
                }
            }
        });
        var migrationParticipantsCandidates = migrationParticipants.stream()
                .filter(this::isCandidateVersion)
                .sorted(Comparator.comparing(IMigrationParticipant::getVersion))
                .toList();
        for (IMigrationParticipant migrationParticipant : migrationParticipantsCandidates) {
            migrationParticipant.postSerialization(resource, jsonObject);
        }
    }

    @Override
    public void postObjectLoading(EObject eObject, JsonObject jsonObject, boolean isTopObject) {
        var migrationParticipantsCandidates = migrationParticipants.stream()
                .filter(this::isCandidateVersion)
                .sorted(Comparator.comparing(IMigrationParticipant::getVersion))
                .toList();
        for (IMigrationParticipant migrationParticipant : migrationParticipantsCandidates) {
            migrationParticipant.postObjectLoading(eObject, jsonObject);
        }
    }

    @Override
    public Object getValue(EObject object, EStructuralFeature feature, Object value) {
        Object returnValue = value;
        var migrationParticipantsCandidates = migrationParticipants.stream()
                .filter(this::isCandidateVersion)
                .sorted(Comparator.comparing(IMigrationParticipant::getVersion))
                .toList();
        for (IMigrationParticipant migrationParticipant : migrationParticipantsCandidates) {
            Object newValue = migrationParticipant.getValue(object, feature, value);
            if (newValue != null) {
                returnValue = newValue;
            }
        }
        return returnValue;
    }

    private Optional<ResourceMetadataAdapter> getOptionalResourceMetadataAdapter(JsonResource resource) {
        return resource.eAdapters().stream()
                .filter(ResourceMetadataAdapter.class::isInstance)
                .map(ResourceMetadataAdapter.class::cast)
                .findFirst();
    }

    private void setResourceMetadataAdapterMigrationData(JsonResource resource, MigrationData migrationData) {
        getOptionalResourceMetadataAdapter(resource).ifPresent(resourceMetadataAdapter -> resourceMetadataAdapter.setMigrationData(migrationData));
    }

    private void setMigrationDataFromDocumentContent(JsonObject jsonObject) {
        var optionalMigrationData = Optional.ofNullable(jsonObject.getAsJsonObject(MigrationData.JSON_OBJECT_ROOT))
                .map(migrationRootElement -> new Gson().fromJson(migrationRootElement, MigrationData.class)).stream().findFirst();
        optionalMigrationData.ifPresent(migrationData -> this.documentMigrationData = migrationData);
    }
}
