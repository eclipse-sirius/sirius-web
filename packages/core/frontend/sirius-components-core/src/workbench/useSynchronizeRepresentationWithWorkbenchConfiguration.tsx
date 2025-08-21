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

import { useEffect, useState } from 'react';
import { useRepresentationMetadata } from '../representationmetadata/useRepresentationMetadata';
import { GQLRepresentationMetadata } from '../representationmetadata/useRepresentationMetadata.types';
import { UseSynchronizeRepresentationWithWorkbenchConfigurationValue } from './useSynchronizeRepresentationWithWorkbenchConfiguration.types';
import { WorkbenchConfiguration } from './Workbench.types';

export const useSynchronizeRepresentationWithWorkbenchConfiguration = (
  editingContextId: string,
  initialWorkbenchConfiguration: WorkbenchConfiguration | null
): UseSynchronizeRepresentationWithWorkbenchConfigurationValue => {
  const [representationIds] = useState<string[] | null>(
    initialWorkbenchConfiguration?.mainPanel?.representationEditors.map((editor) => editor.representationId) ?? null
  );

  const { getRepresentationMetadata, data } = useRepresentationMetadata();

  useEffect(() => {
    if (representationIds) {
      getRepresentationMetadata(editingContextId, representationIds);
    }
  }, [representationIds]);

  const representationMetadata: GQLRepresentationMetadata[] | null =
    data?.viewer.editingContext.representations.edges.map((edge) => edge.node) ?? null;

  return { representationMetadata };
};
