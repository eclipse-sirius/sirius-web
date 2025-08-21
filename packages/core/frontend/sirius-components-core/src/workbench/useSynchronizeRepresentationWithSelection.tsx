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
import { useEffect } from 'react';
import { useRepresentationMetadata } from '../representationmetadata/useRepresentationMetadata';
import { GQLRepresentationMetadata } from '../representationmetadata/useRepresentationMetadata.types';
import { useSelection } from '../selection/useSelection';
import { UseSynchronizeRepresentationWithSelectionValue } from './useSynchronizeRepresentationWithSelection.types';

export const useSynchronizeRepresentationWithSelection = (
  editingContextId: string
): UseSynchronizeRepresentationWithSelectionValue => {
  const { selection } = useSelection();
  const { getRepresentationMetadata, data } = useRepresentationMetadata();

  useEffect(() => {
    getRepresentationMetadata(
      editingContextId,
      selection.entries.map((entry) => entry.id)
    );
  }, [editingContextId, selection]);

  const representationMetadata: GQLRepresentationMetadata[] | null =
    data?.viewer.editingContext.representations.edges.map((edge) => edge.node) ?? null;

  return { representationMetadata };
};
