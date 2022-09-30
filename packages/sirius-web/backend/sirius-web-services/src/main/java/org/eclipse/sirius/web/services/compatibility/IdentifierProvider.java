/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.web.services.compatibility;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.web.persistence.entities.IdMappingEntity;
import org.eclipse.sirius.web.persistence.repositories.IIdMappingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Provides a unique identifier (in the scope of its VSM) for a Sirius description element.
 *
 * @implNote To avoid exposing implementation details and to reduce the size of the IDs, we do not use the obvious
 *           choice of the URIs of the VSM elements, but generated UUIDs instead. The correspondence between the EMF
 *           URIs and UUIDs is stored in the database, and cached in memory to reduce traffic to the database.
 *
 * @author pcdavid
 */
@Service
public class IdentifierProvider implements IIdentifierProvider {

    private final IIdMappingRepository repository;

    private final Cache<String, IdMappingEntity> idMappingByExternalId;

    private final Cache<String, IdMappingEntity> idMappingById;

    public IdentifierProvider(IIdMappingRepository repository, @Value("${sirius.web.identifierProvider.cacheSize:10000}") int cacheSize) {
        this.repository = Objects.requireNonNull(repository);
        this.idMappingByExternalId = CacheBuilder.newBuilder().maximumSize(cacheSize).build();
        this.idMappingById = CacheBuilder.newBuilder().maximumSize(cacheSize).build();
        this.repository.findAll().forEach(this::cached);
    }

    @Override
    public String getIdentifier(Object element) {
        // @formatter:off
        String vsmElementId = Optional.of(element).filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .map(EcoreUtil::getURI)
                .map(Object::toString)
                .orElse(""); //$NON-NLS-1$

        Optional<IdMappingEntity> optional = this.getOrFetchByExternalId(vsmElementId);
        return optional.orElseGet(() -> this.newIdMapping(vsmElementId))
               .getId()
               .toString();
        // @formatter:on
    }

    @Override
    public Optional<String> findVsmElementId(String id) {
        return this.getOrFetchById(id).map(IdMappingEntity::getExternalId);
    }

    private Optional<IdMappingEntity> getOrFetchByExternalId(String vsmElementId) {
        try {
            Callable<? extends IdMappingEntity> loader = () -> {
                return this.repository.findByExternalId(vsmElementId).orElseThrow(() -> this.loadingError(vsmElementId));
            };
            IdMappingEntity idMapping = this.idMappingByExternalId.get(vsmElementId, loader);
            return Optional.of(this.cached(idMapping));
        } catch (ExecutionException e) {
            // Do not log: it is expected not to find a mapping in the repo the first time.
            return Optional.empty();
        }
    }

    private Optional<IdMappingEntity> getOrFetchById(String id) {
        try {
            Callable<? extends IdMappingEntity> loader = () -> {
                return this.repository.findById(id).orElseThrow(() -> this.loadingError(id.toString()));
            };
            IdMappingEntity idMapping = this.idMappingById.get(id, loader);
            return Optional.of(this.cached(idMapping));
        } catch (ExecutionException e) {
            // Do not log: it is expected not to find a mapping in the repo the first time.
            return Optional.empty();
        }
    }

    private ExecutionException loadingError(String id) {
        return new ExecutionException(id, new NoSuchElementException(id));
    }

    private IdMappingEntity cached(IdMappingEntity idMapping) {
        this.idMappingByExternalId.put(idMapping.getExternalId(), idMapping);
        this.idMappingById.put(idMapping.getId(), idMapping);
        return idMapping;
    }

    private IdMappingEntity newIdMapping(String vsmElementId) {
        IdMappingEntity idMappingEntity = new IdMappingEntity();
        idMappingEntity.setId(UUID.nameUUIDFromBytes(vsmElementId.getBytes()).toString());
        idMappingEntity.setExternalId(vsmElementId);
        idMappingEntity = this.repository.save(idMappingEntity);
        return this.cached(idMappingEntity);
    }

}
